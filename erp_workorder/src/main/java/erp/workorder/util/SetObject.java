
package erp.workorder.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import erp.workorder.dto.BoqItemDTO;
import erp.workorder.dto.CategoryItemDTO;
import erp.workorder.dto.ChainageBoqParentChildDTO;
import erp.workorder.dto.ChainageBoqQuantityMappingDTO;
import erp.workorder.dto.ChainageBoqsRenderDTO;
import erp.workorder.dto.ChainageDTO;
import erp.workorder.dto.ChainageTraverseDTO;
import erp.workorder.dto.CompanyClientDTO;
import erp.workorder.dto.CompanyDTO;
import erp.workorder.dto.ContractorBankDetailDTO;
import erp.workorder.dto.ContractorBillingAddressDTO;
import erp.workorder.dto.ContractorDTO;
import erp.workorder.dto.CountryStateDTO;
import erp.workorder.dto.CurrencyDTO;
import erp.workorder.dto.DieselRateMappingDTO;
import erp.workorder.dto.DocumentTypeDTO;
import erp.workorder.dto.EmployeeDTO;
import erp.workorder.dto.EngineStateDTO;
import erp.workorder.dto.EquipmentCategoryDTO;
import erp.workorder.dto.EquipmentDTO;
import erp.workorder.dto.FileDTO;
import erp.workorder.dto.MaterialGroupDTO;
import erp.workorder.dto.S3FileDTO;
import erp.workorder.dto.SbqParentChildDTO;
import erp.workorder.dto.SbqRenderDTO;
import erp.workorder.dto.SiteDTO;
import erp.workorder.dto.SubcategoryItemDTO;
import erp.workorder.dto.UnitDTO;
import erp.workorder.dto.UnitTypeDTO;
import erp.workorder.dto.UserDetail;
import erp.workorder.dto.WoFormulaVariableDTO;
import erp.workorder.dto.WoTncDTO;
import erp.workorder.dto.WoTncFormulaVariableDTO;
import erp.workorder.dto.WoTncFormulaVariableValueDTO;
import erp.workorder.dto.WoTncMappingDTO;
import erp.workorder.dto.WoTncTypeDTO;
import erp.workorder.dto.WoTypeTncMappingDTO;
import erp.workorder.dto.WorkorderBoqWorkDTO;
import erp.workorder.dto.WorkorderBoqWorkLocationDTO;
import erp.workorder.dto.WorkorderBoqWorkParentChildDTO;
import erp.workorder.dto.WorkorderBoqWorkQtyMappingDTO;
import erp.workorder.dto.WorkorderContractorDTO;
import erp.workorder.dto.WorkorderDTO;
import erp.workorder.dto.WorkorderEquipmentIssueDTO;
import erp.workorder.dto.WorkorderFileDTO;
import erp.workorder.dto.WorkorderListDTO;
import erp.workorder.dto.WorkorderMasterVariableDTO;
import erp.workorder.dto.WorkorderMaterialConfigDTO;
import erp.workorder.dto.WorkorderTypeDTO;
import erp.workorder.dto.request.BillDeductionMappingDTO;
import erp.workorder.dto.request.MachineDprAddUpdateRequest;
import erp.workorder.dto.request.WorkorderBillInfoUpdateRequest;
import erp.workorder.dto.request.WorkorderConsultantWorkAddUpdateRequestDTO;
import erp.workorder.dto.request.WorkorderConsultantWorkItemAddUpdateRequest;
import erp.workorder.dto.request.WorkorderHiringMachineWorkAddUpdateRequestDTO;
import erp.workorder.dto.request.WorkorderHiringMachineWorkItemAddUpdateRequest;
import erp.workorder.dto.request.WorkorderLabourWorkAddUpdateRequestDTO;
import erp.workorder.dto.request.WorkorderLabourWorkItemAddUpdateRequest;
import erp.workorder.dto.request.WorkorderPayMilestoneAddUpdateRequest;
import erp.workorder.dto.request.WorkorderTransportationWorkAddUpdateRequestDTO;
import erp.workorder.dto.request.WorkorderTransportationWorkItemAddUpdateRequest;
import erp.workorder.dto.response.AmendWorkorderInvocationGetResponse;
import erp.workorder.dto.response.BillDeductionResponseDto;
import erp.workorder.dto.response.BoqItemResponse;
import erp.workorder.dto.response.WorkShiftScheduleResponse;
import erp.workorder.dto.response.WorkorderCloseGetResponse;
import erp.workorder.dto.response.WorkorderConsultantWorkItemGetResponse;
import erp.workorder.dto.response.WorkorderHiringMachineWorkItemGetResponse;
import erp.workorder.dto.response.WorkorderLabourWorkItemGetResponse;
import erp.workorder.dto.response.WorkorderTransportWorkItemGetResponse;
import erp.workorder.entity.AmendWorkorderInvocation;
import erp.workorder.entity.BillDeduction;
import erp.workorder.entity.BillDeductionMapTransac;
import erp.workorder.entity.BillDeductionMapping;
import erp.workorder.entity.BoqItem;
import erp.workorder.entity.CategoryItem;
import erp.workorder.entity.Chainage;
import erp.workorder.entity.ChainageBoqQuantityMapping;
import erp.workorder.entity.ChainageTraverse;
import erp.workorder.entity.Company;
import erp.workorder.entity.CompanyClient;
import erp.workorder.entity.Contractor;
import erp.workorder.entity.ContractorBankDetail;
import erp.workorder.entity.ContractorBillingAddress;
import erp.workorder.entity.CountryState;
import erp.workorder.entity.Currency;
import erp.workorder.entity.DieselRateMapping;
import erp.workorder.entity.DieselRateTransacs;
import erp.workorder.entity.DocumentType;
import erp.workorder.entity.Employee;
import erp.workorder.entity.EngineState;
import erp.workorder.entity.Equipment;
import erp.workorder.entity.EquipmentCategory;
import erp.workorder.entity.FileEntity;
import erp.workorder.entity.HighwayBoqMapping;
import erp.workorder.entity.MachineDPR;
import erp.workorder.entity.MaterialGroup;
import erp.workorder.entity.S3File;
import erp.workorder.entity.Site;
import erp.workorder.entity.StructureBoqQuantityMapping;
import erp.workorder.entity.SubcategoryItem;
import erp.workorder.entity.Unit;
import erp.workorder.entity.UnitMaster;
import erp.workorder.entity.UnitType;
import erp.workorder.entity.WoFormulaVariable;
import erp.workorder.entity.WoTnc;
import erp.workorder.entity.WoTncFormulaVariable;
import erp.workorder.entity.WoTncFormulaVariableValue;
import erp.workorder.entity.WoTncFormulaVariableValueVersion;
import erp.workorder.entity.WoTncMapping;
import erp.workorder.entity.WoTncMappingVersions;
import erp.workorder.entity.WoTncType;
import erp.workorder.entity.WoTypeTncMapping;
import erp.workorder.entity.WorkShiftSchedule;
import erp.workorder.entity.Workorder;
import erp.workorder.entity.WorkorderBillInfo;
import erp.workorder.entity.WorkorderBoqWork;
import erp.workorder.entity.WorkorderBoqWorkLocation;
import erp.workorder.entity.WorkorderBoqWorkLocationVersion;
import erp.workorder.entity.WorkorderBoqWorkQtyMapping;
import erp.workorder.entity.WorkorderBoqWorkQtyMappingVersion;
import erp.workorder.entity.WorkorderBoqWorkVersion;
import erp.workorder.entity.WorkorderClose;
import erp.workorder.entity.WorkorderConsultantWork;
import erp.workorder.entity.WorkorderConsultantWorkItemMapping;
import erp.workorder.entity.WorkorderConsultantWorkItemMappingVersion;
import erp.workorder.entity.WorkorderConsultantWorkVersion;
import erp.workorder.entity.WorkorderContractor;
import erp.workorder.entity.WorkorderEquipmentIssue;
import erp.workorder.entity.WorkorderEquipmentIssueVersion;
import erp.workorder.entity.WorkorderFile;
import erp.workorder.entity.WorkorderHiringMachineRateDetailVersion;
import erp.workorder.entity.WorkorderHiringMachineRateDetails;
import erp.workorder.entity.WorkorderHiringMachineWork;
import erp.workorder.entity.WorkorderHiringMachineWorkItemMapping;
import erp.workorder.entity.WorkorderHiringMachineWorkItemMappingVersion;
import erp.workorder.entity.WorkorderHiringMachineWorkVersion;
import erp.workorder.entity.WorkorderLabourWork;
import erp.workorder.entity.WorkorderLabourWorkItemMapping;
import erp.workorder.entity.WorkorderLabourWorkItemMappingVersion;
import erp.workorder.entity.WorkorderLabourWorkVersion;
import erp.workorder.entity.WorkorderMasterVariable;
import erp.workorder.entity.WorkorderMaterialConfig;
import erp.workorder.entity.WorkorderPayMilestone;
import erp.workorder.entity.WorkorderTransportWork;
import erp.workorder.entity.WorkorderTransportWorkItemMapping;
import erp.workorder.entity.WorkorderTransportWorkItemMappingVersion;
import erp.workorder.entity.WorkorderTransportWorkVersion;
import erp.workorder.entity.WorkorderType;
import erp.workorder.entity.WorkorderV2;
import erp.workorder.entity.WorkorderVersion;
import erp.workorder.enums.ItemType;
import erp.workorder.enums.MachineryAttendanceStatus;

@Component
public class SetObject {

//	@Autowired
//	private UtilityService utilityService;

	public static final String fileSavingPath = "/opt/erp/";
	public static final String folderName = "erp_files/";
	public static final String tncVariableDelimeter = "@$*#";

	public static final Integer storeDieselDepartmentId = 4;

	public static final Integer billingModuleId = 2;

	public static final UserDetail getUserDetailFromHttpRequest(HttpServletRequest request) {

		String userId = request.getHeader("userId");
		String userCode = request.getHeader("userCode");
		String userCompanyId = request.getHeader("userCompanyId");
		String userRoleId = request.getHeader("userRoleId");
		String userName = request.getHeader("userName");
		if (userId == null || userId.isEmpty())
			return new UserDetail();
		return new UserDetail(Long.parseLong(userId), userCode, userName, Integer.parseInt(userRoleId),
				Integer.parseInt(userCompanyId));
	}

	public ChainageBoqQuantityMappingDTO chainageBoqQtyMapEntityToDto(ChainageBoqQuantityMapping obj) {
		if (obj == null)
			return null;
		return new ChainageBoqQuantityMappingDTO(obj.getId(), chainageEntityToDto(obj.getChainage()), obj.getBoq(),
				null, obj.getSiteId(), obj.getLhsQuantity(), obj.getRhsQuantity(), obj.getStructureRemark(),
				obj.getIsActive(), obj.getModifiedOn(), obj.getModifiedBy(), obj.getCompanyId());
	}

	public WorkorderMasterVariable woMasterVariableDtoToEntity(WorkorderMasterVariableDTO obj) {
		if (obj == null)
			return null;
		return new WorkorderMasterVariable(obj.getId(), obj.getName(), obj.getDescription(), obj.getSqlQuery(),
				obj.getIsActive(), obj.getCreatedOn());
	}

	public WorkorderMasterVariableDTO woMasterVariableEntityToDto(WorkorderMasterVariable obj) {
		if (obj == null)
			return null;
		return new WorkorderMasterVariableDTO(obj.getId(), obj.getName(), obj.getDescription(), obj.getSqlQuery(),
				obj.getIsActive(), obj.getCreatedOn());
	}

	public DieselRateMappingDTO dieselRateMappingEntityToDto(DieselRateMapping obj) {
		if (obj == null)
			return null;
		return new DieselRateMappingDTO(obj.getId(), obj.getDate(), obj.getRate(), obj.getSiteId(), obj.getIsActive(),
				obj.getModifiedOn(), obj.getModifiedBy());
	}

	public DieselRateMapping dieselRateMappingDtoToEntity(DieselRateMappingDTO obj) {
		if (obj == null)
			return null;
		return new DieselRateMapping(obj.getId(), obj.getDate(), obj.getRate(), obj.getSiteId(), obj.getIsActive(),
				obj.getModifiedOn(), obj.getModifiedBy());
	}

	public DieselRateTransacs dieselRateEntityMappingToTransac(DieselRateMapping obj) {
		if (obj == null)
			return null;
		return new DieselRateTransacs(obj.getId(), obj.getDate(), obj.getRate(), obj.getSiteId(), obj.getIsActive(),
				new Date(), obj.getModifiedBy());
	}

	public ChainageTraverse chainageTraverseDtoToEntity(ChainageTraverseDTO obj) {
		if (obj == null)
			return null;
		return new ChainageTraverse(obj.getId(), obj.getName(), obj.getIsActive(), obj.getSiteId(), obj.getCompanyId(),
				obj.getModifiedOn(), obj.getModifiedBy());
	}

	public ChainageTraverseDTO chainageTraverseEntityToDto(ChainageTraverse obj) {
		if (obj == null)
			return null;
		return new ChainageTraverseDTO(obj.getId(), obj.getName(), obj.getIsActive(), obj.getSiteId(),
				obj.getCompanyId(), obj.getModifiedOn(), obj.getModifiedBy());
	}

	public Chainage chainageDtoToEntity(ChainageDTO obj) {
		if (obj == null)
			return null;
		return new Chainage(obj.getId(), obj.getName(), obj.getIsActive(), obj.getNumericChainageValue(),
				chainageTraverseDtoToEntity(obj.getPrevious()), chainageTraverseDtoToEntity(obj.getNext()),
				obj.getSiteId(), obj.getCompanyId(), obj.getModifiedOn(), obj.getModifiedBy());
	}

	public ChainageDTO chainageEntityToDto(Chainage obj) {
		if (obj == null)
			return null;
		return new ChainageDTO(obj.getId(), obj.getName(), obj.getIsActive(), obj.getNumericChainageValue(),
				chainageTraverseEntityToDto(obj.getPrevious()), chainageTraverseEntityToDto(obj.getNext()),
				obj.getSiteId(), obj.getCompanyId(), obj.getModifiedOn(), obj.getModifiedBy());
	}

	public EngineState engineStateDtoToEntity(EngineStateDTO obj) {
		if (obj == null)
			return null;
		return new EngineState(obj.getId(), obj.getName(), obj.getAlias(), obj.getRgbColorCode(), obj.getButtonText(),
				obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy());
	}

	public EngineStateDTO engineStateEntityToDto(EngineState obj) {
		if (obj == null)
			return null;
		return new EngineStateDTO(obj.getId(), obj.getName(), obj.getAlias(), obj.getRgbColorCode(),
				obj.getButtonText(), obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy());
	}

