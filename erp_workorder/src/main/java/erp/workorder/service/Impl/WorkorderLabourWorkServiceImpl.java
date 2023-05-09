package erp.workorder.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import erp.workorder.dao.WorkorderDao;
import erp.workorder.dao.WorkorderLabourWorkDao;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.UserDetail;
import erp.workorder.dto.request.AmendWorkorderInvocationUpdateStateRequest;
import erp.workorder.dto.request.WorkorderLabourWorkAddUpdateRequestDTO;
import erp.workorder.dto.request.WorkorderLabourWorkGetRequestDTO;
import erp.workorder.dto.request.WorkorderLabourWorkItemAddUpdateRequest;
import erp.workorder.dto.request.WorkorderLabourWorkItemDeactivateRequest;
import erp.workorder.dto.response.WorkorderLabourWorkGetResponse;
import erp.workorder.dto.response.WorkorderLabourWorkItemGetResponse;
import erp.workorder.entity.Workorder;
import erp.workorder.entity.WorkorderLabourType;
import erp.workorder.entity.WorkorderLabourWork;
import erp.workorder.entity.WorkorderLabourWorkItemMapping;
import erp.workorder.entity.WorkorderLabourWorkItemMappingVersion;
import erp.workorder.entity.WorkorderLabourWorkVersion;
import erp.workorder.entity.WorkorderVersion;
import erp.workorder.enums.Responses;
import erp.workorder.enums.WorkorderTypes;
import erp.workorder.service.WorkorderLabourWorkService;
import erp.workorder.util.CustomValidationUtil;
import erp.workorder.util.SetObject;

