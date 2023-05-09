package erp.workorder.notificator.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import erp.workorder.dto.CustomResponse;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.service.NotificatorWorkflowService;
import erp.workorder.feignClient.service.NotifierEngineService;
import erp.workorder.notificator.dto.NotifierWorkflowRuleResponse;
import erp.workorder.notificator.dto.SendEmailRequest;
import erp.workorder.notificator.entity.ClientNotificationCredential;
import erp.workorder.notificator.entity.EmpDepartmentGroupMapping;
import erp.workorder.notificator.entity.EmployeeEmailInfo;
import erp.workorder.notificator.entity.TemplateVariable;
import erp.workorder.notificator.entity.UserNotificationOptOut;
import erp.workorder.notificator.enums.WorkflowRuleActionTypeEnum;
import erp.workorder.notificator.repository.NotificatorRepository;

@Service
public class NotificatorService {

	@Autowired
	private NotificatorWorkflowService notificatorWorkflowService;
	@Autowired
	private NotifierEngineService notifierEngineService;
	@Autowired
	private NotificatorRepository notificatorRepository;

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	public CustomResponse sendNotification(Integer entityId, Integer eventStateId, Long entityItemId, Integer siteId,
			Integer companyId, Integer moduleId, Map<String, Object> queryVariableValue) {

		try {

			new Thread(new Runnable() {
				@Override
				public void run() {

					// fetch workflow rule
					LOGGER.info("Calling NotificatorWorkflowService.");
					CustomResponse workflowRuleObject = notificatorWorkflowService
							.getWorkflowRuleByEntityIdAndStateId(entityId, eventStateId, companyId, siteId);

					if (workflowRuleObject == null || workflowRuleObject.getData() == null)
						LOGGER.info("Workflow rule not found for entity id : " + entityId + " and event state id : "
								+ eventStateId + ".");

					ObjectMapper mapper = new ObjectMapper();
					mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					List<NotifierWorkflowRuleResponse> workflowRuleList = mapper.convertValue(
							workflowRuleObject.getData(), new TypeReference<List<NotifierWorkflowRuleResponse>>() {
							});

					if (workflowRuleList == null || workflowRuleList.size() == 0) {
						LOGGER.info("Workflow rule not found for entity id : " + entityId + " and event state id : "
								+ eventStateId + ".");
					}

					// set roles
					Set<Integer> roleIdsForEmailList = new HashSet<>();
					Set<Integer> roleIdsForDeviceToken = new HashSet<>();
					Set<String> variableList = new HashSet<>();

					String queryVariableName = "";
					String queryVariablePattern = "";

					Set<Long> workflowRuleActionIds = new HashSet<Long>();

					// set department group ids
					Set<Integer> departmentGroupIds = new HashSet<Integer>();

					for (NotifierWorkflowRuleResponse nwr : workflowRuleList) {
						workflowRuleActionIds.add(nwr.getWorkflowRuleActionId());
						queryVariableName = nwr.getQueryVarName();
						queryVariablePattern = nwr.getQueryVarPattern();
						if (nwr.getDepartmentGroupListIds() != null)
							departmentGroupIds.addAll(nwr.getDepartmentGroupListIds());

						if (nwr.getContent() != null)
							variableList.addAll(
									getVariables(nwr.getContent(), nwr.getVariableName(), nwr.getVariablePattern()));

						if (nwr.getActionTypeId().equals(WorkflowRuleActionTypeEnum.EMAIL_ALERT.getId()))
							for (Integer roleId : nwr.getRoleRecipient()) {
								roleIdsForEmailList.add(roleId);
							}
						if (nwr.getActionTypeId().equals(WorkflowRuleActionTypeEnum.IN_APP_NOTIFICATION.getId()))
							for (Integer roleId : nwr.getRoleRecipient()) {
								roleIdsForDeviceToken.add(roleId);
							}
					}

					// fetch departmentGroupIds
					List<EmpDepartmentGroupMapping> departmentGroupList = new ArrayList<>();
					if (departmentGroupIds != null && !departmentGroupIds.isEmpty()) {
						departmentGroupList = notificatorRepository.fetchDepartmentGroupListByIds(departmentGroupIds);
					}

					// fetch email info by roleIds
					List<EmployeeEmailInfo> emailListInfo = notificatorRepository
							.fetchEmployeeEmailList(roleIdsForEmailList, companyId, siteId);

					// fetch template variable list
					List<TemplateVariable> templateVariableList = notificatorRepository
							.fetchTemplateVariableList(variableList);
					Map<String, Object> templateVariableValues = new HashMap<>();
					for (TemplateVariable tv : templateVariableList) {

						String updatedSqlQuery = replaceQueryVariable(tv.getSqlQuery(), queryVariableValue,
								queryVariableName, queryVariablePattern);

						templateVariableValues.put(tv.getName(),
								notificatorRepository.executeSQLQuery(updatedSqlQuery));
					}

					// fetch notification opt-out list
					List<UserNotificationOptOut> notificationOptOutList = notificatorRepository
							.fetchUserNotificationOptOutList(workflowRuleActionIds);

					// fetch client credentials
					ClientNotificationCredential clientCredential = notificatorRepository
							.getClientCredential(companyId);

					// set email email request object
					for (NotifierWorkflowRuleResponse nwr : workflowRuleList) {

						SendEmailRequest emailRequest = new SendEmailRequest();
						Set<String> roleRecipientEmail = new HashSet<>();
						for (Integer roleId : nwr.getRoleRecipient()) {
							for (EmployeeEmailInfo eei : emailListInfo) {

								Boolean empOptOut = false;
								for (UserNotificationOptOut unoo : notificationOptOutList) {
									if (eei.getId().equals(unoo.getUserId())) {
										if (nwr.getWorkflowRuleActionId().equals(unoo.getRuleActionId())) {
											empOptOut = true;
											break;
										}
									}
								}

								if (roleId.equals(eei.getRoleId()) && empOptOut == false) {
									for (Integer departmentId : nwr.getDepartmentListIds()) {
										for (Integer departmentGroupId : nwr.getDepartmentGroupListIds()) {
											for (EmpDepartmentGroupMapping edgm : departmentGroupList) {
												if (departmentGroupId.equals(edgm.getDepartmentGroupId())) {
													roleRecipientEmail.add(eei.getEmailAddress());
												}

											}
										}
										if (departmentId.equals(eei.getDepartmentId())
												&& nwr.getDepartmentGroupListIds() == null)
											roleRecipientEmail.add(eei.getEmailAddress());
									}

								}
							}
						}

						// set recipients
						emailRequest.setToEmailIds(nwr.getAdditionalRecipients() != null
								? Stream.concat(Arrays.stream(nwr.getAdditionalRecipients().split(",")),
										Arrays.stream(roleRecipientEmail.toArray(new String[0]))).toArray(String[]::new)
								: roleRecipientEmail.toArray(new String[0]));
						emailRequest
								.setCcEmailIds(nwr.getCcRecipients() != null ? nwr.getCcRecipients().split(",") : null);

						String updatedContent = nwr.getContent();

						// manipulate query variables
						for (Map.Entry<String, Object> entry : templateVariableValues.entrySet()) {

							if (entry.getValue() != null) {
								updatedContent = updatedContent.replaceAll(
										nwr.getVariablePattern().replace(nwr.getVariableName(), entry.getKey()),
										entry.getValue().toString());
							} else {
								updatedContent = updatedContent.replaceAll(
										nwr.getVariablePattern().replace(nwr.getVariableName(), entry.getKey()), " - ");
							}
						}

						emailRequest.setContent(updatedContent);
						emailRequest.setSubject(nwr.getSubject());

						// set credentials
						byte[] senderPassword = Base64.getDecoder().decode(clientCredential.getEmailPassword());
						emailRequest.setSenderPassword(new String(senderPassword));
						emailRequest.setSenderEmail(clientCredential.getEmailAddress());
						emailRequest.setHost(clientCredential.getHost());
						emailRequest.setPort(clientCredential.getPort());

						if (emailRequest.getSenderPassword() == null || emailRequest.getSenderEmail() == null
								|| emailRequest.getHost() == null || emailRequest.getPort() == null
								|| emailRequest.getToEmailIds() == null || emailRequest.getToEmailIds().length == 0)
							LOGGER.info("Invalid email request.");

						LOGGER.info("Calling NotifierEngineService.");
						notifierEngineService.sendEmail(emailRequest);

					}

				}
			}).start();

			return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					Responses.BAD_REQUEST.toString());
		}
	}

	public CustomResponse sendNotificationV2(Integer entityId, Integer eventStateId, Long entityItemId, Integer siteId,
			Integer companyId, Integer moduleId, Map<String, Object> queryVariableValue, Set<String> emailList) {

		try {

			new Thread(new Runnable() {
				@Override
				public void run() {

					// fetch workflow rule
					LOGGER.info("Calling NotificatorWorkflowService.");
					CustomResponse workflowRuleObject = notificatorWorkflowService
							.getWorkflowRuleByEntityIdAndStateId(entityId, eventStateId, companyId, siteId);

					if (workflowRuleObject == null || workflowRuleObject.getData() == null)
						LOGGER.info("Workflow rule not found for entity id : " + entityId + " and event state id : "
								+ eventStateId + ".");

					ObjectMapper mapper = new ObjectMapper();
					mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					List<NotifierWorkflowRuleResponse> workflowRuleList = mapper.convertValue(
							workflowRuleObject.getData(), new TypeReference<List<NotifierWorkflowRuleResponse>>() {
							});

					if (workflowRuleList == null || workflowRuleList.isEmpty())
						LOGGER.info("Workflow rule not found for entity id : " + entityId + " and event state id : "
								+ eventStateId + ".");

					// set roles
					Set<Integer> roleIdsForEmailList = new HashSet<>();
					Set<Integer> roleIdsForDeviceToken = new HashSet<>();
					Set<String> variableList = new HashSet<>();

					String queryVariableName = "";
					String queryVariablePattern = "";

					Set<Long> workflowRuleActionIds = new HashSet<Long>();

					// set department group ids
					Set<Integer> departmentGroupIds = new HashSet<Integer>();

					for (NotifierWorkflowRuleResponse nwr : workflowRuleList) {
						workflowRuleActionIds.add(nwr.getWorkflowRuleActionId());
						queryVariableName = nwr.getQueryVarName();
						queryVariablePattern = nwr.getQueryVarPattern();
						if (nwr.getDepartmentGroupListIds() != null)
							departmentGroupIds.addAll(nwr.getDepartmentGroupListIds());

						if (nwr.getContent() != null)
							variableList.addAll(
									getVariables(nwr.getContent(), nwr.getVariableName(), nwr.getVariablePattern()));

						if (nwr.getActionTypeId().equals(WorkflowRuleActionTypeEnum.EMAIL_ALERT.getId()))
							for (Integer roleId : nwr.getRoleRecipient()) {
								roleIdsForEmailList.add(roleId);
							}
						if (nwr.getActionTypeId().equals(WorkflowRuleActionTypeEnum.IN_APP_NOTIFICATION.getId()))
							for (Integer roleId : nwr.getRoleRecipient()) {
								roleIdsForDeviceToken.add(roleId);
							}
					}

					// fetch departmentGroupIds
					List<EmpDepartmentGroupMapping> departmentGroupList = new ArrayList<>();
					if (departmentGroupIds != null && !departmentGroupIds.isEmpty()) {
						departmentGroupList = notificatorRepository.fetchDepartmentGroupListByIds(departmentGroupIds);
					}

					// fetch email info by roleIds
					List<EmployeeEmailInfo> emailListInfo = notificatorRepository
							.fetchEmployeeEmailList(roleIdsForEmailList, companyId, siteId);

					// fetch template variable list
					List<TemplateVariable> templateVariableList = notificatorRepository
							.fetchTemplateVariableList(variableList);
					Map<String, Object> templateVariableValues = new HashMap<>();
					for (TemplateVariable tv : templateVariableList) {

						String updatedSqlQuery = replaceQueryVariable(tv.getSqlQuery(), queryVariableValue,
								queryVariableName, queryVariablePattern);

						templateVariableValues.put(tv.getName(),
								notificatorRepository.executeSQLQuery(updatedSqlQuery));
					}

					// fetch notification opt-out list
					List<UserNotificationOptOut> notificationOptOutList = notificatorRepository
							.fetchUserNotificationOptOutList(workflowRuleActionIds);

					// fetch client credentials
					ClientNotificationCredential clientCredential = notificatorRepository
							.getClientCredential(companyId);

					// set email email request object
					for (NotifierWorkflowRuleResponse nwr : workflowRuleList) {

						SendEmailRequest emailRequest = new SendEmailRequest();
						Set<String> roleRecipientEmail = new HashSet<>();
						for (Integer roleId : nwr.getRoleRecipient()) {
							for (EmployeeEmailInfo eei : emailListInfo) {

								Boolean empOptOut = false;
								for (UserNotificationOptOut unoo : notificationOptOutList) {
									if (eei.getId().equals(unoo.getUserId())) {
										if (nwr.getWorkflowRuleActionId().equals(unoo.getRuleActionId())) {
											empOptOut = true;
											break;
										}
									}
								}

								if (roleId.equals(eei.getRoleId()) && empOptOut == false) {
									for (Integer departmentId : nwr.getDepartmentListIds()) {
										for (Integer departmentGroupId : nwr.getDepartmentGroupListIds()) {
											for (EmpDepartmentGroupMapping edgm : departmentGroupList) {
												if (departmentGroupId.equals(edgm.getDepartmentGroupId())) {
													roleRecipientEmail.add(eei.getEmailAddress());
												}

											}
										}
										if (departmentId.equals(eei.getDepartmentId())
												&& nwr.getDepartmentGroupListIds() == null)
											roleRecipientEmail.add(eei.getEmailAddress());
									}

								}
							}
						}

						// set recipients
						if (emailList != null && emailList.size() > 0) {
							emailRequest.setToEmailIds(nwr.getAdditionalRecipients() != null
									? Stream.concat(Arrays.stream(nwr.getAdditionalRecipients().split(",")),
											Arrays.stream(emailList.toArray(new String[0]))).toArray(String[]::new)
									: Arrays.stream(emailList.toArray(new String[0])).toArray(String[]::new));
						}

						if (emailList == null) {

							emailRequest
									.setToEmailIds(
											nwr.getAdditionalRecipients() != null ? Stream
													.concat(Arrays.stream(nwr.getAdditionalRecipients().split(",")),
															Arrays.stream(roleRecipientEmail.toArray(new String[0])))
													.toArray(String[]::new)
													: roleRecipientEmail.toArray(new String[0]));

						}

						emailRequest.setCcEmailIds(
								nwr.getCcRecipients() != null
										? (nwr.getCcRecipients().contains(",") ? nwr.getCcRecipients().split(",")
												: (new String[] { nwr.getCcRecipients() }))
										: null);

						String updatedContent = nwr.getContent();

						// manipulate query variables
						for (Map.Entry<String, Object> entry : templateVariableValues.entrySet()) {

							if (entry.getValue() != null) {
								updatedContent = updatedContent.replaceAll(
										nwr.getVariablePattern().replace(nwr.getVariableName(), entry.getKey()),
										entry.getValue().toString());
							} else {
								updatedContent = updatedContent.replaceAll(
										nwr.getVariablePattern().replace(nwr.getVariableName(), entry.getKey()), " - ");
							}

						}

						emailRequest.setContent(updatedContent);
						emailRequest.setSubject(nwr.getSubject());

						// set credentials
						byte[] senderPassword = Base64.getDecoder().decode(clientCredential.getEmailPassword().trim());
						emailRequest.setSenderPassword(new String(senderPassword));
						emailRequest.setSenderEmail(clientCredential.getEmailAddress());
						emailRequest.setHost(clientCredential.getHost());
						emailRequest.setPort(clientCredential.getPort());

						if (emailRequest.getSenderPassword() == null || emailRequest.getSenderEmail() == null
								|| emailRequest.getHost() == null || emailRequest.getPort() == null
								|| emailRequest.getToEmailIds() == null || emailRequest.getToEmailIds().length == 0)
							LOGGER.info("Invalid email request.");

						LOGGER.info("Calling NotifierEngineService.");
						notifierEngineService.sendEmail(emailRequest);

					}

				}
			}).start();

			return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					Responses.BAD_REQUEST.toString());
		}
	}

	public CustomResponse sendNotificationV3(Integer entityId, Integer eventStateId, Long entityItemId, Integer siteId,
			Integer companyId, Integer moduleId, Map<String, Object> queryVariableValue, Set<String> emailList,
			String timeZone, String dateTimePattern) {

		try {

			new Thread(new Runnable() {
				@Override
				public void run() {

					// fetch workflow rule
					LOGGER.info("Calling NotificatorWorkflowService.");
					CustomResponse workflowRuleObject = notificatorWorkflowService
							.getWorkflowRuleByEntityIdAndStateId(entityId, eventStateId, companyId, siteId);

					if (workflowRuleObject == null || workflowRuleObject.getData() == null)
						LOGGER.info("Workflow rule not found for entity id : " + entityId + " and event state id : "
								+ eventStateId + ".");

					ObjectMapper mapper = new ObjectMapper();
					mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					List<NotifierWorkflowRuleResponse> workflowRuleList = mapper.convertValue(
							workflowRuleObject.getData(), new TypeReference<List<NotifierWorkflowRuleResponse>>() {
							});

					if (workflowRuleList == null || workflowRuleList.isEmpty())
						LOGGER.info("Workflow rule not found for entity id : " + entityId + " and event state id : "
								+ eventStateId + ".");

					// set roles
					Set<Integer> roleIdsForEmailList = new HashSet<>();
					Set<Integer> roleIdsForDeviceToken = new HashSet<>();
					Set<String> variableList = new HashSet<>();

					String queryVariableName = "";
					String queryVariablePattern = "";

					Set<Long> workflowRuleActionIds = new HashSet<Long>();

					// set department group ids
					Set<Integer> departmentGroupIds = new HashSet<Integer>();

					for (NotifierWorkflowRuleResponse nwr : workflowRuleList) {
						workflowRuleActionIds.add(nwr.getWorkflowRuleActionId());
						queryVariableName = nwr.getQueryVarName();
						queryVariablePattern = nwr.getQueryVarPattern();
						if (nwr.getDepartmentGroupListIds() != null)
							departmentGroupIds.addAll(nwr.getDepartmentGroupListIds());

						if (nwr.getContent() != null)
							variableList.addAll(
									getVariables(nwr.getContent(), nwr.getVariableName(), nwr.getVariablePattern()));

						if (nwr.getActionTypeId().equals(WorkflowRuleActionTypeEnum.EMAIL_ALERT.getId()))
							for (Integer roleId : nwr.getRoleRecipient()) {
								roleIdsForEmailList.add(roleId);
							}
						if (nwr.getActionTypeId().equals(WorkflowRuleActionTypeEnum.IN_APP_NOTIFICATION.getId()))
							for (Integer roleId : nwr.getRoleRecipient()) {
								roleIdsForDeviceToken.add(roleId);
							}
					}

					// fetch departmentGroupIds
					List<EmpDepartmentGroupMapping> departmentGroupList = new ArrayList<>();
					if (departmentGroupIds != null && !departmentGroupIds.isEmpty()) {
						departmentGroupList = notificatorRepository.fetchDepartmentGroupListByIds(departmentGroupIds);
					}

					// fetch email info by roleIds
					List<EmployeeEmailInfo> emailListInfo = notificatorRepository
							.fetchEmployeeEmailList(roleIdsForEmailList, companyId, siteId);

					// fetch template variable list
					List<TemplateVariable> templateVariableList = notificatorRepository
							.fetchTemplateVariableList(variableList);
					Map<String, Object> templateVariableValues = new HashMap<>();
					for (TemplateVariable tv : templateVariableList) {

						String updatedSqlQuery = replaceQueryVariable(tv.getSqlQuery(), queryVariableValue,
								queryVariableName, queryVariablePattern);

						Object executeQuery = notificatorRepository.executeSQLQuery(updatedSqlQuery);

						ZonedDateTime updatedZone = null;
						try {

							DateTimeFormatter formatter = DateTimeFormatter
									.ofPattern(dateTimePattern != null ? dateTimePattern : "dd/MM/yyyy");
							LocalDateTime dateTime = LocalDateTime.parse(executeQuery.toString(), formatter);

							ZonedDateTime zonedUTC = dateTime.atZone(ZoneId.of("UTC"));
							updatedZone = zonedUTC.withZoneSameInstant(ZoneId.of(timeZone != null ? timeZone : "UTC"));

						} catch (Exception e) {
							updatedZone = null;
						}

						templateVariableValues.put(tv.getName(), updatedZone != null ? updatedZone : executeQuery);
					}

					// fetch notification opt-out list
					List<UserNotificationOptOut> notificationOptOutList = notificatorRepository
							.fetchUserNotificationOptOutList(workflowRuleActionIds);

					// fetch client credentials
					ClientNotificationCredential clientCredential = notificatorRepository
							.getClientCredential(companyId);

					// set email email request object
					for (NotifierWorkflowRuleResponse nwr : workflowRuleList) {

						SendEmailRequest emailRequest = new SendEmailRequest();
						Set<String> roleRecipientEmail = new HashSet<>();
						for (Integer roleId : nwr.getRoleRecipient()) {
							for (EmployeeEmailInfo eei : emailListInfo) {

								Boolean empOptOut = false;
								for (UserNotificationOptOut unoo : notificationOptOutList) {
									if (eei.getId().equals(unoo.getUserId())) {
										if (nwr.getWorkflowRuleActionId().equals(unoo.getRuleActionId())) {
											empOptOut = true;
											break;
										}
									}
								}

								if (roleId.equals(eei.getRoleId()) && empOptOut == false) {
									for (Integer departmentId : nwr.getDepartmentListIds()) {
										for (Integer departmentGroupId : nwr.getDepartmentGroupListIds()) {
											for (EmpDepartmentGroupMapping edgm : departmentGroupList) {
												if (departmentGroupId.equals(edgm.getDepartmentGroupId())) {
													roleRecipientEmail.add(eei.getEmailAddress());
												}

											}
										}
										if (departmentId.equals(eei.getDepartmentId())
												&& nwr.getDepartmentGroupListIds() == null)
											roleRecipientEmail.add(eei.getEmailAddress());
									}

								}
							}
						}

						// set recipients
						if (emailList != null && emailList.size() > 0) {
							emailRequest.setToEmailIds(nwr.getAdditionalRecipients() != null
									? Stream.concat(Arrays.stream(nwr.getAdditionalRecipients().split(",")),
											Arrays.stream(emailList.toArray(new String[0]))).toArray(String[]::new)
									: Arrays.stream(emailList.toArray(new String[0])).toArray(String[]::new));
						}

						if (emailList == null) {

							emailRequest
									.setToEmailIds(
											nwr.getAdditionalRecipients() != null ? Stream
													.concat(Arrays.stream(nwr.getAdditionalRecipients().split(",")),
															Arrays.stream(roleRecipientEmail.toArray(new String[0])))
													.toArray(String[]::new)
													: roleRecipientEmail.toArray(new String[0]));

						}

						emailRequest.setCcEmailIds(
								nwr.getCcRecipients() != null
										? (nwr.getCcRecipients().contains(",") ? nwr.getCcRecipients().split(",")
												: (new String[] { nwr.getCcRecipients() }))
										: null);

						String updatedContent = nwr.getContent();

						// manipulate query variables
						for (Map.Entry<String, Object> entry : templateVariableValues.entrySet()) {

							if (entry.getValue() != null) {
								updatedContent = updatedContent.replaceAll(
										nwr.getVariablePattern().replace(nwr.getVariableName(), entry.getKey()),
										entry.getValue().toString());
							} else {
								updatedContent = updatedContent.replaceAll(
										nwr.getVariablePattern().replace(nwr.getVariableName(), entry.getKey()), " - ");
							}

						}

						emailRequest.setContent(updatedContent);
						emailRequest.setSubject(nwr.getSubject());

						// set credentials
						byte[] senderPassword = Base64.getDecoder().decode(clientCredential.getEmailPassword().trim());
						emailRequest.setSenderPassword(new String(senderPassword));
						emailRequest.setSenderEmail(clientCredential.getEmailAddress());
						emailRequest.setHost(clientCredential.getHost());
						emailRequest.setPort(clientCredential.getPort());

						if (emailRequest.getSenderPassword() == null || emailRequest.getSenderEmail() == null
								|| emailRequest.getHost() == null || emailRequest.getPort() == null
								|| emailRequest.getToEmailIds() == null || emailRequest.getToEmailIds().length == 0)
							LOGGER.info("Invalid email request.");

						LOGGER.info("Calling NotifierEngineService.");
						notifierEngineService.sendEmail(emailRequest);

					}

				}
			}).start();

			return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					Responses.BAD_REQUEST.toString());
		}
	}

	public CustomResponse sendNotificationV4(Integer entityId, Integer eventStateId, Long entityItemId, Integer siteId,
			Integer companyId, Integer moduleId, Map<String, Object> queryVariableValue, Set<String> emailList,
			String timeZone, String dateTimePattern, HashMap<String, String> attachmentFilesByteArraysAndFileNames,
			String attachment, String attachmentName) {

		try {

			new Thread(new Runnable() {
				@Override
				public void run() {

					// fetch workflow rule
					LOGGER.info("Calling NotificatorWorkflowService.");
					CustomResponse workflowRuleObject = notificatorWorkflowService
							.getWorkflowRuleByEntityIdAndStateId(entityId, eventStateId, companyId, siteId);

					if (workflowRuleObject == null || workflowRuleObject.getData() == null)
						LOGGER.info("Workflow rule not found for entity id : " + entityId + " and event state id : "
								+ eventStateId + ".");

					ObjectMapper mapper = new ObjectMapper();
					mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					List<NotifierWorkflowRuleResponse> workflowRuleList = mapper.convertValue(
							workflowRuleObject.getData(), new TypeReference<List<NotifierWorkflowRuleResponse>>() {
							});

					if (workflowRuleList == null || workflowRuleList.isEmpty())
						LOGGER.info("Workflow rule not found for entity id : " + entityId + " and event state id : "
								+ eventStateId + ".");

					// set roles
					Set<Integer> roleIdsForEmailList = new HashSet<>();
					Set<Integer> roleIdsForDeviceToken = new HashSet<>();
					Set<String> variableList = new HashSet<>();

					String queryVariableName = "";
					String queryVariablePattern = "";

					Set<Long> workflowRuleActionIds = new HashSet<Long>();

					// set department group ids
					Set<Integer> departmentGroupIds = new HashSet<Integer>();

					for (NotifierWorkflowRuleResponse nwr : workflowRuleList) {
						workflowRuleActionIds.add(nwr.getWorkflowRuleActionId());
						queryVariableName = nwr.getQueryVarName();
						queryVariablePattern = nwr.getQueryVarPattern();
						if (nwr.getDepartmentGroupListIds() != null)
							departmentGroupIds.addAll(nwr.getDepartmentGroupListIds());

						if (nwr.getContent() != null)
							variableList.addAll(
									getVariables(nwr.getContent(), nwr.getVariableName(), nwr.getVariablePattern()));

						if (nwr.getActionTypeId().equals(WorkflowRuleActionTypeEnum.EMAIL_ALERT.getId()))
							for (Integer roleId : nwr.getRoleRecipient()) {
								roleIdsForEmailList.add(roleId);
							}
						if (nwr.getActionTypeId().equals(WorkflowRuleActionTypeEnum.IN_APP_NOTIFICATION.getId()))
							for (Integer roleId : nwr.getRoleRecipient()) {
								roleIdsForDeviceToken.add(roleId);
							}
					}

					// fetch departmentGroupIds
					List<EmpDepartmentGroupMapping> departmentGroupList = new ArrayList<>();
					if (departmentGroupIds != null && !departmentGroupIds.isEmpty()) {
						departmentGroupList = notificatorRepository.fetchDepartmentGroupListByIds(departmentGroupIds);
					}

					// fetch email info by roleIds
					List<EmployeeEmailInfo> emailListInfo = notificatorRepository
							.fetchEmployeeEmailList(roleIdsForEmailList, companyId, siteId);

					// fetch template variable list
					List<TemplateVariable> templateVariableList = notificatorRepository
							.fetchTemplateVariableList(variableList);
					Map<String, Object> templateVariableValues = new HashMap<>();
					for (TemplateVariable tv : templateVariableList) {

						String updatedSqlQuery = replaceQueryVariable(tv.getSqlQuery(), queryVariableValue,
								queryVariableName, queryVariablePattern);

						Object executeQuery = notificatorRepository.executeSQLQuery(updatedSqlQuery);

						ZonedDateTime updatedZone = null;
						try {

							DateTimeFormatter formatter = DateTimeFormatter
									.ofPattern(dateTimePattern != null ? dateTimePattern : "dd/MM/yyyy");
							LocalDateTime dateTime = LocalDateTime.parse(executeQuery.toString(), formatter);

							ZonedDateTime zonedUTC = dateTime.atZone(ZoneId.of("UTC"));
							updatedZone = zonedUTC.withZoneSameInstant(ZoneId.of(timeZone != null ? timeZone : "UTC"));

						} catch (Exception e) {
							updatedZone = null;
						}

						templateVariableValues.put(tv.getName(), updatedZone != null ? updatedZone : executeQuery);
					}

					// fetch notification opt-out list
					List<UserNotificationOptOut> notificationOptOutList = notificatorRepository
							.fetchUserNotificationOptOutList(workflowRuleActionIds);

					// fetch client credentials
					ClientNotificationCredential clientCredential = notificatorRepository
							.getClientCredential(companyId);

					// set email email request object
					for (NotifierWorkflowRuleResponse nwr : workflowRuleList) {

						SendEmailRequest emailRequest = new SendEmailRequest();
						Set<String> roleRecipientEmail = new HashSet<>();
						for (Integer roleId : nwr.getRoleRecipient()) {
							for (EmployeeEmailInfo eei : emailListInfo) {

								Boolean empOptOut = false;
								for (UserNotificationOptOut unoo : notificationOptOutList) {
									if (eei.getId().equals(unoo.getUserId())) {
										if (nwr.getWorkflowRuleActionId().equals(unoo.getRuleActionId())) {
											empOptOut = true;
											break;
										}
									}
								}

								if (roleId.equals(eei.getRoleId()) && empOptOut == false) {
									for (Integer departmentId : nwr.getDepartmentListIds()) {
										for (Integer departmentGroupId : nwr.getDepartmentGroupListIds()) {
											for (EmpDepartmentGroupMapping edgm : departmentGroupList) {
												if (departmentGroupId.equals(edgm.getDepartmentGroupId())) {
													roleRecipientEmail.add(eei.getEmailAddress());
												}

											}
										}
										if (departmentId.equals(eei.getDepartmentId())
												&& nwr.getDepartmentGroupListIds() == null)
											roleRecipientEmail.add(eei.getEmailAddress());
									}

								}
							}
						}

						// set recipients
						if (emailList != null && emailList.size() > 0) {
							emailRequest.setToEmailIds(nwr.getAdditionalRecipients() != null
									? Stream.concat(Arrays.stream(nwr.getAdditionalRecipients().split(",")),
											Arrays.stream(emailList.toArray(new String[0]))).toArray(String[]::new)
									: Arrays.stream(emailList.toArray(new String[0])).toArray(String[]::new));
						}

						if (emailList == null) {

							emailRequest
									.setToEmailIds(
											nwr.getAdditionalRecipients() != null ? Stream
													.concat(Arrays.stream(nwr.getAdditionalRecipients().split(",")),
															Arrays.stream(roleRecipientEmail.toArray(new String[0])))
													.toArray(String[]::new)
													: roleRecipientEmail.toArray(new String[0]));

						}

						emailRequest.setCcEmailIds(
								nwr.getCcRecipients() != null
										? (nwr.getCcRecipients().contains(",") ? nwr.getCcRecipients().split(",")
												: (new String[] { nwr.getCcRecipients() }))
										: null);

						String updatedContent = nwr.getContent();

						// manipulate query variables
						for (Map.Entry<String, Object> entry : templateVariableValues.entrySet()) {

							if (entry.getValue() != null) {
								updatedContent = updatedContent.replaceAll(
										nwr.getVariablePattern().replace(nwr.getVariableName(), entry.getKey()),
										entry.getValue().toString());
							} else {
								updatedContent = updatedContent.replaceAll(
										nwr.getVariablePattern().replace(nwr.getVariableName(), entry.getKey()), " - ");
							}

						}

						emailRequest.setContent(updatedContent);
						emailRequest.setSubject(nwr.getSubject());

						// set credentials
						byte[] senderPassword = Base64.getDecoder().decode(clientCredential.getEmailPassword().trim());
						emailRequest.setSenderPassword(new String(senderPassword));
						emailRequest.setSenderEmail(clientCredential.getEmailAddress());
						emailRequest.setHost(clientCredential.getHost());
						emailRequest.setPort(clientCredential.getPort());

						if (emailRequest.getSenderPassword() == null || emailRequest.getSenderEmail() == null
								|| emailRequest.getHost() == null || emailRequest.getPort() == null
								|| emailRequest.getToEmailIds() == null || emailRequest.getToEmailIds().length == 0)
							LOGGER.info("Invalid email request.");

						LOGGER.info("Calling NotifierEngineService.");
						emailRequest.setAttachment(attachment);
						emailRequest.setAttachmentName(attachmentName);

						notifierEngineService.sendEmail(emailRequest);
					}
				}
			}).start();

			return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					Responses.BAD_REQUEST.toString());
		}
	}

	private String replaceQueryVariable(String sqlQuery, Map<String, Object> queryVariableValue, String queryVarName,
			String queryVarPattern) {
		for (Map.Entry<String, Object> entry : queryVariableValue.entrySet()) {

			sqlQuery = sqlQuery.replaceAll(queryVarPattern.replace(queryVarName, entry.getKey()),
					entry.getValue().toString());

		}
		return sqlQuery;
	}

	private Set<String> getVariables(String content, String variableName, String variablePattern) {

		String varPattern = variablePattern.replace(variableName, "(.*?)");
		Pattern pattern = Pattern.compile(varPattern, Pattern.DOTALL);
		Matcher matcher = pattern.matcher(content);
		Set<String> variables = new HashSet<String>();
		while (matcher.find()) {
			variables.add(matcher.group(1));
		}
		return variables;
	}

}