	public WoTncMapping woTncMappingDtoToEntity(WoTncMappingDTO obj, Workorder workorder) {
		if (obj == null)
			return null;
		List<WoTncFormulaVariableValueDTO> variableValuesDTO = obj.getVariableValues();
		List<WoTncFormulaVariableValue> variableValues = new ArrayList<>();
		WoTncMapping woTncMap = new WoTncMapping(obj.getId(), workorder, woTncDtoToEntity(obj.getTermAndCondition()),
				obj.getDescription(), variableValues, obj.getSequenceNo(), obj.getIsActive(), obj.getModifiedOn(),
				obj.getModifiedBy());
		if (variableValuesDTO != null) {
			variableValuesDTO.forEach(item -> variableValues.add(woTncFormulaVariableValueDtoToEntity(item, woTncMap)));
		}
		woTncMap.setVariableValues(variableValues);
		return woTncMap;
	}

	public WoTncMappingDTO woTncMappingEntityToDto(WoTncMapping obj) {
		if (obj == null)
			return null;
		List<WoTncFormulaVariableValue> variableValues = obj.getVariableValues();
		List<WoTncFormulaVariableValueDTO> variableValuesDTO = new ArrayList<>();
		if (variableValues != null) {
			variableValues.forEach(item -> variableValuesDTO.add(woTncFormulaVariableValueEntityToDto(item)));
		}
		return new WoTncMappingDTO(obj.getId(), woTncEntityToDto(obj.getTermAndCondition()), obj.getDescription(),
				variableValuesDTO, obj.getSequenceNo(), obj.getIsActive(), obj.getModifiedOn(), obj.getModifiedBy());
	}

	public WoTncFormulaVariableValueDTO woTncFormulaVariableValueEntityToDto(WoTncFormulaVariableValue obj) {
		if (obj == null)
			return null;
		return new WoTncFormulaVariableValueDTO(obj.getId(), woFormulaVariableEntityToDto(obj.getVariable()),
				unitEntityToDto(obj.getUnit()), obj.getType(), woMasterVariableEntityToDto(obj.getMasterVariable()),
				obj.getValueAtCreation(), obj.getValue(), obj.getModifiedOn(), obj.getModifiedBy());
	}

	public WoTncFormulaVariableValue woTncFormulaVariableValueDtoToEntity(WoTncFormulaVariableValueDTO obj,
			WoTncMapping woTncMap) {
		if (obj == null)
			return null;
		return new WoTncFormulaVariableValue(obj.getId(), woTncMap, woFormulaVariableDtoToEntity(obj.getVariable()),
				unitDtoToEntity(obj.getUnit()), obj.getType(), woMasterVariableDtoToEntity(obj.getMasterVariable()),
				obj.getValueAtCreation(), obj.getValue(), obj.getModifiedOn(), obj.getModifiedBy());
	}

	public WoTypeTncMapping woTypeTncMappingDtoToEntity(WoTypeTncMappingDTO obj) {
		if (obj == null)
			return null;
		return new WoTypeTncMapping(obj.getId(), obj.getWoTypeId(), woTncDtoToEntity(obj.getWoTnc()), obj.getIsActive(),
				obj.getCreatedOn(), obj.getCreatedBy(), obj.getCompanyId());
	}

	public WoTypeTncMappingDTO woTypeTncMappingEntityToDto(WoTypeTncMapping obj) {
		if (obj == null)
			return null;
		return new WoTypeTncMappingDTO(obj.getId(), obj.getWoTypeId(), woTncEntityToDto(obj.getWoTnc()),
				obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy(), obj.getCompanyId());
	}

	public WoTncDTO woTncEntityToDto(WoTnc obj) {
		if (obj == null)
			return null;
		List<WoTncFormulaVariable> formulaVariables = obj.getFormulaVariables();
		List<WoTncFormulaVariableDTO> formulaVariablesDTO = new ArrayList<>();
		if (formulaVariables != null) {
			formulaVariables.forEach(item -> formulaVariablesDTO.add(woTncFormulaVariableEntityToDto(item)));
		}
		return new WoTncDTO(obj.getId(), woTncTypeEntityToDto(obj.getType()), obj.getCode(), obj.getName(),
				obj.getDescription(), obj.getFormula(), formulaVariablesDTO, obj.getIsActive(), obj.getModifiedOn(),
				obj.getModifiedBy(), obj.getCompanyId());
	}

	public WoTnc woTncDtoToEntity(WoTncDTO obj) {
		if (obj == null)
			return null;
		List<WoTncFormulaVariableDTO> formulaVariablesDTO = obj.getFormulaVariables();
		List<WoTncFormulaVariable> formulaVariables = new ArrayList<>();
		WoTnc woTnc = new WoTnc(obj.getId(), woTncTypeDtoToEntity(obj.getType()), obj.getCode(), obj.getName(),
				obj.getDescription(), obj.getFormula(), formulaVariables, obj.getIsActive(), obj.getModifiedOn(),
				obj.getModifiedBy(), obj.getCompanyId());
		if (formulaVariablesDTO != null) {
			formulaVariablesDTO.forEach(item -> formulaVariables.add(woTncFormulaVariableDtoToEntity(item, woTnc)));
		}
		woTnc.setFormulaVariables(formulaVariables);
		return woTnc;
	}

	public WoTncType woTncTypeDtoToEntity(WoTncTypeDTO obj) {
		if (obj == null)
			return null;
		return new WoTncType(obj.getId(), obj.getName(), obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy(),
				obj.getCompanyId());
	}

	public WoTncTypeDTO woTncTypeEntityToDto(WoTncType obj) {
		if (obj == null)
			return null;
		return new WoTncTypeDTO(obj.getId(), obj.getName(), obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy(),
				obj.getCompanyId());
	}

	public WoTncFormulaVariableDTO woTncFormulaVariableEntityToDto(WoTncFormulaVariable obj) {
		if (obj == null)
			return null;
		return new WoTncFormulaVariableDTO(obj.getId(), woFormulaVariableEntityToDto(obj.getVariable()),
				unitEntityToDto(obj.getUnit()), obj.getType(), obj.getValueAtCreation(),
				woMasterVariableEntityToDto(obj.getMasterVariable()), obj.getIsActive(), obj.getCreatedOn(),
				obj.getCreatedBy());
	}

	public WoTncFormulaVariable woTncFormulaVariableDtoToEntity(WoTncFormulaVariableDTO obj, WoTnc woTnc) {
		if (obj == null)
			return null;
		return new WoTncFormulaVariable(obj.getId(), woTnc, woFormulaVariableDtoToEntity(obj.getVariable()),
				unitDtoToEntity(obj.getUnit()), obj.getType(), obj.getValueAtCreation(),
				woMasterVariableDtoToEntity(obj.getMasterVariable()), obj.getIsActive(), obj.getCreatedOn(),
				obj.getCreatedBy());
	}

	public WoFormulaVariable woFormulaVariableDtoToEntity(WoFormulaVariableDTO obj) {
		if (obj == null)
			return null;
		return new WoFormulaVariable(obj.getId(), obj.getName(), obj.getCreatedOn(), obj.getCreatedBy(),
				obj.getCompanyId());
	}

	public WoFormulaVariableDTO woFormulaVariableEntityToDto(WoFormulaVariable obj) {
		if (obj == null)
			return null;
		return new WoFormulaVariableDTO(obj.getId(), obj.getName(), obj.getCreatedOn(), obj.getCreatedBy(),
				obj.getCompanyId());
	}

	public EquipmentDTO equipmentEntityToDto(Equipment obj) {
		if (obj == null)
			return null;
		return new EquipmentDTO(obj.getId(), obj.getAssetCode(), obj.getRegNo(),
				equipmentCategoryEntityToDto(obj.getCategory()), obj.getIsOwned(), obj.getType(), obj.getIsActive(),
				obj.getSiteId(), obj.getCompanyId());
	}

	public Equipment equipmentDtoToEntity(EquipmentDTO obj) {
		if (obj == null)
			return null;
		return new Equipment(obj.getId(), obj.getAssetCode(), obj.getRegNo(),
				equipmentCategoryDtoToEntity(obj.getCategory()), obj.getIsOwned(), obj.getType(), obj.getIsActive(),
				obj.getSiteId(), obj.getCompanyId());
	}

	public WorkorderMaterialConfig workorderMaterialConfigDtoToEntity(WorkorderMaterialConfigDTO obj) {
		if (obj == null)
			return null;
		return new WorkorderMaterialConfig(obj.getId(), obj.getWorkorderId(),
				materialGroupDtoToEntity(obj.getMaterial()), obj.getIssyeType(), obj.getIsActive(), obj.getModifiedOn(),
				obj.getModifiedBy());
	}

	public WorkorderMaterialConfigDTO workorderMaterialConfigEntityToDto(WorkorderMaterialConfig obj) {
		if (obj == null)
			return null;
		return new WorkorderMaterialConfigDTO(obj.getId(), obj.getWorkorderId(),
				materialGroupEntityToDto(obj.getMaterialGroup()), obj.getIssyeType(), obj.getIsActive(),
				obj.getModifiedOn(), obj.getModifiedBy());
	}

	public MaterialGroupDTO materialGroupEntityToDto(MaterialGroup obj) {
		if (obj == null)
			return null;
		return new MaterialGroupDTO(obj.getId(), obj.getName(), obj.getIsActive(), obj.getCreatedOn(),
				obj.getCompanyId());
	}

	public MaterialGroup materialGroupDtoToEntity(MaterialGroupDTO obj) {
		if (obj == null)
			return null;
		return new MaterialGroup(obj.getId(), obj.getName(), obj.getIsActive(), obj.getCreatedOn(), obj.getCompanyId());
	}

	public WorkorderEquipmentIssue workorderEquipmentIssueDtoToEntity(WorkorderEquipmentIssueDTO obj) {
		if (obj == null)
			return null;
		return new WorkorderEquipmentIssue(obj.getId(), obj.getWorkorderId(),
				equipmentCategoryDtoToEntity(obj.getEquipmentCategory()), obj.getEquipmentCount(), obj.getRunningCost(),
				obj.getCostPeriod(), obj.getIssueType(), obj.getIsActive(), obj.getModifiedOn(), obj.getModifiedBy());
	}

	public WorkorderEquipmentIssueDTO workorderEquipmentIssueEntityToDto(WorkorderEquipmentIssue obj) {
		if (obj == null)
			return null;
		return new WorkorderEquipmentIssueDTO(obj.getId(), obj.getWorkorderId(),
				equipmentCategoryEntityToDto(obj.getEquipmentCategory()), obj.getEquipmentCount(), obj.getRunningCost(),
				obj.getCostPeriod(), obj.getIssueType(), obj.getIsActive(), obj.getModifiedOn(), obj.getModifiedBy());
	}

	public EquipmentCategory equipmentCategoryDtoToEntity(EquipmentCategoryDTO obj) {
		if (obj == null)
			return null;
		return new EquipmentCategory(obj.getId(), obj.getName(), obj.getAbbreviation(), obj.getOperatingCost(), true,
				null);
	}

	public EquipmentCategoryDTO equipmentCategoryEntityToDto(EquipmentCategory obj) {
		if (obj == null)
			return null;
		return new EquipmentCategoryDTO(obj.getId(), obj.getName(), obj.getAbbreviation(), obj.getOperatingCost(),
				obj.getIsMultiFuel(), obj.getPrimaryReadingUnit() != null ? obj.getPrimaryReadingUnit().getId() : null,
				obj.getPrimaryReadingUnit() != null ? obj.getPrimaryReadingUnit().getName() : null,
				obj.getSecondaryReadingUnit() != null ? obj.getSecondaryReadingUnit().getId() : null,
				obj.getSecondaryReadingUnit() != null ? obj.getSecondaryReadingUnit().getName() : null);
	}

	public WorkorderBoqWorkLocation workorderBoqWorkLocationDtoToEntity(WorkorderBoqWorkLocationDTO obj,
			WorkorderBoqWork boqWork) {
		if (obj == null)
			return null;
		return new WorkorderBoqWorkLocation(obj.getId(), boqWork, obj.getStructureTypeId(), obj.getStructureId(),
				obj.getFromChainageId(), obj.getToChainageId(), obj.getIsActive(), obj.getSiteId(), obj.getModifiedOn(),
				obj.getModifiedBy());
	}

	public WorkorderBoqWorkLocationDTO workorderBoqWorkLocationEntityToDto(WorkorderBoqWorkLocation obj) {
		if (obj == null)
			return null;
		return new WorkorderBoqWorkLocationDTO(obj.getId(), obj.getStructureTypeId(), obj.getStructureId(),
				obj.getFromChainageId(), obj.getToChainageId(), obj.getIsActive(), obj.getSiteId(), obj.getModifiedOn(),
				obj.getModifiedBy());
	}

	public WorkorderBoqWork workorderBoqWorkDtoToEntity(WorkorderBoqWorkDTO obj) {
		if (obj == null)
			return null;
		List<WorkorderBoqWorkQtyMappingDTO> wbwqDTO = obj.getBoqWorkQty();
		List<WorkorderBoqWorkQtyMapping> wbwq = new ArrayList<>();
		List<WorkorderBoqWorkLocationDTO> wbwlocDTO = obj.getLocations();
		List<WorkorderBoqWorkLocation> wbwloc = new ArrayList<>();
		WorkorderBoqWork boqWork = new WorkorderBoqWork(obj.getId(), obj.getWorkorderId(), obj.getWorkScope(),
				obj.getAnnexureNote(), null, null, obj.getIsActive(), obj.getVersion(), obj.getSiteId(),
				obj.getModifiedOn(), obj.getModifiedBy());
		if (wbwqDTO != null)
			wbwqDTO.forEach(item -> wbwq.add(workorderBoqWorkQtyDtoToEntity(item, boqWork)));
		boqWork.setBoqWorkQty(wbwq);
		if (wbwlocDTO != null)
			wbwlocDTO.forEach(item -> wbwloc.add(workorderBoqWorkLocationDtoToEntity(item, boqWork)));
		boqWork.setLocations(wbwloc);
		return boqWork;
	}

	public WorkorderBoqWorkQtyMapping workorderBoqWorkQtyDtoToEntity(WorkorderBoqWorkQtyMappingDTO obj,
			WorkorderBoqWork boqWork) {
		if (obj == null)
			return null;
		return new WorkorderBoqWorkQtyMapping(obj.getId(), obj.getStructureTypeId(), obj.getStructureId(), boqWork,
				boqItemDtoToEntity(obj.getBoq()), obj.getBoq().getCode(), obj.getBoq().getStandardBookCode(),
				unitDtoToEntity(obj.getUnit()), obj.getDescription(), obj.getVendorDescription(), obj.getRate(),
				obj.getQuantity(), obj.getRemark(), obj.getIsBoqEditable(), obj.getIsActive(), obj.getVersion(),
				obj.getModifiedOn(), obj.getModifiedBy());
	}

