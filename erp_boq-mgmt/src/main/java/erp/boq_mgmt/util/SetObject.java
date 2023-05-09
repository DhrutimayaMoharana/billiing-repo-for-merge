package erp.boq_mgmt.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import erp.boq_mgmt.dto.BoqItemDTO;
import erp.boq_mgmt.dto.CategoryBoqParentChildDTO;
import erp.boq_mgmt.dto.CategoryBoqsRenderDTO;
import erp.boq_mgmt.dto.CategoryItemDTO;
import erp.boq_mgmt.dto.ChainageBoqQuantityMappingDTO;
import erp.boq_mgmt.dto.ChainageDTO;
import erp.boq_mgmt.dto.ChainageTraverseDTO;
import erp.boq_mgmt.dto.ConsolidatedBoqParentChildDTO;
import erp.boq_mgmt.dto.ConsolidatedBoqsDTO;
import erp.boq_mgmt.dto.ConsolidatedBoqsRenderDTO;
import erp.boq_mgmt.dto.DocumentTypeDTO;
import erp.boq_mgmt.dto.EngineStateDTO;
import erp.boq_mgmt.dto.FileDTO;
import erp.boq_mgmt.dto.HighwayBoqItemsRenderDTO;
import erp.boq_mgmt.dto.HighwayBoqMappingDTO;
import erp.boq_mgmt.dto.HighwayBoqParentChildDTO;
import erp.boq_mgmt.dto.HighwayBoqsCategoryRenderDTO;
import erp.boq_mgmt.dto.HighwayBoqsRenderDTO;
import erp.boq_mgmt.dto.HighwayBoqsSubcategoryRenderDTO;
import erp.boq_mgmt.dto.S3FileDTO;
import erp.boq_mgmt.dto.SbqParentChildDTO;
import erp.boq_mgmt.dto.SbqRenderDTO;
import erp.boq_mgmt.dto.SiteDTO;
import erp.boq_mgmt.dto.StructureBoqQuantityMappingDTO;
import erp.boq_mgmt.dto.StructureDTO;
import erp.boq_mgmt.dto.StructureTypeDTO;
import erp.boq_mgmt.dto.SubcategoryItemDTO;
import erp.boq_mgmt.dto.UnitDTO;
import erp.boq_mgmt.dto.UnitTypeDTO;
import erp.boq_mgmt.dto.UserDetail;
import erp.boq_mgmt.dto.request.BoqCostDefinitionAddRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionLabourAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionMachineryAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionMaterialAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionUpdateRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsLabourAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsMachineryAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsMaterialAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiChecklistItemBoqsAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiCustomWorkItemAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiMainAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiMainCommentAddUpdateRequest;
import erp.boq_mgmt.dto.request.WorkLayerBoqsAddUpdateRequest;
import erp.boq_mgmt.dto.response.BoqCostDefinitionFetchByIdResponse;
import erp.boq_mgmt.dto.response.BoqCostDefinitionFetchFinalSuccessResponse;
import erp.boq_mgmt.dto.response.BoqCostDefinitionFetchListResponse;
import erp.boq_mgmt.dto.response.BoqCostDefinitionLabourResponse;
import erp.boq_mgmt.dto.response.BoqCostDefinitionMachineryResponse;
import erp.boq_mgmt.dto.response.BoqCostDefinitionMaterialResponse;
import erp.boq_mgmt.dto.response.BoqCostDefinitionStateTransitionFetchResponse;
import erp.boq_mgmt.dto.response.BoqMasterLmpsFetchByIdResponse;
import erp.boq_mgmt.dto.response.BoqMasterLmpsFetchFinalSuccessResponse;
import erp.boq_mgmt.dto.response.BoqMasterLmpsFetchListResponse;
import erp.boq_mgmt.dto.response.BoqMasterLmpsLabourResponse;
import erp.boq_mgmt.dto.response.BoqMasterLmpsMachineryResponse;
import erp.boq_mgmt.dto.response.BoqMasterLmpsMaterialResponse;
import erp.boq_mgmt.dto.response.BoqMasterLmpsStateTransitionFetchResponse;
import erp.boq_mgmt.dto.response.ChainageResponseDTO;
import erp.boq_mgmt.dto.response.IdNameDTO;
import erp.boq_mgmt.dto.response.MaterialFetchResponse;
import erp.boq_mgmt.dto.response.MinimalStructureResponse;
import erp.boq_mgmt.dto.response.PrintRfiMainResponse;
import erp.boq_mgmt.dto.response.ProjectPlanBoqResponse;
import erp.boq_mgmt.dto.response.RfiChecklistItemFetchFinalSuccessListResponse;
import erp.boq_mgmt.dto.response.RfiChecklistItemStateTransitionFetchResponse;
import erp.boq_mgmt.dto.response.RfiCustomWorkItemFetchByIdResponse;
import erp.boq_mgmt.dto.response.RfiCustomWorkItemFetchFinalSuccessListResponse;
import erp.boq_mgmt.dto.response.RfiCustomWorkItemFetchListResponse;
import erp.boq_mgmt.dto.response.RfiCustomWorkItemResponseV2;
import erp.boq_mgmt.dto.response.RfiCustomWorkItemStateTransitionFetchResponse;
import erp.boq_mgmt.dto.response.RfiMainCommentResponse;
import erp.boq_mgmt.dto.response.RfiMainResponse;
import erp.boq_mgmt.dto.response.RfiMainStateTransitionFetchResponse;
import erp.boq_mgmt.dto.response.S3FileResponse;
import erp.boq_mgmt.dto.response.UndefinedCostDefinitionBoqItemResponse;
import erp.boq_mgmt.dto.response.UndefinedMasterLmpsBoqItemResponse;
import erp.boq_mgmt.dto.response.WorkLayerFetchFinalSuccessListResponse;
import erp.boq_mgmt.dto.response.WorkLayerStateTransitionFetchResponse;
import erp.boq_mgmt.dto.response.WorkTypeBoqResponse;
import erp.boq_mgmt.entity.BoqCostDefinition;
import erp.boq_mgmt.entity.BoqCostDefinitionLabour;
import erp.boq_mgmt.entity.BoqCostDefinitionMachinery;
import erp.boq_mgmt.entity.BoqCostDefinitionMaterial;
import erp.boq_mgmt.entity.BoqCostDefinitionStateTransition;
import erp.boq_mgmt.entity.BoqItem;
import erp.boq_mgmt.entity.BoqMasterLmps;
import erp.boq_mgmt.entity.BoqMasterLmpsLabour;
import erp.boq_mgmt.entity.BoqMasterLmpsMachinery;
import erp.boq_mgmt.entity.BoqMasterLmpsMaterial;
import erp.boq_mgmt.entity.BoqMasterLmpsStateTransition;
import erp.boq_mgmt.entity.CategoryItem;
import erp.boq_mgmt.entity.Chainage;
import erp.boq_mgmt.entity.ChainageBoqQuantityMapping;
import erp.boq_mgmt.entity.ChainageBoqQuantityTransacs;
import erp.boq_mgmt.entity.ChainageTraverse;
import erp.boq_mgmt.entity.DocumentType;
import erp.boq_mgmt.entity.EngineState;
import erp.boq_mgmt.entity.FileEntity;
import erp.boq_mgmt.entity.HighwayBoqMapping;
import erp.boq_mgmt.entity.HighwayBoqTransacs;
import erp.boq_mgmt.entity.Material;
import erp.boq_mgmt.entity.RfiChecklistItemBoqsV2;
import erp.boq_mgmt.entity.RfiChecklistItemStateTransition;
import erp.boq_mgmt.entity.RfiChecklistItems;
import erp.boq_mgmt.entity.RfiCustomWorkItemStateTransition;
import erp.boq_mgmt.entity.RfiCustomWorkItems;
import erp.boq_mgmt.entity.RfiCustomWorkItemsV2;
import erp.boq_mgmt.entity.RfiMain;
import erp.boq_mgmt.entity.RfiMainComments;
import erp.boq_mgmt.entity.RfiMainStateTransition;
import erp.boq_mgmt.entity.S3File;
import erp.boq_mgmt.entity.Site;
import erp.boq_mgmt.entity.Structure;
import erp.boq_mgmt.entity.StructureBoqQuantityMapping;
import erp.boq_mgmt.entity.StructureBoqQuantityTransacs;
import erp.boq_mgmt.entity.StructureType;
import erp.boq_mgmt.entity.SubcategoryItem;
import erp.boq_mgmt.entity.Unit;
import erp.boq_mgmt.entity.UnitType;
import erp.boq_mgmt.entity.WorkLayer;
import erp.boq_mgmt.entity.WorkLayerBoqsV2;
import erp.boq_mgmt.entity.WorkLayerStateTransition;
import erp.boq_mgmt.enums.ItemType;
import erp.boq_mgmt.enums.MachineryRangeType;
import erp.boq_mgmt.enums.MachineryTrip;
import erp.boq_mgmt.enums.UnitTypes;

@Component
public class SetObject {

	public static final String fileSavingPath = "/opt/erp/";
	public static final String folderName = "erp_files/";

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

	public StructureTypeDTO structureTypeEntityToDto(StructureType obj) {
		if (obj == null)
			return null;
		return new StructureTypeDTO(obj.getId(), obj.getCode(), obj.getName(), obj.getIsActive(), obj.getCreatedOn(),
				obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy(), obj.getCompanyId());
	}

