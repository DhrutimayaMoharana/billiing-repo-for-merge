package erp.boq_mgmt.util;

import org.springframework.stereotype.Service;

import erp.boq_mgmt.dto.CategoryItemDTO;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.request.BoqCostDefinitionUpdateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionAddRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionLabourAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionMachineryAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionMaterialAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsLabourAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsMachineryAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsMaterialAddUpdateRequest;
import erp.boq_mgmt.dto.request.ProjectPlanBoqAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiBoqGetExecutableQuantityRequest;
import erp.boq_mgmt.dto.request.RfiChecklistItemBoqsAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiChecklistItemBoqsFetchRequest;
import erp.boq_mgmt.dto.request.RfiChecklistItemFinalSuccessFetchRequest;
import erp.boq_mgmt.dto.request.RfiCustomWorkItemAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiMainAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiMainCommentAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiMainExportSummaryRequest;
import erp.boq_mgmt.dto.request.RfiMainFetchRequest;
import erp.boq_mgmt.dto.request.StructureDocumentDeactivateRequest;
import erp.boq_mgmt.dto.request.StructureDocumentRequest;
import erp.boq_mgmt.dto.request.StructureGroupAddUpdateRequest;
import erp.boq_mgmt.dto.request.WorkLayerBoqsAddUpdateRequest;
import erp.boq_mgmt.dto.request.WorkLayerBoqsFetchRequest;
import erp.boq_mgmt.dto.request.WorkLayerFinalSuccessFetchRequest;
import erp.boq_mgmt.dto.response.PrintRfiMainResponse;
import erp.boq_mgmt.enums.MachineryRangeType;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.enums.RfiMainCommentType;
import erp.boq_mgmt.enums.RfiMode;
import erp.boq_mgmt.enums.RfiWorkType;

@Service
public class CustomValidationUtil {

