package erp.workorder.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import erp.workorder.dao.BoqItemDao;
import erp.workorder.dao.WorkorderDao;
import erp.workorder.dao.WorkorderTransportationWorkDao;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.UserDetail;
import erp.workorder.dto.request.AmendWorkorderInvocationUpdateStateRequest;
import erp.workorder.dto.request.WorkorderTransportationWorkAddUpdateRequestDTO;
import erp.workorder.dto.request.WorkorderTransportationWorkGetRequestDTO;
import erp.workorder.dto.request.WorkorderTransportationWorkItemAddUpdateRequest;
import erp.workorder.dto.request.WorkorderTransportationWorkItemDeactivateRequestDTO;
import erp.workorder.dto.response.BoqItemResponse;
import erp.workorder.dto.response.WorkorderTransportWorkGetResponse;
import erp.workorder.dto.response.WorkorderTransportWorkItemGetResponse;
import erp.workorder.entity.BoqItem;
import erp.workorder.entity.Workorder;
import erp.workorder.entity.WorkorderTransportWork;
import erp.workorder.entity.WorkorderTransportWorkItemMapping;
import erp.workorder.entity.WorkorderTransportWorkItemMappingVersion;
import erp.workorder.entity.WorkorderTransportWorkVersion;
import erp.workorder.entity.WorkorderVersion;
import erp.workorder.enums.Responses;
import erp.workorder.enums.WorkorderTypes;
import erp.workorder.service.WorkorderTransportationWorkService;
import erp.workorder.util.CustomValidationUtil;
import erp.workorder.util.SetObject;