	public BoqItemDTO boqItemEntityToDto(BoqItem obj) {
		if (obj == null)
			return null;
		return new BoqItemDTO(obj.getId(), obj.getCode(), obj.getStandardBookCode(), obj.getName(), obj.getName(),
				unitEntityToDto(obj.getUnit()), categoryItemEntityToDto(obj.getCategory()),
				subcategoryItemEntityToDto(obj.getSubcategory()), obj.getIsActive(), obj.getCreatedOn(),
				obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy(), obj.getCompanyId());
	}

	public BoqItem boqItemDtoToEntity(BoqItemDTO obj) {
		if (obj == null)
			return null;
		return new BoqItem(obj.getId(), obj.getCode(), obj.getStandardBookCode(), obj.getName(),
				unitDtoToEntity(obj.getUnit()), categoryItemDtoToEntity(obj.getCategory()),
				subcategoryItemDtoToEntity(obj.getSubcategory()), obj.getIsActive(), obj.getCreatedOn(),
				obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy(), obj.getCompanyId());
	}

	public SubcategoryItem subcategoryItemDtoToEntity(SubcategoryItemDTO obj) {
		if (obj == null)
			return null;
		return new SubcategoryItem(obj.getId(), obj.getCode(), obj.getStandardBookCode(), obj.getName(),
				obj.getCreatedOn(), obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy(), obj.getIsActive(),
				obj.getCompanyId());
	}

	public SubcategoryItemDTO subcategoryItemEntityToDto(SubcategoryItem obj) {
		if (obj == null)
			return null;
		return new SubcategoryItemDTO(obj.getId(), obj.getCode(), obj.getStandardBookCode(), obj.getName(),
				obj.getCreatedOn(), obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy(), obj.getIsActive(),
				obj.getCompanyId());
	}

	public CategoryItemDTO categoryItemEntityToDto(CategoryItem obj) {
		if (obj == null)
			return null;
		return new CategoryItemDTO(obj.getId(), obj.getCode(), obj.getStandardBookCode(), obj.getName(),
				obj.getOfStructure(), obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy(), obj.getModifiedOn(),
				obj.getModifiedBy(), obj.getCompanyId());
	}

	public CategoryItem categoryItemDtoToEntity(CategoryItemDTO obj) {
		if (obj == null)
			return null;
		return new CategoryItem(obj.getId(), obj.getCode(), obj.getStandardBookCode(), obj.getName(),
				obj.getOfStructure(), obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy(), obj.getModifiedOn(),
				obj.getModifiedBy(), obj.getCompanyId());
	}

	public FileDTO fileEntityToDto(FileEntity obj) {
		if (obj == null)
			return null;
		FileDTO file = new FileDTO(obj.getId(), obj.getName(), obj.getPath(), obj.getCreatedOn(), obj.getCreatedBy(),
				obj.getModuleId());
		if (file.getPath() != null && obj.getModule() != null) {
			file.setFullFilePath(obj.getModule().getBaseUrl() + file.getPath());
		}
		return file;
	}

	public FileEntity fileDtoToEntity(FileDTO obj) {
		if (obj == null)
			return null;
		return new FileEntity(obj.getId(), obj.getName(), obj.getPath(), obj.getCreatedOn(), obj.getCreatedBy(),
				obj.getModuleId());
	}

	public S3FileDTO s3FileEntityToDto(S3File obj) {
		if (obj == null)
			return null;
//		String encodedBase64File = utilityService.getFileS3EncodedBase64(obj.getId());
		String encodedBase64File = null;
		S3FileDTO file = new S3FileDTO(obj.getId(), obj.getName(), obj.getModifiedName(), obj.getPath(),
				obj.getBucket(), obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy(), obj.getModuleId(),
				obj.getSiteId(), obj.getCompanyId());
		file.setEncodedBase64(encodedBase64File);
		return file;
	}

	public S3File s3FileDtoToEntity(S3FileDTO obj) {
		if (obj == null)
			return null;
		return new S3File(obj.getId(), obj.getName(), obj.getModifiedName(), obj.getPath(), obj.getBucket(),
				obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy(), obj.getModuleId(), obj.getSiteId(),
				obj.getCompanyId());
	}

	public ContractorBankDetailDTO contractorBankDetailEntityToDto(ContractorBankDetail obj) {
		if (obj == null)
			return null;
		return new ContractorBankDetailDTO(obj.getId(), obj.getContractorId(), obj.getAccountNo(), obj.getAccountName(),
				obj.getBankName(), obj.getIfscCode(), obj.getAddress(), fileEntityToDto(obj.getCancelChequeFile()),
				obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy());
	}

	public ContractorBankDetail contractorBankDetailDtoToEntity(ContractorBankDetailDTO obj) {
		if (obj == null)
			return null;
		return new ContractorBankDetail(obj.getId(), obj.getContractorId(), obj.getAccountNo(), obj.getAccountName(),
				obj.getBankName(), obj.getIfscCode(), obj.getAddress(), fileDtoToEntity(obj.getCancelChequeFile()),
				obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy());
	}

	public ContractorBillingAddressDTO contractorBillingAddressEntityToDto(ContractorBillingAddress obj) {
		if (obj == null)
			return null;
		return new ContractorBillingAddressDTO(obj.getId(), obj.getContractorId(), obj.getAddress(), obj.getGstNo(),
				obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy());
	}

	public ContractorBillingAddress contractorBillingAddressDtoToEntity(ContractorBillingAddressDTO obj) {
		if (obj == null)
			return null;
		return new ContractorBillingAddress(obj.getId(), obj.getContractorId(), obj.getAddress(), obj.getGstNo(),
				obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy());
	}

	public WorkorderContractor workorderContractorDtoToEntity(WorkorderContractorDTO obj) {

		if (obj == null)
			return null;
		return new WorkorderContractor(obj.getId(), obj.getWorkorderId(), obj.getContractorId(),
				contractorBankDetailDtoToEntity(obj.getBankDetail()),
				contractorBillingAddressDtoToEntity(obj.getBillingAddress()), obj.getIsActive(), obj.getModifiedOn(),
				obj.getModifiedBy());
	}

	public WorkorderContractorDTO workorderContractorEntityToDto(WorkorderContractor obj) {

		if (obj == null)
			return null;
		return new WorkorderContractorDTO(obj.getId(), obj.getWorkorderId(), obj.getContractorId(),
				contractorBankDetailEntityToDto(obj.getBankDetail()),
				contractorBillingAddressEntityToDto(obj.getBillingAddress()), obj.getIsActive(), obj.getModifiedOn(),
				obj.getModifiedBy());
	}

	public WorkorderTypeDTO workorderTypeEntityToDto(WorkorderType obj) {
		if (obj == null)
			return null;
		return new WorkorderTypeDTO(obj.getId(), obj.getName(), obj.getCode(), obj.getIsActive(), obj.getModifiedOn(),
				obj.getModifiedBy());
	}

	public WorkorderType workorderTypeDtoToEntity(WorkorderTypeDTO obj) {
		if (obj == null)
			return null;
		return new WorkorderType(obj.getId(), obj.getName(), obj.getCode(), obj.getIsActive(), obj.getModifiedOn(),
				obj.getModifiedBy());
	}

	public CurrencyDTO currencyEntityToDto(Currency obj) {
		if (obj == null)
			return null;
		return new CurrencyDTO(obj.getId(), obj.getName(), obj.getCode(), obj.getIsActive(), obj.getCreatedOn());
	}

	public Currency currencyDtoToEntity(CurrencyDTO obj) {
		if (obj == null)
			return null;
		return new Currency(obj.getId(), obj.getName(), obj.getCode(), obj.getIsActive(), obj.getCreatedOn());
	}

	public CountryStateDTO countryStateEntityToDto(CountryState obj) {
		if (obj == null)
			return null;
		return new CountryStateDTO(obj.getId(), obj.getName());
	}

	public CountryState countryStateDtoToEntity(CountryStateDTO obj) {
		if (obj == null)
			return null;
		return new CountryState(obj.getId(), obj.getName());
	}

	public EmployeeDTO employeeEntityToDto(Employee obj) {
		if (obj == null)
			return null;
		return new EmployeeDTO(obj.getId(), obj.getName(), obj.getCode(), obj.getEmailId(), obj.getDob(),
				obj.getContactNo(), obj.getCreatedOn(), obj.getIsActive(), obj.getCompanyId());
	}

	public Employee employeeDtoToEntity(EmployeeDTO obj) {
		if (obj == null)
			return null;
		return new Employee(obj.getId(), obj.getName(), obj.getCode(), obj.getEmailId(), obj.getDob(),
				obj.getContactNo(), obj.getCreatedOn(), obj.getIsActive(), obj.getCompanyId());
	}

	public SiteDTO siteEntityToDto(Site obj) {
		if (obj == null)
			return null;
		return new SiteDTO(obj.getId(), obj.getParentId(), obj.getCode(), obj.getName(), obj.getSubject(),
				obj.getDescription(), obj.getContractNo(), obj.getAddress(), obj.getBillingAddress(), obj.getGstNo(),
				employeeEntityToDto(obj.getContactPerson()), obj.getTdsOnIncomeTax(), obj.getTdsOnLabourCess(),
				obj.getTdsOnGst(), obj.getStartDate(), obj.getEndDate(), currencyEntityToDto(obj.getCurreny()),
				obj.getDieselRetailPrice(), obj.getLubricantsFactor(), obj.getDieselPriceWithLubricants(),
				chainageEntityToDto(obj.getStartChainage()), chainageEntityToDto(obj.getEndChainage()),
				obj.getProjectLength(), countryStateEntityToDto(obj.getState()), obj.getThresholdAmount(),
				obj.getRetention(), obj.getConsultantName(), obj.getConsultantAddress(), obj.getCreatedOn(),
				obj.getIsActive(), obj.getCompanyId(), companyEntityToDto(obj.getCompany()),
				obj.getConcessionaire() != null ? obj.getConcessionaire().getName() : null,
				obj.getConcessionaire() != null ? obj.getConcessionaire().getDescription() : null);
	}

	public Site siteDtoToEntity(SiteDTO obj) {
		if (obj == null)
			return null;
		return new Site(obj.getId(), obj.getParentId(), obj.getCode(), obj.getName(), obj.getSubject(),
				obj.getDescription(), obj.getContractNo(), obj.getAddress(), obj.getBillingAddress(), obj.getGstNo(),
				employeeDtoToEntity(obj.getContactPerson()), obj.getTdsOnIncomeTax(), obj.getTdsOnLabourCess(),
				obj.getTdsOnGst(), obj.getStartDate(), obj.getEndDate(), currencyDtoToEntity(obj.getCurreny()),
				obj.getDieselRetailPrice(), obj.getLubricantsFactor(), obj.getDieselPriceWithLubricants(),
				chainageDtoToEntity(obj.getStartChainage()), chainageDtoToEntity(obj.getEndChainage()),
				obj.getProjectLength(), countryStateDtoToEntity(obj.getState()), obj.getThresholdAmount(),
				obj.getRetention(), obj.getConsultantName(), obj.getConsultantAddress(), obj.getCreatedOn(),
				obj.getIsActive(), obj.getCompanyId(), null);
	}

	public CompanyDTO companyEntityToDto(Company obj) {
		if (obj == null)
			return null;
		return new CompanyDTO(obj.getId(), obj.getName(), obj.getAddress(), obj.getGstNo(), obj.getRegNo(),
				obj.getCinNo(), obj.getShortName(), obj.getHeaderLogo(), obj.getPrintLogo(),
				obj.getRegisteredOfficeAddress(), obj.getCorporateOfficeAddress(), obj.getEmail(), obj.getPhoneNo(),
				obj.getWebsite(), companyClientEntityToDto(obj.getClient()));
	}

	public Company companyDtoToEntity(CompanyDTO obj) {
		if (obj == null)
			return null;
		return new Company(obj.getId(), obj.getName(), obj.getAddress(), obj.getGstNo(), obj.getRegNo(), obj.getCinNo(),
				obj.getShortName(), obj.getHeaderLogo(), obj.getPrintLogo(), obj.getRegisteredOfficeAddress(),
				obj.getCorporateOfficeAddress(), obj.getEmail(), obj.getPhoneNo(), obj.getWebsite(), null,
				companyClientDtoToEntity(obj.getClient()));
	}

	public CompanyClient companyClientDtoToEntity(CompanyClientDTO obj) {
		if (obj == null)
			return null;
		return new CompanyClient(obj.getId(), obj.getName(), obj.getAddress(), obj.getIsActive(), obj.getCreatdeOn(),
				obj.getCreatedBy());
	}

	public CompanyClientDTO companyClientEntityToDto(CompanyClient obj) {
		if (obj == null)
			return null;
		return new CompanyClientDTO(obj.getId(), obj.getName(), obj.getAddress(), obj.getIsActive(), obj.getCreatdeOn(),
				obj.getCreatedBy());
	}

	public UnitTypeDTO unitTypeEntityToDto(UnitType obj) {
		if (obj == null)
			return null;
		return new UnitTypeDTO(obj.getId(), obj.getName());
	}

	public UnitType unitTypeDtoToEntity(UnitTypeDTO obj) {
		if (obj == null)
			return null;
		return new UnitType(obj.getId(), obj.getName());
	}

	public UnitDTO unitEntityToDto(Unit obj) {
		if (obj == null)
			return null;
		return new UnitDTO(obj.getId(), obj.getName(), obj.getDescription(), unitTypeEntityToDto(obj.getType()),
				obj.getIsActive(), obj.getCompanyId());
	}

	public Unit unitDtoToEntity(UnitDTO obj) {
		if (obj == null)
			return null;
		return new Unit(obj.getId(), obj.getName(), obj.getDescription(), unitTypeDtoToEntity(obj.getType()),
				obj.getIsActive(), obj.getCompanyId());
	}

	public ContractorDTO contractorEntityToDto(Contractor obj) {
		if (obj == null)
			return null;
		return new ContractorDTO(obj.getId(), obj.getName(), obj.getGstNo(), obj.getPanNo(), obj.getEmail(),
				obj.getAddress(), obj.getPhoneNo(), obj.getVendorCode(), obj.getCreatedOn(), obj.getIsActive(),
				obj.getCompanyId());
	}

	public Contractor contractorDtoToEntity(ContractorDTO obj) {
		if (obj == null)
			return null;
		return new Contractor(obj.getId(), obj.getName(), obj.getGstNo(), obj.getPanNo(), obj.getEmail(),
				obj.getAddress(), obj.getPhoneNo(), obj.getVendorCode(), obj.getCreatedOn(), obj.getIsActive(),
				obj.getCompanyId());
	}

