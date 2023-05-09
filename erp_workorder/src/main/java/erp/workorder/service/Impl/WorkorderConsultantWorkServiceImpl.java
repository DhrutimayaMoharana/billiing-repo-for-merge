package erp.workorder.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import erp.workorder.dao.WorkorderConsultantWorkDao;
import erp.workorder.dao.WorkorderDao;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.UserDetail;
import erp.workorder.dto.request.AmendWorkorderInvocationUpdateStateRequest;
import erp.workorder.dto.request.WorkorderConsultantWorkAddUpdateRequestDTO;
import erp.workorder.dto.request.WorkorderConsultantWorkGetRequestDTO;
import erp.workorder.dto.request.WorkorderConsultantWorkItemAddUpdateRequest;
import erp.workorder.dto.request.WorkorderConsultantWorkItemDeactivateRequestDTO;
import erp.workorder.dto.response.WorkorderConsultantWorkCategoryWiseItemGetResponse;
import erp.workorder.dto.response.WorkorderConsultantWorkGetResponse;
import erp.workorder.dto.response.WorkorderConsultantWorkItemGetResponse;
import erp.workorder.entity.Workorder;
import erp.workorder.entity.WorkorderConsultantWork;
import erp.workorder.entity.WorkorderConsultantWorkItemMapping;
import erp.workorder.entity.WorkorderConsultantWorkItemMappingVersion;
import erp.workorder.entity.WorkorderConsultantWorkVersion;
import erp.workorder.entity.WorkorderVersion;
import erp.workorder.enums.Responses;
import erp.workorder.enums.WorkorderTypes;
import erp.workorder.service.WorkorderConsultantWorkService;
import erp.workorder.util.CustomValidationUtil;
import erp.workorder.util.SetObject;

