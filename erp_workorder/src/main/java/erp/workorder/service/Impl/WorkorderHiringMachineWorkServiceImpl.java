package erp.workorder.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import erp.workorder.dao.WorkorderDao;
import erp.workorder.dao.WorkorderHiringMachineWorkDao;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.UserDetail;
import erp.workorder.dto.request.AmendWorkorderInvocationUpdateStateRequest;
import erp.workorder.dto.request.WorkorderHiringMachineRateDetailsAddUpdateRequest;
import erp.workorder.dto.request.WorkorderHiringMachineWorkAddUpdateRequestDTO;
import erp.workorder.dto.request.WorkorderHiringMachineWorkGetRequestDTO;
import erp.workorder.dto.request.WorkorderHiringMachineWorkItemAddUpdateRequest;
import erp.workorder.dto.request.WorkorderHiringMachineWorkItemDeactivateRequestDTO;
import erp.workorder.dto.response.HiringMachineDurationTypeGetResponse;
import erp.workorder.dto.response.WorkorderHiringMachineRateDetailsResponse;
import erp.workorder.dto.response.WorkorderHiringMachineWorkGetResponse;
import erp.workorder.dto.response.WorkorderHiringMachineWorkItemGetResponse;
import erp.workorder.entity.UnitMaster;
import erp.workorder.entity.Workorder;
import erp.workorder.entity.WorkorderHiringMachineRateDetailVersion;
import erp.workorder.entity.WorkorderHiringMachineRateDetails;
import erp.workorder.entity.WorkorderHiringMachineWork;
import erp.workorder.entity.WorkorderHiringMachineWorkItemMapping;
import erp.workorder.entity.WorkorderHiringMachineWorkItemMappingVersion;
import erp.workorder.entity.WorkorderHiringMachineWorkVersion;
import erp.workorder.entity.WorkorderVersion;
import erp.workorder.enums.MachineryRunningMode;
import erp.workorder.enums.Responses;
import erp.workorder.enums.WorkorderTypes;
import erp.workorder.service.WorkorderHiringMachineWorkService;
import erp.workorder.util.CustomValidationUtil;
import erp.workorder.util.SetObject;