	public WorkorderListDTO workorderV2EntityToListDto(WorkorderV2 obj) {
		if (obj == null)
			return null;
		return new WorkorderListDTO(obj.getId(), obj.getUniqueNo(), obj.getReferenceWorkorderNo(),
				contractorEntityToDto(obj.getContractor()), obj.getType().getId().longValue(), obj.getType().getName(),
				obj.getState().getId(), obj.getState().getName(), obj.getState().getAlias(), obj.getCloseType(), null,
				false, 0.00, 0.00, 0.00, obj.getModifiedOn(), null, null);
	}

	public WorkorderListDTO workorderEntityToListDto(Workorder obj) {
		if (obj == null)
			return null;
		return new WorkorderListDTO(obj.getId(), obj.getUniqueNo(), obj.getReferenceWorkorderNo(),
				contractorEntityToDto(obj.getContractor()), obj.getType().getId().longValue(), obj.getType().getName(),
				obj.getState().getId(),
				obj.getCloseType() != null ? obj.getCloseType().getName() : obj.getState().getName(),
				obj.getState().getAlias(), obj.getCloseType(), null, false, 0.00, 0.00, 0.00, obj.getModifiedOn(), null,
				null);
	}

	public Workorder workorderDtoToEntity(WorkorderDTO obj) {
		if (obj == null)
			return null;
		return new Workorder(obj.getId(), obj.getSubject(), obj.getUniqueNo(), obj.getReferenceWorkorderNo(),
				obj.getStartDate(), obj.getEndDate(), contractorDtoToEntity(obj.getContractor()),
				workorderContractorDtoToEntity(obj.getWoContractor()), new WorkorderType(obj.getType().getId()),
				engineStateDtoToEntity(obj.getState()), obj.getRemarks(), null, null, null, null, null,
				obj.getSystemBillStartDate(), obj.getPreviousBillAmount(), obj.getPreviousBillNo(), obj.getIsActive(),
				obj.getVersion(), obj.getCreatedOn(), obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy(),
				obj.getSiteId(), obj.getCompanyId());
	}

	public WorkorderDTO workorderEntityToDto(Workorder obj) {
		if (obj == null)
			return null;
		return new WorkorderDTO(obj.getId(), obj.getSubject(), obj.getUniqueNo(), obj.getReferenceWorkorderNo(),
				obj.getStartDate(), obj.getEndDate(), contractorEntityToDto(obj.getContractor()),
				workorderContractorEntityToDto(obj.getWoContractor()), workorderTypeEntityToDto(obj.getType()),
				engineStateEntityToDto(obj.getState()), obj.getRemark(), obj.getVersion(), obj.getSystemBillStartDate(),
				obj.getPreviousBillAmount(), obj.getPreviousBillNo(), null, obj.getIsActive(), obj.getCreatedOn(),
				obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy(), obj.getSiteId(), obj.getCompanyId(),
				false);
	}

	public Workorder updatedWorkorder(Workorder result, Workorder obj) {

		if (result == null || obj == null)
			return null;
		result.setModifiedBy(obj.getModifiedBy());
		result.setModifiedOn(new Date());
		result.setStartDate(obj.getStartDate());
		result.setSystemBillStartDate(obj.getSystemBillStartDate());
		result.setPreviousBillAmount(obj.getPreviousBillAmount());
		result.setPreviousBillNo(obj.getPreviousBillNo());
		result.setSubject(obj.getSubject());
		result.setRemark(obj.getRemark());
		result.setReferenceWorkorderNo(obj.getReferenceWorkorderNo());
		return result;
	}

	public ChainageBoqsRenderDTO renderChildParentHighwayBoqsInCbqFormat(List<HighwayBoqMapping> cbqs,
			List<BoqItem> boqs) {

		List<ChainageBoqParentChildDTO> resultList = new ArrayList<>();
		int uniquePid = 0;
		ChainageBoqsRenderDTO result = new ChainageBoqsRenderDTO(resultList);
		Set<Long> uniqueCategoryIds = new LinkedHashSet<>();
		for (HighwayBoqMapping cbq : cbqs) {
			if (cbq.getBoq() != null && cbq.getBoq().getCategory() != null) {
				uniqueCategoryIds.add(cbq.getBoq().getCategory().getId());
			}
		}
		for (Long categoryId : uniqueCategoryIds) {
			++uniquePid;
			List<HighwayBoqMapping> categoryData = new ArrayList<>();
			for (HighwayBoqMapping cbq : cbqs) {
				if (cbq.getBoq() != null && cbq.getBoq().getCategory() != null
						&& cbq.getBoq().getCategory().getId().equals(categoryId)) {
					categoryData.add(cbq);
				}
			}
			if (categoryData != null && categoryData.size() > 0) {
				BoqItem categoryBoq = categoryData.get(0).getBoq();
				for (BoqItem boq : boqs) {
					if (categoryBoq.getId().equals(boq.getId())) {
						categoryBoq = boq;
						break;
					}
				}
				CategoryItem category = categoryBoq.getCategory();
				ChainageBoqParentChildDTO parentDTO = new ChainageBoqParentChildDTO(category.getId(), null,
						category.getName(), category.getName(), null, null, category.getCode(),
						category.getStandardBookCode(), null, null, null, ItemType.Category.toString(), null, uniquePid,
						null, null);
				int catPid = uniquePid;
				for (HighwayBoqMapping obj : categoryData) {
					if (obj.getBoq().getSubcategory() == null) {
						BoqItem boq = obj.getBoq();
						int boqPid = ++uniquePid;
						ChainageBoqParentChildDTO childDTO = new ChainageBoqParentChildDTO(boq.getId(), obj.getId(),
								obj.getDescription(),
								obj.getVendorDescription() != null ? obj.getVendorDescription() : obj.getDescription(),
								boq.getUnit().getId(), boq.getUnit().getName(), boq.getCode(),
								boq.getStandardBookCode(), obj.getRate(), obj.getMaxRate(), obj.getQuantity(),
								ItemType.Boq.toString(), ItemType.Category.toString(), boqPid, catPid, obj.getRemark());
						resultList.add(childDTO);
					}
				}

				Set<Long> uniqueSubcategoryIds = new HashSet<>();
				for (HighwayBoqMapping obj : categoryData) {
					if (obj.getBoq().getSubcategory() != null) {
						uniqueSubcategoryIds.add(obj.getBoq().getSubcategory().getId());
					}
				}
				for (Long subcategoryId : uniqueSubcategoryIds) {
					List<HighwayBoqMapping> subcategoryData = new ArrayList<>();
					for (HighwayBoqMapping cbq : categoryData) {
						if (cbq.getBoq().getSubcategory() != null && cbq.getBoq().getSubcategory().getId() != null
								&& cbq.getBoq().getSubcategory().getId().equals(subcategoryId)) {
							subcategoryData.add(cbq);
						}
					}
					if (subcategoryData != null && subcategoryData.size() > 0) {
						BoqItem subcategoryBoq = subcategoryData.get(0).getBoq();
						for (BoqItem boq : boqs) {
							if (subcategoryBoq.getId().equals(boq.getId())) {
								subcategoryBoq = boq;
								break;
							}
						}
						SubcategoryItem subcategory = subcategoryBoq.getSubcategory();
						int subcatPid = ++uniquePid;
						ChainageBoqParentChildDTO subparentDTO = new ChainageBoqParentChildDTO(subcategory.getId(),
								null, subcategory.getName(), subcategory.getName(), null, null, subcategory.getCode(),
								subcategory.getStandardBookCode(), null, null, null, ItemType.Subcategory.toString(),
								ItemType.Category.toString(), subcatPid, catPid, null);
						for (HighwayBoqMapping obj : subcategoryData) {
							if (obj.getBoq() != null) {
								BoqItem boq = obj.getBoq();
								int boqPid = ++uniquePid;
								ChainageBoqParentChildDTO childDTO = new ChainageBoqParentChildDTO(boq.getId(),
										obj.getId(), obj.getDescription(),
										obj.getVendorDescription() != null ? obj.getVendorDescription()
												: obj.getDescription(),
										boq.getUnit().getId(), boq.getUnit().getName(), boq.getCode(),
										boq.getStandardBookCode(), obj.getRate(), obj.getMaxRate(), obj.getQuantity(),
										ItemType.Boq.toString(), ItemType.Subcategory.toString(), boqPid, subcatPid,
										obj.getRemark());
								resultList.add(childDTO);
							}
						}
						resultList.add(subparentDTO);
					}
				}
				resultList.add(parentDTO);
			}
		}
		result.setData(resultList);
		return result;
	}

	public ChainageBoqsRenderDTO renderChildParentChainageBoqs(List<ChainageBoqQuantityMapping> cbqs,
			List<BoqItem> boqs) {

		List<ChainageBoqParentChildDTO> resultList = new ArrayList<>();
		int uniquePid = 0;
		ChainageBoqsRenderDTO result = new ChainageBoqsRenderDTO(resultList);
		Set<Long> uniqueCategoryIds = new LinkedHashSet<>();
		for (ChainageBoqQuantityMapping cbq : cbqs) {
			if (cbq.getBoq() != null && cbq.getBoq().getCategory() != null) {
				uniqueCategoryIds.add(cbq.getBoq().getCategory().getId());
			}
		}
		for (Long categoryId : uniqueCategoryIds) {
			++uniquePid;
			List<ChainageBoqQuantityMapping> categoryData = new ArrayList<>();
			for (ChainageBoqQuantityMapping cbq : cbqs) {
				if (cbq.getBoq() != null && cbq.getBoq().getCategory() != null
						&& cbq.getBoq().getCategory().getId().equals(categoryId)) {
					categoryData.add(cbq);
				}
			}
			if (categoryData != null && categoryData.size() > 0) {
				BoqItem categoryBoq = categoryData.get(0).getBoq();
				for (BoqItem boq : boqs) {
					if (categoryBoq.getId().equals(boq.getId())) {
						categoryBoq = boq;
						break;
					}
				}
				CategoryItem category = categoryBoq.getCategory();
				ChainageBoqParentChildDTO parentDTO = new ChainageBoqParentChildDTO(category.getId(), null,
						category.getName(), category.getName(), null, null, category.getCode(),
						category.getStandardBookCode(), null, null, null, ItemType.Category.toString(), null, uniquePid,
						null);
				int catPid = uniquePid;
				for (ChainageBoqQuantityMapping obj : categoryData) {
					if (obj.getBoq().getSubcategory() == null) {
						BoqItem boq = obj.getBoq();
						int boqPid = ++uniquePid;
						ChainageBoqParentChildDTO childDTO = new ChainageBoqParentChildDTO(boq.getId(), obj.getId(),
								boq.getName(), obj.getHighwayBoq().getDescription(), boq.getUnit().getId(),
								boq.getUnit().getName(), boq.getCode(), boq.getStandardBookCode(),
								obj.getHighwayBoq().getRate(), obj.getHighwayBoq().getMaxRate(),
								obj.getLhsQuantity() + obj.getRhsQuantity(), ItemType.Boq.toString(),
								ItemType.Category.toString(), boqPid, catPid);
						resultList.add(childDTO);
					}
				}

				Set<Long> uniqueSubcategoryIds = new HashSet<>();
				for (ChainageBoqQuantityMapping obj : categoryData) {
					if (obj.getBoq().getSubcategory() != null) {
						uniqueSubcategoryIds.add(obj.getBoq().getSubcategory().getId());
					}
				}
				for (Long subcategoryId : uniqueSubcategoryIds) {
					List<ChainageBoqQuantityMapping> subcategoryData = new ArrayList<>();
					for (ChainageBoqQuantityMapping cbq : categoryData) {
						if (cbq.getBoq().getSubcategory() != null && cbq.getBoq().getSubcategory().getId() != null
								&& cbq.getBoq().getSubcategory().getId().equals(subcategoryId)) {
							subcategoryData.add(cbq);
						}
					}
					if (subcategoryData != null && subcategoryData.size() > 0) {
						BoqItem subcategoryBoq = subcategoryData.get(0).getBoq();
						for (BoqItem boq : boqs) {
							if (subcategoryBoq.getId().equals(boq.getId())) {
								subcategoryBoq = boq;
								break;
							}
						}
						SubcategoryItem subcategory = subcategoryBoq.getSubcategory();
						int subcatPid = ++uniquePid;
						ChainageBoqParentChildDTO subparentDTO = new ChainageBoqParentChildDTO(subcategory.getId(),
								null, subcategory.getName(), subcategory.getName(), null, null, subcategory.getCode(),
								subcategory.getStandardBookCode(), null, null, null, ItemType.Subcategory.toString(),
								ItemType.Category.toString(), subcatPid, catPid);
						for (ChainageBoqQuantityMapping obj : subcategoryData) {
							if (obj.getBoq() != null) {
								BoqItem boq = obj.getBoq();
								int boqPid = ++uniquePid;
								ChainageBoqParentChildDTO childDTO = new ChainageBoqParentChildDTO(boq.getId(),
										obj.getId(), boq.getName(), obj.getHighwayBoq().getDescription(),
										boq.getUnit().getId(), boq.getUnit().getName(), boq.getCode(),
										boq.getStandardBookCode(), obj.getHighwayBoq().getRate(),
										obj.getHighwayBoq().getMaxRate(), obj.getLhsQuantity() + obj.getRhsQuantity(),
										ItemType.Boq.toString(), ItemType.Subcategory.toString(), boqPid, subcatPid);
								resultList.add(childDTO);
							}
						}
						resultList.add(subparentDTO);
					}
				}
				resultList.add(parentDTO);
			}
		}
		result.setData(resultList);
		return result;
	}