	public CustomResponse validateGetStructureDocuments(SearchDTO search) {

		if (search.getStructureId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide structureId.");
		}
		if (search.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide siteId.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateGetStructureDocumentByRevision(SearchDTO search) {

		if (search.getStructureDocumentId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide structureDocumentId.");
		}

		if (search.getVersion() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide version.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateGetStructureDocumentRevisions(SearchDTO search) {

		if (search.getStructureDocumentId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide structureDocumentId.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddUpdateStructureDocument(StructureDocumentRequest docRequest) {

		if (docRequest.getId() == null && docRequest.getStructureId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					"Provide either documentId or structureId.");
		}
		if (docRequest.getTypeId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide typeId.");
		}
		if (docRequest.getDate() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide date.");
		}
		if (docRequest.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide getSiteId.");
		}
		if (docRequest.getFileIds() == null || docRequest.getFileIds().size() == 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide fileIds.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateDeactivateStructureDocumentFiles(StructureDocumentDeactivateRequest remRequest) {

		if (remRequest.getStructureDocumentId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide structureDocumentId.");
		}
		if (remRequest.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide getSiteId.");
		}
		if (remRequest.getRemoveFilesOnly() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide 'removeFilesOnly' value.");
		}
		if (remRequest.getRemoveFilesOnly() != null && remRequest.getRemoveFilesOnly()) {
			if (remRequest.getFileIds() == null || remRequest.getFileIds().size() == 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide fileIds.");
			}
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddStructureDocumentFiles(SearchDTO search) {

		if (search.getStructureDocumentId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide structureDocumentId.");
		}
		if (search.getIdsArr() == null || search.getIdsArr().size() == 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide fileIds.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddCategoryItem(CategoryItemDTO categoryDTO) {

		if (categoryDTO.getCode() == null || categoryDTO.getCode().trim().isEmpty()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide system code.");
		}
		if (categoryDTO.getStandardBookCode() == null || categoryDTO.getStandardBookCode().trim().isEmpty()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide standard data book code.");
		}
		if (categoryDTO.getName() == null || categoryDTO.getName().trim().isEmpty()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide name.");
		}
		if (categoryDTO.getCompanyId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user company.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateUpdateCategoryItem(CategoryItemDTO categoryDTO) {

		if (categoryDTO.getId() == null || !(categoryDTO.getId().longValue() > 0)) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid category Id.");
		}
		if (categoryDTO.getName() == null || categoryDTO.getName().trim().isEmpty()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide name.");
		}
		if (categoryDTO.getCompanyId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user company.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateStructureGroupAddRequest(StructureGroupAddUpdateRequest groupRequest) {

		if (groupRequest.getName() == null || groupRequest.getName().trim().isEmpty()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide name.");
		}
		if (groupRequest.getStructureTypeId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide structure type.");
		}
		if (groupRequest.getUserDetail().getCompanyId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user company.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateStructureGroupUpdateRequest(StructureGroupAddUpdateRequest groupRequest) {

		if (groupRequest.getId() < 1) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid id.");
		}
		if (groupRequest.getName() == null || groupRequest.getName().trim().isEmpty()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide name.");
		}
		if (groupRequest.getUserDetail().getCompanyId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user company.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse addRfiCustomWorkItem(RfiCustomWorkItemAddUpdateRequest requestDTO) {
		if (requestDTO.getName() == null || requestDTO.getName().trim().isEmpty()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide name.");
		}
		if (requestDTO.getUserDetail().getCompanyId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user company.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse updateRfiCustomWorkItem(RfiCustomWorkItemAddUpdateRequest requestDTO) {
		if (requestDTO.getName() == null || requestDTO.getName().trim().isEmpty()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide name.");
		}
		if (requestDTO.getUserDetail().getCompanyId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user company.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddRfiChecklistItemRequest(RfiChecklistItemBoqsAddUpdateRequest requestDTO) {
		if (requestDTO.getName() == null || requestDTO.getName().isBlank()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide name.");
		}
		if (requestDTO.getStateId() == null || requestDTO.getStateId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide state.");
		}
		if (requestDTO.getBoqItemIds() == null || requestDTO.getBoqItemIds().isEmpty()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide atleast one BOQ Item.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateUpdateRfiChecklistItemRequest(RfiChecklistItemBoqsAddUpdateRequest requestDTO) {

		if (requestDTO.getId() == null || requestDTO.getId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide id.");
		}
		if (requestDTO.getName() == null || requestDTO.getName().isBlank()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide name.");
		}
		if (requestDTO.getStateId() == null || requestDTO.getStateId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide state.");
		}
		if (requestDTO.getBoqItemIds() == null || requestDTO.getBoqItemIds().isEmpty()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide atleast one BOQ Item.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateFetchRfiChecklistItemRequest(RfiChecklistItemBoqsFetchRequest requestDTO) {

		if (requestDTO.getPageNo() == null || requestDTO.getPageSize() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide page no.");
		}
		if (requestDTO.getPageSize() == null || requestDTO.getPageSize() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide page size.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddWorkLayerRequest(WorkLayerBoqsAddUpdateRequest requestDTO) {
		if (requestDTO.getName() == null || requestDTO.getName().isBlank()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide name.");
		}
		if (requestDTO.getStateId() == null || requestDTO.getStateId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide state.");
		}
		if (requestDTO.getBoqItemIds() == null || requestDTO.getBoqItemIds().isEmpty()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide atleast one BOQ Item.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateUpdateWorkLayerRequest(WorkLayerBoqsAddUpdateRequest requestDTO) {

		if (requestDTO.getId() == null || requestDTO.getId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide id.");
		}
		if (requestDTO.getName() == null || requestDTO.getName().isBlank()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide name.");
		}
		if (requestDTO.getStateId() == null || requestDTO.getStateId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide state.");
		}
		if (requestDTO.getBoqItemIds() == null || requestDTO.getBoqItemIds().isEmpty()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide atleast one BOQ Item.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateFetchWorkLayerRequest(WorkLayerBoqsFetchRequest requestDTO) {

		if (requestDTO.getPageNo() == null || requestDTO.getPageSize() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide page no.");
		}
		if (requestDTO.getPageSize() == null || requestDTO.getPageSize() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide page size.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateFetchRfiMainRequest(RfiMainFetchRequest requestDTO) {

		if (requestDTO.getSiteId() == null || requestDTO.getSiteId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide site.");
		}
		if (requestDTO.getPageNo() == null || requestDTO.getPageSize() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide page no.");
		}
		if (requestDTO.getPageSize() == null || requestDTO.getPageSize() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide page size.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddRfiMainRequest(RfiMainAddUpdateRequest requestDTO) {

		if (requestDTO.getMode() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide mode.");
		}
		if (requestDTO.getType() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide work type.");
		}
		if (requestDTO.getMode().equals(RfiMode.Reinspection)) {
			if (requestDTO.getParentId() == null || requestDTO.getParentId() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide reinspection reference RFI.");
			}
		}
		if (requestDTO.getStateId() == null || requestDTO.getStateId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide state.");
		}
		if ((requestDTO.getBoqId() == null || requestDTO.getBoqId() <= 0)
				&& (requestDTO.getCustomItemId() == null || requestDTO.getCustomItemId() <= 0)) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide work item.");
		}

		if (requestDTO.getType().equals(RfiWorkType.Structure)) {
			if (requestDTO.getStructureId() == null || requestDTO.getStructureId() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide structure.");
			}
		} else if (requestDTO.getType().equals(RfiWorkType.Highway)) {
			if (requestDTO.getFromChainageId() != null && requestDTO.getFromChainageId() >= 0) {

				if (requestDTO.getToChainageId() == null || requestDTO.getToChainageId() <= 0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide to-chainage.");
				}
			} else if (requestDTO.getChainageInfo() == null || requestDTO.getChainageInfo().isBlank()) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide chainage info.");
			}
		}
		if (requestDTO.getWorkDescription() == null || requestDTO.getWorkDescription().isBlank()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide work description.");
		}
		if (requestDTO.getExecutableWorkQuantity() == null || requestDTO.getExecutableWorkQuantity() < 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					"Provide appropriate executable work quantity.");
		}
		if (requestDTO.getClientExecutedQuantity() == null || requestDTO.getClientExecutedQuantity() < 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					"Provide appropriate client executed work quantity.");
		}
		if (requestDTO.getActualExecutedQuantity() == null || requestDTO.getActualExecutedQuantity() < 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					"Provide appropriate actual executed work quantity.");
		}
		if (requestDTO.getActualExecutedQuantity() > requestDTO.getExecutableWorkQuantity()
				|| requestDTO.getClientExecutedQuantity() > requestDTO.getExecutableWorkQuantity()) {

			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					"Executed work quantity must not be greater than executable work quantity.");
		}
		if (requestDTO.getSiteId() == null || requestDTO.getSiteId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide site.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		if (requestDTO.getRfiMainComments() != null) {
			requestDTO.setRfiMainComments(null);
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateUpdateRfiMainRequest(RfiMainAddUpdateRequest requestDTO) {

		if (requestDTO.getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide id.");
		}
		if (requestDTO.getMode() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide mode.");
		}
		if (requestDTO.getType() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide work type.");
		}
		if (requestDTO.getMode().equals(RfiMode.Reinspection)) {
			if (requestDTO.getParentId() == null || requestDTO.getParentId() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide reinspection reference RFI.");
			}
		}
		if (requestDTO.getStateId() == null || requestDTO.getStateId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide state.");
		}
		if ((requestDTO.getBoqId() == null || requestDTO.getBoqId() <= 0)
				&& (requestDTO.getCustomItemId() == null || requestDTO.getCustomItemId() <= 0)) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide work item.");
		}

		if (requestDTO.getType().equals(RfiWorkType.Structure)) {
			if (requestDTO.getStructureId() == null || requestDTO.getStructureId() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide structure.");
			}
		} else if (requestDTO.getType().equals(RfiWorkType.Highway)) {
			if (requestDTO.getFromChainageId() != null && requestDTO.getFromChainageId() >= 0) {

				if (requestDTO.getToChainageId() == null || requestDTO.getToChainageId() <= 0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide to-chainage.");
				}
			} else if (requestDTO.getChainageInfo() == null || requestDTO.getChainageInfo().isBlank()) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide chainage info.");
			}
		}
		if (requestDTO.getWorkDescription() == null || requestDTO.getWorkDescription().isBlank()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide work description.");
		}
		if (requestDTO.getExecutableWorkQuantity() == null || requestDTO.getExecutableWorkQuantity() < 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					"Provide appropriate executable work quantity.");
		}
		if (requestDTO.getClientExecutedQuantity() == null || requestDTO.getClientExecutedQuantity() < 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					"Provide appropriate client executed work quantity.");
		}
		if (requestDTO.getActualExecutedQuantity() == null || requestDTO.getActualExecutedQuantity() < 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					"Provide appropriate actual executed work quantity.");
		}
		if (requestDTO.getActualExecutedQuantity() > requestDTO.getExecutableWorkQuantity()
				|| requestDTO.getClientExecutedQuantity() > requestDTO.getExecutableWorkQuantity()) {

			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					"Executed work quantity must not be greater than executable work quantity.");
		}
		if (requestDTO.getSiteId() == null || requestDTO.getSiteId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide site.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		if (requestDTO.getRfiMainComments() != null && !requestDTO.getRfiMainComments().isEmpty()) {
			for (RfiMainCommentAddUpdateRequest rfiComment : requestDTO.getRfiMainComments()) {
				if (rfiComment.getCommentType() == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide RFI Comment Type.");
				}
				if (rfiComment.getCommentType().equals(RfiMainCommentType.INDEPENDENT_ENGINEER)
						&& rfiComment.getCommentTimestamp() == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide RFI Independent Engineer Comment Date.");
				}

			}
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateRfiBoqGetExecutableQuantityRequest(RfiBoqGetExecutableQuantityRequest requestDTO) {

		if (requestDTO.getRfiMode() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide RFI Mode.");
		}
		if (requestDTO.getRfiWorkType() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide RFI work type.");
		}
		if (requestDTO.getBoqId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide BOQ.");
		}
		if (requestDTO.getRfiWorkType().equals(RfiWorkType.Structure)) {
			if (requestDTO.getStructureId() == null || requestDTO.getStructureId() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide structure.");
			}
		} else if (requestDTO.getRfiWorkType().equals(RfiWorkType.Highway)) {

			if (requestDTO.getFromChainageId() != null && requestDTO.getFromChainageId() >= 0) {
				if (requestDTO.getToChainageId() == null || requestDTO.getToChainageId() <= 0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide to-chainage.");
				}
			}
		}
		if (requestDTO.getRfiMode().equals(RfiMode.Reinspection)
				&& (requestDTO.getRfiMainId() == null || requestDTO.getRfiMainId() <= 0)) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide rfiMainId.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateFetchRfiChecklistItemFinalSuccessRequest(
			RfiChecklistItemFinalSuccessFetchRequest requestDTO) {

		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validatePrintRfiMainResponse(PrintRfiMainResponse obj) {

		if (obj.getSiteDescription() == null || obj.getSiteDescription().isBlank()) {
			return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "No site description found.");
		}
		if (obj.getConcessionaireName() == null || obj.getConcessionaireName().isBlank()) {
			return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "No site concessionaire found.");
		}
		if (obj.getIndependentEngineerName() == null || obj.getIndependentEngineerName().isBlank()) {
			return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "No independent engineer found.");
		}
		if (obj.getClientName() == null || obj.getClientName().isBlank()) {
			return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "No client found.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateFetchWorkLayerFinalSuccessRequest(WorkLayerFinalSuccessFetchRequest requestDTO) {

		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateExportRfiSummary(RfiMainExportSummaryRequest requestDTO) {

		if (requestDTO.getSiteId() == null || requestDTO.getSiteId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide site.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse addBoqCostDefinition(BoqCostDefinitionAddRequest requestDTO) {

		if (requestDTO.getBoqId() == null || requestDTO.getBoqId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide boqId.");
		}
		if (requestDTO.getExpectedOutputValue() == null || requestDTO.getExpectedOutputValue() < 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct expected output value.");
		}
		if (requestDTO.getFoamworkIncluded() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide foamworkIncluded.");
		}
		if (requestDTO.getOverheadIncluded() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide overheadIncluded.");
		}
		if (requestDTO.getContractorProfitIncluded() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide contractorProfitIncluded.");
		}
		if (requestDTO.getExtraExpenseAmount() == null || requestDTO.getExtraExpenseAmount() < 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct extra expense amount.");
		}
		if (requestDTO.getStateId() == null || requestDTO.getStateId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide stateId.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse updateBoqCostDefinition(BoqCostDefinitionUpdateRequest requestDTO) {

		if (requestDTO.getId() == null || requestDTO.getId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide id.");
		}
		if (requestDTO.getBoqId() == null || requestDTO.getBoqId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide boqId.");
		}
		if (requestDTO.getExpectedOutputValue() == null || requestDTO.getExpectedOutputValue() < 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct expected output value.");
		}
		if (requestDTO.getFoamworkIncluded() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide foamworkIncluded.");
		}
		if (requestDTO.getOverheadIncluded() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide overheadIncluded.");
		}
		if (requestDTO.getContractorProfitIncluded() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide contractorProfitIncluded.");
		}
		if (requestDTO.getExtraExpenseAmount() == null || requestDTO.getExtraExpenseAmount() < 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct extra expense amount.");
		}
		if (requestDTO.getStateId() == null || requestDTO.getStateId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide stateId.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse addBoqCostDefinitionLabour(BoqCostDefinitionLabourAddUpdateRequest requestDTO) {

		if (requestDTO.getBoqCostDefinitionId() == null && requestDTO.getBoqId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide boqCostDefinitionId or boqId.");
		}
		if (requestDTO.getLabourTypeId() == null || requestDTO.getLabourTypeId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide labourTypeId.");
		}
		if (requestDTO.getUnitId() == null || requestDTO.getUnitId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide unitId.");
		}
		if (requestDTO.getQuantity() == null || requestDTO.getQuantity() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct quantity.");
		}
		if (requestDTO.getRate() == null || requestDTO.getRate() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct rate.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse updateBoqCostDefinitionLabour(BoqCostDefinitionLabourAddUpdateRequest requestDTO) {
		if (requestDTO.getId() == null || requestDTO.getId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide id.");
		}
		if (requestDTO.getBoqCostDefinitionId() == null || requestDTO.getBoqCostDefinitionId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide boqCostDefinitionId.");
		}
		if (requestDTO.getLabourTypeId() == null || requestDTO.getLabourTypeId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide labourTypeId.");
		}
		if (requestDTO.getUnitId() == null || requestDTO.getUnitId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide unitId.");
		}
		if (requestDTO.getQuantity() == null || requestDTO.getQuantity() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct quantity.");
		}
		if (requestDTO.getRate() == null || requestDTO.getRate() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct rate.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse addBoqCostDefinitionMaterial(BoqCostDefinitionMaterialAddUpdateRequest requestDTO) {

		if (requestDTO.getBoqCostDefinitionId() == null && requestDTO.getBoqId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide boqCostDefinitionId or boqId.");
		}
		if (requestDTO.getMaterialId() == null || requestDTO.getMaterialId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide materialId.");
		}
		if (requestDTO.getUnitId() == null || requestDTO.getUnitId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide unitId.");
		}
		if (requestDTO.getQuantity() == null || requestDTO.getQuantity() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct quantity.");
		}
		if (requestDTO.getRate() == null || requestDTO.getRate() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct rate.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse updateBoqCostDefinitionMaterial(BoqCostDefinitionMaterialAddUpdateRequest requestDTO) {
		if (requestDTO.getId() == null || requestDTO.getId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide id.");
		}
		if (requestDTO.getBoqCostDefinitionId() == null || requestDTO.getBoqCostDefinitionId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide boqCostDefinitionId.");
		}
		if (requestDTO.getMaterialId() == null || requestDTO.getMaterialId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide materialId.");
		}
		if (requestDTO.getUnitId() == null || requestDTO.getUnitId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide unitId.");
		}
		if (requestDTO.getQuantity() == null || requestDTO.getQuantity() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct quantity.");
		}
		if (requestDTO.getRate() == null || requestDTO.getRate() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct rate.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse addBoqCostDefinitionMachinery(BoqCostDefinitionMachineryAddUpdateRequest requestDTO) {

		if (requestDTO.getBoqCostDefinitionId() == null && requestDTO.getBoqId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide boqCostDefinitionId or boqId.");
		}
		if (requestDTO.getMachineryCategoryId() == null || requestDTO.getMachineryCategoryId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide machineryCategoryId.");
		}
		if (requestDTO.getUnitId() == null || requestDTO.getUnitId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide unitId.");
		}
		if (requestDTO.getRate() == null || requestDTO.getRate() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct rate.");
		}
		if (requestDTO.getRangeType() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide rangeType.");
		}
		if (requestDTO.getRangeType() != null && requestDTO.getRangeType().equals(MachineryRangeType.Upto_After)) {

			if (requestDTO.getRangeUnitId() == null || requestDTO.getRangeUnitId() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide rangeUnitId.");
			}
			if (requestDTO.getRangeQuantity() == null || requestDTO.getRangeQuantity() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide rangeQuantity.");
			}
			if (requestDTO.getAfterUnitId() == null || requestDTO.getAfterUnitId() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide afterUnitId.");
			}
			if (requestDTO.getAfterRate() == null || requestDTO.getAfterRate() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct after rate.");
			}

		} else {
			requestDTO.setRangeUnitId(null);
			requestDTO.setRangeQuantity(null);
			requestDTO.setAfterUnitId(null);
			requestDTO.setAfterRate(null);
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse updateBoqCostDefinitionMachinery(BoqCostDefinitionMachineryAddUpdateRequest requestDTO) {

		if (requestDTO.getId() == null || requestDTO.getId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide id.");
		}
		if (requestDTO.getBoqCostDefinitionId() == null || requestDTO.getBoqCostDefinitionId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide boqCostDefinitionId.");
		}
		if (requestDTO.getMachineryCategoryId() == null || requestDTO.getMachineryCategoryId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide machineryCategoryId.");
		}
		if (requestDTO.getUnitId() == null || requestDTO.getUnitId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide unitId.");
		}
		if (requestDTO.getRate() == null || requestDTO.getRate() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct rate.");
		}
		if (requestDTO.getRangeType() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide rangeType.");
		}

		if (requestDTO.getRangeType() != null && requestDTO.getRangeType().equals(MachineryRangeType.Upto_After)) {

			if (requestDTO.getRangeUnitId() == null || requestDTO.getRangeUnitId() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide rangeUnitId.");
			}
			if (requestDTO.getRangeQuantity() == null || requestDTO.getRangeQuantity() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide rangeQuantity.");
			}
			if (requestDTO.getAfterUnitId() == null || requestDTO.getAfterUnitId() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide afterUnitId.");
			}
			if (requestDTO.getAfterRate() == null || requestDTO.getAfterRate() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct after rate.");
			}

		} else {
			requestDTO.setRangeUnitId(null);
			requestDTO.setRangeQuantity(null);
			requestDTO.setAfterUnitId(null);
			requestDTO.setAfterRate(null);
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse addBoqMasterLmps(BoqMasterLmpsAddUpdateRequest requestDTO) {

		if (requestDTO.getBoqId() == null || requestDTO.getBoqId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide boqId.");
		}
		if (requestDTO.getExpectedOutputValue() == null || requestDTO.getExpectedOutputValue() < 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct expected output value.");
		}
		if (requestDTO.getFoamworkIncluded() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide foamworkIncluded.");
		}
		if (requestDTO.getOverheadIncluded() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide overheadIncluded.");
		}
		if (requestDTO.getContractorProfitIncluded() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide contractorProfitIncluded.");
		}
		if (requestDTO.getExtraExpenseAmount() == null || requestDTO.getExtraExpenseAmount() < 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct extra expense amount.");
		}
		if (requestDTO.getStateId() == null || requestDTO.getStateId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide stateId.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse updateBoqMasterLmps(BoqMasterLmpsAddUpdateRequest requestDTO) {

		if (requestDTO.getId() == null || requestDTO.getId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide id.");
		}
		if (requestDTO.getBoqId() == null || requestDTO.getBoqId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide boqId.");
		}
		if (requestDTO.getExpectedOutputValue() == null || requestDTO.getExpectedOutputValue() < 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct expected output value.");
		}
		if (requestDTO.getFoamworkIncluded() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide foamworkIncluded.");
		}
		if (requestDTO.getOverheadIncluded() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide overheadIncluded.");
		}
		if (requestDTO.getContractorProfitIncluded() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide contractorProfitIncluded.");
		}
		if (requestDTO.getExtraExpenseAmount() == null || requestDTO.getExtraExpenseAmount() < 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct extra expense amount.");
		}
		if (requestDTO.getStateId() == null || requestDTO.getStateId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide stateId.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse addBoqMasterLmpsLabour(BoqMasterLmpsLabourAddUpdateRequest requestDTO) {

		if (requestDTO.getBoqMasterLmpsId() == null && requestDTO.getBoqId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide boqMasterLmpsId or boqId.");
		}
		if (requestDTO.getLabourTypeId() == null || requestDTO.getLabourTypeId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide labourTypeId.");
		}
		if (requestDTO.getUnitId() == null || requestDTO.getUnitId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide unitId.");
		}
		if (requestDTO.getQuantity() == null || requestDTO.getQuantity() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct quantity.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse updateBoqMasterLmpsLabour(BoqMasterLmpsLabourAddUpdateRequest requestDTO) {
		if (requestDTO.getId() == null || requestDTO.getId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide id.");
		}
		if (requestDTO.getBoqMasterLmpsId() == null || requestDTO.getBoqMasterLmpsId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide boqMasterLmpsId.");
		}
		if (requestDTO.getLabourTypeId() == null || requestDTO.getLabourTypeId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide labourTypeId.");
		}
		if (requestDTO.getUnitId() == null || requestDTO.getUnitId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide unitId.");
		}
		if (requestDTO.getQuantity() == null || requestDTO.getQuantity() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct quantity.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse addBoqMasterLmpsMaterial(BoqMasterLmpsMaterialAddUpdateRequest requestDTO) {

		if (requestDTO.getMaterialId() == null || requestDTO.getMaterialId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide materialId.");
		}
		if (requestDTO.getUnitId() == null || requestDTO.getUnitId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide unitId.");
		}
		if (requestDTO.getQuantity() == null || requestDTO.getQuantity() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct quantity.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse updateBoqMasterLmpsMaterial(BoqMasterLmpsMaterialAddUpdateRequest requestDTO) {
		if (requestDTO.getId() == null || requestDTO.getId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide id.");
		}
		if (requestDTO.getMaterialId() == null || requestDTO.getMaterialId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide materialId.");
		}
		if (requestDTO.getUnitId() == null || requestDTO.getUnitId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide unitId.");
		}
		if (requestDTO.getQuantity() == null || requestDTO.getQuantity() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide correct quantity.");
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse addBoqMasterLmpsMachinery(BoqMasterLmpsMachineryAddUpdateRequest requestDTO) {

		if (requestDTO.getBoqMasterLmpsId() == null && requestDTO.getBoqId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide boqMasterLmpsId or boqId.");
		}
		if (requestDTO.getMachineryCategoryId() == null || requestDTO.getMachineryCategoryId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide machineryCategoryId.");
		}
		if (requestDTO.getUnitId() == null || requestDTO.getUnitId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide unitId.");
		}
		if (requestDTO.getSiteVariableId() == null || requestDTO.getSiteVariableId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide lead.");
		}
		if (requestDTO.getRangeType() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide rangeType.");
		}
		if (requestDTO.getRangeType() != null && requestDTO.getRangeType().equals(MachineryRangeType.Upto_After)) {

			if (requestDTO.getRangeUnitId() == null || requestDTO.getRangeUnitId() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide rangeUnitId.");
			}
			if (requestDTO.getRangeQuantity() == null || requestDTO.getRangeQuantity() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide rangeQuantity.");
			}
			if (requestDTO.getAfterUnitId() == null || requestDTO.getAfterUnitId() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide afterUnitId.");
			}

		} else {
			requestDTO.setRangeUnitId(null);
			requestDTO.setRangeQuantity(null);
			requestDTO.setAfterUnitId(null);
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse updateBoqMasterLmpsMachinery(BoqMasterLmpsMachineryAddUpdateRequest requestDTO) {

		if (requestDTO.getId() == null || requestDTO.getId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide id.");
		}
		if (requestDTO.getBoqMasterLmpsId() == null || requestDTO.getBoqMasterLmpsId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide boqMasterLmpsId.");
		}
		if (requestDTO.getMachineryCategoryId() == null || requestDTO.getMachineryCategoryId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide machineryCategoryId.");
		}
		if (requestDTO.getUnitId() == null || requestDTO.getUnitId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide unitId.");
		}
		if (requestDTO.getSiteVariableId() == null || requestDTO.getSiteVariableId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide lead.");
		}
		if (requestDTO.getRangeType() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide rangeType.");
		}

		if (requestDTO.getRangeType() != null && requestDTO.getRangeType().equals(MachineryRangeType.Upto_After)) {

			if (requestDTO.getRangeUnitId() == null || requestDTO.getRangeUnitId() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide rangeUnitId.");
			}
			if (requestDTO.getRangeQuantity() == null || requestDTO.getRangeQuantity() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide rangeQuantity.");
			}
			if (requestDTO.getAfterUnitId() == null || requestDTO.getAfterUnitId() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide afterUnitId.");
			}

		} else {
			requestDTO.setRangeUnitId(null);
			requestDTO.setRangeQuantity(null);
			requestDTO.setAfterUnitId(null);
		}
		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user details.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse addUpdateProjectPlanBoq(ProjectPlanBoqAddUpdateRequest requestDTO) {

		if (requestDTO.getSiteId() == null || requestDTO.getSiteId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide siteId.");
		}
		if (requestDTO.getDistributionList() == null || requestDTO.getDistributionList().isEmpty()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide distribution list.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}
}