@Service
@Transactional
public class WorkorderHiringMachineWorkServiceImpl implements WorkorderHiringMachineWorkService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WorkorderHiringMachineWorkDao hmWorkDao;

	@Autowired
	private CustomValidationUtil validationUtil;

	@Autowired
	private SetObject setObject;

	@Autowired
	private WorkorderDao workorderDao;

	@Override
	public CustomResponse addOrUpdateWorkorderHiringMachineWork(
			WorkorderHiringMachineWorkAddUpdateRequestDTO hmWorkDTO) {
		try {
			CustomResponse validationRes = validationUtil.validateAddOrUpdateWorkorderHiringMachineWork(hmWorkDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}
			Workorder workorder = workorderDao.fetchWorkorderById(hmWorkDTO.getWorkorderId());
			if (workorder == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");

			if (!(workorder.getType().getId().equals(WorkorderTypes.Machine_Hiring.getId()))) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Inappropriate workorder type.");
			}

//			add work with items
			if (hmWorkDTO.getHmWorkId() == null) {

				WorkorderHiringMachineWork hmWork = setObject
						.workorderHiringMachineWorkAddUpdateRequestDtoToEntity(hmWorkDTO);
				Long hmWorkId = hmWorkDao.saveWorkorderHiringMachineWork(hmWork);

				if (hmWorkId != null && hmWorkId > 0L) {
					hmWork.setId(hmWorkId);
					for (WorkorderHiringMachineWorkItemAddUpdateRequest workItemRequest : hmWorkDTO.getHmWorkItems()) {
						WorkorderHiringMachineWorkItemMapping workItem = setObject
								.workorderHiringMachineWorkItemAddUpdateRequestDtoToEntity(workItemRequest);
						workItem.setWorkorderHmWorkId(hmWorkId);
						workItem.setCreatedBy(hmWorkDTO.getUserDetail().getId());
						workItem.setModifiedBy(hmWorkDTO.getUserDetail().getId());

						Long workItemId = hmWorkDao.saveWorkorderHiringMachineWorkItemMap(workItem);

						if (workItemId != null && workItemRequest.getRunningMode() != null) {

							addOrUpdateHiringMachineItemRateDetails(workItemId, workItemRequest.getRunningMode(),
									workItemRequest.getMachineRateDetails(), hmWorkDTO.getUserDetail());

						}
					}

					workorder.setHiringMachineWork(hmWork);
					workorder.setModifiedOn(new Date());
					workorder.setModifiedBy(hmWorkDTO.getUserDetail().getId());
					workorderDao.forceUpdateWorkorder(workorder);
					return new CustomResponse(Responses.SUCCESS.getCode(), hmWorkId, "Added.");
				}
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Already exists.");
			}
//			update work without items
			else {
				WorkorderHiringMachineWork hmWork = hmWorkDao
						.fetchWorkorderHiringMachineWorkById(hmWorkDTO.getHmWorkId());
				if (hmWork == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid hmWorkId.");
				}
				hmWork = setObject.updatedWorkorderHiringMachineWorkEntityFromRequestDto(hmWork, hmWorkDTO);
				Boolean isUpdated = hmWorkDao.updateWorkorderHiringMachineWork(hmWork);
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

	private Boolean addOrUpdateHiringMachineItemRateDetails(Long workItemId, MachineryRunningMode runningMode,
			List<WorkorderHiringMachineRateDetailsAddUpdateRequest> machineRateDetails, UserDetail userDetail) {

		try {

			Set<Long> distinctWorkItemIds = new HashSet<>();
			distinctWorkItemIds.add(workItemId);

			List<WorkorderHiringMachineRateDetails> itemRateDetailDbList = hmWorkDao
					.fetchHiringMachineItemRateDetailsByWorkItemIds(distinctWorkItemIds);

			if (runningMode.equals(MachineryRunningMode.TRIP)) {

				WorkorderHiringMachineRateDetails itemRateDetail = new WorkorderHiringMachineRateDetails(null,
						workItemId, null, null, true, new Date(), userDetail.getId().intValue());

				for (WorkorderHiringMachineRateDetailsAddUpdateRequest rateDetailReqObj : machineRateDetails) {

					if (rateDetailReqObj.getShift() == null) {
						itemRateDetail.setRate(rateDetailReqObj.getRate());
					}

				}

				List<WorkorderHiringMachineRateDetails> listToDeactivate = new ArrayList<>();
				if (itemRateDetailDbList != null && !itemRateDetailDbList.isEmpty()) {
					for (WorkorderHiringMachineRateDetails dbObj : itemRateDetailDbList) {
						if (dbObj.getShift() == null && itemRateDetail.getId() == null) {
							itemRateDetail.setId(dbObj.getId());
						} else {
							dbObj.setIsActive(false);
							dbObj.setUpdatedOn(new Date());
							dbObj.setUpdatedBy(userDetail.getId().intValue());
							listToDeactivate.add(dbObj);
						}
					}

				}

				hmWorkDao.saveOrUpdateHiringMachineItemRateDetail(itemRateDetail);

				for (WorkorderHiringMachineRateDetails obj : listToDeactivate) {
					hmWorkDao.forceUpdateHiringMachineItemRateDetail(obj);
				}

			} else {
				List<WorkorderHiringMachineRateDetails> listToSaveOrUpdate = new ArrayList<>();
				List<WorkorderHiringMachineRateDetails> listToDeactivate = new ArrayList<>();

				for (WorkorderHiringMachineRateDetails dbObj : itemRateDetailDbList) {

					Boolean hasFound = false;
					for (WorkorderHiringMachineRateDetailsAddUpdateRequest obj : machineRateDetails) {
						if (dbObj.getShift() == null) {
							dbObj.setIsActive(false);
							dbObj.setUpdatedOn(new Date());
							dbObj.setUpdatedBy(userDetail.getId().intValue());
							listToDeactivate.add(dbObj);
						} else if (dbObj.getShift().equals(obj.getShift())) {
							dbObj.setRate(obj.getRate());
							dbObj.setUpdatedOn(new Date());
							dbObj.setUpdatedBy(userDetail.getId().intValue());
							listToSaveOrUpdate.add(dbObj);
							hasFound = true;
						}

					}
					if (!hasFound) {
						dbObj.setIsActive(false);
						dbObj.setUpdatedOn(new Date());
						dbObj.setUpdatedBy(userDetail.getId().intValue());
						listToDeactivate.add(dbObj);
					}
				}

				for (WorkorderHiringMachineRateDetailsAddUpdateRequest obj : machineRateDetails) {

					Boolean hasFound = false;
					for (WorkorderHiringMachineRateDetails dbObj : itemRateDetailDbList) {
						if (dbObj.getShift() != null && obj.getShift() != null
								&& dbObj.getShift().equals(obj.getShift())) {
							dbObj.setRate(obj.getRate());
							dbObj.setIsActive(true);
							listToSaveOrUpdate.add(dbObj);
							hasFound = true;
						}

					}
					if (!hasFound) {
						listToSaveOrUpdate.add(new WorkorderHiringMachineRateDetails(null, workItemId, obj.getShift(),
								obj.getRate(), true, new Date(), userDetail.getId().intValue()));
					}
				}

				for (WorkorderHiringMachineRateDetails obj : listToSaveOrUpdate) {
					hmWorkDao.saveOrUpdateHiringMachineItemRateDetail(obj);
				}
				for (WorkorderHiringMachineRateDetails obj : listToDeactivate) {
					hmWorkDao.forceUpdateHiringMachineItemRateDetail(obj);
				}

			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return false;
		}
	}

	@Override
	public CustomResponse getWorkorderHiringMachineWork(WorkorderHiringMachineWorkGetRequestDTO hmWorkDTO) {

		try {
			CustomResponse validationRes = validationUtil.validateGetWorkorderHiringMachineWork(hmWorkDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}
			Workorder workorder = workorderDao.fetchWorkorderById(hmWorkDTO.getWorkorderId());
			if (workorder == null)
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Provide valid workorderId.");
			WorkorderHiringMachineWork hmWork = workorder.getHiringMachineWork();
			if (hmWork == null) {
				return new CustomResponse(Responses.NOT_FOUND.getCode(), null,
						"Hiring Machine work is not defined yet.");
			}
			List<WorkorderHiringMachineWorkItemMapping> hmWorkItems = hmWorkDao
					.fetchWorkorderHiringMachineWorkItemsByHmWorkId(hmWork.getId());

			List<WorkorderHiringMachineWorkItemGetResponse> workItemsDto = new ArrayList<>();
			Double hmWorkAmount = 0.0;
			Double hmWorkRate = 0.0;
			if (hmWorkItems != null) {

				Set<Long> distinctHmWorkItemIds = new HashSet<>();
				hmWorkItems.forEach(obj -> distinctHmWorkItemIds.add(obj.getId()));
				List<WorkorderHiringMachineRateDetails> hmWorkItemsRateDetails = hmWorkDao
						.fetchHiringMachineItemRateDetailsByWorkItemIds(distinctHmWorkItemIds);

				for (WorkorderHiringMachineWorkItemMapping workItem : hmWorkItems) {
					List<WorkorderHiringMachineRateDetailsResponse> rateDetailsResponseObj = new ArrayList<>();

					if (hmWorkItemsRateDetails != null && !hmWorkItemsRateDetails.isEmpty())
						for (WorkorderHiringMachineRateDetails rateDetails : hmWorkItemsRateDetails) {
							if (workItem.getRunningMode() != null
									&& rateDetails.getWoHiringMachineItemId().equals(workItem.getId())) {
								hmWorkRate += rateDetails.getRate();
								rateDetailsResponseObj.add(new WorkorderHiringMachineRateDetailsResponse(
										rateDetails.getShift() != null ? rateDetails.getShift().getId() : null,
										rateDetails.getShift(), rateDetails.getRate()));
							}

						}
					WorkorderHiringMachineWorkItemGetResponse workItemDto = setObject
							.workorderHiringMachineWorkItemMappingEntityToGetResponse(workItem, hmWorkRate);
					workItemDto.setMachineRateDetails(rateDetailsResponseObj);
					hmWorkAmount += workItemDto.getAmount();
					workItemsDto.add(workItemDto);
				}
			}

			WorkorderHiringMachineWorkGetResponse resultResponse = new WorkorderHiringMachineWorkGetResponse(
					hmWork.getId(), hmWork.getWorkScope(), hmWork.getAnnexureNote(), workItemsDto,
					hmWork.getDieselRate(), hmWorkAmount);
			return new CustomResponse(Responses.SUCCESS.getCode(), resultResponse, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse addOrUpdateWorkorderHiringMachineWorkItem(
			WorkorderHiringMachineWorkItemAddUpdateRequest hmWorkItemDTO) {
		try {
			CustomResponse validationRes = validationUtil.validateUpdateWorkorderHiringMachineWorkItem(hmWorkItemDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}
//			update work item
			if (hmWorkItemDTO.getHmWorkItemId() != null) {

				WorkorderHiringMachineWorkItemMapping hmWorkItem = hmWorkDao
						.fetchWorkorderHiringMachineWorkItemMappingById(hmWorkItemDTO.getHmWorkItemId());
				if (hmWorkItem == null) {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Provide valid hmWorkItemId.");
				}

				hmWorkItem = setObject.updatedWorkorderHiringMachineWorkItemMapEntityFromRequestDto(hmWorkItem,
						hmWorkItemDTO);
				Boolean isUpdated = hmWorkDao.updateWorkorderHiringMachineWorkItem(hmWorkItem);
				if (isUpdated && hmWorkItemDTO.getRunningMode() != null) {

					addOrUpdateHiringMachineItemRateDetails(hmWorkItem.getId(), hmWorkItemDTO.getRunningMode(),
							hmWorkItemDTO.getMachineRateDetails(), hmWorkItemDTO.getUserDetail());

				}

				return new CustomResponse(Responses.SUCCESS.getCode(), null,
						isUpdated ? "Updated." : "Already exists.");
			}
//			add work item
			else {
				WorkorderHiringMachineWork hmWork = hmWorkDao
						.fetchWorkorderHiringMachineWorkById(hmWorkItemDTO.getHmWorkId());
				if (hmWork == null) {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Provide valid hmWorkId.");
				}

				WorkorderHiringMachineWorkItemMapping workItem = setObject
						.workorderHiringMachineWorkItemAddUpdateRequestDtoToEntity(hmWorkItemDTO);

				workItem.setWorkorderHmWorkId(hmWork.getId());
				workItem.setCreatedBy(hmWorkItemDTO.getUserDetail().getId());
				workItem.setModifiedBy(hmWorkItemDTO.getUserDetail().getId());

				Long workItemId = hmWorkDao.saveWorkorderHiringMachineWorkItemMap(workItem);

				if (workItemId != null && hmWorkItemDTO.getRunningMode() != null) {

					addOrUpdateHiringMachineItemRateDetails(workItemId, hmWorkItemDTO.getRunningMode(),
							hmWorkItemDTO.getMachineRateDetails(), hmWorkItemDTO.getUserDetail());

				}

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
	public CustomResponse deactivateWorkorderHiringMachineWorkItem(
			WorkorderHiringMachineWorkItemDeactivateRequestDTO hmWorkItemDTO) {

		try {
			CustomResponse validationRes = validationUtil
					.validateDeactivateWorkorderHiringMachineWorkItem(hmWorkItemDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}
			WorkorderHiringMachineWorkItemMapping hmWorkItem = hmWorkDao
					.fetchWorkorderHiringMachineWorkItemMappingById(hmWorkItemDTO.getHmWorkItemId());
			if (hmWorkItem == null) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Provide valid hmWorkItemId.");
			}
			List<WorkorderHiringMachineWorkItemMapping> hmWorkItems = hmWorkDao
					.fetchWorkorderHiringMachineWorkItemsByHmWorkId(hmWorkItem.getWorkorderHmWorkId());
			if (hmWorkItems.size() == 1 && hmWorkItem.getIsActive()) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Need atleast one item existed.");
			}
			hmWorkItem.setModifiedOn(new Date());
			hmWorkItem.setModifiedBy(hmWorkItemDTO.getUserDetail().getId());
			hmWorkItem.setIsActive(false);
			hmWorkDao.forceUpdateWorkorderHiringMachineWorkItem(hmWorkItem);
			return new CustomResponse(Responses.SUCCESS.getCode(), null, "Removed.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getWorkorderHiringMachineUnits() {

		try {
			List<UnitMaster> units = hmWorkDao.fetchHiringMachineUnits();
			if (units == null || units.size() == 0) {
				return new CustomResponse(Responses.SUCCESS.getCode(), null, "No Unit Exists.");
			}
			List<HiringMachineDurationTypeGetResponse> result = new ArrayList<>();
			for (UnitMaster unit : units) {
				result.add(new HiringMachineDurationTypeGetResponse(unit.getId(), unit.getName()));
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	public CustomResponse versioningWorkorderHiringMachineWorkFlow(
			AmendWorkorderInvocationUpdateStateRequest clientRequestDTO, Long workorderId) {

		try {
			Workorder workorder = workorderDao.fetchWorkorderById(workorderId);
			if (workorder == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");

			WorkorderHiringMachineWork hiringMachineWork = hmWorkDao
					.fetchWorkorderHiringMachineWorkByWorkorderId(workorderId);
			if (hiringMachineWork == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid hiringMachineWorkId.");

			WorkorderVersion workorderVersion = setObject.workorderEntityToVersionEntity(workorder);
			workorderVersion.setCreatedBy(clientRequestDTO.getUserDetail().getId());
			Long workorderVersionId = workorderDao.saveWorkorderVersion(workorderVersion);

			if (workorderVersionId == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderVersionId.");
			}

			WorkorderHiringMachineWorkVersion hiringMachineWorkVersion = setObject
					.workorderHiringMachineWorkEntityToVersionEntity(hiringMachineWork);
			hiringMachineWorkVersion.setWorkorderVersionId(workorderVersionId);
			hiringMachineWorkVersion.setCreatedBy(clientRequestDTO.getUserDetail().getId());

			Long hiringMachineWorkVersionId = hmWorkDao.saveWorkorderHiringMachineWorkVersion(hiringMachineWorkVersion);

			if (hiringMachineWorkVersionId != null && hiringMachineWorkVersionId > 0L) {

				List<WorkorderHiringMachineWorkItemMapping> workorderHiringMachineWorkItemList = hmWorkDao
						.fetchWorkorderHiringMachineWorkItemsByHmWorkId(hiringMachineWork.getId());

				Map<Long, Long> itemAndItemVersionIdsMap = new HashMap<>();

				if (workorderHiringMachineWorkItemList != null && !workorderHiringMachineWorkItemList.isEmpty()) {

					for (WorkorderHiringMachineWorkItemMapping itemObj : workorderHiringMachineWorkItemList) {

						WorkorderHiringMachineWorkItemMappingVersion itemVersionObj = setObject
								.workorderHiringMachineWorkItemMappingEntityToVersionEntity(itemObj);
						itemVersionObj.setWorkorderHmWorkVersionId(hiringMachineWorkVersionId);
						itemVersionObj.setCreatedBy(clientRequestDTO.getUserDetail().getId());
						Long itemVersionId = hmWorkDao.saveWorkorderHiringMachineWorkItemMapVersion(itemVersionObj);
						itemAndItemVersionIdsMap.put(itemObj.getId(), itemVersionId);

					}

					List<WorkorderHiringMachineRateDetails> rateDetailsList = hmWorkDao
							.fetchHiringMachineItemRateDetailsByWorkItemIds(itemAndItemVersionIdsMap.keySet());

					if (rateDetailsList != null && !rateDetailsList.isEmpty()) {

						for (WorkorderHiringMachineRateDetails workorderHiringMachineRateDetails : rateDetailsList) {

							WorkorderHiringMachineRateDetailVersion rateDetailVersionObj = setObject
									.workorderHiringMachineRateDetailEntityToVersionEntity(
											workorderHiringMachineRateDetails);
							rateDetailVersionObj.setWoHiringMachineItemVersionId(itemAndItemVersionIdsMap
									.get(workorderHiringMachineRateDetails.getWoHiringMachineItemId()));
							rateDetailVersionObj.setCreatedBy(clientRequestDTO.getUserDetail().getId());
							hmWorkDao.saveWorkorderHiringMachineRateDetailVersion(rateDetailVersionObj);

						}

					}

				}

			}

			return new CustomResponse(Responses.SUCCESS.getCode(), hiringMachineWorkVersionId, "Added.");

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse amendWorkorderHiringMachineWorkFlow(UserDetail userDetail, Long amendWorkorderId,
			Long workorderId) {

		try {

			WorkorderHiringMachineWork hiringMachineWork = hmWorkDao
					.fetchWorkorderHiringMachineWorkByWorkorderId(workorderId);
			if (hiringMachineWork == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid hiringMachineWorkId.");

			WorkorderHiringMachineWork amendHiringMachineWork = setObject
					.workorderHiringMachineWorkEntityToAmendEntity(hiringMachineWork);
			amendHiringMachineWork.setWorkorderId(amendWorkorderId);
			amendHiringMachineWork.setCreatedBy(userDetail.getId());
			amendHiringMachineWork.setModifiedBy(userDetail.getId());

			Long amendHiringMachineWorkId = hmWorkDao.saveWorkorderHiringMachineWork(amendHiringMachineWork);

			if (amendHiringMachineWorkId != null && amendHiringMachineWorkId > 0L) {

				List<WorkorderHiringMachineWorkItemMapping> workorderHiringMachineWorkItemList = hmWorkDao
						.fetchWorkorderHiringMachineWorkItemsByHmWorkId(hiringMachineWork.getId());

				Map<Long, Long> itemAndAmendItemIdsMap = new HashMap<>();

				if (workorderHiringMachineWorkItemList != null && !workorderHiringMachineWorkItemList.isEmpty()) {

					for (WorkorderHiringMachineWorkItemMapping itemObj : workorderHiringMachineWorkItemList) {

						WorkorderHiringMachineWorkItemMapping amendItemObj = setObject
								.workorderHiringMachineWorkItemMappingEntityToAmendEntity(itemObj);
						amendItemObj.setWorkorderHmWorkId(amendHiringMachineWorkId);
						amendItemObj.setCreatedBy(userDetail.getId());
						amendItemObj.setModifiedBy(userDetail.getId());
						Long amendItemId = hmWorkDao.saveWorkorderHiringMachineWorkItemMap(amendItemObj);
						itemAndAmendItemIdsMap.put(itemObj.getId(), amendItemId);

					}

					List<WorkorderHiringMachineRateDetails> rateDetailsList = hmWorkDao
							.fetchHiringMachineItemRateDetailsByWorkItemIds(itemAndAmendItemIdsMap.keySet());

					if (rateDetailsList != null && !rateDetailsList.isEmpty()) {

						for (WorkorderHiringMachineRateDetails workorderHiringMachineRateDetails : rateDetailsList) {

							WorkorderHiringMachineRateDetails amendRateDetailObj = setObject
									.workorderHiringMachineRateDetailEntityToAmendEntity(
											workorderHiringMachineRateDetails);
							amendRateDetailObj.setWoHiringMachineItemId(itemAndAmendItemIdsMap
									.get(workorderHiringMachineRateDetails.getWoHiringMachineItemId()));
							amendRateDetailObj.setUpdatedBy(userDetail.getId().intValue());
							hmWorkDao.saveOrUpdateHiringMachineItemRateDetail(amendRateDetailObj);

						}

					}

				}

			}

			return new CustomResponse(Responses.SUCCESS.getCode(), amendHiringMachineWorkId, Responses.SUCCESS.name());

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