	public SbqRenderDTO renderChildParentSbqs(List<StructureBoqQuantityMapping> sbqList) {

		List<SbqParentChildDTO> resultList = new ArrayList<>();
		int uniquePid = 0;
		SbqRenderDTO result = new SbqRenderDTO(resultList);
		Set<String> uniqueCategories = new HashSet<>();
		for (StructureBoqQuantityMapping structureBoq : sbqList) {
			if (structureBoq.getCategory() != null && structureBoq.getCategory().getName() != null) {
				uniqueCategories.add(structureBoq.getCategory().getName());
			}
		}
		for (String categoryName : uniqueCategories) {
			++uniquePid;
			List<StructureBoqQuantityMapping> categoryData = new ArrayList<>();
			for (StructureBoqQuantityMapping sbq : sbqList) {
				if (sbq.getCategory() != null && sbq.getCategory().getName() != null
						&& sbq.getCategory().getName().equals(categoryName)) {
					categoryData.add(sbq);
				}
			}
			if (categoryData != null && categoryData.size() > 0) {
				CategoryItem category = categoryData.get(0).getCategory();
				SbqParentChildDTO parentDTO = new SbqParentChildDTO(category.getId(), categoryData.get(0).getId(),
						category.getName(), category.getName(), null, null, category.getCode(),
						category.getStandardBookCode(), null, null, null, ItemType.Category.toString(), null, uniquePid,
						null, null);
				int catPid = uniquePid;
				for (StructureBoqQuantityMapping obj : categoryData) {
					if (obj.getSubcategory() == null) {
						BoqItem boq = obj.getBoq();
						int boqPid = ++uniquePid;
						SbqParentChildDTO childDTO = new SbqParentChildDTO(boq.getId(), obj.getId(),
								obj.getDescription(),
								obj.getVendorDescription() != null ? obj.getVendorDescription() : obj.getDescription(),
								obj.getUnit().getId(), obj.getUnit().getName(), boq.getCode(),
								boq.getStandardBookCode(), obj.getRate(), obj.getMaxRate(), obj.getQuantity(),
								ItemType.Boq.toString(), ItemType.Category.toString(), boqPid, catPid, obj.getRemark());
						resultList.add(childDTO);
					}
				}

				Set<String> uniqueSubcategories = new HashSet<>();
				for (StructureBoqQuantityMapping obj : categoryData) {
					if (obj.getSubcategory() != null) {
						uniqueSubcategories.add(obj.getSubcategory().getName());
					}
				}
				for (String subcategoryName : uniqueSubcategories) {
					List<StructureBoqQuantityMapping> subcategoryData = new ArrayList<>();
					for (StructureBoqQuantityMapping sbq : categoryData) {
						if (sbq.getSubcategory() != null && sbq.getSubcategory().getName() != null
								&& sbq.getSubcategory().getName().equals(subcategoryName)) {
							subcategoryData.add(sbq);
						}
					}
					if (subcategoryData != null && subcategoryData.size() > 0) {
						SubcategoryItem subcategory = subcategoryData.get(0).getSubcategory();
						int subcatPid = ++uniquePid;
						SbqParentChildDTO subparentDTO = new SbqParentChildDTO(subcategory.getId(),
								subcategoryData.get(0).getId(), subcategory.getName(), subcategory.getName(), null,
								null, subcategory.getCode(), subcategory.getStandardBookCode(), null, null, null,
								ItemType.Subcategory.toString(), ItemType.Category.toString(), subcatPid, catPid, null);
						for (StructureBoqQuantityMapping obj : subcategoryData) {
							if (obj.getBoq() != null) {
								BoqItem boq = obj.getBoq();
								int boqPid = ++uniquePid;
								SbqParentChildDTO childDTO = new SbqParentChildDTO(boq.getId(), obj.getId(),
										obj.getDescription(),
										obj.getVendorDescription() != null ? obj.getVendorDescription()
												: obj.getDescription(),
										obj.getUnit().getId(), obj.getUnit().getName(), boq.getCode(),
										boq.getStandardBookCode(), obj.getRate(), obj.getMaxRate(), obj.getQuantity(),
										ItemType.Boq.toString(), ItemType.Subcategory.toString(), boqPid, subcatPid,
										obj.getRemark());
								resultList.add(childDTO);
							}
						}
						resultList.add(subparentDTO);
					}
				}
				resultList.add(parentDTO);
			}
		}
		result.setData(resultList);
		return result;
	}

	public List<WorkorderBoqWorkParentChildDTO> renderParentChildWoBoqWork(List<WorkorderBoqWorkQtyMapping> boqWorkQtys,
			List<BoqItem> boqs) {

		List<WorkorderBoqWorkParentChildDTO> resultList = new ArrayList<>();
		if (boqWorkQtys == null) {
			return resultList;
		}
		int sortingIndex = 0;
		int uniquePid = 0;
		boqWorkQtys.sort(Comparator.comparing(WorkorderBoqWorkQtyMapping::getId));
		boqs.sort(Comparator.comparing(BoqItem::getId));
		Set<Long> uniqueCategoryIds = new LinkedHashSet<>();
		for (WorkorderBoqWorkQtyMapping wbwq : boqWorkQtys) {
			if (wbwq.getBoq() != null && wbwq.getBoq().getCategory() != null) {
				uniqueCategoryIds.add(wbwq.getBoq().getCategory().getId());
			}
		}
		for (Long categoryId : uniqueCategoryIds) {
			++uniquePid;
			List<WorkorderBoqWorkQtyMapping> categoryData = new ArrayList<>();
			for (WorkorderBoqWorkQtyMapping wbwq : boqWorkQtys) {
				if (wbwq.getBoq() != null && wbwq.getBoq().getCategory() != null
						&& wbwq.getBoq().getCategory().getId().equals(categoryId) && wbwq.getIsActive() != null
						&& wbwq.getIsActive()) {
					categoryData.add(wbwq);
				}
			}
			if (categoryData != null && categoryData.size() > 0) {
				BoqItem categoryBoq = categoryData.get(0).getBoq();
				for (BoqItem boq : boqs) {
					if (categoryBoq.getId().equals(boq.getId())) {
						categoryBoq = boq;
						break;
					}
				}
				CategoryItem category = categoryBoq.getCategory();
				WorkorderBoqWorkParentChildDTO parentDTO = new WorkorderBoqWorkParentChildDTO(category.getId(), null,
						category.getName(), category.getName(), null, null, null, category.getCode(),
						category.getStandardBookCode(), null, null, null, null, null, null, null,
						ItemType.Category.toString(), null, uniquePid, null, null, null, ++sortingIndex);
				int catPid = uniquePid;
				for (WorkorderBoqWorkQtyMapping obj : categoryData) {
					if (obj.getBoq().getSubcategory() == null) {
						BoqItem boq = obj.getBoq();
						int boqPid = ++uniquePid;
						WorkorderBoqWorkParentChildDTO childDTO = new WorkorderBoqWorkParentChildDTO(boq.getId(),
								obj.getId(), boq.getName(), boq.getName(), obj.getVendorDescription(),
								obj.getUnit().getId(), obj.getUnit().getName(),
								!obj.getIsBoqEditable() ? boq.getCode() : obj.getCode(),
								!obj.getIsBoqEditable() ? boq.getStandardBookCode() : obj.getStandardBookCode(), null,
								null, obj.getRate(), null, obj.getQuantity(), (obj.getRate() * obj.getQuantity()), true,
								ItemType.Boq.toString(), ItemType.Category.toString(), boqPid, catPid, obj.getRemark(),
								obj.getIsBoqEditable(), ++sortingIndex);
						resultList.add(childDTO);
					}
				}

				Set<Long> uniqueSubcategoryIds = new HashSet<>();
				for (WorkorderBoqWorkQtyMapping obj : categoryData) {
					if (obj.getBoq().getSubcategory() != null) {
						uniqueSubcategoryIds.add(obj.getBoq().getSubcategory().getId());
					}
				}
				for (Long subcategoryId : uniqueSubcategoryIds) {
					List<WorkorderBoqWorkQtyMapping> subcategoryData = new ArrayList<>();
					for (WorkorderBoqWorkQtyMapping cbq : categoryData) {
						if (cbq.getBoq().getSubcategory() != null && cbq.getBoq().getSubcategory().getId() != null
								&& cbq.getBoq().getSubcategory().getId().equals(subcategoryId)) {
							subcategoryData.add(cbq);
						}
					}
					if (subcategoryData != null && subcategoryData.size() > 0) {
						BoqItem subcategoryBoq = subcategoryData.get(0).getBoq();
						for (BoqItem boq : boqs) {
							if (subcategoryBoq.getId().equals(boq.getId())) {
								subcategoryBoq = boq;
								break;
							}
						}
						SubcategoryItem subcategory = subcategoryBoq.getSubcategory();
						int subcatPid = ++uniquePid;
						WorkorderBoqWorkParentChildDTO subparentDTO = new WorkorderBoqWorkParentChildDTO(
								subcategory.getId(), null, subcategory.getName(), subcategory.getName(), null, null,
								null, subcategory.getCode(), subcategory.getStandardBookCode(), null, null, null, null,
								null, null, null, ItemType.Subcategory.toString(), ItemType.Category.toString(),
								subcatPid, catPid, null, null, ++sortingIndex);
						for (WorkorderBoqWorkQtyMapping obj : subcategoryData) {
							if (obj.getBoq() != null) {
								BoqItem boq = obj.getBoq();
								int boqPid = ++uniquePid;
								WorkorderBoqWorkParentChildDTO childDTO = new WorkorderBoqWorkParentChildDTO(
										boq.getId(), obj.getId(), boq.getName(), boq.getName(),
										obj.getVendorDescription(), obj.getUnit().getId(), obj.getUnit().getName(),
										!obj.getIsBoqEditable() ? boq.getCode() : obj.getCode(),
										!obj.getIsBoqEditable() ? boq.getStandardBookCode() : obj.getStandardBookCode(),
										null, null, obj.getRate(), null, obj.getQuantity(),
										obj.getRate() * obj.getQuantity(), true, ItemType.Boq.toString(),
										ItemType.Subcategory.toString(), boqPid, subcatPid, obj.getRemark(),
										obj.getIsBoqEditable(), ++sortingIndex);
								resultList.add(childDTO);
							}
						}
						resultList.add(subparentDTO);
					}
				}
				resultList.add(parentDTO);
			}
		}
		return resultList;
	}

	public DocumentType documentTypeDtoToEntity(DocumentTypeDTO obj) {
		if (obj == null)
			return null;
		return new DocumentType(obj.getId(), obj.getName(), obj.getDescription(), obj.getIsActive(), obj.getCreatedOn(),
				obj.getCreatedBy(), obj.getCompanyId());
	}

	public DocumentTypeDTO documentTypeEntityToDto(DocumentType obj) {
		if (obj == null)
			return null;
		return new DocumentTypeDTO(obj.getId(), obj.getName(), obj.getDescription(), obj.getIsActive(),
				obj.getCreatedOn(), obj.getCreatedBy(), obj.getCompanyId());
	}

	public WorkorderFileDTO workorderFilesEntityToDto(WorkorderFile obj) {
		if (obj == null)
			return null;
		return new WorkorderFileDTO(obj.getId(), obj.getWorkorderId(), documentTypeEntityToDto(obj.getType()),
				s3FileEntityToDto(obj.getFile()), obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy());
	}

	public WorkorderFile workorderFilesEntityToDto(WorkorderFileDTO obj) {
		if (obj == null)
			return null;
		return new WorkorderFile(obj.getId(), obj.getWorkorderId(), documentTypeDtoToEntity(obj.getType()),
				s3FileDtoToEntity(obj.getFile()), obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy());
	}

	public AmendWorkorderInvocationGetResponse amendWorkorderInvocationEntityToGetResponseDto(
			AmendWorkorderInvocation obj) {
		if (obj == null)
			return null;
		return new AmendWorkorderInvocationGetResponse(obj.getId(), obj.getWorkorderId(),
				obj.getWorkorder().getStartDate(), obj.getWorkorder().getUniqueNo(), obj.getDated(), obj.getSubject(),
				obj.getState().getId(), obj.getState().getName(), obj.getState().getAlias(),
				obj.getModifiedByUser().getName(), false, false, null);
	}

	public WorkorderCloseGetResponse workorderCloseEntityToGetResponseDto(WorkorderClose obj) {
		if (obj == null)
			return null;
		return new WorkorderCloseGetResponse(obj.getId(), obj.getWorkorderId(), obj.getCloseType().getId(),
				obj.getCloseType().name(), obj.getCloseType().getName(), obj.getWorkorder().getStartDate(),
				obj.getWorkorder().getUniqueNo(), obj.getDated(), obj.getRemarks(), obj.getState().getId(),
				obj.getState().getName(), obj.getState().getAlias(), obj.getUpdatedByUser().getName(), false, false,
				null);
	}

	public WorkorderConsultantWork workorderConsultantWorkAddUpdateRequestDtoToEntity(
			WorkorderConsultantWorkAddUpdateRequestDTO obj) {
		if (obj == null)
			return null;
		return new WorkorderConsultantWork(obj.getConsultantWorkId(), obj.getWorkorderId(), obj.getWorkScope(),
				obj.getAnnexureNote(), true, obj.getSiteId(), new Date(), obj.getUserDetail().getId(), new Date(),
				obj.getUserDetail().getId());
	}

	public WorkorderConsultantWorkItemMapping workorderConsultantWorkItemAddUpdateRequestDtoToEntity(
			WorkorderConsultantWorkItemAddUpdateRequest obj) {
		if (obj == null)
			return null;
		return new WorkorderConsultantWorkItemMapping(obj.getConsultantWorkItemId(), obj.getConsultantWorkId(),
				obj.getCategoryDescription() != null ? obj.getCategoryDescription().trim() : null, obj.getDescription(),
				obj.getChainageId() != null ? new ChainageTraverse(obj.getChainageId()) : null, obj.getQuantity(),
				obj.getRate(), new Unit(obj.getUnitId()), obj.getRemark(), true, new Date(), null, new Date(), null);
	}

	public WorkorderConsultantWork updatedWorkorderConsultantWorkEntityFromRequestDto(
			WorkorderConsultantWork consultantWork, WorkorderConsultantWorkAddUpdateRequestDTO consultantWorkDTO) {

		if (consultantWorkDTO == null)
			return consultantWork;
		consultantWork.setAnnexureNote(consultantWorkDTO.getAnnexureNote());
		consultantWork.setWorkScope(consultantWorkDTO.getWorkScope());
		consultantWork.setModifiedBy(consultantWorkDTO.getUserDetail().getId());
		consultantWork.setModifiedOn(new Date());
		return consultantWork;
	}

	public WorkorderConsultantWorkItemGetResponse workorderConsultantWorkItemMappingEntityToGetResponse(
			WorkorderConsultantWorkItemMapping workItem) {
		if (workItem == null)
			return null;
		WorkorderConsultantWorkItemGetResponse workItemDto = new WorkorderConsultantWorkItemGetResponse(
				workItem.getId(), workItem.getCategoryDescription(), workItem.getDescription(),
				workItem.getChainage() != null ? workItem.getChainage().getId() : null,
				workItem.getChainage() != null ? workItem.getChainage().getName() : null, workItem.getQuantity(),
				workItem.getRate(), workItem.getQuantity() * workItem.getRate(),
				workItem.getUnit() != null ? workItem.getUnit().getId() : null,
				workItem.getUnit() != null ? workItem.getUnit().getName() : null, workItem.getRemark());
		return workItemDto;
	}

