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

import erp.workorder.dao.WorkorderDao;
import erp.workorder.dao.WorkorderEquipmentIssueDao;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.EquipmentCategoryDTO;
import erp.workorder.dto.EquipmentDTO;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.WorkorderEquipmentIssueDTO;
import erp.workorder.dto.response.MachineCategoryGetResponse;
import erp.workorder.dto.response.MachineGetResponse;
import erp.workorder.entity.Equipment;
import erp.workorder.entity.EquipmentCategory;
import erp.workorder.entity.Unit;
import erp.workorder.entity.Workorder;
import erp.workorder.entity.WorkorderEquipmentIssue;
import erp.workorder.enums.EngineStates;
import erp.workorder.enums.EquipmentIssueCostPeriod;
import erp.workorder.enums.MachineType;
import erp.workorder.enums.Responses;
import erp.workorder.service.WorkorderEquipmentIssueService;
import erp.workorder.util.SetObject;

@Service
@Transactional
public class WorkorderEquipmentIssueServiceImpl implements WorkorderEquipmentIssueService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private WorkorderEquipmentIssueDao woEquipmentIssueDao;

	@Autowired
	private SetObject setObject;

	@Autowired
	private WorkorderDao workorderDao;

	@Override
	public CustomResponse getEquipmentCategories(SearchDTO search) {

		try {
			List<EquipmentCategoryDTO> resultCategories = new ArrayList<>();
			List<EquipmentCategory> categories = woEquipmentIssueDao.fetchEquipmentCategories(search);
			if (categories != null) {
				categories.forEach(item -> resultCategories.add(setObject.equipmentCategoryEntityToDto(item)));
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), resultCategories, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getIssueCostPeriods(SearchDTO search) {

		try {
			Map<Integer, String> costPeriods = new HashMap<>();
			for (EquipmentIssueCostPeriod costPeriod : EquipmentIssueCostPeriod.values()) {
				costPeriods.put(costPeriod.ordinal(), costPeriod.toString());
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), costPeriods, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getEquipments(SearchDTO search) {

		try {
			List<EquipmentDTO> resultEquipments = new ArrayList<>();
			List<Equipment> equipments = woEquipmentIssueDao.fetchEquipments(search);
			if (equipments != null) {
				equipments.forEach(item -> resultEquipments.add(setObject.equipmentEntityToDto(item)));
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), resultEquipments, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse issueEquipment(WorkorderEquipmentIssueDTO woEquipmentIssueDTO) {

		try {
			WorkorderEquipmentIssue woEquipmentIssue = setObject
					.workorderEquipmentIssueDtoToEntity(woEquipmentIssueDTO);
			if (woEquipmentIssue.getRunningCost() != null && woEquipmentIssue.getCostPeriod() == null) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Select wage period along with wages.");
			}
			Workorder workorder = workorderDao.fetchDetachedWorkorderById(woEquipmentIssue.getWorkorderId());
			if (!workorder.getState().getId().equals(EngineStates.Draft.getValue()))
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Workorder is not in draft state.");
			woEquipmentIssue.setModifiedOn(new Date());
			woEquipmentIssue.setIsActive(true);
			Long id = woEquipmentIssueDao.issueEquipment(woEquipmentIssue);
			return new CustomResponse(Responses.SUCCESS.getCode(), id, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse deactivateIssuedEquipment(SearchDTO search) {
		try {

			WorkorderEquipmentIssue issuedEquipment = woEquipmentIssueDao.fetchIssuedEquipmentById(search.getId());
			Workorder workorder = workorderDao.fetchDetachedWorkorderById(issuedEquipment.getWorkorderId());
			if (!workorder.getState().getId().equals(EngineStates.Draft.getValue()))
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Workorder is not in draft state.");
			issuedEquipment.setModifiedBy(search.getUserId());
			issuedEquipment.setModifiedOn(new Date());
			issuedEquipment.setIsActive(false);
			Boolean result = woEquipmentIssueDao.updateIssuedEquipment(issuedEquipment);
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

// TODO : TRANSACTION
	@Override
	public CustomResponse updateIssuedEquipment(WorkorderEquipmentIssueDTO woEquipmentIssue) {

		try {
			WorkorderEquipmentIssue issuedEquipment = setObject.workorderEquipmentIssueDtoToEntity(woEquipmentIssue);
			Workorder workorder = workorderDao.fetchDetachedWorkorderById(issuedEquipment.getWorkorderId());
			if (!workorder.getState().getId().equals(EngineStates.Draft.getValue()))
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Workorder is not in draft state.");
			if (woEquipmentIssue.getRunningCost() != null && woEquipmentIssue.getCostPeriod() == null) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Select wage period along with wages.");
			}
			issuedEquipment.setModifiedOn(new Date());
			Boolean result = woEquipmentIssueDao.updateIssuedEquipment(issuedEquipment);
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getCategoryMachines(Long siteId) {

		try {
			List<MachineCategoryGetResponse> resultList = new ArrayList<>();
			Set<Long> distinctMachineCategories = new HashSet<>();
			List<Equipment> equipments = woEquipmentIssueDao.fetchHiredEquipments(siteId);
			if (equipments != null) {
				equipments.forEach(item -> distinctMachineCategories.add(item.getCategory().getId()));
			}
			for (Long categoryId : distinctMachineCategories) {
				String categoryName = null;
				boolean isMultifuel = false;
				Unit primaryReadingUnit = null;
				Unit secondaryReadingUnit = null;
				List<MachineGetResponse> machinesList = new ArrayList<>();

//				for equipments
				for (Equipment equipment : equipments) {
					if (equipment.getCategory().getId().equals(categoryId)) {
						if (categoryName == null) {
							categoryName = equipment.getCategory().getName();
							primaryReadingUnit = equipment.getCategory().getPrimaryReadingUnit();
							secondaryReadingUnit = equipment.getCategory().getSecondaryReadingUnit();
							isMultifuel = equipment.getCategory().getIsMultiFuel() != null
									? equipment.getCategory().getIsMultiFuel()
									: false;
						}
						machinesList.add(new MachineGetResponse(equipment.getId(), equipment.getAssetCode(),
								equipment.getRegNo(), (byte) MachineType.Equipment.ordinal()));
					}
				}

				resultList.add(new MachineCategoryGetResponse(categoryId, categoryName, machinesList, isMultifuel,
						primaryReadingUnit != null ? primaryReadingUnit.getId() : null,
						primaryReadingUnit != null ? primaryReadingUnit.getName() : null,
						secondaryReadingUnit != null ? secondaryReadingUnit.getId() : null,
						secondaryReadingUnit != null ? secondaryReadingUnit.getName() : null));
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), resultList, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getHiredMachines(Long siteId, Long categoryId) {

		try {
			List<Equipment> equipments = woEquipmentIssueDao.fetchHiredEquipments(siteId, categoryId);

			List<MachineGetResponse> machinesList = new ArrayList<>();

//			for equipments
			if (equipments != null) {

				for (Equipment equipment : equipments) {
					machinesList.add(new MachineGetResponse(equipment.getId(), equipment.getAssetCode(),
							equipment.getRegNo(), (byte) MachineType.Equipment.ordinal()));
				}
			}

			return new CustomResponse(Responses.SUCCESS.getCode(), machinesList, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