	public StructureType structureTypeDtoToEntity(StructureTypeDTO obj) {
		if (obj == null)
			return null;
		return new StructureType(obj.getId(), obj.getCode(), obj.getName(), obj.getIsActive(), obj.getCreatedOn(),
				obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy(), obj.getCompanyId());
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

	public Structure structureDtoToEntity(StructureDTO obj) {
		if (obj == null)
			return null;
		return new Structure(obj.getId(), obj.getGroupId(), obj.getName(), structureTypeDtoToEntity(obj.getType()),
				chainageDtoToEntity(obj.getStartChainage()), chainageDtoToEntity(obj.getEndChainage()), obj.getSiteId(),
				true, obj.getCreatedOn(), obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy(),
				obj.getCompanyId());
	}

	public StructureDTO structureEntityToDto(Structure obj) {
		if (obj == null)
			return null;
		return new StructureDTO(obj.getId(), obj.getGroupId(), obj.getName(), structureTypeEntityToDto(obj.getType()),
				chainageEntityToDto(obj.getStartChainage()), chainageEntityToDto(obj.getEndChainage()), obj.getSiteId(),
				obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy(),
				obj.getCompanyId(), obj.getGroup() != null ? obj.getGroup().getName() : null);
	}

	public HighwayBoqTransacs highwayBoqEntityMappingToTransac(HighwayBoqMapping obj) {
		if (obj == null)
			return null;
		return new HighwayBoqTransacs(null, obj.getBoq().getId(), obj.getDescription(), obj.getDescription(),
				obj.getCategory() != null ? obj.getCategory().getId() : null,
				obj.getSubcategory() != null ? obj.getSubcategory().getId() : null, obj.getRate(), obj.getMaxRate(),
				obj.getQuantity(), obj.getIsActive(), obj.getRemark(), obj.getSiteId(), obj.getFile().getId(),
				obj.getVersion(), obj.getQuantityVersion(), new Date(), obj.getModifiedBy(), obj.getCompanyId());
	}

	public ConsolidatedBoqsDTO highwayBoqEntityToConsolidatedBoqDTO(HighwayBoqMapping obj) {
		if (obj == null)
			return null;
		return new ConsolidatedBoqsDTO(boqItemEntityToDto(obj.getBoq()), categoryItemEntityToDto(obj.getCategory()),
				subcategoryItemEntityToDto(obj.getSubcategory()), obj.getQuantity(), obj.getRate(), obj.getMaxRate());
	}

	public HighwayBoqMappingDTO boqCategoryMapEntityToDto(HighwayBoqMapping obj) {
		if (obj == null)
			return null;
		return new HighwayBoqMappingDTO(obj.getId(), boqItemEntityToDto(obj.getBoq()), obj.getDescription(),
				obj.getVendorDescription(), categoryItemEntityToDto(obj.getCategory()),
				subcategoryItemEntityToDto(obj.getSubcategory()), obj.getQuantity(), obj.getRate(), obj.getMaxRate(),
				obj.getRemark(), obj.getSiteId(), fileEntityToDto(obj.getFile()), obj.getVersion(),
				obj.getQuantityVersion(), obj.getIsActive(), obj.getModifiedOn(), obj.getModifiedBy(),
				obj.getCompanyId());
	}

	public HighwayBoqMapping boqCategoryMapDtoToEntity(HighwayBoqMappingDTO obj) {
		if (obj == null)
			return null;
		return new HighwayBoqMapping(obj.getId(), boqItemDtoToEntity(obj.getBoq()), obj.getDescription(),
				obj.getVendorDescription(), categoryItemDtoToEntity(obj.getCategory()),
				subcategoryItemDtoToEntity(obj.getSubcategory()), obj.getQuantity(), obj.getRate(), obj.getMaxRate(),
				obj.getRemark(), obj.getSiteId(), fileDtoToEntity(obj.getFile()), obj.getVersion(),
				obj.getQuantityVersion(), obj.getIsActive(), obj.getModifiedOn(), obj.getModifiedBy(),
				obj.getCompanyId());
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
		return new CategoryItem(obj.getId(), obj.getCode() != null ? obj.getCode().trim() : null,
				obj.getStandardBookCode() != null ? obj.getStandardBookCode().trim() : null,
				obj.getName() != null ? obj.getName().trim() : null, obj.getOfStructure(), obj.getIsActive(),
				obj.getCreatedOn(), obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy(), obj.getCompanyId());
	}

	public SiteDTO siteEntityToDto(Site obj) {
		if (obj == null)
			return null;
		return new SiteDTO(obj.getId(), obj.getName(), obj.getIsActive(), obj.getCompanyId());
	}

	public Site siteDtoToEntity(SiteDTO obj) {
		if (obj == null)
			return null;
		return new Site(obj.getId(), obj.getName(), obj.getIsActive(), obj.getCompanyId());
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
		return new Chainage(obj.getId(), obj.getName(),
				obj.getName() != null ? Integer.parseInt(obj.getName().replaceAll("[^0-9]", "")) : 0, obj.getIsActive(),
				chainageTraverseDtoToEntity(obj.getPrevious()), chainageTraverseDtoToEntity(obj.getNext()),
				obj.getSiteId(), obj.getCompanyId(), obj.getModifiedOn(), obj.getModifiedBy());
	}

	public ChainageDTO chainageEntityToDto(Chainage obj) {
		if (obj == null)
			return null;
		return new ChainageDTO(obj.getId(), obj.getName(), obj.getIsActive(),
				chainageTraverseEntityToDto(obj.getPrevious()), chainageTraverseEntityToDto(obj.getNext()),
				obj.getSiteId(), obj.getCompanyId(), obj.getModifiedOn(), obj.getModifiedBy());
	}

	public ChainageResponseDTO chainageEntityToResponseDto(Chainage obj) {
		if (obj == null)
			return null;
		return new ChainageResponseDTO(obj.getId(), obj.getName());
	}

	public ChainageBoqQuantityMapping chainageBoqQtyMapDtoToEntity(ChainageBoqQuantityMappingDTO obj) {
		if (obj == null)
			return null;
		return new ChainageBoqQuantityMapping(obj.getId(), chainageDtoToEntity(obj.getChainage()),
				boqItemDtoToEntity(obj.getBoq()), boqCategoryMapDtoToEntity(obj.getHighwayBoq()), obj.getSiteId(),
				obj.getLhsQuantity(), obj.getRhsQuantity(), obj.getStructureRemark(), obj.getIsActive(),
				obj.getModifiedOn(), obj.getModifiedBy(), obj.getCompanyId());
	}

	public ChainageBoqQuantityMappingDTO chainageBoqQtyMapEntityToDto(ChainageBoqQuantityMapping obj) {
		if (obj == null)
			return null;
		return new ChainageBoqQuantityMappingDTO(obj.getId(), chainageEntityToDto(obj.getChainage()),
				boqItemEntityToDto(obj.getBoq()), boqCategoryMapEntityToDto(obj.getHighwayBoq()), obj.getSiteId(),
				obj.getLhsQuantity(), obj.getRhsQuantity(), obj.getStructureRemark(), obj.getIsActive(),
				obj.getModifiedOn(), obj.getModifiedBy(), obj.getCompanyId());
	}

	public ChainageBoqQuantityTransacs cbqEntityMappingToTransac(ChainageBoqQuantityMapping obj) {
		if (obj == null)
			return null;
		return new ChainageBoqQuantityTransacs(null, obj.getChainage().getId(), obj.getBoq().getId(),
				obj.getHighwayBoq().getId(), obj.getSiteId(), obj.getLhsQuantity(), obj.getRhsQuantity(),
				obj.getStructureRemark(), obj.getIsActive(), obj.getModifiedOn(), obj.getModifiedBy(),
				obj.getCompanyId());
	}

	public ConsolidatedBoqsDTO structureBoqEntityToConsolidatedBoqDTO(StructureBoqQuantityMapping obj) {
		if (obj == null)
			return null;
		return new ConsolidatedBoqsDTO(boqItemEntityToDto(obj.getBoq()), categoryItemEntityToDto(obj.getCategory()),
				subcategoryItemEntityToDto(obj.getSubcategory()), obj.getQuantity(), obj.getRate(), obj.getMaxRate());
	}

	public StructureBoqQuantityMapping structureBoqQtyMapDtoToEntity(StructureBoqQuantityMappingDTO obj) {
		if (obj == null)
			return null;
		return new StructureBoqQuantityMapping(obj.getId(), structureDtoToEntity(obj.getStructure()),
				obj.getDescription(), obj.getVendorDescription(), unitDtoToEntity(obj.getUnit()),
				boqItemDtoToEntity(obj.getBoq()), categoryItemDtoToEntity(obj.getCategory()),
				subcategoryItemDtoToEntity(obj.getSubcategory()), obj.getSiteId(), obj.getQuantity(), obj.getRate(),
				obj.getMaxRate(), obj.getRemark(), fileDtoToEntity(obj.getFile()), obj.getVersion(),
				obj.getQuantityVersion(), obj.getModifiedOn(), obj.getModifiedBy(), obj.getIsActive(),
				obj.getCompanyId());
	}

	public StructureBoqQuantityMappingDTO structureBoqQtyMapEntityToDto(StructureBoqQuantityMapping obj) {
		if (obj == null)
			return null;
		return new StructureBoqQuantityMappingDTO(obj.getId(), structureEntityToDto(obj.getStructure()),
				obj.getDescription(), obj.getVendorDescription(), unitEntityToDto(obj.getUnit()),
				boqItemEntityToDto(obj.getBoq()), categoryItemEntityToDto(obj.getCategory()),
				subcategoryItemEntityToDto(obj.getSubcategory()), obj.getSiteId(), obj.getQuantity(), obj.getRate(),
				obj.getMaxRate(), obj.getRemark(), fileEntityToDto(obj.getFile()), obj.getVersion(),
				obj.getQuantityVersion(), obj.getModifiedOn(), obj.getModifiedBy(), obj.getIsActive(),
				obj.getCompanyId());
	}

	public StructureBoqQuantityTransacs sbqEntityMappingToTransac(StructureBoqQuantityMapping obj) {
		if (obj == null)
			return null;
		return new StructureBoqQuantityTransacs(null, obj.getStructure().getId(), obj.getUnit().getId(),
				obj.getDescription(), obj.getVendorDescription(), obj.getBoq().getId(),
				obj.getCategory() != null ? obj.getCategory().getId() : null,
				obj.getSubcategory() != null ? obj.getSubcategory().getId() : null, obj.getSiteId(), obj.getQuantity(),
				obj.getRate(), obj.getMaxRate(), obj.getRemark(), obj.getFile().getId(), obj.getVersion(),
				obj.getQuantityVersion(), new Date(), obj.getModifiedBy(), obj.getIsActive(), obj.getCompanyId());
	}

	public BoqItem updatedBoqItem(BoqItem result, BoqItemDTO obj) {

		if (result == null || obj == null)
			return null;
//		result.setCode(obj.getCode());
		result.setStandardBookCode(obj.getStandardBookCode());
		result.setName(obj.getName());
//		result.setUnit(unitDtoToEntity(obj.getUnit()));
//		result.setSubcategory(obj.getSubcategory() != null && result.getSubcategory() != null &&
//				obj.getSubcategory().getId().equals(result.getSubcategory().getId()) ?
//						result.getSubcategory() : subcategoryItemDtoToEntity(obj.getSubcategory()));
//		result.setCategory(obj.getCategory() != null && result.getCategory() != null &&
//				obj.getCategory().getId().equals(result.getCategory().getId()) ?
//						result.getCategory() : categoryItemDtoToEntity(obj.getCategory()));
		result.setModifiedOn(new Date());
		result.setModifiedBy(obj.getModifiedBy());
		return result;
	}

	public Structure updatedStructure(Structure result, StructureDTO obj) {

		if (result == null || obj == null)
			return null;
		result.setName(obj.getName());
		if (!result.getType().getId().equals(obj.getType().getId())) {
			result.setType(new StructureType(obj.getType().getId()));
		}
		result.setGroupId(obj.getGroupId());
		result.setStartChainage(chainageDtoToEntity(obj.getStartChainage()));
		result.setEndChainage(chainageDtoToEntity(obj.getEndChainage()));
		result.setModifiedOn(new Date());
		result.setModifiedBy(obj.getModifiedBy());
		result.setSiteId(obj.getSiteId());
		return result;
	}

	public ChainageBoqQuantityMapping updatedCbq(ChainageBoqQuantityMapping result, ChainageBoqQuantityMapping obj) {

		if (result == null || obj == null)
			return null;
		result.setChainage(obj.getChainage().getId().equals(result.getChainage().getId()) ? result.getChainage()
				: obj.getChainage());
		result.setBoq(obj.getBoq().getId().equals(result.getBoq().getId()) ? result.getBoq() : obj.getBoq());
		result.setHighwayBoq(obj.getHighwayBoq().getId().equals(result.getHighwayBoq().getId()) ? result.getHighwayBoq()
				: obj.getHighwayBoq());
		result.setSiteId(obj.getSiteId());
		result.setLhsQuantity(obj.getLhsQuantity());
		result.setRhsQuantity(obj.getRhsQuantity());
		result.setCompanyId(obj.getCompanyId());
		result.setModifiedBy(obj.getModifiedBy());
		result.setIsActive(obj.getIsActive());
		result.setModifiedOn(new Date());
		return result;
	}

	public SubcategoryItem updatedSubcategoryItem(SubcategoryItem result, SubcategoryItem obj) {

		if (result == null || obj == null)
			return null;
		result.setName(obj.getName());
//		result.setCode(obj.getCode());
		result.setStandardBookCode(obj.getStandardBookCode());
//		result.setIsActive(obj.getIsActive());
		result.setModifiedOn(new Date());
		result.setModifiedBy(obj.getModifiedBy());
		return result;
	}

	public StructureBoqQuantityMapping updatedSbq(StructureBoqQuantityMapping result, StructureBoqQuantityMapping obj) {

		if (result == null || obj == null)
			return null;
		result.setStructure(obj.getStructure().getId().equals(result.getStructure().getId()) ? result.getStructure()
				: obj.getStructure());
		result.setBoq(obj.getBoq().getId().equals(result.getBoq().getId()) ? result.getBoq() : obj.getBoq());
		result.setSubcategory(obj.getSubcategory() != null && result.getSubcategory() != null
				&& obj.getSubcategory().getId().equals(result.getSubcategory().getId()) ? result.getSubcategory()
						: obj.getSubcategory());
		result.setCategory(obj.getCategory() != null && result.getCategory() != null
				&& obj.getCategory().getId().equals(result.getCategory().getId()) ? result.getCategory()
						: obj.getCategory());
		result.setSiteId(obj.getSiteId());
		result.setQuantity(obj.getQuantity());
		result.setDescription(obj.getDescription());
		result.setVendorDescription(obj.getVendorDescription());
		result.setRate(obj.getRate());
		result.setMaxRate(obj.getMaxRate());
		result.setRemark(obj.getRemark());
		result.setCompanyId(obj.getCompanyId());
		result.setModifiedBy(obj.getModifiedBy());
		result.setIsActive(obj.getIsActive());
		result.setModifiedOn(new Date());
		return result;
	}

	public CategoryItem updatedCategoryItem(CategoryItem result, CategoryItem obj) {

		if (result == null || obj == null)
			return null;
		result.setName(obj.getName());
//		result.setCode(obj.getCode());
//		result.setStandardBookCode(obj.getStandardBookCode());
		result.setModifiedBy(obj.getModifiedBy());
		result.setModifiedOn(new Date());
		return result;
	}

	public StructureType updatedStructureType(StructureType result, StructureType obj) {

		if (result == null || obj == null)
			return null;
		result.setName(obj.getName());
		result.setCode(obj.getCode());
		result.setCompanyId(obj.getCompanyId());
		result.setModifiedOn(new Date());
		result.setModifiedBy(obj.getModifiedBy());
		result.setIsActive(true);
		return result;
	}

	public HighwayBoqMapping updatedBoqCategoryMapping(HighwayBoqMapping result, HighwayBoqMapping obj) {

		if (result == null || obj == null)
			return null;
		result.setBoq(obj.getBoq().getId().equals(result.getBoq().getId()) ? result.getBoq() : obj.getBoq());
		result.setSubcategory(obj.getSubcategory() != null && result.getSubcategory() != null
				&& obj.getSubcategory().getId().equals(result.getSubcategory().getId()) ? result.getSubcategory()
						: obj.getSubcategory());
		result.setCategory(obj.getCategory() != null && result.getCategory() != null
				&& obj.getCategory().getId().equals(result.getCategory().getId()) ? result.getCategory()
						: obj.getCategory());
		result.setSiteId(obj.getSiteId());
		result.setCompanyId(obj.getCompanyId());
		result.setDescription(obj.getDescription());
		result.setRate(obj.getRate());
		result.setVendorDescription(obj.getVendorDescription());
		result.setMaxRate(obj.getMaxRate());
		result.setRemark(obj.getRemark());
		result.setQuantity(obj.getQuantity());
		result.setModifiedBy(obj.getModifiedBy());
		result.setIsActive(obj.getIsActive());
		result.setModifiedOn(new Date());
		return result;
	}

	public HighwayBoqsRenderDTO renderNestedHighwayBoqs(List<HighwayBoqMappingDTO> highwayBoqList) {

		Double totalAmount = 0.0;
		List<HighwayBoqsCategoryRenderDTO> resultCategories = new ArrayList<>();
		HighwayBoqsRenderDTO result = new HighwayBoqsRenderDTO(resultCategories, totalAmount);
		Set<String> uniqueCategories = new HashSet<>();
		for (HighwayBoqMappingDTO highBoq : highwayBoqList) {
			if (highBoq.getCategory() != null && highBoq.getCategory().getName() != null) {
				uniqueCategories.add(highBoq.getCategory().getName());
			}
		}
		for (String categoryName : uniqueCategories) {
			List<HighwayBoqMappingDTO> categoryData = new ArrayList<>();
			for (HighwayBoqMappingDTO highBoq : highwayBoqList) {
				if (highBoq.getCategory() != null && highBoq.getCategory().getName() != null
						&& highBoq.getCategory().getName().equals(categoryName)) {
					categoryData.add(highBoq);
				}
			}
			Double subTotal = 0.0;
			List<HighwayBoqItemsRenderDTO> boqItems = new ArrayList<>();
			List<HighwayBoqsSubcategoryRenderDTO> subcategories = new ArrayList<>();
			for (HighwayBoqMappingDTO data : categoryData) {
				if (data.getSubcategory() == null) {
					Double amount = data.getRate() * data.getQuantity();
					HighwayBoqItemsRenderDTO boqItem = new HighwayBoqItemsRenderDTO(data.getBoq().getId(),
							data.getBoq().getCode(), data.getBoq().getName(), data.getDescription(),
							data.getBoq().getUnit().getName(), data.getRate(), data.getMaxRate(), data.getQuantity(),
							amount);
					subTotal += amount;
					boqItems.add(boqItem);
				}
			}
			Set<String> uniqueSubcategories = new HashSet<>();
			for (HighwayBoqMappingDTO data : categoryData) {
				if (data.getSubcategory() != null && data.getSubcategory().getName() != null) {
					uniqueSubcategories.add(data.getSubcategory().getName());
				}
			}
			for (String subcategoryName : uniqueSubcategories) {
				List<HighwayBoqItemsRenderDTO> subcatBoqs = new ArrayList<>();
				HighwayBoqsSubcategoryRenderDTO renderSubcategory = new HighwayBoqsSubcategoryRenderDTO(null,
						subcategoryName, subcatBoqs);
				for (HighwayBoqMappingDTO data : categoryData) {
					if (data.getSubcategory() != null && data.getSubcategory().getName() != null
							&& data.getSubcategory().getName().equals(subcategoryName)) {
						renderSubcategory.setId(data.getSubcategory().getId());
						Double amount = data.getRate() * data.getQuantity();
						HighwayBoqItemsRenderDTO boqItem = new HighwayBoqItemsRenderDTO(data.getBoq().getId(),
								data.getBoq().getCode(), data.getBoq().getName(), data.getDescription(),
								data.getBoq().getUnit().getName(), data.getRate(), data.getMaxRate(),
								data.getQuantity(), amount);
						subTotal += amount;
						subcatBoqs.add(boqItem);
					}
				}
				subcategories.add(renderSubcategory);
			}
			HighwayBoqsCategoryRenderDTO renderCategory = new HighwayBoqsCategoryRenderDTO(null, categoryName, subTotal,
					subcategories, boqItems);
			if (categoryData != null && categoryData.size() > 0)
				renderCategory.setCategoryId(categoryData.get(0).getId());
			resultCategories.add(renderCategory);
		}
		for (HighwayBoqsCategoryRenderDTO obj : resultCategories) {
			if (obj.getSubTotal() != null)
				totalAmount += obj.getSubTotal();
		}
		result.setTotalAmount(totalAmount);
		result.setData(resultCategories);
		return result;
	}

	public HighwayBoqsRenderDTO renderChildParentHighwayBoqs(List<HighwayBoqMappingDTO> highwayBoqList,
			List<CategoryItemDTO> categories) {

		List<HighwayBoqParentChildDTO> resultList = new ArrayList<>();
		int uniquePid = 0;
		Double totalAmount = 0.0;
		HighwayBoqsRenderDTO result = new HighwayBoqsRenderDTO(resultList, totalAmount);
		Set<String> uniqueCategories = new HashSet<>();
		for (HighwayBoqMappingDTO highBoq : highwayBoqList) {
			if (highBoq.getCategory() != null && highBoq.getCategory().getName() != null) {
				uniqueCategories.add(highBoq.getCategory().getName());
			}
		}
		for (String categoryName : uniqueCategories) {
			++uniquePid;
			List<HighwayBoqMappingDTO> categoryData = new ArrayList<>();
			for (HighwayBoqMappingDTO highBoq : highwayBoqList) {
				if (highBoq.getCategory() != null && highBoq.getCategory().getName() != null
						&& highBoq.getCategory().getName().equals(categoryName)) {
					categoryData.add(highBoq);
				}
			}
			if (categoryData != null && categoryData.size() > 0) {
				CategoryItemDTO category = categoryData.get(0).getCategory();
				Boolean isStructureCategory = category.getOfStructure();
				Double parentAmount = 0.0;
				HighwayBoqParentChildDTO parentDTO = new HighwayBoqParentChildDTO(category.getId(),
						categoryData.get(0).getId(), category.getName(), category.getName(), category.getName(), null,
						null, null, false, category.getCode(), category.getStandardBookCode(), null, null, null,
						parentAmount, null, ItemType.Category.toString(), null, uniquePid, null);
				int catPid = uniquePid;
				for (HighwayBoqMappingDTO obj : categoryData) {
					if (obj.getSubcategory() == null) {
						BoqItemDTO boq = obj.getBoq();
						Double amount = obj.getRate() * obj.getQuantity();
						int boqPid = ++uniquePid;
						Boolean isImportable = boq.getUnit().getType() == null || (boq.getUnit().getType() != null
								&& boq.getUnit().getType().getId().equals(UnitTypes.COUNT.getTypeId())) ? false : true;
						String unitTypeName = null;
						if (boq.getUnit().getType() != null) {
							unitTypeName = boq.getUnit().getType().getName();
						}
						HighwayBoqParentChildDTO childDTO = new HighwayBoqParentChildDTO(boq.getId(), obj.getId(),
								boq.getName(), obj.getDescription(), obj.getVendorDescription(), boq.getUnit().getId(),
								boq.getUnit().getName(), unitTypeName, isImportable && !isStructureCategory,
								boq.getCode(), boq.getStandardBookCode(), obj.getRate(), obj.getMaxRate(),
								obj.getQuantity(), amount, obj.getRemark(), ItemType.Boq.toString(),
								ItemType.Category.toString(), boqPid, catPid);
						resultList.add(childDTO);
						parentAmount += amount;
						totalAmount += amount;
					}
				}

				Set<String> uniqueSubcategories = new HashSet<>();
				for (HighwayBoqMappingDTO obj : categoryData) {
					if (obj.getSubcategory() != null) {
						uniqueSubcategories.add(obj.getSubcategory().getName());
					}
				}
				for (String subcategoryName : uniqueSubcategories) {
					List<HighwayBoqMappingDTO> subcategoryData = new ArrayList<>();
					for (HighwayBoqMappingDTO highBoq : categoryData) {
						if (highBoq.getSubcategory() != null && highBoq.getSubcategory().getName() != null
								&& highBoq.getSubcategory().getName().equals(subcategoryName)) {
							subcategoryData.add(highBoq);
						}
					}
					if (subcategoryData != null && subcategoryData.size() > 0) {
						SubcategoryItemDTO subcategory = subcategoryData.get(0).getSubcategory();
						Double subparentAmount = 0.0;
						int subcatPid = ++uniquePid;
						HighwayBoqParentChildDTO subparentDTO = new HighwayBoqParentChildDTO(subcategory.getId(),
								subcategoryData.get(0).getId(), subcategory.getName(), subcategory.getName(),
								subcategory.getName(), null, null, null, false, subcategory.getCode(),
								subcategory.getStandardBookCode(), null, null, null, subparentAmount, null,
								ItemType.Subcategory.toString(), ItemType.Category.toString(), subcatPid, catPid);
						for (HighwayBoqMappingDTO obj : subcategoryData) {
							if (obj.getBoq() != null) {
								BoqItemDTO boq = obj.getBoq();
								Double amount = obj.getRate() * obj.getQuantity();
								int boqPid = ++uniquePid;
								Boolean isImportable = boq.getUnit().getType() == null
										|| (boq.getUnit().getType() != null
												&& boq.getUnit().getType().getId().equals(UnitTypes.COUNT.getTypeId()))
														? false
														: true;
								String unitTypeName = null;
								if (boq.getUnit().getType() != null) {
									unitTypeName = boq.getUnit().getType().getName();
								}
								HighwayBoqParentChildDTO childDTO = new HighwayBoqParentChildDTO(boq.getId(),
										obj.getId(), boq.getName(), obj.getDescription(), obj.getVendorDescription(),
										boq.getUnit().getId(), boq.getUnit().getName(), unitTypeName,
										isImportable && !isStructureCategory, boq.getCode(), boq.getStandardBookCode(),
										obj.getRate(), obj.getMaxRate(), obj.getQuantity(), amount, obj.getRemark(),
										ItemType.Boq.toString(), ItemType.Subcategory.toString(), boqPid, subcatPid);
								resultList.add(childDTO);
								subparentAmount += amount;
								parentAmount += amount;
								totalAmount += amount;
							}
						}
						subparentDTO.setAmount(subparentAmount);
						resultList.add(subparentDTO);
					}
				}
				parentDTO.setAmount(parentAmount);
				resultList.add(parentDTO);
			}
		}
		if (categories != null && categories.size() > 0) {
			List<HighwayBoqParentChildDTO> resultListSorted = new ArrayList<>();
			List<HighwayBoqParentChildDTO> categoryBoqs = new ArrayList<>();
			for (CategoryItemDTO category : categories) {
				String code = category.getCode();
				if (code != null) {
					for (HighwayBoqParentChildDTO hbm : resultList) {
						if (hbm.getCode() != null && hbm.getCode().toLowerCase().startsWith(code.toLowerCase())) {
							categoryBoqs.add(hbm);
						}
					}
					categoryBoqs.sort(Comparator.comparing(HighwayBoqParentChildDTO::getCode));
					resultListSorted.addAll(resultListSorted);
				}
			}
			for (HighwayBoqParentChildDTO obj1 : resultList) {
				boolean isPresent = false;
				for (HighwayBoqParentChildDTO obj2 : resultListSorted) {
					if (obj1.getPid().equals(obj2.getPid())) {
						isPresent = true;
						break;
					}
				}
				if (!isPresent) {
					resultListSorted.add(obj1);
				}
			}
			result.setData(resultListSorted);
		} else {
			result.setData(resultList);
		}
		result.setTotalAmount(totalAmount);
		return result;
	}

	public SbqRenderDTO renderChildParentSbqs(List<StructureBoqQuantityMappingDTO> sbqList,
			List<CategoryItemDTO> categories) {

		List<SbqParentChildDTO> resultList = new ArrayList<>();
		int uniquePid = 0;
		Double totalAmount = 0.0;
		SbqRenderDTO result = new SbqRenderDTO(resultList, null, totalAmount);
		Set<String> uniqueCategories = new HashSet<>();
		for (StructureBoqQuantityMappingDTO highBoq : sbqList) {
			if (highBoq.getCategory() != null && highBoq.getCategory().getName() != null) {
				uniqueCategories.add(highBoq.getCategory().getName());
			}
		}
		for (String categoryName : uniqueCategories) {
			++uniquePid;
			List<StructureBoqQuantityMappingDTO> categoryData = new ArrayList<>();
			for (StructureBoqQuantityMappingDTO sbq : sbqList) {
				if (sbq.getCategory() != null && sbq.getCategory().getName() != null
						&& sbq.getCategory().getName().equals(categoryName)) {
					categoryData.add(sbq);
				}
			}
			if (categoryData != null && categoryData.size() > 0) {
				CategoryItemDTO category = categoryData.get(0).getCategory();
				Double parentAmount = 0.0;
				SbqParentChildDTO parentDTO = new SbqParentChildDTO(category.getId(), categoryData.get(0).getId(),
						category.getName(), category.getName(), category.getName(), null, null, false,
						category.getCode(), category.getStandardBookCode(), null, null, null, parentAmount, null,
						ItemType.Category.toString(), null, uniquePid, null);
				int catPid = uniquePid;
				for (StructureBoqQuantityMappingDTO obj : categoryData) {
					if (obj.getSubcategory() == null) {
						BoqItemDTO boq = obj.getBoq();
						Double amount = obj.getRate() * obj.getQuantity();
						int boqPid = ++uniquePid;
						Boolean isImportable = boq.getUnit().getType() == null || (boq.getUnit().getType() != null
								&& boq.getUnit().getType().getId().equals(UnitTypes.COUNT.getTypeId())) ? false : true;
						SbqParentChildDTO childDTO = new SbqParentChildDTO(boq.getId(), obj.getId(), boq.getName(),
								obj.getDescription(), obj.getVendorDescription(), obj.getUnit().getId(),
								obj.getUnit().getName(), isImportable, boq.getCode(), boq.getStandardBookCode(),
								obj.getRate(), obj.getMaxRate(), obj.getQuantity(), amount, obj.getRemark(),
								ItemType.Boq.toString(), ItemType.Category.toString(), boqPid, catPid);
						resultList.add(childDTO);
						parentAmount += amount;
						totalAmount += amount;
					}
				}

				Set<String> uniqueSubcategories = new HashSet<>();
				for (StructureBoqQuantityMappingDTO obj : categoryData) {
					if (obj.getSubcategory() != null) {
						uniqueSubcategories.add(obj.getSubcategory().getName());
					}
				}
				for (String subcategoryName : uniqueSubcategories) {
					List<StructureBoqQuantityMappingDTO> subcategoryData = new ArrayList<>();
					for (StructureBoqQuantityMappingDTO sbq : categoryData) {
						if (sbq.getSubcategory() != null && sbq.getSubcategory().getName() != null
								&& sbq.getSubcategory().getName().equals(subcategoryName)) {
							subcategoryData.add(sbq);
						}
					}
					if (subcategoryData != null && subcategoryData.size() > 0) {
						SubcategoryItemDTO subcategory = subcategoryData.get(0).getSubcategory();
						Double subparentAmount = 0.0;
						int subcatPid = ++uniquePid;
						SbqParentChildDTO subparentDTO = new SbqParentChildDTO(subcategory.getId(),
								subcategoryData.get(0).getId(), subcategory.getName(), subcategory.getName(),
								subcategory.getName(), null, null, false, subcategory.getCode(),
								subcategory.getStandardBookCode(), null, null, null, subparentAmount, null,
								ItemType.Subcategory.toString(), ItemType.Category.toString(), subcatPid, catPid);
						for (StructureBoqQuantityMappingDTO obj : subcategoryData) {
							if (obj.getBoq() != null) {
								BoqItemDTO boq = obj.getBoq();
								Double amount = obj.getRate() * obj.getQuantity();
								int boqPid = ++uniquePid;
								Boolean isImportable = boq.getUnit().getType() == null
										|| (boq.getUnit().getType() != null
												&& boq.getUnit().getType().getId().equals(UnitTypes.COUNT.getTypeId()))
														? false
														: true;
								SbqParentChildDTO childDTO = new SbqParentChildDTO(boq.getId(), obj.getId(),
										boq.getName(), obj.getDescription(), obj.getVendorDescription(),
										obj.getUnit().getId(), obj.getUnit().getName(), isImportable, boq.getCode(),
										boq.getStandardBookCode(), obj.getRate(), obj.getMaxRate(), obj.getQuantity(),
										amount, obj.getRemark(), ItemType.Boq.toString(),
										ItemType.Subcategory.toString(), boqPid, subcatPid);
								resultList.add(childDTO);
								subparentAmount += amount;
								parentAmount += amount;
								totalAmount += amount;
							}
						}
						subparentDTO.setAmount(subparentAmount);
						resultList.add(subparentDTO);
					}
				}
				parentDTO.setAmount(parentAmount);
				resultList.add(parentDTO);
			}
		}
		if (categories != null && categories.size() > 0) {
			List<SbqParentChildDTO> resultListSorted = new ArrayList<>();
			List<SbqParentChildDTO> categoryBoqs = new ArrayList<>();
			for (CategoryItemDTO category : categories) {
				String code = category.getCode();
				if (code != null) {
					for (SbqParentChildDTO sbq : resultList) {
						if (sbq.getCode() != null && sbq.getCode().toLowerCase().startsWith(code.toLowerCase())) {
							categoryBoqs.add(sbq);
						}
					}
					categoryBoqs.sort(Comparator.comparing(SbqParentChildDTO::getCode));
					resultListSorted.addAll(resultListSorted);
				}
			}
			for (SbqParentChildDTO obj1 : resultList) {
				boolean isPresent = false;
				for (SbqParentChildDTO obj2 : resultListSorted) {
					if (obj1.getPid().equals(obj2.getPid())) {
						isPresent = true;
						break;
					}
				}
				if (!isPresent) {
					resultListSorted.add(obj1);
				}
			}
			result.setData(resultListSorted);
		} else {
			result.setData(resultList);
		}
		result.setTotalAmount(totalAmount);
		return result;
	}

	public CategoryBoqsRenderDTO renderChildParentCategoryBoqs(List<BoqItemDTO> boqList,
			List<CategoryItemDTO> categories) {

		List<CategoryBoqParentChildDTO> resultList = new ArrayList<>();
		int uniquePid = 0;
		CategoryBoqsRenderDTO result = new CategoryBoqsRenderDTO();
		Set<String> uniqueCategories = new HashSet<>();
		for (BoqItemDTO boq : boqList) {
			if (boq.getCategory() != null && boq.getCategory().getName() != null) {
				uniqueCategories.add(boq.getCategory().getName());
			}
		}
		for (String categoryName : uniqueCategories) {
			++uniquePid;
			List<BoqItemDTO> categoryData = new ArrayList<>();
			for (BoqItemDTO boq : boqList) {
				if (boq.getCategory() != null && boq.getCategory().getName() != null
						&& boq.getCategory().getName().equals(categoryName)) {
					categoryData.add(boq);
				}
			}
			if (categoryData != null && categoryData.size() > 0) {
				CategoryItemDTO category = categoryData.get(0).getCategory();
				CategoryBoqParentChildDTO parentDTO = new CategoryBoqParentChildDTO(category.getId(),
						category.getName(), category.getName(), null, null, category.getCode(),
						category.getStandardBookCode(), ItemType.Category.toString(), null, uniquePid, null);
				int catPid = uniquePid;
				for (BoqItemDTO obj : categoryData) {
					if (obj.getSubcategory() == null) {
						BoqItemDTO boq = obj;
						int boqPid = ++uniquePid;
						CategoryBoqParentChildDTO childDTO = new CategoryBoqParentChildDTO(boq.getId(), boq.getName(),
								boq.getName(), boq.getUnit().getId(), boq.getUnit().getName(), boq.getCode(),
								boq.getStandardBookCode(), ItemType.Boq.toString(), ItemType.Category.toString(),
								boqPid, catPid);
						resultList.add(childDTO);
					}
				}

				Set<String> uniqueSubcategories = new HashSet<>();
				for (BoqItemDTO obj : categoryData) {
					if (obj.getSubcategory() != null) {
						uniqueSubcategories.add(obj.getSubcategory().getName());
					}
				}
				for (String subcategoryName : uniqueSubcategories) {
					List<BoqItemDTO> subcategoryData = new ArrayList<>();
					for (BoqItemDTO boq : categoryData) {
						if (boq.getSubcategory() != null && boq.getSubcategory().getName() != null
								&& boq.getSubcategory().getName().equals(subcategoryName)) {
							subcategoryData.add(boq);
						}
					}
					if (subcategoryData != null && subcategoryData.size() > 0) {
						SubcategoryItemDTO subcategory = subcategoryData.get(0).getSubcategory();
						int subcatPid = ++uniquePid;
						CategoryBoqParentChildDTO subparentDTO = new CategoryBoqParentChildDTO(subcategory.getId(),
								subcategory.getName(), subcategory.getName(), null, null, subcategory.getCode(),
								subcategory.getStandardBookCode(), ItemType.Subcategory.toString(),
								ItemType.Category.toString(), subcatPid, catPid);
						for (BoqItemDTO obj : subcategoryData) {
							if (obj != null) {
								BoqItemDTO boq = obj;
								int boqPid = ++uniquePid;
								CategoryBoqParentChildDTO childDTO = new CategoryBoqParentChildDTO(boq.getId(),
										boq.getName(), boq.getName(), boq.getUnit().getId(), boq.getUnit().getName(),
										boq.getCode(), boq.getStandardBookCode(), ItemType.Boq.toString(),
										ItemType.Subcategory.toString(), boqPid, subcatPid);
								resultList.add(childDTO);
							}
						}
						resultList.add(subparentDTO);
					}
				}
				resultList.add(parentDTO);
			}
		}
		if (categories != null && categories.size() > 0) {
			List<CategoryBoqParentChildDTO> resultListSorted = new ArrayList<>();
			List<CategoryBoqParentChildDTO> categoryBoqs = new ArrayList<>();
			for (CategoryItemDTO category : categories) {
				String code = category.getCode();
				if (code != null) {
					for (CategoryBoqParentChildDTO catBoq : resultList) {
						if (catBoq.getCode() != null && catBoq.getCode().toLowerCase().startsWith(code.toLowerCase())) {
							categoryBoqs.add(catBoq);
						}
					}
					categoryBoqs.sort(Comparator.comparing(CategoryBoqParentChildDTO::getCode));
					resultListSorted.addAll(resultListSorted);
				}
			}
			for (CategoryBoqParentChildDTO obj1 : resultList) {
				boolean isPresent = false;
				for (CategoryBoqParentChildDTO obj2 : resultListSorted) {
					if (obj1.getPid().equals(obj2.getPid())) {
						isPresent = true;
						break;
					}
				}
				if (!isPresent) {
					resultListSorted.add(obj1);
				}
			}
			result.setData(resultListSorted);
		} else {
			result.setData(resultList);
		}
		return result;
	}

	public ConsolidatedBoqsRenderDTO renderChildParentConsolidatedBoqs(List<ConsolidatedBoqsDTO> consolidatedBoqs,
			List<CategoryItemDTO> categories) {

		List<ConsolidatedBoqParentChildDTO> resultList = new ArrayList<>();
		int uniquePid = 0;
		Double totalAmount = 0.0;
		ConsolidatedBoqsRenderDTO result = new ConsolidatedBoqsRenderDTO(resultList, totalAmount);
		Set<String> uniqueCategories = new HashSet<>();
		for (ConsolidatedBoqsDTO highBoq : consolidatedBoqs) {
			if (highBoq.getCategory() != null && highBoq.getCategory().getName() != null) {
				uniqueCategories.add(highBoq.getCategory().getName());
			}
		}
		for (String categoryName : uniqueCategories) {
			++uniquePid;
			List<ConsolidatedBoqsDTO> categoryData = new ArrayList<>();
			for (ConsolidatedBoqsDTO highBoq : consolidatedBoqs) {
				if (highBoq.getCategory() != null && highBoq.getCategory().getName() != null
						&& highBoq.getCategory().getName().equals(categoryName)) {
					categoryData.add(highBoq);
				}
			}
			if (categoryData != null && categoryData.size() > 0) {
				CategoryItemDTO category = categoryData.get(0).getCategory();
				Double parentAmount = 0.0;
				ConsolidatedBoqParentChildDTO parentDTO = new ConsolidatedBoqParentChildDTO(category.getId(),
						category.getName(), category.getName(), null, null, null, category.getCode(),
						category.getStandardBookCode(), null, null, null, parentAmount, ItemType.Category.toString(),
						null, uniquePid, null);
				int catPid = uniquePid;
				for (ConsolidatedBoqsDTO obj : categoryData) {
					if (obj.getSubcategory() == null) {
						BoqItemDTO boq = obj.getBoq();
						Double amount = obj.getRate() * obj.getQuantity();
						int boqPid = ++uniquePid;
						String unitTypeName = null;
						if (boq.getUnit().getType() != null) {
							unitTypeName = boq.getUnit().getType().getName();
						}
						ConsolidatedBoqParentChildDTO childDTO = new ConsolidatedBoqParentChildDTO(boq.getId(),
								boq.getName(), boq.getName(), boq.getUnit().getId(), boq.getUnit().getName(),
								unitTypeName, boq.getCode(), boq.getStandardBookCode(), obj.getRate(), obj.getMaxRate(),
								obj.getQuantity(), amount, ItemType.Boq.toString(), ItemType.Category.toString(),
								boqPid, catPid);
						resultList.add(childDTO);
						parentAmount += amount;
						totalAmount += amount;
					}
				}

				Set<String> uniqueSubcategories = new HashSet<>();
				for (ConsolidatedBoqsDTO obj : categoryData) {
					if (obj.getSubcategory() != null) {
						uniqueSubcategories.add(obj.getSubcategory().getName());
					}
				}
				for (String subcategoryName : uniqueSubcategories) {
					List<ConsolidatedBoqsDTO> subcategoryData = new ArrayList<>();
					for (ConsolidatedBoqsDTO highBoq : categoryData) {
						if (highBoq.getSubcategory() != null && highBoq.getSubcategory().getName() != null
								&& highBoq.getSubcategory().getName().equals(subcategoryName)) {
							subcategoryData.add(highBoq);
						}
					}
					if (subcategoryData != null && subcategoryData.size() > 0) {
						SubcategoryItemDTO subcategory = subcategoryData.get(0).getSubcategory();
						Double subparentAmount = 0.0;
						int subcatPid = ++uniquePid;
						ConsolidatedBoqParentChildDTO subparentDTO = new ConsolidatedBoqParentChildDTO(
								subcategory.getId(), subcategory.getName(), subcategory.getName(), null, null, null,
								subcategory.getCode(), subcategory.getStandardBookCode(), null, null, null,
								subparentAmount, ItemType.Subcategory.toString(), ItemType.Category.toString(),
								subcatPid, catPid);
						for (ConsolidatedBoqsDTO obj : subcategoryData) {
							if (obj.getBoq() != null) {
								BoqItemDTO boq = obj.getBoq();
								Double amount = obj.getRate() * obj.getQuantity();
								int boqPid = ++uniquePid;
								String unitTypeName = null;
								if (boq.getUnit().getType() != null) {
									unitTypeName = boq.getUnit().getType().getName();
								}
								ConsolidatedBoqParentChildDTO childDTO = new ConsolidatedBoqParentChildDTO(boq.getId(),
										boq.getName(), boq.getName(), boq.getUnit().getId(), boq.getUnit().getName(),
										unitTypeName, boq.getCode(), boq.getStandardBookCode(), obj.getRate(),
										obj.getMaxRate(), obj.getQuantity(), amount, ItemType.Boq.toString(),
										ItemType.Subcategory.toString(), boqPid, subcatPid);
								resultList.add(childDTO);
								subparentAmount += amount;
								parentAmount += amount;
								totalAmount += amount;
							}
						}
						subparentDTO.setAmount(subparentAmount);
						resultList.add(subparentDTO);
					}
				}
				parentDTO.setAmount(parentAmount);
				resultList.add(parentDTO);
			}
		}
		if (categories != null && categories.size() > 0) {
			List<ConsolidatedBoqParentChildDTO> resultListSorted = new ArrayList<>();
			List<ConsolidatedBoqParentChildDTO> categoryBoqs = new ArrayList<>();
			for (CategoryItemDTO category : categories) {
				String code = category.getCode();
				if (code != null) {
					for (ConsolidatedBoqParentChildDTO hbm : resultList) {
						if (hbm.getCode() != null && hbm.getCode().toLowerCase().startsWith(code.toLowerCase())) {
							categoryBoqs.add(hbm);
						}
					}
					categoryBoqs.sort(Comparator.comparing(ConsolidatedBoqParentChildDTO::getCode));
					resultListSorted.addAll(resultListSorted);
				}
			}
			for (ConsolidatedBoqParentChildDTO obj1 : resultList) {
				boolean isPresent = false;
				for (ConsolidatedBoqParentChildDTO obj2 : resultListSorted) {
					if (obj1.getPid().equals(obj2.getPid())) {
						isPresent = true;
						break;
					}
				}
				if (!isPresent) {
					resultListSorted.add(obj1);
				}
			}
			result.setData(resultListSorted);
		} else {
			result.setData(resultList);
		}
		result.setTotalAmount(totalAmount);
		return result;

	}

	public RfiChecklistItems rfiChecklistItemAddUpdateRequestDtoToEntity(RfiChecklistItemBoqsAddUpdateRequest obj) {
		if (obj == null)
			return null;
		return new RfiChecklistItems(obj.getId(), obj.getName(), obj.getDescription(),
				new EngineState(obj.getStateId()), obj.getUserDetail().getCompanyId(), true, new Date(),
				obj.getUserDetail().getId().intValue());
	}

	public RfiCustomWorkItems rfiCustomWorkItemsAddUpdateRequestDtoToEntity(RfiCustomWorkItemAddUpdateRequest obj) {
		if (obj == null)
			return null;
		return new RfiCustomWorkItems(obj.getId(), obj.getName(), obj.getCode(), obj.getDescription(), obj.getUnitId(),
				new EngineState(obj.getStateId()), obj.getUserDetail().getCompanyId(), true, new Date(),
				obj.getUserDetail().getId().intValue());
	}

	public RfiCustomWorkItemFetchByIdResponse rfiCustomWorkItemEntityToFetchByIdResponse(RfiCustomWorkItems obj) {
		if (obj == null)
			return null;

		return new RfiCustomWorkItemFetchByIdResponse(obj.getId(), obj.getName(), obj.getCode(), obj.getDescription(),
				obj.getUnit() != null ? new IdNameDTO(obj.getUnit().getId(), obj.getUnit().getName()) : null,
				obj.getState().getId(), obj.getState().getName());
	}

	public RfiCustomWorkItemFetchListResponse rfiCustomWorkItemEntityToFetchListResponseDto(RfiCustomWorkItems obj) {
		if (obj == null)
			return null;

		return new RfiCustomWorkItemFetchListResponse(obj.getId(), obj.getName(), obj.getCode(), obj.getDescription(),
				obj.getUnit() != null ? new IdNameDTO(obj.getUnit().getId(), obj.getUnit().getName()) : null,
				obj.getState().getId(), obj.getState().getName());

	}

	public RfiChecklistItemStateTransitionFetchResponse rfiChecklistItemStateTransitionEntityToDto(
			RfiChecklistItemStateTransition obj) {

		if (obj == null)
			return null;
		return new RfiChecklistItemStateTransitionFetchResponse(obj.getId(), obj.getState().getId(),
				obj.getState().getName(), obj.getCreatedOn(), obj.getCreatedBy(), obj.getCreatedByUser().getName(),
				obj.getCreatedByUser().getCode(), obj.getRemark());
	}

	public RfiCustomWorkItemStateTransitionFetchResponse rfiCustomWorkItemStateTransitionEntityToDto(
			RfiCustomWorkItemStateTransition obj) {

		if (obj == null)
			return null;
		return new RfiCustomWorkItemStateTransitionFetchResponse(obj.getId(), obj.getState().getId(),
				obj.getState().getName(), obj.getCreatedOn(), obj.getCreatedBy(), obj.getCreatedByUser().getName(),
				obj.getCreatedByUser().getCode(), obj.getRemark());
	}

	public RfiMain rfiMainAddUpdateRequestDtoToEntity(RfiMainAddUpdateRequest obj) {
		if (obj == null)
			return null;
		return new RfiMain(obj.getId(), obj.getType(), obj.getMode(), obj.getParentId(), null, obj.getStateId(),
				obj.getBoqId(), obj.getFromChainageId(), obj.getToChainageId(), obj.getStructureId(),
				obj.getChainageSide(), obj.getChainageInfo(), obj.getCustomItemId(), obj.getWorkDescription(),
				obj.getLength(), obj.getWidth(), obj.getHeight(), obj.getLayerInfo(), obj.getWorkLayerId(),
				obj.getExecutableWorkQuantity(), obj.getClientExecutedQuantity(), obj.getActualExecutedQuantity(),
				new Date(), obj.getSiteId(), true, new Date(), obj.getUserDetail().getId().intValue(), new Date(),
				obj.getUserDetail().getId().intValue());
	}

	public WorkTypeBoqResponse boqItemEntityToWorkTypeBoqResponse(BoqItem obj) {
		if (obj == null)
			return null;
		return new WorkTypeBoqResponse(obj.getId(), obj.getStandardBookCode(), obj.getCode(), obj.getName(),
				obj.getCategory() != null ? obj.getCategory().getName() : null,
				obj.getSubcategory() != null ? obj.getSubcategory().getName() : null,
				obj.getUnit() != null ? obj.getUnit().getId() : null,
				obj.getUnit() != null ? obj.getUnit().getName() : null);
	}

	public RfiCustomWorkItemFetchFinalSuccessListResponse rfiCustomWorkItemEntityToFetchFinalSuccessListResponseDto(
			RfiCustomWorkItemsV2 obj) {
		if (obj == null)
			return null;
		return new RfiCustomWorkItemFetchFinalSuccessListResponse(obj.getId(), obj.getName(), obj.getCode(),
				obj.getDescription());
	}

	public RfiChecklistItemFetchFinalSuccessListResponse rfiChecklistItemBoqEntityToFetchFinalSuccessListResponseDto(
			RfiChecklistItemBoqsV2 obj) {
		if (obj == null)
			return null;
		return new RfiChecklistItemFetchFinalSuccessListResponse(obj.getChecklistItem().getId(),
				obj.getChecklistItem().getName(), obj.getChecklistItem().getDescription());
	}

	public MinimalStructureResponse structureEntityToMinimalResponseDto(Structure obj) {
		if (obj == null)
			return null;
		return new MinimalStructureResponse(obj.getId(), obj.getName(),
				obj.getType() != null ? new IdNameDTO(obj.getType().getId(), obj.getType().getName()) : null);
	}

	public RfiMainResponse rfiMainEntityToResponseDto(RfiMain obj) {
		if (obj == null)
			return null;
		return new RfiMainResponse(obj.getId(), obj.getType(), obj.getMode(), obj.getParentId(), obj.getUniqueCode(),
				obj.getStateId(), obj.getState() != null ? obj.getState().getName() : null,
				obj.getState() != null ? obj.getState().getRgbColorCode() : null,
				boqItemEntityToWorkTypeBoqResponse(obj.getBoq()),
				obj.getFromChainage() != null
						? new IdNameDTO(obj.getFromChainage().getId(), obj.getFromChainage().getName())
						: null,
				obj.getToChainage() != null ? new IdNameDTO(obj.getToChainage().getId(), obj.getToChainage().getName())
						: null,
				structureEntityToMinimalResponseDto(obj.getStructure()), obj.getSide(), obj.getChainageInfo(),
				rfiCustomWorkItemEntityToResponseV2(obj.getCustomWorkItem()), obj.getWorkDescription(), obj.getLength(),
				obj.getWidth(), obj.getHeight(), obj.getLayerInfo(), obj.getExecutableWorkQuantity(),
				obj.getClientExecutedQuantity(), obj.getActualExecutedQuantity(),
				obj.getWorkLayer() != null ? new WorkLayerFetchFinalSuccessListResponse(obj.getWorkLayer().getId(),
						obj.getWorkLayer().getCode(), obj.getWorkLayer().getName(), obj.getWorkLayer().getDescription())
						: null,
				obj.getSubmissionDate(), new ArrayList<>(), new ArrayList<>(), obj.getCreatedOn(),
				obj.getUpdatedByObj() != null ? obj.getUpdatedByObj().getName() : null, obj.getUpdatedOn());
	}

	public RfiCustomWorkItemResponseV2 rfiCustomWorkItemEntityToResponseV2(RfiCustomWorkItems obj) {
		if (obj == null)
			return null;
		return new RfiCustomWorkItemResponseV2(obj.getId(), obj.getName(), obj.getCode(), obj.getDescription(),
				obj.getUnit() != null ? new IdNameDTO(obj.getUnit().getId(), obj.getUnit().getName()) : null);
	}

	public PrintRfiMainResponse rfiMainEntityToPrintResponseDto(RfiMain obj) {
		if (obj == null)
			return null;
		return new PrintRfiMainResponse(obj.getId(), obj.getType(), obj.getMode(), obj.getParentId(),
				obj.getUniqueCode(), obj.getStateId(), obj.getState() != null ? obj.getState().getName() : null,
				boqItemEntityToWorkTypeBoqResponse(obj.getBoq()),
				obj.getFromChainage() != null
						? new IdNameDTO(obj.getFromChainage().getId(), obj.getFromChainage().getName())
						: null,
				obj.getToChainage() != null ? new IdNameDTO(obj.getToChainage().getId(), obj.getToChainage().getName())
						: null,
				structureEntityToMinimalResponseDto(obj.getStructure()), obj.getSide(), obj.getChainageInfo(),
				rfiCustomWorkItemEntityToResponseV2(obj.getCustomWorkItem()), obj.getWorkDescription(), obj.getLength(),
				obj.getWidth(), obj.getHeight(), obj.getLayerInfo(), obj.getExecutableWorkQuantity(),
				obj.getClientExecutedQuantity(), obj.getActualExecutedQuantity(), new ArrayList<>(), new ArrayList<>(),
				obj.getSubmissionDate(), obj.getUpdatedByObj() != null ? obj.getUpdatedByObj().getName() : null,
				obj.getUpdatedOn(), obj.getSite().getClient() != null ? obj.getSite().getClient().getName() : null,
				obj.getSite().getClient() != null ? obj.getSite().getClient().getLogoUrl() : null,
				obj.getSite().getCompany() != null ? obj.getSite().getCompany().getPrintLogo() : null,
				obj.getSite().getConcessionaire() != null ? obj.getSite().getConcessionaire().getName() : null,
				obj.getSite().getConsultant() != null ? obj.getSite().getConsultant().getName() : null,
				obj.getSite().getCompany() != null ? obj.getSite().getCompany().getName() : null,
				obj.getSite().getDescription());
	}

	public S3FileResponse s3FileEntityToResponseDto(S3File obj) {
		if (obj == null)
			return null;
		return new S3FileResponse(obj.getId(), obj.getName(), obj.getModifiedName(), obj.getPath());
	}

	public RfiChecklistItemFetchFinalSuccessListResponse rfiChecklistItemEntityToFinalSuccessResponse(
			RfiChecklistItems obj) {
		if (obj == null)
			return null;
		return new RfiChecklistItemFetchFinalSuccessListResponse(obj.getId(), obj.getName(), obj.getDescription());
	}

	public RfiMain mergeRfiEntityWithDto(RfiMain dbObj, RfiMainAddUpdateRequest requestDto) {
		if (requestDto == null)
			return dbObj;
		dbObj.setStateId(requestDto.getStateId());
		dbObj.setLayerInfo(requestDto.getLayerInfo());
		dbObj.setWorkDescription(requestDto.getWorkDescription());
		dbObj.setChainageInfo(requestDto.getChainageInfo());
		dbObj.setLength(requestDto.getLength());
		dbObj.setWidth(requestDto.getWidth());
		dbObj.setHeight(requestDto.getHeight());
		dbObj.setWorkLayerId(requestDto.getWorkLayerId());
		dbObj.setClientExecutedQuantity(requestDto.getClientExecutedQuantity());
		dbObj.setActualExecutedQuantity(requestDto.getActualExecutedQuantity());
		dbObj.setUpdatedBy(requestDto.getUserDetail().getId().intValue());
		dbObj.setUpdatedOn(new Date());
		return dbObj;

	}

	public RfiMainComments rfiMainCommentAddUpdateRequestDtoToEntity(RfiMainCommentAddUpdateRequest requestDTO) {
		if (requestDTO == null)
			return null;
		return new RfiMainComments(requestDTO.getId(), null, requestDTO.getCommentType(),
				requestDTO.getCommentatorName(), requestDTO.getCommentTimestamp(), requestDTO.getComment(), true,
				new Date(), null);
	}

	public RfiMainCommentResponse rfiMainCommentEntityToDto(RfiMainComments obj) {
		if (obj == null)
			return null;
		return new RfiMainCommentResponse(obj.getId(), obj.getCommentType(), obj.getCommentatorName(),
				obj.getCommentTimestamp(), obj.getComment());
	}

	public WorkLayer workLayerAddUpdateRequestDtoToEntity(WorkLayerBoqsAddUpdateRequest obj) {
		if (obj == null)
			return null;
		return new WorkLayer(obj.getId(), obj.getCode(), obj.getName(), obj.getDescription(),
				new EngineState(obj.getStateId()), obj.getUserDetail().getCompanyId(), true, new Date(),
				obj.getUserDetail().getId().intValue());
	}

	public WorkLayerStateTransitionFetchResponse workLayerStateTransitionEntityToDto(WorkLayerStateTransition obj) {

		if (obj == null)
			return null;
		return new WorkLayerStateTransitionFetchResponse(obj.getId(), obj.getState().getId(), obj.getState().getName(),
				obj.getCreatedOn(), obj.getCreatedBy(), obj.getCreatedByUser().getName(),
				obj.getCreatedByUser().getCode(), obj.getRemark());
	}

	public WorkLayerFetchFinalSuccessListResponse workLayerBoqEntityToFetchFinalSuccessListResponseDto(
			WorkLayerBoqsV2 obj) {
		if (obj == null)
			return null;
		return new WorkLayerFetchFinalSuccessListResponse(obj.getWorkLayer().getId(), obj.getWorkLayer().getCode(),
				obj.getWorkLayer().getName(), obj.getWorkLayer().getDescription());
	}

	public BoqCostDefinition boqCostDefinitionsAddRequestDtoToEntity(BoqCostDefinitionAddRequest obj) {
		if (obj == null)
			return null;
		return new BoqCostDefinition(obj.getId(), obj.getBoqId(), obj.getExpectedOutputValue(),
				obj.getFoamworkIncluded(), obj.getFoamworkSiteVariableId(), obj.getOverheadIncluded(),
				obj.getOverheadSiteVariableId(), obj.getContractorProfitIncluded(),
				obj.getContractorProfitSiteVariableId(), obj.getExtraExpenseAmount(), obj.getExtraExpenseRemark(),
				new EngineState(obj.getStateId()), obj.getSiteId(), obj.getUserDetail().getCompanyId(), true,
				new Date(), obj.getUserDetail().getId().intValue(), new Date(), obj.getUserDetail().getId().intValue());
	}

	public BoqCostDefinition boqCostDefinitionsUpdateRequestDtoToEntity(BoqCostDefinitionUpdateRequest obj) {
		if (obj == null)
			return null;
		return new BoqCostDefinition(obj.getId(), obj.getBoqId(), obj.getExpectedOutputValue(),
				obj.getFoamworkIncluded(), obj.getFoamworkSiteVariableId(), obj.getOverheadIncluded(),
				obj.getOverheadSiteVariableId(), obj.getContractorProfitIncluded(),
				obj.getContractorProfitSiteVariableId(), obj.getExtraExpenseAmount(), obj.getExtraExpenseRemark(),
				new EngineState(obj.getStateId()), obj.getSiteId(), obj.getUserDetail().getCompanyId(), true,
				new Date(), obj.getUserDetail().getId().intValue(), new Date(), obj.getUserDetail().getId().intValue());
	}

	public BoqCostDefinitionFetchByIdResponse boqCostDefinitionEntityToFetchByIdResponse(BoqCostDefinition obj) {
		if (obj == null)
			return null;
		return new BoqCostDefinitionFetchByIdResponse(obj.getId(), obj.getBoqId(),
				obj.getBoq() != null ? obj.getBoq().getStandardBookCode() : null,
				obj.getBoq() != null ? obj.getBoq().getCode() : null,
				obj.getBoq() != null ? obj.getBoq().getName() : null,
				obj.getBoq() != null && obj.getBoq().getCategory() != null ? obj.getBoq().getCategory().getName()
						: null,
				obj.getBoq() != null && obj.getBoq().getUnit() != null ? obj.getBoq().getUnit().getId().intValue()
						: null,
				obj.getBoq() != null && obj.getBoq().getUnit() != null ? obj.getBoq().getUnit().getName() : null,
				obj.getExpectedOutputValue(), obj.getFoamworkIncluded(), obj.getFoamworkSiteVariableId(), null,
				obj.getOverheadIncluded(), obj.getOverheadSiteVariableId(), null, obj.getContractorProfitIncluded(),
				obj.getContractorProfitSiteVariableId(), null, obj.getExtraExpenseAmount(), obj.getExtraExpenseRemark(),
				obj.getState().getId(), obj.getState().getName());
	}

	public BoqCostDefinitionFetchListResponse boqCostDefinitionEntityToFetchListResponseDto(BoqCostDefinition obj) {
		if (obj == null)
			return null;
		return new BoqCostDefinitionFetchListResponse(obj.getId(), obj.getBoqId(), obj.getBoq().getCode(),
				obj.getBoq().getName(), obj.getExpectedOutputValue(),
				obj.getBoq().getUnit() != null ? obj.getBoq().getUnit().getId().intValue() : null,
				obj.getBoq().getUnit() != null ? obj.getBoq().getUnit().getName() : null, obj.getState().getId(),
				obj.getState().getName(), obj.getUpdatedOn(), obj.getUpdatedByUser().getName());
	}

	public BoqCostDefinitionStateTransitionFetchResponse boqCostDefinitionStateTransitionEntityToDto(
			BoqCostDefinitionStateTransition obj) {

		if (obj == null)
			return null;
		return new BoqCostDefinitionStateTransitionFetchResponse(obj.getId(), obj.getState().getId(),
				obj.getState().getName(), obj.getCreatedOn(), obj.getCreatedBy(), obj.getCreatedByUser().getName(),
				obj.getCreatedByUser().getCode(), obj.getRemark());
	}

	public BoqCostDefinitionFetchFinalSuccessResponse boqCostDefinitionEntityToFetchFinalSuccessListResponseDto(
			BoqCostDefinition obj) {
		if (obj == null)
			return null;
		return new BoqCostDefinitionFetchFinalSuccessResponse(obj.getId(), obj.getBoqId(), obj.getExpectedOutputValue(),
				obj.getFoamworkIncluded(), obj.getFoamworkSiteVariableId(), obj.getOverheadIncluded(),
				obj.getOverheadSiteVariableId(), obj.getContractorProfitIncluded(),
				obj.getContractorProfitSiteVariableId(), obj.getExtraExpenseAmount(), obj.getExtraExpenseRemark(),
				obj.getState().getId(), obj.getState().getName());
	}

	public RfiMainStateTransitionFetchResponse rfiMainStateTransitionEntityToDto(RfiMainStateTransition obj) {

		if (obj == null)
			return null;
		return new RfiMainStateTransitionFetchResponse(obj.getId(), obj.getState().getId(), obj.getState().getName(),
				obj.getCreatedOn(), obj.getCreatedByUser().getId(), obj.getCreatedByUser().getName(),
				obj.getCreatedByUser().getCode(), obj.getRemark());
	}

	public BoqCostDefinitionLabour boqCostDefinitionLabourAddUpdateRequestDtoToEntity(
			BoqCostDefinitionLabourAddUpdateRequest obj) {

		if (obj == null)
			return null;
		return new BoqCostDefinitionLabour(obj.getId(), obj.getBoqCostDefinitionId(), obj.getLabourTypeId(),
				obj.getUnitId(), obj.getQuantity(), obj.getRate(), true, new Date(),
				obj.getUserDetail().getId().intValue());
	}

	public BoqCostDefinitionLabourResponse boqCostDefinitionLabourEntityToDto(BoqCostDefinitionLabour obj) {

		if (obj == null)
			return null;
		return new BoqCostDefinitionLabourResponse(obj.getId(), obj.getBoqCostDefinitionId(), obj.getLabourTypeId(),
				obj.getLabourType() != null ? obj.getLabourType().getName() : null, obj.getUnitId(),
				obj.getUnit() != null ? obj.getUnit().getName() : null, obj.getQuantity(), obj.getRate(),
				obj.getQuantity() * obj.getRate());
	}

	public BoqCostDefinitionMaterial boqCostDefinitionMaterialAddUpdateRequestDtoToEntity(
			BoqCostDefinitionMaterialAddUpdateRequest obj) {

		if (obj == null)
			return null;
		return new BoqCostDefinitionMaterial(obj.getId(), obj.getBoqCostDefinitionId(), obj.getMaterialId(),
				obj.getUnitId(), obj.getQuantity(), obj.getRate(), true, new Date(),
				obj.getUserDetail().getId().intValue());
	}

	public BoqCostDefinitionMaterialResponse boqCostDefinitionMaterialEntityToDto(BoqCostDefinitionMaterial obj) {

		if (obj == null)
			return null;
		return new BoqCostDefinitionMaterialResponse(obj.getId(), obj.getBoqCostDefinitionId(), obj.getMaterialId(),
				obj.getMaterial() != null ? obj.getMaterial().getName() : null, obj.getUnitId(),
				obj.getUnit() != null ? obj.getUnit().getName() : null, obj.getQuantity(), obj.getRate(),
				obj.getQuantity() * obj.getRate());
	}

	public UndefinedCostDefinitionBoqItemResponse boqItemEntityToUndefinedCostDefinitionBoqItemResponse(BoqItem obj) {
		if (obj == null)
			return null;
		return new UndefinedCostDefinitionBoqItemResponse(obj.getId(), obj.getCode(), obj.getStandardBookCode(),
				obj.getName(), obj.getCategory() != null ? obj.getCategory().getCode() : null,
				obj.getCategory() != null ? obj.getCategory().getStandardBookCode() : null,
				obj.getCategory() != null ? obj.getCategory().getName() : null,
				obj.getUnit() != null ? obj.getUnit().getId().intValue() : null,
				obj.getUnit() != null ? obj.getUnit().getName() : null);
	}

	public MaterialFetchResponse materialEntityToDto(Material obj) {
		if (obj == null)
			return null;
		return new MaterialFetchResponse(obj.getId(), obj.getName(), obj.getSpecification(),
				obj.getUnit() != null ? obj.getUnit().getId() : null,
				obj.getUnit() != null ? obj.getUnit().getName() : null);
	}

	public BoqCostDefinitionMachinery boqCostDefinitionMachineryAddUpdateRequestDtoToEntity(
			BoqCostDefinitionMachineryAddUpdateRequest obj) {
		if (obj == null)
			return null;
		return new BoqCostDefinitionMachinery(obj.getId(), obj.getBoqCostDefinitionId(), obj.getMachineryCategoryId(),
				obj.getMachineryTrip() != null && obj.getMachineryTrip().equals(MachineryTrip.Loaded) ? true : false,
				obj.getLeadSiteVariableId(), obj.getUnitId(), obj.getRate(), obj.getAfterUnitId(), obj.getAfterRate(),
				obj.getRangeType(), obj.getRangeUnitId(), obj.getRangeQuantity(), true, new Date(),
				obj.getUserDetail().getId().intValue());
	}

	public BoqCostDefinitionMachineryResponse boqCostDefinitionMachineryEntityToDto(BoqCostDefinitionMachinery obj) {
		if (obj == null)
			return null;
		BoqCostDefinitionMachineryResponse resultObj = new BoqCostDefinitionMachineryResponse(obj.getId(),
				obj.getBoqCostDefinitionId(),
				obj.getMachineryCategory() != null ? obj.getMachineryCategory().getId() : null,
				obj.getMachineryCategory() != null ? obj.getMachineryCategory().getName() : null,

				obj.getIsMachineryLoaded() != null && obj.getIsMachineryLoaded() ? MachineryTrip.Loaded.getId()
						: MachineryTrip.Unloaded.getId(),
				obj.getIsMachineryLoaded() != null && obj.getIsMachineryLoaded() ? MachineryTrip.Loaded
						: MachineryTrip.Unloaded,
				obj.getUnit() != null ? obj.getUnit().getId().intValue() : null,
				obj.getUnit() != null ? obj.getUnit().getName() : null, obj.getRate(),
				obj.getAfterUnit() != null ? obj.getAfterUnit().getId().intValue() : null,
				obj.getAfterUnit() != null ? obj.getAfterUnit().getName() : null, obj.getAfterRate(),
				obj.getRangeType(), obj.getRangeUnit() != null ? obj.getRangeUnit().getId().intValue() : null,
				obj.getRangeUnit() != null ? obj.getRangeUnit().getName() : null, obj.getRangeQuantity(),
				obj.getRangeType() != null && obj.getRangeType().equals(MachineryRangeType.Consolidated)
						? (obj.getRate())
						: 0.0);
		if (resultObj.getRangeType() != null && resultObj.getRangeType().equals(MachineryRangeType.Upto_After)) {

			Double amount = 0.0;
//			if (resultObj.getRangeQuantity() >= resultObj.getQuantity()) {
//
//				amount = resultObj.getMachineryCount() * resultObj.getQuantity() * resultObj.getRate();
//
//			} else {
//				// upto range quantity
//				amount = resultObj.getMachineryCount() * resultObj.getRangeQuantity() * resultObj.getRate();
//				// after range quantity
//				amount = amount + (resultObj.getMachineryCount()
//						* (resultObj.getQuantity() - resultObj.getRangeQuantity()) * resultObj.getAfterRate());
//			}
			resultObj.setAmount(amount);

		}
		return resultObj;
	}

	public BoqMasterLmps boqMasterLmpsAddUpdateRequestDtoToEntity(BoqMasterLmpsAddUpdateRequest obj) {
		if (obj == null)
			return null;
		return new BoqMasterLmps(obj.getId(), obj.getBoqId(), obj.getExpectedOutputValue(), obj.getFoamworkIncluded(),
				obj.getFoamworkPercentValue(), obj.getOverheadIncluded(), obj.getOverheadPercentValue(),
				obj.getContractorProfitIncluded(), obj.getContractorProfitPercentValue(), obj.getExtraExpenseAmount(),
				obj.getExtraExpenseRemark(), new EngineState(obj.getStateId()), obj.getUserDetail().getCompanyId(),
				true, new Date(), obj.getUserDetail().getId().intValue(), new Date(),
				obj.getUserDetail().getId().intValue());
	}

	public BoqMasterLmpsFetchByIdResponse boqMasterLmpsEntityToFetchByIdResponse(BoqMasterLmps obj) {
		if (obj == null)
			return null;
		return new BoqMasterLmpsFetchByIdResponse(obj.getId(), obj.getBoqId(),
				obj.getBoq() != null ? obj.getBoq().getStandardBookCode() : null,
				obj.getBoq() != null ? obj.getBoq().getCode() : null,
				obj.getBoq() != null ? obj.getBoq().getName() : null,
				obj.getBoq() != null && obj.getBoq().getCategory() != null ? obj.getBoq().getCategory().getName()
						: null,
				obj.getBoq() != null && obj.getBoq().getUnit() != null ? obj.getBoq().getUnit().getId().intValue()
						: null,
				obj.getBoq() != null && obj.getBoq().getUnit() != null ? obj.getBoq().getUnit().getName() : null,
				obj.getExpectedOutputValue(), obj.getFoamworkIncluded(), obj.getFoamworkPercentValue(),
				obj.getOverheadIncluded(), obj.getOverheadPercentValue(), obj.getContractorProfitIncluded(),
				obj.getContractorProfitPercentValue(), obj.getExtraExpenseAmount(), obj.getExtraExpenseRemark(),
				obj.getState().getId(), obj.getState().getName());
	}

	public BoqMasterLmpsFetchListResponse boqMasterLmpsEntityToFetchListResponseDto(BoqMasterLmps obj) {
		if (obj == null)
			return null;
		return new BoqMasterLmpsFetchListResponse(obj.getId(), obj.getBoqId(), obj.getBoq().getCode(),
				obj.getBoq().getName(), obj.getExpectedOutputValue(),
				obj.getBoq().getUnit() != null ? obj.getBoq().getUnit().getId().intValue() : null,
				obj.getBoq().getUnit() != null ? obj.getBoq().getUnit().getName() : null, obj.getState().getId(),
				obj.getState().getName(), obj.getUpdatedOn(), obj.getUpdatedByUser().getName());
	}

	public BoqMasterLmpsStateTransitionFetchResponse boqMasterLmpsStateTransitionEntityToDto(
			BoqMasterLmpsStateTransition obj) {

		if (obj == null)
			return null;
		return new BoqMasterLmpsStateTransitionFetchResponse(obj.getId(), obj.getState().getId(),
				obj.getState().getName(), obj.getCreatedOn(), obj.getCreatedBy(), obj.getCreatedByUser().getName(),
				obj.getCreatedByUser().getCode(), obj.getRemark());
	}

	public BoqMasterLmpsFetchFinalSuccessResponse boqMasterLmpsEntityToFetchFinalSuccessListResponseDto(
			BoqMasterLmps obj) {
		if (obj == null)
			return null;
		return new BoqMasterLmpsFetchFinalSuccessResponse(obj.getId(), obj.getBoqId(), obj.getExpectedOutputValue(),
				obj.getFoamworkIncluded(), obj.getFoamworkPercentValue(), obj.getOverheadIncluded(),
				obj.getOverheadPercentValue(), obj.getContractorProfitIncluded(), obj.getContractorProfitPercentValue(),
				obj.getExtraExpenseAmount(), obj.getExtraExpenseRemark(), obj.getState().getId(),
				obj.getState().getName());
	}

	public BoqMasterLmpsLabour boqMasterLmpsLabourAddUpdateRequestDtoToEntity(BoqMasterLmpsLabourAddUpdateRequest obj) {

		if (obj == null)
			return null;
		return new BoqMasterLmpsLabour(obj.getId(), obj.getBoqMasterLmpsId(), obj.getLabourTypeId(), obj.getUnitId(),
				obj.getQuantity(), true, new Date(), obj.getUserDetail().getId().intValue());
	}

	public BoqMasterLmpsLabourResponse boqMasterLmpsLabourEntityToDto(BoqMasterLmpsLabour obj) {

		if (obj == null)
			return null;
		return new BoqMasterLmpsLabourResponse(obj.getId(), obj.getBoqMasterLmpsId(), obj.getLabourTypeId(),
				obj.getLabourType() != null ? obj.getLabourType().getName() : null, obj.getUnitId(),
				obj.getUnit() != null ? obj.getUnit().getName() : null, obj.getQuantity());
	}

	public BoqMasterLmpsMaterial boqMasterLmpsMaterialAddUpdateRequestDtoToEntity(
			BoqMasterLmpsMaterialAddUpdateRequest obj) {

		if (obj == null)
			return null;
		return new BoqMasterLmpsMaterial(obj.getId(), obj.getBoqMasterLmpsId(), obj.getMaterialId(), obj.getUnitId(),
				obj.getQuantity(), true, new Date(), obj.getUserDetail().getId().intValue());
	}

	public BoqMasterLmpsMaterialResponse boqMasterLmpsMaterialEntityToDto(BoqMasterLmpsMaterial obj) {

		if (obj == null)
			return null;
		return new BoqMasterLmpsMaterialResponse(obj.getId(), obj.getBoqMasterLmpsId(), obj.getMaterialId(),
				obj.getMaterial() != null ? obj.getMaterial().getName() : null, obj.getUnitId(),
				obj.getUnit() != null ? obj.getUnit().getName() : null, obj.getQuantity());
	}

	public UndefinedMasterLmpsBoqItemResponse boqItemEntityToUndefinedMasterLmpsBoqItemResponse(BoqItem obj) {
		if (obj == null)
			return null;
		return new UndefinedMasterLmpsBoqItemResponse(obj.getId(), obj.getCode(), obj.getStandardBookCode(),
				obj.getName(), obj.getCategory() != null ? obj.getCategory().getCode() : null,
				obj.getCategory() != null ? obj.getCategory().getStandardBookCode() : null,
				obj.getCategory() != null ? obj.getCategory().getName() : null,
				obj.getUnit() != null ? obj.getUnit().getId().intValue() : null,
				obj.getUnit() != null ? obj.getUnit().getName() : null);
	}

	public BoqMasterLmpsMachinery boqMasterLmpsMachineryAddUpdateRequestDtoToEntity(
			BoqMasterLmpsMachineryAddUpdateRequest obj) {
		if (obj == null)
			return null;
		return new BoqMasterLmpsMachinery(obj.getId(), obj.getBoqMasterLmpsId(), obj.getMachineryCategoryId(),
				obj.getMachineryTrip() != null && obj.getMachineryTrip().equals(MachineryTrip.Loaded) ? true : false,
				obj.getUnitId(), obj.getSiteVariableId(), obj.getAfterUnitId(), obj.getRangeType(),
				obj.getRangeUnitId(), obj.getRangeQuantity(), true, new Date(), obj.getUserDetail().getId().intValue());
	}

	public BoqMasterLmpsMachineryResponse boqMasterLmpsMachineryEntityToDto(BoqMasterLmpsMachinery obj) {
		if (obj == null)
			return null;
		BoqMasterLmpsMachineryResponse resultObj = new BoqMasterLmpsMachineryResponse(obj.getId(),
				obj.getBoqMasterLmpsId(),
				obj.getMachineryCategory() != null ? obj.getMachineryCategory().getId() : null,
				obj.getMachineryCategory() != null ? obj.getMachineryCategory().getName() : null,
				obj.getIsMachineryLoaded() != null && obj.getIsMachineryLoaded() ? MachineryTrip.Loaded.getId()
						: MachineryTrip.Unloaded.getId(),
				obj.getIsMachineryLoaded() != null && obj.getIsMachineryLoaded() ? MachineryTrip.Loaded
						: MachineryTrip.Unloaded,
				obj.getUnit() != null ? obj.getUnit().getId().intValue() : null,
				obj.getUnit() != null ? obj.getUnit().getName() : null,
				obj.getSiteVariable() != null ? obj.getSiteVariable().getId() : null,
				obj.getSiteVariable() != null ? obj.getSiteVariable().getName() : null,
				obj.getAfterUnit() != null ? obj.getAfterUnit().getId().intValue() : null,
				obj.getAfterUnit() != null ? obj.getAfterUnit().getName() : null, obj.getRangeType(),
				obj.getRangeUnit() != null ? obj.getRangeUnit().getId().intValue() : null,
				obj.getRangeUnit() != null ? obj.getRangeUnit().getName() : null, obj.getRangeQuantity());
		return resultObj;
	}

	public ProjectPlanBoqResponse highwayBoqMappingEntityToProjectPlanBoqResponse(HighwayBoqMapping obj) {
		if (obj == null)
			return null;
		return new ProjectPlanBoqResponse(null, obj.getCategory() != null ? obj.getCategory().getId().intValue() : null,
				obj.getCategory() != null ? obj.getCategory().getName() : null,
				obj.getBoq() != null ? obj.getBoq().getStandardBookCode() : null,
				obj.getBoq() != null ? obj.getBoq().getCode() : null,
				obj.getBoq() != null ? obj.getBoq().getName() : null, null, null,
				obj.getBoq() != null && obj.getBoq().getUnit() != null ? obj.getBoq().getUnit().getId().intValue()
						: null,
				obj.getBoq() != null && obj.getBoq().getUnit() != null ? obj.getBoq().getUnit().getName() : null, null,
				null, null, null);
	}

	public ProjectPlanBoqResponse structureBoqMappingEntityToProjectPlanBoqResponse(StructureBoqQuantityMapping obj) {
		if (obj == null)
			return null;
		return new ProjectPlanBoqResponse(null, obj.getCategory() != null ? obj.getCategory().getId().intValue() : null,
				obj.getCategory() != null ? obj.getCategory().getName() : null,
				obj.getBoq() != null ? obj.getBoq().getStandardBookCode() : null,
				obj.getBoq() != null ? obj.getBoq().getCode() : null,
				obj.getBoq() != null ? obj.getBoq().getName() : null,
				obj.getStructure() != null && obj.getStructure().getType() != null
						? obj.getStructure().getType().getId()
						: null,
				obj.getStructure() != null && obj.getStructure().getType() != null
						? obj.getStructure().getType().getName()
						: null,
				obj.getBoq() != null && obj.getBoq().getUnit() != null ? obj.getBoq().getUnit().getId().intValue()
						: null,
				obj.getBoq() != null && obj.getBoq().getUnit() != null ? obj.getBoq().getUnit().getName() : null, null,
				null, null, null);
	}

}