	public WorkorderConsultantWorkItemMapping updatedWorkorderConsultantWorkItemMapEntityFromRequestDto(
			WorkorderConsultantWorkItemMapping consultantWorkItem,
			WorkorderConsultantWorkItemAddUpdateRequest consultantWorkItemDTO) {
		if (consultantWorkItemDTO == null)
			return consultantWorkItem;
		consultantWorkItem.setCategoryDescription(consultantWorkItemDTO.getCategoryDescription() != null
				? consultantWorkItemDTO.getCategoryDescription().trim()
				: null);
		consultantWorkItem.setDescription(consultantWorkItemDTO.getDescription());
		consultantWorkItem.setRemark(consultantWorkItemDTO.getRemark());
		consultantWorkItem.setQuantity(consultantWorkItemDTO.getQuantity());
		consultantWorkItem.setRate(consultantWorkItemDTO.getRate());
		if (!consultantWorkItemDTO.getUnitId().equals(consultantWorkItem.getUnit().getId()))
			consultantWorkItem.setUnit(new Unit(consultantWorkItemDTO.getUnitId()));
		if (consultantWorkItemDTO.getChainageId() != null && consultantWorkItem.getChainage() != null
				&& !consultantWorkItemDTO.getChainageId().equals(consultantWorkItem.getChainage().getId())) {
			consultantWorkItem.setChainage(new ChainageTraverse(consultantWorkItemDTO.getChainageId()));
		} else if (consultantWorkItemDTO.getChainageId() == null || consultantWorkItem.getChainage() == null) {
			consultantWorkItem.setChainage(consultantWorkItemDTO.getChainageId() != null
					? new ChainageTraverse(consultantWorkItemDTO.getChainageId())
					: null);
		}
		consultantWorkItem.setModifiedBy(consultantWorkItemDTO.getUserDetail().getId());
		consultantWorkItem.setModifiedOn(new Date());
		return consultantWorkItem;
	}

	public WorkorderHiringMachineWork workorderHiringMachineWorkAddUpdateRequestDtoToEntity(
			WorkorderHiringMachineWorkAddUpdateRequestDTO obj) {
		if (obj == null)
			return null;
		return new WorkorderHiringMachineWork(obj.getHmWorkId(), obj.getWorkorderId(), obj.getWorkScope(),
				obj.getAnnexureNote(), obj.getDieselRate(), true, obj.getSiteId(), new Date(),
				obj.getUserDetail().getId(), new Date(), obj.getUserDetail().getId());
	}

	public WorkorderHiringMachineWorkItemMapping workorderHiringMachineWorkItemAddUpdateRequestDtoToEntity(
			WorkorderHiringMachineWorkItemAddUpdateRequest obj) {
		if (obj == null)
			return null;
		return new WorkorderHiringMachineWorkItemMapping(obj.getHmWorkItemId(), obj.getHmWorkId(), obj.getDescription(),
				obj.getMachineCategoryId(), obj.getMachineCount(), obj.getQuantity(), obj.getRate(),
				obj.getRunningMode(), new UnitMaster(obj.getUnitId()), obj.getThresholdApplicable(),
				obj.getThresholdQuantity(),
				obj.getThresholdUnitId() != null ? new UnitMaster(obj.getThresholdUnitId()) : null,
				obj.getPostThresholdRatePerUnit(), obj.getIsMultiFuel(), obj.getPrimaryEngineMileage(),
				obj.getSecondaryEngineMileage(), obj.getDieselInclusive(), obj.getFuelHandlingCharge(), true,
				new Date(), null, new Date(), null,
				obj.getScheduleId() != null ? new WorkShiftSchedule(obj.getScheduleId()) : null);
	}

	public WorkorderHiringMachineWork updatedWorkorderHiringMachineWorkEntityFromRequestDto(
			WorkorderHiringMachineWork hmWork, WorkorderHiringMachineWorkAddUpdateRequestDTO hmWorkDTO) {

		if (hmWorkDTO == null)
			return hmWork;
		hmWork.setDieselRate(hmWorkDTO.getDieselRate());
		hmWork.setAnnexureNote(hmWorkDTO.getAnnexureNote());
		hmWork.setWorkScope(hmWorkDTO.getWorkScope());
		hmWork.setModifiedBy(hmWorkDTO.getUserDetail().getId());
		hmWork.setModifiedOn(new Date());
		return hmWork;
	}

	public WorkorderHiringMachineWorkItemGetResponse workorderHiringMachineWorkItemMappingEntityToGetResponse(
			WorkorderHiringMachineWorkItemMapping workItem, Double hmWorkRate) {
		if (workItem == null)
			return null;
		WorkorderHiringMachineWorkItemGetResponse workItemDto = new WorkorderHiringMachineWorkItemGetResponse(
				workItem.getId(), workItem.getDescription(), workItem.getMachineCategory().getId(),
				workItem.getMachineCategory().getName(), workItem.getMachineCount(),
				workItem.getRunningMode() != null ? workItem.getRunningMode().getId() : null, workItem.getRunningMode(),
				workItem.getUnit() != null ? workItem.getUnit().getId() : null,
				workItem.getUnit() != null ? workItem.getUnit().getName() : null, workItem.getQuantity(), hmWorkRate,
				workItem.getQuantity() * hmWorkRate * workItem.getMachineCount(), workItem.getThresholdApplicable(),
				workItem.getThresholdQuantity(),
				workItem.getThresholdUnit() != null ? workItem.getThresholdUnit().getId() : null,
				workItem.getThresholdUnit() != null ? workItem.getThresholdUnit().getName() : null,
				workItem.getPostThresholdRatePerUnit(), workItem.getIsMultiFuel(), workItem.getPrimaryEngineMileage(),
				workItem.getMachineCategory().getPrimaryReadingUnit() != null
						? workItem.getMachineCategory().getPrimaryReadingUnit().getId().shortValue()
						: null,
				workItem.getMachineCategory().getPrimaryReadingUnit() != null
						? workItem.getMachineCategory().getPrimaryReadingUnit().getName()
						: null,
				workItem.getSecondaryEngineMileage(),
				workItem.getMachineCategory().getSecondaryReadingUnit() != null
						? workItem.getMachineCategory().getSecondaryReadingUnit().getId().shortValue()
						: null,
				workItem.getMachineCategory().getSecondaryReadingUnit() != null
						? workItem.getMachineCategory().getSecondaryReadingUnit().getName()
						: null,
				workItem.getDieselInclusive(), workItem.getFuelHandlingCharge(),
				workItem.getShiftSchedule() != null
						? new WorkShiftScheduleResponse(workItem.getShiftSchedule().getId(),
								workItem.getShiftSchedule().getShiftHours())
						: null);
		return workItemDto;
	}

	public WorkorderHiringMachineWorkItemMapping updatedWorkorderHiringMachineWorkItemMapEntityFromRequestDto(
			WorkorderHiringMachineWorkItemMapping hmWorkItem,
			WorkorderHiringMachineWorkItemAddUpdateRequest hmWorkItemDTO) {
		if (hmWorkItemDTO == null)
			return hmWorkItem;
		hmWorkItem.setDescription(hmWorkItemDTO.getDescription());
		hmWorkItem.setQuantity(hmWorkItemDTO.getQuantity());
		hmWorkItem.setRate(hmWorkItemDTO.getRate());
		hmWorkItem.setMachineCatgeoryId(hmWorkItemDTO.getMachineCategoryId());
		hmWorkItem.setMachineCount(hmWorkItemDTO.getMachineCount());
		hmWorkItem.setRunningMode(hmWorkItemDTO.getRunningMode());
		if (!hmWorkItemDTO.getUnitId().equals(hmWorkItem.getUnit().getId()))
			hmWorkItem.setUnit(new UnitMaster(hmWorkItemDTO.getUnitId()));
		if (hmWorkItemDTO.getThresholdApplicable()) {

			hmWorkItem.setThresholdApplicable(true);
			hmWorkItem.setThresholdQuantity(hmWorkItemDTO.getThresholdQuantity());
			if (hmWorkItemDTO.getThresholdUnitId() != null && hmWorkItem.getThresholdUnit() != null
					&& !hmWorkItemDTO.getThresholdUnitId().equals(hmWorkItem.getThresholdUnit().getId())) {
				hmWorkItem.setThresholdUnit(new UnitMaster(hmWorkItemDTO.getThresholdUnitId()));
			} else if (hmWorkItemDTO.getThresholdUnitId() == null || hmWorkItem.getThresholdUnit() == null) {
				hmWorkItem.setThresholdUnit(
						hmWorkItemDTO.getThresholdUnitId() != null ? new UnitMaster(hmWorkItemDTO.getThresholdUnitId())
								: null);
			}
			hmWorkItem.setPostThresholdRatePerUnit(hmWorkItemDTO.getPostThresholdRatePerUnit());
		} else {
			hmWorkItem.setThresholdApplicable(false);
			hmWorkItem.setThresholdQuantity(0.0);
			hmWorkItem.setThresholdUnit(null);
			hmWorkItem.setPostThresholdRatePerUnit(0.0);
		}
		if (hmWorkItemDTO.getIsMultiFuel() != null && hmWorkItemDTO.getIsMultiFuel()) {
			hmWorkItem.setPrimaryEngineMileage(hmWorkItemDTO.getPrimaryEngineMileage());
			hmWorkItem.setSecondaryEngineMileage(hmWorkItemDTO.getSecondaryEngineMileage());
		} else {
			hmWorkItem.setPrimaryEngineMileage(hmWorkItemDTO.getPrimaryEngineMileage());
		}
		hmWorkItem.setDieselInclusive(hmWorkItemDTO.getDieselInclusive());
		hmWorkItem.setFuelHandlingCharge(hmWorkItemDTO.getFuelHandlingCharge());
		hmWorkItem.setModifiedBy(hmWorkItemDTO.getUserDetail().getId());
		hmWorkItem.setModifiedOn(new Date());
		hmWorkItem.setShiftSchedule(
				hmWorkItemDTO.getScheduleId() != null ? new WorkShiftSchedule(hmWorkItemDTO.getScheduleId()) : null);
		return hmWorkItem;
	}

	public MachineDPR updatedMachineDprEntityFromRequestDto(MachineDPR mDpr, MachineDprAddUpdateRequest requestDTO) {

		mDpr.setDated(requestDTO.getDated());
		mDpr.setRunningMode(requestDTO.getRunningMode());
		mDpr.setShift(requestDTO.getShift());
		mDpr.setPrimaryOpeningMeterReading(requestDTO.getPrimaryOpeningMeterReading());
		mDpr.setPrimaryClosingMeterReading(requestDTO.getPrimaryClosingMeterReading());
		mDpr.setSecondaryOpeningMeterReading(requestDTO.getSecondaryOpeningMeterReading());
		mDpr.setSecondaryClosingMeterReading(requestDTO.getSecondaryClosingMeterReading());
		mDpr.setPrimaryOpeningActualReading(requestDTO.getPrimaryOpeningActualReading());
		mDpr.setPrimaryClosingActualReading(requestDTO.getPrimaryClosingActualReading());
		mDpr.setSecondaryOpeningActualReading(requestDTO.getSecondaryOpeningActualReading());
		mDpr.setSecondaryClosingActualReading(requestDTO.getSecondaryClosingActualReading());
		mDpr.setTripCount(requestDTO.getTripCount());
		mDpr.setRemarks(requestDTO.getRemarks());
		mDpr.setBreakdownHours(requestDTO.getBreakdownHours());
		mDpr.setAttendanceStatus(requestDTO.getAttendanceStatus() != null ? requestDTO.getAttendanceStatus()
				: MachineryAttendanceStatus.PRESENT);
		mDpr.setModifiedOn(new Date());
		mDpr.setModifiedBy(requestDTO.getUserDetail().getId());
		return mDpr;
	}

	public MachineDPR machineDprAddRequestDtoToEntity(MachineDprAddUpdateRequest requestDTO) {
		if (requestDTO == null)
			return null;
		return new MachineDPR(null, requestDTO.getDated(), requestDTO.getRunningMode(), requestDTO.getShift(),
				requestDTO.getMachineType(), requestDTO.getMachineId(), requestDTO.getPrimaryOpeningMeterReading(),
				requestDTO.getPrimaryClosingMeterReading(), requestDTO.getSecondaryOpeningMeterReading(),
				requestDTO.getSecondaryClosingMeterReading(), requestDTO.getPrimaryOpeningActualReading(),
				requestDTO.getPrimaryClosingActualReading(), requestDTO.getSecondaryOpeningActualReading(),
				requestDTO.getSecondaryClosingActualReading(), requestDTO.getTripCount(), requestDTO.getRemarks(),
				requestDTO.getBreakdownHours(),
				requestDTO.getAttendanceStatus() != null ? requestDTO.getAttendanceStatus()
						: MachineryAttendanceStatus.PRESENT,
				requestDTO.getSiteId(), true, new Date(), requestDTO.getUserDetail().getId(), new Date(),
				requestDTO.getUserDetail().getId());
	}

	public WorkorderTransportWork workorderTransportationWorkAddUpdateRequestDtoToEntity(
			WorkorderTransportationWorkAddUpdateRequestDTO obj) {

		if (obj == null)
			return null;
		return new WorkorderTransportWork(obj.getTransportWorkId(), obj.getWorkorderId(), obj.getWorkScope(),
				obj.getAnnexureNote(), true, obj.getSiteId(), new Date(), obj.getUserDetail().getId(), new Date(),
				obj.getUserDetail().getId());
	}

	public WorkorderTransportWorkItemMapping workorderTransportWorkItemAddUpdateRequestDtoToEntity(
			WorkorderTransportationWorkItemAddUpdateRequest obj) {
		return new WorkorderTransportWorkItemMapping(obj.getTransportWorkItemId(), obj.getTransportWorkId(),
				obj.getBoqId(), obj.getDescription(), obj.getQuantity(), obj.getRate(),
				new UnitMaster(obj.getUnitId().shortValue()), obj.getRemark(), true, new Date(), null, new Date(),
				null);
	}

	public WorkorderTransportWork updatedWorkorderTransportWorkEntityFromRequestDto(
			WorkorderTransportWork transportWork, WorkorderTransportationWorkAddUpdateRequestDTO transportWorkDTO) {

		if (transportWorkDTO == null)
			return transportWork;
		transportWork.setAnnexureNote(transportWorkDTO.getAnnexureNote());
		transportWork.setWorkScope(transportWorkDTO.getWorkScope());
		transportWork.setModifiedBy(transportWorkDTO.getUserDetail().getId());
		transportWork.setModifiedOn(new Date());
		return transportWork;
	}