@Service
@Transactional
public class WorkorderTransportationWorkServiceImpl implements WorkorderTransportationWorkService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WorkorderTransportationWorkDao transportWorkDao;

	@Autowired
	private CustomValidationUtil validationUtil;

	@Autowired
	private SetObject setObject;

	@Autowired
	private WorkorderDao workorderDao;

	@Autowired
	private BoqItemDao boqDao;

	@Override
	public CustomResponse addOrUpdateWorkorderTransportationWork(
			WorkorderTransportationWorkAddUpdateRequestDTO transportWorkDTO) {
		try {
			CustomResponse validationRes = validationUtil.validateAddOrUpdateWorkorderTransportWork(transportWorkDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}
			Workorder workorder = workorderDao.fetchWorkorderById(transportWorkDTO.getWorkorderId());
			if (workorder == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");

			if (!(workorder.getType().getId().equals(WorkorderTypes.Transportation.getId()))) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Inappropriate workorder type.");
			}

//			add work with items
			if (transportWorkDTO.getTransportWorkId() == null) {

				WorkorderTransportWork transportWork = setObject
						.workorderTransportationWorkAddUpdateRequestDtoToEntity(transportWorkDTO);
				Long transportWorkId = transportWorkDao.saveWorkorderTransportWork(transportWork);

				if (transportWorkId != null && transportWorkId > 0L) {
					transportWork.setId(transportWorkId);
					for (WorkorderTransportationWorkItemAddUpdateRequest workItemRequest : transportWorkDTO
							.getTransportWorkItems()) {
						WorkorderTransportWorkItemMapping workItem = setObject
								.workorderTransportWorkItemAddUpdateRequestDtoToEntity(workItemRequest);
						workItem.setWorkorderTransportWorkId(transportWorkId);
						workItem.setCreatedBy(transportWorkDTO.getUserDetail().getId());
						workItem.setModifiedBy(transportWorkDTO.getUserDetail().getId());

						transportWorkDao.saveWorkorderTransportWorkItemMap(workItem);
					}

					workorder.setTransportWork(transportWork);
					workorder.setModifiedOn(new Date());
					workorder.setModifiedBy(transportWorkDTO.getUserDetail().getId());
					workorderDao.forceUpdateWorkorder(workorder);
					return new CustomResponse(Responses.SUCCESS.getCode(), transportWorkId, "Added.");
				}
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Already exists.");
			}
//			update work without items
			else {
				WorkorderTransportWork transportWork = transportWorkDao
						.fetchWorkorderTransportWorkById(transportWorkDTO.getTransportWorkId());
				if (transportWork == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid transportWorkId.");
				}
				transportWork = setObject.updatedWorkorderTransportWorkEntityFromRequestDto(transportWork,
						transportWorkDTO);
				Boolean isUpdated = transportWorkDao.updateWorkorderTransportWork(transportWork);
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
	public CustomResponse getWorkorderTransportationWork(WorkorderTransportationWorkGetRequestDTO transportWorkDTO) {

		try {
			CustomResponse validationRes = validationUtil.validateGetWorkorderTransportWork(transportWorkDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}
			Workorder workorder = workorderDao.fetchWorkorderById(transportWorkDTO.getWorkorderId());
			if (workorder == null)
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Provide valid workorderId.");

			WorkorderTransportWork transportWork = workorder.getTransportWork();
			if (transportWork == null) {
				return new CustomResponse(Responses.NOT_FOUND.getCode(), null, "Transport work is not defined yet.");
			}
			List<WorkorderTransportWorkItemMapping> transportWorkItems = transportWorkDao
					.fetchWorkorderTransportWorkItemsByTransportWorkId(transportWork.getId());

			List<WorkorderTransportWorkItemGetResponse> workItemsDto = new ArrayList<>();
			Double transportWorkAmount = 0.0;
			if (transportWorkItems != null) {

				for (WorkorderTransportWorkItemMapping workItem : transportWorkItems) {
					WorkorderTransportWorkItemGetResponse workItemDto = setObject
							.workorderTransportWorkItemMappingEntityToGetResponse(workItem);
					transportWorkAmount += workItemDto.getAmount();
					workItemsDto.add(workItemDto);
				}

			}

			WorkorderTransportWorkGetResponse resultResponse = new WorkorderTransportWorkGetResponse(
					transportWork.getId(), transportWork.getWorkScope(), transportWork.getAnnexureNote(), workItemsDto,
					transportWorkAmount);
			return new CustomResponse(Responses.SUCCESS.getCode(), resultResponse, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse addOrUpdateWorkorderTransportationWorkItem(
			WorkorderTransportationWorkItemAddUpdateRequest transportWorkItemDTO) {
		try {
			CustomResponse validationRes = validationUtil
					.validateUpdateWorkorderTransportWorkItem(transportWorkItemDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}
//			update transport work item
			if (transportWorkItemDTO.getTransportWorkItemId() != null) {

				WorkorderTransportWorkItemMapping transportWorkItem = transportWorkDao
						.fetchWorkorderTransportWorkItemMappingById(transportWorkItemDTO.getTransportWorkItemId());
				if (transportWorkItem == null) {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
							"Provide valid transportWorkItemId.");
				}

				transportWorkItem = setObject.updatedWorkorderTransportWorkItemMapEntityFromRequestDto(
						transportWorkItem, transportWorkItemDTO);
				Boolean isUpdated = transportWorkDao.updateWorkorderTransportWorkItem(transportWorkItem);

				return new CustomResponse(Responses.SUCCESS.getCode(), null,
						isUpdated ? "Updated." : "Already exists.");
			}
//			add transport work item
			else {
				WorkorderTransportWork transportWork = transportWorkDao
						.fetchWorkorderTransportWorkById(transportWorkItemDTO.getTransportWorkId());
				if (transportWork == null) {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Provide valid transportWorkId.");
				}

				WorkorderTransportWorkItemMapping workItem = setObject
						.workorderTransportWorkItemAddUpdateRequestDtoToEntity(transportWorkItemDTO);

				workItem.setWorkorderTransportWorkId(transportWork.getId());
				workItem.setCreatedBy(transportWorkItemDTO.getUserDetail().getId());
				workItem.setModifiedBy(transportWorkItemDTO.getUserDetail().getId());

				Long workItemId = transportWorkDao.saveWorkorderTransportWorkItemMap(workItem);

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
	public CustomResponse deactivateWorkorderTransportationWorkItem(
			WorkorderTransportationWorkItemDeactivateRequestDTO transportWorkItemDTO) {

		try {
			CustomResponse validationRes = validationUtil
					.validateDeactivateWorkorderTransportWorkItem(transportWorkItemDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}
			WorkorderTransportWorkItemMapping transportWorkItem = transportWorkDao
					.fetchWorkorderTransportWorkItemMappingById(transportWorkItemDTO.getTransportWorkItemId());
			if (transportWorkItem == null) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Provide valid transportWorkItemId.");
			}
			List<WorkorderTransportWorkItemMapping> transportWorkItems = transportWorkDao
					.fetchWorkorderTransportWorkItemsByTransportWorkId(transportWorkItem.getWorkorderTransportWorkId());
			if (transportWorkItems.size() == 1 && transportWorkItem.getIsActive()) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Need atleast one item existed.");
			}
			transportWorkItem.setModifiedOn(new Date());
			transportWorkItem.setModifiedBy(transportWorkItemDTO.getUserDetail().getId());
			transportWorkItem.setIsActive(false);
			transportWorkDao.forceUpdateWorkorderTransportWorkItem(transportWorkItem);
			return new CustomResponse(Responses.SUCCESS.getCode(), null, "Removed.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getWorkorderTransportationBoqDescriptions(Integer companyId) {

		try {

			List<BoqItem> boqs = boqDao.fetchBoqItemsByCompanyId(companyId);
			List<BoqItemResponse> resultList = new ArrayList<>();
			if (boqs != null) {
				for (BoqItem boq : boqs) {
					resultList.add(new BoqItemResponse(boq.getId(), boq.getCode(), boq.getStandardBookCode(),
							boq.getName(), boq.getCategory() != null ? boq.getCategory().getName() : null,
							boq.getSubcategory() != null ? boq.getSubcategory().getName() : null));
				}
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), resultList, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	public CustomResponse versioningWorkorderTransportWorkFlow(
			AmendWorkorderInvocationUpdateStateRequest clientRequestDTO, Long workorderId) {

		try {
			Workorder workorder = workorderDao.fetchWorkorderById(workorderId);
			if (workorder == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");

			WorkorderTransportWork transportWork = transportWorkDao
					.fetchWorkorderTransportWorkByWorkorderId(workorderId);
			if (transportWork == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid labourWorkId.");

			WorkorderVersion workorderVersion = setObject.workorderEntityToVersionEntity(workorder);
			workorderVersion.setCreatedBy(clientRequestDTO.getUserDetail().getId());
			Long workorderVersionId = workorderDao.saveWorkorderVersion(workorderVersion);

			if (workorderVersionId == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderVersionId.");
			}

			WorkorderTransportWorkVersion transportWorkVersion = setObject
					.workorderTransportWorkEntityToVersionEntity(transportWork);
			transportWorkVersion.setWorkorderVersionId(workorderVersionId);
			transportWorkVersion.setCreatedBy(clientRequestDTO.getUserDetail().getId());

			Long transportWorkVersionId = transportWorkDao.saveWorkorderTransportWorkVersion(transportWorkVersion);

			if (transportWorkVersionId != null && transportWorkVersionId > 0L) {

				List<WorkorderTransportWorkItemMapping> workorderTransportWorkItemList = transportWorkDao
						.fetchWorkorderTransportWorkItemsByTransportWorkId(transportWork.getId());

				if (workorderTransportWorkItemList != null && !workorderTransportWorkItemList.isEmpty()) {

					for (WorkorderTransportWorkItemMapping itemObj : workorderTransportWorkItemList) {

						WorkorderTransportWorkItemMappingVersion itemVersionObj = setObject
								.workorderTransportWorkItemMappingEntityToVersionEntity(itemObj);
						itemVersionObj.setWorkorderTransportWorkVersionId(transportWorkVersionId);
						itemVersionObj.setCreatedBy(clientRequestDTO.getUserDetail().getId());

						transportWorkDao.saveWorkorderTransportWorkItemMapVersion(itemVersionObj);

					}

				}

			}

			return new CustomResponse(Responses.SUCCESS.getCode(), transportWorkVersionId, "Added.");

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse amendWorkorderTransportWorkFlow(UserDetail userDetail, Long amendWorkorderId,
			Long workorderId) {

		try {
			WorkorderTransportWork transportWork = transportWorkDao
					.fetchWorkorderTransportWorkByWorkorderId(workorderId);
			if (transportWork == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid labourWorkId.");

			WorkorderTransportWork amendTransportWork = setObject
					.workorderTransportWorkEntityToAmendEntity(transportWork);

			amendTransportWork.setWorkorderId(amendWorkorderId);
			amendTransportWork.setCreatedBy(userDetail.getId());
			amendTransportWork.setModifiedBy(userDetail.getId());

			Long amendTransportWorkId = transportWorkDao.saveWorkorderTransportWork(amendTransportWork);

			if (amendTransportWorkId != null && amendTransportWorkId > 0L) {

				List<WorkorderTransportWorkItemMapping> workorderTransportWorkItemList = transportWorkDao
						.fetchWorkorderTransportWorkItemsByTransportWorkId(transportWork.getId());

				if (workorderTransportWorkItemList != null && !workorderTransportWorkItemList.isEmpty()) {

					for (WorkorderTransportWorkItemMapping itemObj : workorderTransportWorkItemList) {

						WorkorderTransportWorkItemMapping amendItemObj = setObject
								.workorderTransportWorkItemMappingEntityToAmendEntity(itemObj);
						amendItemObj.setWorkorderTransportWorkId(amendTransportWorkId);
						amendItemObj.setCreatedBy(userDetail.getId());
						amendItemObj.setModifiedBy(userDetail.getId());

						transportWorkDao.saveWorkorderTransportWorkItemMap(amendItemObj);

					}

				}

			}

			return new CustomResponse(Responses.SUCCESS.getCode(), amendTransportWorkId, Responses.SUCCESS.name());

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