@Service
@Transactional
public class WorkorderConsultantWorkServiceImpl implements WorkorderConsultantWorkService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WorkorderConsultantWorkDao consultantWorkDao;

	@Autowired
	private CustomValidationUtil validationUtil;

	@Autowired
	private SetObject setObject;

	@Autowired
	private WorkorderDao workorderDao;

	@Override
	public CustomResponse addOrUpdateWorkorderConsultantWork(
			WorkorderConsultantWorkAddUpdateRequestDTO consultantWorkDTO) {
		try {
			CustomResponse validationRes = validationUtil.validateAddOrUpdateWorkorderConsultantWork(consultantWorkDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}
			Workorder workorder = workorderDao.fetchWorkorderById(consultantWorkDTO.getWorkorderId());
			if (workorder == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");

			if (!(workorder.getType().getId().equals(WorkorderTypes.Consultancy.getId()))) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Inappropriate workorder type.");
			}

//			add work with items
			if (consultantWorkDTO.getConsultantWorkId() == null) {

				WorkorderConsultantWork consultantWork = setObject
						.workorderConsultantWorkAddUpdateRequestDtoToEntity(consultantWorkDTO);
				Long consultantWorkId = consultantWorkDao.saveWorkorderConsultantWork(consultantWork);

				if (consultantWorkId != null && consultantWorkId > 0L) {
					consultantWork.setId(consultantWorkId);
					for (WorkorderConsultantWorkItemAddUpdateRequest workItemRequest : consultantWorkDTO
							.getConsultantWorkItems()) {
						WorkorderConsultantWorkItemMapping workItem = setObject
								.workorderConsultantWorkItemAddUpdateRequestDtoToEntity(workItemRequest);
						workItem.setWorkorderConsultantWorkId(consultantWorkId);
						workItem.setCreatedBy(consultantWorkDTO.getUserDetail().getId());
						workItem.setModifiedBy(consultantWorkDTO.getUserDetail().getId());

						consultantWorkDao.saveWorkorderConsultantWorkItemMap(workItem);
					}

					workorder.setConsultantWork(consultantWork);
					workorder.setModifiedOn(new Date());
					workorder.setModifiedBy(consultantWorkDTO.getUserDetail().getId());
					workorderDao.forceUpdateWorkorder(workorder);
					return new CustomResponse(Responses.SUCCESS.getCode(), consultantWorkId, "Added.");
				}
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Already exists.");
			}
//			update work without items
			else {
				WorkorderConsultantWork consultantWork = consultantWorkDao
						.fetchWorkorderConsultantWorkById(consultantWorkDTO.getConsultantWorkId());
				if (consultantWork == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid consultantWorkId.");
				}
				consultantWork = setObject.updatedWorkorderConsultantWorkEntityFromRequestDto(consultantWork,
						consultantWorkDTO);
				Boolean isUpdated = consultantWorkDao.updateWorkorderConsultantWork(consultantWork);
				return new CustomResponse(Responses.SUCCESS.getCode(), null,
						isUpdated ? "Updated." : "Could not update.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getWorkorderConsultantWork(WorkorderConsultantWorkGetRequestDTO consultantWorkDTO) {

		try {
			CustomResponse validationRes = validationUtil.validateGetWorkorderConsultantWork(consultantWorkDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}
			Workorder workorder = workorderDao.fetchWorkorderById(consultantWorkDTO.getWorkorderId());
			if (workorder == null)
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Provide valid workorderId.");
			WorkorderConsultantWork consultantWork = workorder.getConsultantWork();
			if (consultantWork == null) {
				return new CustomResponse(Responses.NOT_FOUND.getCode(), null, "Consultant work is not defined yet.");
			}
			List<WorkorderConsultantWorkItemMapping> consultantWorkItems = consultantWorkDao
					.fetchWorkorderConsultantWorkItemsByConsultantWorkId(consultantWork.getId());

			List<WorkorderConsultantWorkCategoryWiseItemGetResponse> categoryWiseWorkItemsDto = new ArrayList<>();
			Set<String> distinctCategoryDescriptions = new LinkedHashSet<>();
			List<WorkorderConsultantWorkItemGetResponse> workItemsDto = new ArrayList<>();
			Double consultantWorkAmount = 0.0;
			if (consultantWorkItems != null) {

				for (WorkorderConsultantWorkItemMapping workItem : consultantWorkItems) {
					WorkorderConsultantWorkItemGetResponse workItemDto = setObject
							.workorderConsultantWorkItemMappingEntityToGetResponse(workItem);
					consultantWorkAmount += workItemDto.getAmount();
					workItemsDto.add(workItemDto);
				}

				boolean hasAddedNullCategoryItem = false;
				for (WorkorderConsultantWorkItemMapping workItem : consultantWorkItems) {
					if (workItem.getCategoryDescription() != null && !workItem.getCategoryDescription().isBlank()) {
						distinctCategoryDescriptions.add(workItem.getCategoryDescription());
					} else if (workItem.getCategoryDescription() == null && !hasAddedNullCategoryItem) {
						hasAddedNullCategoryItem = true;
					}
				}
				if (hasAddedNullCategoryItem) {
					categoryWiseWorkItemsDto
							.add(new WorkorderConsultantWorkCategoryWiseItemGetResponse(null, new ArrayList<>()));
				}

				for (String distinctCategoryDescription : distinctCategoryDescriptions) {
					categoryWiseWorkItemsDto.add(new WorkorderConsultantWorkCategoryWiseItemGetResponse(
							distinctCategoryDescription, new ArrayList<>()));
				}

				for (WorkorderConsultantWorkCategoryWiseItemGetResponse categoryWIList : categoryWiseWorkItemsDto) {

					for (WorkorderConsultantWorkItemGetResponse workItemDto : workItemsDto) {
						if (categoryWIList.getCategoryDescription() == null
								&& workItemDto.getCategoryDescription() == null) {
							categoryWIList.getConsultantWorkItems().add(workItemDto);
						} else if (categoryWIList.getCategoryDescription() != null
								&& workItemDto.getCategoryDescription() != null
								&& categoryWIList.getCategoryDescription()
										.equalsIgnoreCase(workItemDto.getCategoryDescription())) {
							categoryWIList.getConsultantWorkItems().add(workItemDto);
						}
					}

				}
			}

			WorkorderConsultantWorkGetResponse resultResponse = new WorkorderConsultantWorkGetResponse(
					consultantWork.getId(), consultantWork.getWorkScope(), consultantWork.getAnnexureNote(),
					categoryWiseWorkItemsDto, consultantWorkAmount);
			return new CustomResponse(Responses.SUCCESS.getCode(), resultResponse, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse addOrUpdateWorkorderConsultantWorkItem(
			WorkorderConsultantWorkItemAddUpdateRequest consultantWorkItemDTO) {
		try {
			CustomResponse validationRes = validationUtil
					.validateUpdateWorkorderConsultantWorkItem(consultantWorkItemDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}
//			update consultant work item
			if (consultantWorkItemDTO.getConsultantWorkItemId() != null) {

				WorkorderConsultantWorkItemMapping consultantWorkItem = consultantWorkDao
						.fetchWorkorderConsultantWorkItemMappingById(consultantWorkItemDTO.getConsultantWorkItemId());
				if (consultantWorkItem == null) {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
							"Provide valid consultantWorkItemId.");
				}

				consultantWorkItem = setObject.updatedWorkorderConsultantWorkItemMapEntityFromRequestDto(
						consultantWorkItem, consultantWorkItemDTO);
				Boolean isUpdated = consultantWorkDao.updateWorkorderConsultantWorkItem(consultantWorkItem);

				return new CustomResponse(Responses.SUCCESS.getCode(), null,
						isUpdated ? "Updated." : "Already exists.");
			}
//			add consultant work item
			else {
				WorkorderConsultantWork consultantWork = consultantWorkDao
						.fetchWorkorderConsultantWorkById(consultantWorkItemDTO.getConsultantWorkId());
				if (consultantWork == null) {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Provide valid consultantWorkId.");
				}

				WorkorderConsultantWorkItemMapping workItem = setObject
						.workorderConsultantWorkItemAddUpdateRequestDtoToEntity(consultantWorkItemDTO);

				workItem.setWorkorderConsultantWorkId(consultantWork.getId());
				workItem.setCreatedBy(consultantWorkItemDTO.getUserDetail().getId());
				workItem.setModifiedBy(consultantWorkItemDTO.getUserDetail().getId());

				Long workItemId = consultantWorkDao.saveWorkorderConsultantWorkItemMap(workItem);

				return new CustomResponse(Responses.SUCCESS.getCode(), null,
						workItemId != null && workItemId.longValue() > 0L ? "Added." : "Already exists.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse deactivateWorkorderConsultantWorkItem(
			WorkorderConsultantWorkItemDeactivateRequestDTO consultantWorkItemDTO) {
		try {
			CustomResponse validationRes = validationUtil
					.validateDeactivateWorkorderConsultantWorkItem(consultantWorkItemDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}
			WorkorderConsultantWorkItemMapping consultantWorkItem = consultantWorkDao
					.fetchWorkorderConsultantWorkItemMappingById(consultantWorkItemDTO.getConsultantWorkItemId());
			if (consultantWorkItem == null) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Provide valid consultantWorkItemId.");
			}
			List<WorkorderConsultantWorkItemMapping> consultantWorkItems = consultantWorkDao
					.fetchWorkorderConsultantWorkItemsByConsultantWorkId(
							consultantWorkItem.getWorkorderConsultantWorkId());
			if (consultantWorkItems.size() == 1 && consultantWorkItem.getIsActive()) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Need atleast one item existed.");
			}
			consultantWorkItem.setModifiedOn(new Date());
			consultantWorkItem.setModifiedBy(consultantWorkItemDTO.getUserDetail().getId());
			consultantWorkItem.setIsActive(false);
			consultantWorkDao.forceUpdateWorkorderConsultantWorkItem(consultantWorkItem);
			return new CustomResponse(Responses.SUCCESS.getCode(), null, "Removed.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getWorkorderConsultantWorkItemCategoryDescriptions(Integer companyId) {

		try {
			Object distinctCategoryDescriptions = consultantWorkDao
					.fetchWorkorderConsultantWorkItemCategoryDescriptions(companyId);
			return new CustomResponse(Responses.SUCCESS.getCode(), distinctCategoryDescriptions,
					Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	public CustomResponse versioningWorkorderConsultantWorkFlow(
			AmendWorkorderInvocationUpdateStateRequest clientRequestDTO, Long workorderId) {

		try {
			Workorder workorder = workorderDao.fetchWorkorderById(workorderId);
			if (workorder == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");

			WorkorderConsultantWork consultantWork = consultantWorkDao
					.fetchWorkorderConsultantWorkByWorkorderId(workorderId);
			if (consultantWork == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid consultantWorkId.");

			WorkorderVersion workorderVersion = setObject.workorderEntityToVersionEntity(workorder);
			workorderVersion.setCreatedBy(clientRequestDTO.getUserDetail().getId());
			Long workorderVersionId = workorderDao.saveWorkorderVersion(workorderVersion);

			if (workorderVersionId == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderVersionId.");
			}

			WorkorderConsultantWorkVersion consultantWorkVersion = setObject
					.workorderConsultantWorkEntityToVersionEntity(consultantWork);
			consultantWorkVersion.setWorkorderVersionId(workorderVersionId);
			consultantWorkVersion.setCreatedBy(clientRequestDTO.getUserDetail().getId());

			Long consultantWorkVersionId = consultantWorkDao.saveWorkorderConsultantWorkVersion(consultantWorkVersion);

			if (consultantWorkVersionId != null && consultantWorkVersionId > 0L) {

				List<WorkorderConsultantWorkItemMapping> workorderConsultantWorkItemList = consultantWorkDao
						.fetchWorkorderConsultantWorkItemsByConsultantWorkId(consultantWork.getId());

				if (workorderConsultantWorkItemList != null && !workorderConsultantWorkItemList.isEmpty()) {

					for (WorkorderConsultantWorkItemMapping itemObj : workorderConsultantWorkItemList) {

						WorkorderConsultantWorkItemMappingVersion itemVersionObj = setObject
								.workorderConsultantWorkItemMappingEntityToVersionEntity(itemObj);
						itemVersionObj.setWorkorderConsultantWorkVersionId(consultantWorkVersionId);
						itemVersionObj.setCreatedBy(clientRequestDTO.getUserDetail().getId());

						consultantWorkDao.saveWorkorderConsultantWorkItemMapVersion(itemVersionObj);

					}

				}

			}

			return new CustomResponse(Responses.SUCCESS.getCode(), consultantWorkVersionId, "Added.");

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse amendWorkorderConsultantWorkFlow(UserDetail userDetail, Long amendWorkorderId,
			Long workorderId) {

		try {
			WorkorderConsultantWork consultantWork = consultantWorkDao
					.fetchWorkorderConsultantWorkByWorkorderId(workorderId);
			if (consultantWork == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid consultantWorkId.");

			WorkorderConsultantWork amendConsultantWork = setObject
					.workorderConsultantWorkEntityToAmendEntity(consultantWork);
			amendConsultantWork.setWorkorderId(amendWorkorderId);
			amendConsultantWork.setCreatedBy(userDetail.getId());
			amendConsultantWork.setModifiedBy(userDetail.getId());

			Long amendConsultantWorkId = consultantWorkDao.saveWorkorderConsultantWork(amendConsultantWork);

			if (amendConsultantWorkId != null && amendConsultantWorkId > 0L) {

				List<WorkorderConsultantWorkItemMapping> workorderConsultantWorkItemList = consultantWorkDao
						.fetchWorkorderConsultantWorkItemsByConsultantWorkId(consultantWork.getId());

				if (workorderConsultantWorkItemList != null && !workorderConsultantWorkItemList.isEmpty()) {

					for (WorkorderConsultantWorkItemMapping itemObj : workorderConsultantWorkItemList) {

						WorkorderConsultantWorkItemMapping amendItemObj = setObject
								.workorderConsultantWorkItemMappingEntityToAmendEntity(itemObj);
						amendItemObj.setWorkorderConsultantWorkId(amendConsultantWorkId);
						amendItemObj.setCreatedBy(userDetail.getId());
						amendItemObj.setModifiedBy(userDetail.getId());
						consultantWorkDao.saveWorkorderConsultantWorkItemMap(amendItemObj);

					}

				}

			}

			return new CustomResponse(Responses.SUCCESS.getCode(), amendConsultantWorkId, "Added.");

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