	public WorkorderTransportWorkItemGetResponse workorderTransportWorkItemMappingEntityToGetResponse(
			WorkorderTransportWorkItemMapping workItem) {
		if (workItem == null)
			return null;
		BoqItemResponse boq = null;
		if (workItem.getBoq() != null) {
			boq = new BoqItemResponse(workItem.getBoq().getId(), workItem.getBoq().getCode(),
					workItem.getBoq().getStandardBookCode(), workItem.getBoq().getName(),
					workItem.getBoq().getCategory() != null ? workItem.getBoq().getCategory().getName() : null,
					workItem.getBoq().getSubcategory() != null ? workItem.getBoq().getSubcategory().getName() : null);
		}
		WorkorderTransportWorkItemGetResponse workItemDto = new WorkorderTransportWorkItemGetResponse(workItem.getId(),
				workItem.getDescription(), boq, workItem.getQuantity(), workItem.getRate(),
				workItem.getQuantity() * workItem.getRate(),
				workItem.getUnit() != null ? workItem.getUnit().getId().intValue() : null,
				workItem.getUnit() != null ? workItem.getUnit().getName() : null, workItem.getRemark());
		return workItemDto;
	}

	public WorkorderTransportWorkItemMapping updatedWorkorderTransportWorkItemMapEntityFromRequestDto(
			WorkorderTransportWorkItemMapping transportWorkItem,
			WorkorderTransportationWorkItemAddUpdateRequest transportWorkItemDTO) {
		if (transportWorkItemDTO == null)
			return transportWorkItem;
		transportWorkItem.setBoqId(transportWorkItemDTO.getBoqId());
		transportWorkItem.setDescription(transportWorkItemDTO.getDescription());
		transportWorkItem.setRemark(transportWorkItemDTO.getRemark());
		transportWorkItem.setQuantity(transportWorkItemDTO.getQuantity());
		transportWorkItem.setRate(transportWorkItemDTO.getRate());
		if (!(transportWorkItemDTO.getUnitId().shortValue() == transportWorkItem.getUnit().getId()))
			transportWorkItem.setUnit(new UnitMaster(transportWorkItemDTO.getUnitId().shortValue()));
		transportWorkItem.setModifiedBy(transportWorkItemDTO.getUserDetail().getId());
		transportWorkItem.setModifiedOn(new Date());
		return transportWorkItem;
	}

	public WorkorderPayMilestone updatedWorkorderPayMilestone(WorkorderPayMilestoneAddUpdateRequest payTermReq,
			WorkorderPayMilestone payMilestone) {
		if (payTermReq == null)
			return payMilestone;
		payMilestone.setDescription(payTermReq.getDescription().trim());
		payMilestone.setIsPercentage(payTermReq.getIsPercentage());
		payMilestone.setValue(payTermReq.getValue());
		payMilestone.setModifiedOn(new Date());
		payMilestone.setModifiedBy(payTermReq.getTokenDetails().getId());
		return payMilestone;
	}

	public WorkorderPayMilestone workorderPayMilestoneAddRequestToEntity(WorkorderPayMilestoneAddUpdateRequest obj) {
		if (obj == null)
			return null;
		return new WorkorderPayMilestone(null, obj.getWorkorderId(), obj.getDescription().trim(), obj.getIsPercentage(),
				obj.getValue(), true, new Date(), obj.getTokenDetails().getId(), new Date(),
				obj.getTokenDetails().getId());
	}

	public WorkorderLabourWork workorderLabourWorkAddUpdateRequestDtoToEntity(
			WorkorderLabourWorkAddUpdateRequestDTO obj) {

		if (obj == null)
			return null;
		return new WorkorderLabourWork(obj.getLabourWorkId(), obj.getWorkorderId(), obj.getWorkScope(),
				obj.getAnnexureNote(), true, obj.getSiteId(), new Date(), obj.getUserDetail().getId(), new Date(),
				obj.getUserDetail().getId());
	}

	public WorkorderLabourWorkItemMapping workorderLabourWorkItemAddUpdateRequestDtoToEntity(
			WorkorderLabourWorkItemAddUpdateRequest obj) {

		if (obj == null)
			return null;
		return new WorkorderLabourWorkItemMapping(obj.getLabourWorkItemId(), obj.getLabourWorkId(),
				obj.getLabourCount(), obj.getLabourTypeId(), obj.getDescription(), obj.getRate(),
				new UnitMaster(obj.getUnitId()), obj.getFullDurationThreshold(), obj.getHalfDurationThreshold(),
				obj.getRemark(), true, new Date(), null, new Date(), null);
	}

	public WorkorderLabourWork updatedWorkorderLabourWorkEntityFromRequestDto(WorkorderLabourWork labourWork,
			WorkorderLabourWorkAddUpdateRequestDTO labourWorkDTO) {

		if (labourWorkDTO == null)
			return labourWork;
		labourWork.setAnnexureNote(labourWorkDTO.getAnnexureNote());
		labourWork.setWorkScope(labourWorkDTO.getWorkScope());
		labourWork.setModifiedBy(labourWorkDTO.getUserDetail().getId());
		labourWork.setModifiedOn(new Date());
		return labourWork;
	}

	public WorkorderLabourWorkItemMapping updatedWorkorderLabourWorkItemMapEntityFromRequestDto(
			WorkorderLabourWorkItemMapping labourWorkItem, WorkorderLabourWorkItemAddUpdateRequest labourWorkItemDTO) {

		if (labourWorkItemDTO == null)
			return labourWorkItem;
		labourWorkItem.setDescription(labourWorkItemDTO.getDescription());
		labourWorkItem.setRemark(labourWorkItemDTO.getRemark());
		labourWorkItem.setRate(labourWorkItemDTO.getRate());
		labourWorkItem.setLabourTypeId(labourWorkItemDTO.getLabourTypeId());
		labourWorkItem.setLabourCount(labourWorkItemDTO.getLabourCount());
		if (!(labourWorkItemDTO.getUnitId() == labourWorkItem.getUnit().getId()))
			labourWorkItem.setUnit(new UnitMaster(labourWorkItemDTO.getUnitId()));
		labourWorkItem.setFullDurationThreshold(labourWorkItemDTO.getFullDurationThreshold());
		labourWorkItem.setHalfDurationThreshold(labourWorkItemDTO.getHalfDurationThreshold());
		labourWorkItem.setModifiedBy(labourWorkItemDTO.getUserDetail().getId());
		labourWorkItem.setModifiedOn(new Date());
		return labourWorkItem;
	}

	public WorkorderLabourWorkItemGetResponse workorderLabourWorkItemMappingEntityToGetResponse(
			WorkorderLabourWorkItemMapping workItem) {
		if (workItem == null)
			return null;
		WorkorderLabourWorkItemGetResponse workItemDto = new WorkorderLabourWorkItemGetResponse(workItem.getId(),
				workItem.getLabourTypeId(), workItem.getLabourType().getName(), workItem.getDescription(),
				workItem.getLabourCount(), workItem.getRate(),
				(workItem.getLabourCount() != null ? workItem.getLabourCount() : 0.0) * workItem.getRate(),
				workItem.getUnit() != null ? workItem.getUnit().getId() : null,
				workItem.getUnit() != null ? workItem.getUnit().getName() : null, workItem.getFullDurationThreshold(),
				workItem.getHalfDurationThreshold(), workItem.getRemark());
		return workItemDto;
	}

	public WorkorderLabourWorkVersion workorderLabourWorkEntityToVersionEntity(WorkorderLabourWork labourWork) {
		if (labourWork == null)
			return null;
		return new WorkorderLabourWorkVersion(null, null, labourWork.getWorkScope(), labourWork.getAnnexureNote(),
				labourWork.getIsActive(), labourWork.getSiteId(), new Date(), labourWork.getCreatedBy());
	}

	public WorkorderLabourWorkItemMappingVersion workorderLabourWorkItemMappingEntityToVersionEntity(
			WorkorderLabourWorkItemMapping itemObj) {
		if (itemObj == null)
			return null;
		return new WorkorderLabourWorkItemMappingVersion(null, null, itemObj.getLabourTypeId(),
				itemObj.getLabourCount(), itemObj.getDescription(), itemObj.getRate(), itemObj.getUnit().getId(),
				itemObj.getFullDurationThreshold(), itemObj.getHalfDurationThreshold(), itemObj.getRemark(),
				itemObj.getIsActive(), new Date(), itemObj.getCreatedBy());
	}

	public WorkorderVersion workorderEntityToVersionEntity(Workorder workorder) {
		if (workorder == null)
			return null;
		return new WorkorderVersion(null, null, null, workorder.getStartDate(), workorder.getSystemBillStartDate(),
				workorder.getSubject(), workorder.getUniqueNo(), workorder.getReferenceWorkorderNo(),
				workorder.getContractor() != null ? workorder.getContractor().getId() : null,
				workorder.getWoContractor() != null ? workorder.getWoContractor().getId() : null,
				workorder.getType() != null ? workorder.getType().getId() : null,
				workorder.getState() != null ? workorder.getState().getId() : null, workorder.getRemark(),
				workorder.getBoqWork() != null ? workorder.getBoqWork().getId() : null,
				workorder.getConsultantWork() != null ? workorder.getConsultantWork().getId() : null,
				workorder.getHiringMachineWork() != null ? workorder.getHiringMachineWork().getId() : null,
				workorder.getTransportWork() != null ? workorder.getTransportWork().getId() : null,
				workorder.getLabourWork() != null ? workorder.getLabourWork().getId() : null,
				workorder.getPreviousBillAmount(), workorder.getPreviousBillNo(), workorder.getSiteId(),
				workorder.getCompanyId(), true, workorder.getVersion(), new Date(), workorder.getCreatedBy());

	}

	public WorkorderHiringMachineWorkVersion workorderHiringMachineWorkEntityToVersionEntity(
			WorkorderHiringMachineWork hiringMachineWork) {
		if (hiringMachineWork == null)
			return null;
		return new WorkorderHiringMachineWorkVersion(null, null, hiringMachineWork.getWorkScope(),
				hiringMachineWork.getAnnexureNote(), hiringMachineWork.getIsActive(), hiringMachineWork.getSiteId(),
				new Date(), hiringMachineWork.getCreatedBy());

	}

	public WorkorderHiringMachineWorkItemMappingVersion workorderHiringMachineWorkItemMappingEntityToVersionEntity(
			WorkorderHiringMachineWorkItemMapping itemObj) {
		if (itemObj == null)
			return null;
		return new WorkorderHiringMachineWorkItemMappingVersion(null, null, itemObj.getDescription(),
				itemObj.getMachineCatgeoryId(), itemObj.getMachineCount(), itemObj.getQuantity(), itemObj.getRate(),
				itemObj.getRunningMode(), itemObj.getUnit() != null ? itemObj.getUnit().getId() : null,
				itemObj.getThresholdApplicable(), itemObj.getThresholdQuantity(),
				itemObj.getThresholdUnit() != null ? itemObj.getThresholdUnit().getId() : null,
				itemObj.getPostThresholdRatePerUnit(), itemObj.getIsMultiFuel(), itemObj.getPrimaryEngineMileage(),
				itemObj.getSecondaryEngineMileage(), itemObj.getDieselInclusive(), itemObj.getFuelHandlingCharge(),
				itemObj.getIsActive(), new Date(), itemObj.getCreatedBy());

	}

	public WorkorderHiringMachineRateDetailVersion workorderHiringMachineRateDetailEntityToVersionEntity(
			WorkorderHiringMachineRateDetails workorderHiringMachineRateDetails) {
		if (workorderHiringMachineRateDetails == null)
			return null;
		return new WorkorderHiringMachineRateDetailVersion(null, null, workorderHiringMachineRateDetails.getShift(),
				workorderHiringMachineRateDetails.getRate(), workorderHiringMachineRateDetails.getIsActive(),
				new Date(), workorderHiringMachineRateDetails.getUpdatedBy().longValue());

	}

	public WorkorderConsultantWorkVersion workorderConsultantWorkEntityToVersionEntity(
			WorkorderConsultantWork consultantWork) {
		if (consultantWork == null)
			return null;
		return new WorkorderConsultantWorkVersion(null, consultantWork.getWorkorderId(), consultantWork.getWorkScope(),
				consultantWork.getAnnexureNote(), consultantWork.getIsActive(), consultantWork.getSiteId(), new Date(),
				consultantWork.getCreatedBy());

	}

	public WorkorderConsultantWorkItemMappingVersion workorderConsultantWorkItemMappingEntityToVersionEntity(
			WorkorderConsultantWorkItemMapping itemObj) {
		if (itemObj == null)
			return null;
		return new WorkorderConsultantWorkItemMappingVersion(null, itemObj.getWorkorderConsultantWorkId(),
				itemObj.getCategoryDescription(), itemObj.getDescription(),
				itemObj.getChainage() != null ? itemObj.getChainage().getId() : null, itemObj.getQuantity(),
				itemObj.getRate(), itemObj.getUnit() != null ? itemObj.getUnit().getId() : null, itemObj.getRemark(),
				itemObj.getIsActive(), new Date(), itemObj.getCreatedBy());

	}

	public WorkorderBoqWorkVersion workorderBoqWorkEntityToVersionEntity(WorkorderBoqWork boqWork) {
		if (boqWork == null)
			return null;
		return new WorkorderBoqWorkVersion(null, boqWork.getWorkorderId(), boqWork.getWorkScope(),
				boqWork.getAnnexureNote(), boqWork.getIsActive(), boqWork.getVersion(), boqWork.getSiteId(), new Date(),
				boqWork.getModifiedBy());

	}

	public WorkorderBoqWorkQtyMappingVersion workorderBoqWorkQtyMappingEntityToVersionEntity(
			WorkorderBoqWorkQtyMapping boqWorkQtyMapping) {
		if (boqWorkQtyMapping == null)
			return null;
		return new WorkorderBoqWorkQtyMappingVersion(null, boqWorkQtyMapping.getStructureTypeId(),
				boqWorkQtyMapping.getStructureId(), boqWorkQtyMapping.getBoqWork().getId(),
				boqWorkQtyMapping.getBoq() != null ? boqWorkQtyMapping.getBoq().getId() : null,
				boqWorkQtyMapping.getUnit() != null ? boqWorkQtyMapping.getUnit().getId() : null,
				boqWorkQtyMapping.getDescription(), boqWorkQtyMapping.getVendorDescription(),
				boqWorkQtyMapping.getRate(), boqWorkQtyMapping.getQuantity(), boqWorkQtyMapping.getIsActive(),
				boqWorkQtyMapping.getVersion(), new Date(), boqWorkQtyMapping.getModifiedBy());

	}

	public WorkorderBoqWorkLocationVersion workorderBoqWorkLocationEntityToVersionEntity(
			WorkorderBoqWorkLocation boqWorkLocation) {
		if (boqWorkLocation == null)
			return null;
		return new WorkorderBoqWorkLocationVersion(null, boqWorkLocation.getBoqWork().getId(),
				boqWorkLocation.getStructureTypeId(), boqWorkLocation.getStructureId(),
				boqWorkLocation.getFromChainageId(), boqWorkLocation.getToChainageId(), boqWorkLocation.getIsActive(),
				boqWorkLocation.getSiteId(), new Date(), boqWorkLocation.getModifiedBy());

	}