@Service
@Transactional
public class WorkorderLabourWorkServiceImpl implements WorkorderLabourWorkService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WorkorderLabourWorkDao labourWorkDao;

	@Autowired
	private CustomValidationUtil validationUtil;

	@Autowired
	private SetObject setObject;

	@Autowired
	private WorkorderDao workorderDao;

	@Override
	public CustomResponse addOrUpdateWorkorderLabourWork(WorkorderLabourWorkAddUpdateRequestDTO labourWorkDTO) {
		try {
			CustomResponse validationRes = validationUtil.validateAddOrUpdateWorkorderLabourWork(labourWorkDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}
			Workorder workorder = workorderDao.fetchWorkorderById(labourWorkDTO.getWorkorderId());
			if (workorder == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");

			if (!(workorder.getType().getId().equals(WorkorderTypes.Labour_Supply.getId()))) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Inappropriate workorder type.");
			}
//			add work with items
			if (labourWorkDTO.getLabourWorkId() == null) {

				WorkorderLabourWork labourWork = setObject
						.workorderLabourWorkAddUpdateRequestDtoToEntity(labourWorkDTO);
				Long labourWorkId = labourWorkDao.saveWorkorderLabourWork(labourWork);

				List<WorkorderLabourType> labourTypes = labourWorkDao.fetchWoLabourTypes(workorder.getCompanyId());
				Set<Integer> distinctLabourTypeIds = new HashSet<>();
				if (labourTypes != null) {
					labourTypes.forEach(item -> distinctLabourTypeIds.add(item.getId()));
				}

				if (labourWorkId != null && labourWorkId > 0L) {
					labourWork.setId(labourWorkId);
					for (WorkorderLabourWorkItemAddUpdateRequest workItemRequest : labourWorkDTO.getLabourWorkItems()) {
						WorkorderLabourWorkItemMapping workItem = setObject
								.workorderLabourWorkItemAddUpdateRequestDtoToEntity(workItemRequest);
						workItem.setWorkorderLabourWorkId(labourWorkId);
						if (!distinctLabourTypeIds.contains(workItemRequest.getLabourTypeId())) {
							throw new RuntimeException("Invalid labour type.");
						}
						workItem.setCreatedBy(labourWorkDTO.getUserDetail().getId());
						workItem.setModifiedBy(labourWorkDTO.getUserDetail().getId());
						labourWorkDao.saveWorkorderLabourWorkItemMap(workItem);
					}

					workorder.setLabourWork(labourWork);
					workorder.setModifiedOn(new Date());
					workorder.setModifiedBy(labourWorkDTO.getUserDetail().getId());
					workorderDao.forceUpdateWorkorder(workorder);
					return new CustomResponse(Responses.SUCCESS.getCode(), labourWorkId, "Added.");
				}
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Already exists.");
			}
//			update work without items
			else {
				WorkorderLabourWork labourWork = labourWorkDao
						.fetchWorkorderLabourWorkById(labourWorkDTO.getLabourWorkId());
				if (labourWork == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid labourWorkId.");
				}
				labourWork = setObject.updatedWorkorderLabourWorkEntityFromRequestDto(labourWork, labourWorkDTO);
				Boolean isUpdated = labourWorkDao.updateWorkorderLabourWork(labourWork);
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
	public CustomResponse addOrUpdateWorkorderLabourWorkItem(
			WorkorderLabourWorkItemAddUpdateRequest labourWorkItemDTO) {

		try {
			CustomResponse validationRes = validationUtil.validateUpdateWorkorderLabourWorkItem(labourWorkItemDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}

			List<WorkorderLabourType> labourTypes = labourWorkDao
					.fetchWoLabourTypes(labourWorkItemDTO.getUserDetail().getCompanyId());
			Set<Integer> distinctLabourTypeIds = new HashSet<>();
			if (labourTypes != null) {
				labourTypes.forEach(item -> distinctLabourTypeIds.add(item.getId()));
			}
			if (!distinctLabourTypeIds.contains(labourWorkItemDTO.getLabourTypeId())) {
				throw new RuntimeException("Invalid labour type.");
			}
//			update labour work item
			if (labourWorkItemDTO.getLabourWorkItemId() != null) {

				WorkorderLabourWorkItemMapping labourWorkItem = labourWorkDao
						.fetchWorkorderLabourWorkItemMappingById(labourWorkItemDTO.getLabourWorkItemId());
				if (labourWorkItem == null) {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
							"Provide valid transportWorkItemId.");
				}

				labourWorkItem = setObject.updatedWorkorderLabourWorkItemMapEntityFromRequestDto(labourWorkItem,
						labourWorkItemDTO);
				Boolean isUpdated = labourWorkDao.updateWorkorderLabourWorkItem(labourWorkItem);

				return new CustomResponse(Responses.SUCCESS.getCode(), null,
						isUpdated ? "Updated." : "Already exists.");
			}
//			add labour work item
			else {
				WorkorderLabourWork labourWork = labourWorkDao
						.fetchWorkorderLabourWorkById(labourWorkItemDTO.getLabourWorkId());
				if (labourWork == null) {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Provide valid labourWorkWorkId.");
				}

				WorkorderLabourWorkItemMapping workItem = setObject
						.workorderLabourWorkItemAddUpdateRequestDtoToEntity(labourWorkItemDTO);

				workItem.setWorkorderLabourWorkId(labourWork.getId());
				workItem.setCreatedBy(labourWorkItemDTO.getUserDetail().getId());
				workItem.setModifiedBy(labourWorkItemDTO.getUserDetail().getId());

				Long workItemId = labourWorkDao.saveWorkorderLabourWorkItemMap(workItem);

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
	public CustomResponse deactivateWorkorderLabourWorkItem(
			WorkorderLabourWorkItemDeactivateRequest labourWorkItemDTO) {

		try {
			CustomResponse validationRes = validationUtil.validateDeactivateWorkorderLabourWorkItem(labourWorkItemDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}
			WorkorderLabourWorkItemMapping labourWorkItem = labourWorkDao
					.fetchWorkorderLabourWorkItemMappingById(labourWorkItemDTO.getLabourWorkItemId());
			if (labourWorkItem == null) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Provide valid labourWorkItemId.");
			}
			List<WorkorderLabourWorkItemMapping> labourWorkItems = labourWorkDao
					.fetchWorkorderLabourWorkItemsByLabourWorkId(labourWorkItem.getWorkorderLabourWorkId());
			if (labourWorkItems.size() == 1 && labourWorkItem.getIsActive()) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Need atleast one item existed.");
			}
			labourWorkItem.setModifiedOn(new Date());
			labourWorkItem.setModifiedBy(labourWorkItemDTO.getUserDetail().getId());
			labourWorkItem.setIsActive(false);
			labourWorkDao.forceUpdateWorkorderLabourWorkItem(labourWorkItem);
			return new CustomResponse(Responses.SUCCESS.getCode(), null, "Removed.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getWorkorderLabourWork(WorkorderLabourWorkGetRequestDTO labourWorkDTO) {

		try {
			CustomResponse validationRes = validationUtil.validateGetWorkorderLabourWork(labourWorkDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}
			Workorder workorder = workorderDao.fetchWorkorderById(labourWorkDTO.getWorkorderId());
			if (workorder == null)
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Provide valid workorderId.");

			WorkorderLabourWork labourWork = workorder.getLabourWork();
			if (labourWork == null) {
				return new CustomResponse(Responses.NOT_FOUND.getCode(), null, "Labour work is not defined yet.");
			}
			List<WorkorderLabourWorkItemMapping> labourWorkItems = labourWorkDao
					.fetchWorkorderLabourWorkItemsByLabourWorkId(labourWork.getId());

			List<WorkorderLabourWorkItemGetResponse> workItemsDto = new ArrayList<>();
			Double labourWorkAmount = 0.0;
			if (labourWorkItems != null) {

				for (WorkorderLabourWorkItemMapping workItem : labourWorkItems) {
					WorkorderLabourWorkItemGetResponse workItemDto = setObject
							.workorderLabourWorkItemMappingEntityToGetResponse(workItem);
					labourWorkAmount += workItemDto.getAmount();
					workItemsDto.add(workItemDto);
				}

			}

			WorkorderLabourWorkGetResponse resultResponse = new WorkorderLabourWorkGetResponse(labourWork.getId(),
					labourWork.getWorkScope(), labourWork.getAnnexureNote(), workItemsDto, labourWorkAmount);
			return new CustomResponse(Responses.SUCCESS.getCode(), resultResponse, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	public CustomResponse versioningWorkorderLabourWorkFlow(AmendWorkorderInvocationUpdateStateRequest clientRequestDTO,
			Long workorderId) {

		try {
			Workorder workorder = workorderDao.fetchWorkorderById(workorderId);
			if (workorder == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");

			WorkorderLabourWork labourWork = labourWorkDao.fetchWorkorderLabourWorkByWorkorderId(workorderId);
			if (labourWork == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid labourWorkId.");

			WorkorderVersion workorderVersion = setObject.workorderEntityToVersionEntity(workorder);
			workorderVersion.setCreatedBy(clientRequestDTO.getUserDetail().getId());
			Long workorderVersionId = workorderDao.saveWorkorderVersion(workorderVersion);

			if (workorderVersionId == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderVersionId.");
			}

			WorkorderLabourWorkVersion labourWorkVersion = setObject
					.workorderLabourWorkEntityToVersionEntity(labourWork);
			labourWorkVersion.setWorkorderVersionId(workorderVersionId);
			labourWorkVersion.setCreatedBy(clientRequestDTO.getUserDetail().getId());

			Long labourWorkVersionId = labourWorkDao.saveWorkorderLabourWorkVersion(labourWorkVersion);

			if (labourWorkVersionId != null && labourWorkVersionId > 0L) {

				List<WorkorderLabourWorkItemMapping> workorderLabourWorkItemList = labourWorkDao
						.fetchWorkorderLabourWorkItemsByLabourWorkId(labourWork.getId());

				if (workorderLabourWorkItemList != null && !workorderLabourWorkItemList.isEmpty()) {

					for (WorkorderLabourWorkItemMapping itemObj : workorderLabourWorkItemList) {

						WorkorderLabourWorkItemMappingVersion itemVersionObj = setObject
								.workorderLabourWorkItemMappingEntityToVersionEntity(itemObj);
						itemVersionObj.setWorkorderLabourWorkVersionId(labourWorkVersionId);
						itemVersionObj.setCreatedBy(clientRequestDTO.getUserDetail().getId());

						labourWorkDao.saveWorkorderLabourWorkItemMapVersion(itemVersionObj);

					}

				}

			}

			return new CustomResponse(Responses.SUCCESS.getCode(), labourWorkVersionId, "Added.");

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse amendWorkorderLabourWorkFlow(UserDetail userDetail, Long amendWorkorderId, Long workorderId) {

		try {

			WorkorderLabourWork labourWork = labourWorkDao.fetchWorkorderLabourWorkByWorkorderId(workorderId);
			if (labourWork == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid labourWorkId.");

			WorkorderLabourWork amendLabourWork = setObject.workorderLabourWorkEntityToAmendEntity(labourWork);
			amendLabourWork.setWorkorderId(amendWorkorderId);
			amendLabourWork.setCreatedBy(userDetail.getId());
			amendLabourWork.setModifiedBy(userDetail.getId());

			Long amendLabourWorkId = labourWorkDao.saveWorkorderLabourWork(amendLabourWork);

			if (amendLabourWorkId != null && amendLabourWorkId > 0L) {

				List<WorkorderLabourWorkItemMapping> workorderLabourWorkItemList = labourWorkDao
						.fetchWorkorderLabourWorkItemsByLabourWorkId(labourWork.getId());

				if (workorderLabourWorkItemList != null && !workorderLabourWorkItemList.isEmpty()) {

					for (WorkorderLabourWorkItemMapping itemObj : workorderLabourWorkItemList) {

						WorkorderLabourWorkItemMapping amendItemObj = setObject
								.workorderLabourWorkItemMappingEntityToAmendEntity(itemObj);
						amendItemObj.setWorkorderLabourWorkId(amendLabourWorkId);
						amendItemObj.setCreatedBy(userDetail.getId());
						amendItemObj.setModifiedBy(userDetail.getId());
						labourWorkDao.saveWorkorderLabourWorkItemMap(amendItemObj);

					}

				}

			}

			return new CustomResponse(Responses.SUCCESS.getCode(), amendLabourWorkId, Responses.SUCCESS.name());

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