	public WorkorderTransportWorkVersion workorderTransportWorkEntityToVersionEntity(
			WorkorderTransportWork transportWork) {
		if (transportWork == null)
			return null;
		return new WorkorderTransportWorkVersion(null, transportWork.getWorkorderId(), transportWork.getWorkScope(),
				transportWork.getAnnexureNote(), transportWork.getIsActive(), transportWork.getSiteId(), new Date(),
				transportWork.getCreatedBy());

	}

	public WorkorderTransportWorkItemMappingVersion workorderTransportWorkItemMappingEntityToVersionEntity(
			WorkorderTransportWorkItemMapping itemObj) {
		if (itemObj == null)
			return null;
		return new WorkorderTransportWorkItemMappingVersion(null, itemObj.getWorkorderTransportWorkId(),
				itemObj.getBoqId(), itemObj.getDescription(), itemObj.getQuantity(), itemObj.getRate(),
				itemObj.getUnit() != null ? itemObj.getUnit().getId() : null, itemObj.getRemark(),
				itemObj.getIsActive(), new Date(), itemObj.getCreatedBy());

	}

	public WoTncMappingVersions workorderTermsAndConditionsEntityToVersionEntity(WoTncMapping woTncMapping) {
		if (woTncMapping == null)
			return null;
		return new WoTncMappingVersions(null,
				woTncMapping.getWorkorder() != null ? woTncMapping.getWorkorder().getId() : null,
				woTncMapping.getTermAndCondition() != null ? woTncMapping.getTermAndCondition().getId() : null,
				woTncMapping.getDescription(), woTncMapping.getSequenceNo(), woTncMapping.getIsActive(), new Date(),
				woTncMapping.getModifiedBy());

	}

	public WoTncFormulaVariableValueVersion workorderTermsAndConditionsVariableValueEntityToVersionEntity(
			WoTncFormulaVariableValue variableValue) {
		if (variableValue == null)
			return null;
		return new WoTncFormulaVariableValueVersion(null, variableValue.getWoTnc().getId(),
				variableValue.getVariable() != null ? variableValue.getVariable().getId() : null,
				variableValue.getUnit() != null ? variableValue.getUnit().getId() : null, variableValue.getType(),
				variableValue.getMasterVariable() != null ? variableValue.getMasterVariable().getId() : null,
				variableValue.getValueAtCreation(), variableValue.getValue(), new Date(),
				variableValue.getModifiedBy());

	}

	public WorkorderEquipmentIssueVersion workorderEquipmentIssueEntityToVersionEntity(
			WorkorderEquipmentIssue equipmentIssue) {
		if (equipmentIssue == null)
			return null;
		return new WorkorderEquipmentIssueVersion(null, equipmentIssue.getWorkorderId(),
				equipmentIssue.getEquipmentCategory() != null ? equipmentIssue.getEquipmentCategory().getId() : null,
				equipmentIssue.getEquipmentCount(), equipmentIssue.getRunningCost(), equipmentIssue.getCostPeriod(),
				equipmentIssue.getIssueType(), equipmentIssue.getIsActive(), new Date(),
				equipmentIssue.getModifiedBy());

	}

	public Workorder workorderEntityToAmendEntity(Workorder workorder) {
		if (workorder == null)
			return null;
		return new Workorder(null, workorder.getSubject(), workorder.getUniqueNo(), workorder.getReferenceWorkorderNo(),
				workorder.getStartDate(), workorder.getEndDate(), workorder.getContractor(),
				workorder.getWoContractor(), workorder.getType(), workorder.getState(), workorder.getRemark(),
				workorder.getBoqWork(), workorder.getConsultantWork(), workorder.getHiringMachineWork(),
				workorder.getTransportWork(), null, workorder.getSystemBillStartDate(),
				workorder.getPreviousBillAmount(), workorder.getPreviousBillNo(), workorder.getIsActive(),
				workorder.getVersion(), new Date(), workorder.getCreatedBy(), new Date(), workorder.getModifiedBy(),
				workorder.getSiteId(), workorder.getCompanyId()

		);

	}

	public WoTncMapping workorderTermsAndConditionsEntityToAmendEntity(WoTncMapping woTncMapping) {
		if (woTncMapping == null)
			return null;
		return new WoTncMapping(null, woTncMapping.getWorkorder(), woTncMapping.getTermAndCondition(),
				woTncMapping.getDescription(), woTncMapping.getVariableValues(), woTncMapping.getSequenceNo(),
				woTncMapping.getIsActive(), new Date(), woTncMapping.getModifiedBy());

	}

	public WorkorderEquipmentIssue workorderEquipmentIssueEntityToAmendEntity(WorkorderEquipmentIssue equipmentIssue) {
		if (equipmentIssue == null)
			return null;
		return new WorkorderEquipmentIssue(null, equipmentIssue.getWorkorderId(), equipmentIssue.getEquipmentCategory(),
				equipmentIssue.getEquipmentCount(), equipmentIssue.getRunningCost(), equipmentIssue.getCostPeriod(),
				equipmentIssue.getIssueType(), equipmentIssue.getIsActive(), new Date(),
				equipmentIssue.getModifiedBy());

	}

	public WorkorderHiringMachineWork workorderHiringMachineWorkEntityToAmendEntity(
			WorkorderHiringMachineWork hiringMachineWork) {
		if (hiringMachineWork == null)
			return null;
		return new WorkorderHiringMachineWork(null, null, hiringMachineWork.getWorkScope(),
				hiringMachineWork.getAnnexureNote(), hiringMachineWork.getDieselRate(), hiringMachineWork.getIsActive(),
				hiringMachineWork.getSiteId(), new Date(), hiringMachineWork.getCreatedBy(), new Date(),
				hiringMachineWork.getModifiedBy());

	}

	public WorkorderHiringMachineWorkItemMapping workorderHiringMachineWorkItemMappingEntityToAmendEntity(
			WorkorderHiringMachineWorkItemMapping itemObj) {
		if (itemObj == null)
			return null;
		return new WorkorderHiringMachineWorkItemMapping(null, null, itemObj.getDescription(),
				itemObj.getMachineCatgeoryId(), itemObj.getMachineCount(), itemObj.getQuantity(), itemObj.getRate(),
				itemObj.getRunningMode(), itemObj.getUnit(), itemObj.getThresholdApplicable(),
				itemObj.getThresholdQuantity(), itemObj.getThresholdUnit(), itemObj.getPostThresholdRatePerUnit(),
				itemObj.getIsMultiFuel(), itemObj.getPrimaryEngineMileage(), itemObj.getSecondaryEngineMileage(),
				itemObj.getDieselInclusive(), itemObj.getFuelHandlingCharge(), itemObj.getIsActive(), new Date(),
				itemObj.getCreatedBy(), new Date(), itemObj.getModifiedBy(),
				itemObj.getShiftSchedule() != null ? itemObj.getShiftSchedule() : null);

	}

	public WorkorderHiringMachineRateDetails workorderHiringMachineRateDetailEntityToAmendEntity(
			WorkorderHiringMachineRateDetails workorderHiringMachineRateDetails) {
		if (workorderHiringMachineRateDetails == null)
			return null;
		return new WorkorderHiringMachineRateDetails(null, null, workorderHiringMachineRateDetails.getShift(),
				workorderHiringMachineRateDetails.getRate(), workorderHiringMachineRateDetails.getIsActive(),
				new Date(), workorderHiringMachineRateDetails.getUpdatedBy());

	}

	public WorkorderLabourWork workorderLabourWorkEntityToAmendEntity(WorkorderLabourWork labourWork) {
		if (labourWork == null)
			return null;
		return new WorkorderLabourWork(null, null, labourWork.getWorkScope(), labourWork.getAnnexureNote(),
				labourWork.getIsActive(), labourWork.getSiteId(), new Date(), labourWork.getCreatedBy(), new Date(),
				labourWork.getModifiedBy());

	}

	public WorkorderLabourWorkItemMapping workorderLabourWorkItemMappingEntityToAmendEntity(
			WorkorderLabourWorkItemMapping itemObj) {
		if (itemObj == null)
			return null;
		return new WorkorderLabourWorkItemMapping(null, null, itemObj.getLabourTypeId(), itemObj.getLabourCount(),
				itemObj.getDescription(), itemObj.getRate(), itemObj.getUnit(), itemObj.getFullDurationThreshold(),
				itemObj.getHalfDurationThreshold(), itemObj.getRemark(), itemObj.getIsActive(), new Date(),
				itemObj.getCreatedBy(), new Date(), itemObj.getModifiedBy());

	}

	public WorkorderTransportWork workorderTransportWorkEntityToAmendEntity(WorkorderTransportWork transportWork) {
		if (transportWork == null)
			return null;
		return new WorkorderTransportWork(null, null, transportWork.getWorkScope(), transportWork.getAnnexureNote(),
				transportWork.getIsActive(), transportWork.getSiteId(), new Date(), transportWork.getCreatedBy(),
				new Date(), transportWork.getModifiedBy());

	}

	public WorkorderTransportWorkItemMapping workorderTransportWorkItemMappingEntityToAmendEntity(
			WorkorderTransportWorkItemMapping itemObj) {
		if (itemObj == null)
			return null;
		return new WorkorderTransportWorkItemMapping(null, null, itemObj.getBoqId(), itemObj.getDescription(),
				itemObj.getQuantity(), itemObj.getRate(), itemObj.getUnit(), itemObj.getRemark(), itemObj.getIsActive(),
				new Date(), itemObj.getCreatedBy(), new Date(), itemObj.getModifiedBy());

	}

	public WorkorderConsultantWork workorderConsultantWorkEntityToAmendEntity(WorkorderConsultantWork consultantWork) {
		if (consultantWork == null)
			return null;
		return new WorkorderConsultantWork(null, null, consultantWork.getWorkScope(), consultantWork.getAnnexureNote(),
				consultantWork.getIsActive(), consultantWork.getSiteId(), new Date(), consultantWork.getCreatedBy(),
				new Date(), consultantWork.getModifiedBy());

	}

	public WorkorderConsultantWorkItemMapping workorderConsultantWorkItemMappingEntityToAmendEntity(
			WorkorderConsultantWorkItemMapping itemObj) {
		if (itemObj == null)
			return null;
		return new WorkorderConsultantWorkItemMapping(null, null, itemObj.getCategoryDescription(),
				itemObj.getDescription(), itemObj.getChainage(), itemObj.getQuantity(), itemObj.getRate(),
				itemObj.getUnit(), itemObj.getRemark(), itemObj.getIsActive(), new Date(), itemObj.getCreatedBy(),
				new Date(), itemObj.getModifiedBy());

	}

	public WorkorderBoqWork workorderBoqWorkEntityToAmendEntity(WorkorderBoqWork boqWork) {
		if (boqWork == null)
			return null;
		return new WorkorderBoqWork(null, null, boqWork.getWorkScope(), boqWork.getAnnexureNote(),
				boqWork.getLocations(), boqWork.getBoqWorkQty(), boqWork.getIsActive(), boqWork.getVersion(),
				boqWork.getSiteId(), new Date(), boqWork.getModifiedBy());

	}

	public WorkorderBoqWorkQtyMapping workorderBoqWorkQtyMappingEntityToAmendEntity(
			WorkorderBoqWorkQtyMapping boqWorkQtyMapping) {
		if (boqWorkQtyMapping == null)
			return null;
		return new WorkorderBoqWorkQtyMapping(null, boqWorkQtyMapping.getStructureTypeId(),
				boqWorkQtyMapping.getStructureId(), null, boqWorkQtyMapping.getBoq(), boqWorkQtyMapping.getCode(),
				boqWorkQtyMapping.getStandardBookCode(), boqWorkQtyMapping.getUnit(),
				boqWorkQtyMapping.getDescription(), boqWorkQtyMapping.getVendorDescription(),
				boqWorkQtyMapping.getRate(), boqWorkQtyMapping.getQuantity(), boqWorkQtyMapping.getRemark(),
				boqWorkQtyMapping.getIsBoqEditable(), boqWorkQtyMapping.getIsActive(), boqWorkQtyMapping.getVersion(),
				new Date(), boqWorkQtyMapping.getModifiedBy());

	}

	public WorkorderBoqWorkLocation workorderBoqWorkLocationEntityToAmendEntity(
			WorkorderBoqWorkLocation boqWorkLocation) {
		if (boqWorkLocation == null)
			return null;
		return new WorkorderBoqWorkLocation(null, null, boqWorkLocation.getStructureTypeId(),
				boqWorkLocation.getStructureId(), boqWorkLocation.getFromChainageId(),
				boqWorkLocation.getToChainageId(), boqWorkLocation.getIsActive(), boqWorkLocation.getSiteId(),
				new Date(), boqWorkLocation.getModifiedBy());

	}

	public WorkorderBillInfo workorderBillInfoUpdateRequestDtoToEntity(WorkorderBillInfoUpdateRequest obj) {
		if (obj == null)
			return null;
		return new WorkorderBillInfo(null, obj.getPreviousBillNo(), obj.getPreviousBillAmount(),
				obj.getSystemBillStartDate(), null, obj.getApplicableIgst(), obj.getIsIgstOnly(), obj.getTotal(),
				obj.getTotalDeduction(), obj.getPayableAmount(), obj.getId(), obj.getSiteId(), true, new Date(),
				obj.getUserDetail().getId(), new Date(), obj.getUserDetail().getId());
	}

	public BillDeduction billDeductionMappingDTOToEntity(BillDeductionMappingDTO obj, UserDetail userDetail) {
		if (obj == null)
			return null;
		return new BillDeduction(null, obj.getDeductionName(), true, userDetail.getCompanyId(), new Date(),
				userDetail.getId(), new Date(), userDetail.getId());
	}

	public BillDeductionMapTransac billDeductionMappingEntityToBillDeductionMapTransacEntity(BillDeductionMapping obj,
			UserDetail userDetail) {
		if (obj == null)
			return null;
		return new BillDeductionMapTransac(null, null, obj.getWorkorderBillInfoId(), obj.getBillDeductionId(),
				obj.getIsPercentage(), obj.getValue(), true, new Date(), userDetail.getId());
	}

	public BillDeductionResponseDto billDeductionMappingEntitytoBillDeductionResponseDto(BillDeductionMapping obj) {
		if (obj == null)
			return null;
		return new BillDeductionResponseDto(obj.getId(), obj.getBillDeduction().getName(), obj.getIsPercentage(),
				obj.getValue());
	}

}
