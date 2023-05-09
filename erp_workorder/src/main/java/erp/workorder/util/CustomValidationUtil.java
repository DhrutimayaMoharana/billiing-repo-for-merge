package erp.workorder.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.WoTncTypeDTO;
import erp.workorder.dto.request.AmendWorkorderInvocationAddUpdateRequest;
import erp.workorder.dto.request.AmendWorkorderInvocationDeactivateRequest;
import erp.workorder.dto.request.AmendWorkorderInvocationGetRequest;
import erp.workorder.dto.request.AmendWorkorderInvocationNextActionsRequest;
import erp.workorder.dto.request.AmendWorkorderInvocationUpdateStateRequest;
import erp.workorder.dto.request.MachineDPRImportExcelRequest;
import erp.workorder.dto.request.MachineDprAddUpdateRequest;
import erp.workorder.dto.request.MachineDprDeactivateRequest;
import erp.workorder.dto.request.MachineDprGetRequest;
import erp.workorder.dto.request.WorkoderHireMachineRequest;
import erp.workorder.dto.request.WorkorderBillInfoUpdateRequest;
import erp.workorder.dto.request.WorkorderCloseAddUpdateRequest;
import erp.workorder.dto.request.WorkorderCloseDeactivateRequest;
import erp.workorder.dto.request.WorkorderCloseGetRequest;
import erp.workorder.dto.request.WorkorderCloseUpdateStateRequest;
import erp.workorder.dto.request.WorkorderConsultantWorkAddUpdateRequestDTO;
import erp.workorder.dto.request.WorkorderConsultantWorkGetRequestDTO;
import erp.workorder.dto.request.WorkorderConsultantWorkItemAddUpdateRequest;
import erp.workorder.dto.request.WorkorderConsultantWorkItemDeactivateRequestDTO;
import erp.workorder.dto.request.WorkorderHiringMachineRateDetailsAddUpdateRequest;
import erp.workorder.dto.request.WorkorderHiringMachineWorkAddUpdateRequestDTO;
import erp.workorder.dto.request.WorkorderHiringMachineWorkGetRequestDTO;
import erp.workorder.dto.request.WorkorderHiringMachineWorkItemAddUpdateRequest;
import erp.workorder.dto.request.WorkorderHiringMachineWorkItemDeactivateRequestDTO;
import erp.workorder.dto.request.WorkorderLabourWorkAddUpdateRequestDTO;
import erp.workorder.dto.request.WorkorderLabourWorkGetRequestDTO;
import erp.workorder.dto.request.WorkorderLabourWorkItemAddUpdateRequest;
import erp.workorder.dto.request.WorkorderLabourWorkItemDeactivateRequest;
import erp.workorder.dto.request.WorkorderPayMilestoneAddUpdateRequest;
import erp.workorder.dto.request.WorkorderPayMilestoneDeactivateRequest;
import erp.workorder.dto.request.WorkorderPayMilestoneGetRequest;
import erp.workorder.dto.request.WorkorderTransportationWorkAddUpdateRequestDTO;
import erp.workorder.dto.request.WorkorderTransportationWorkGetRequestDTO;
import erp.workorder.dto.request.WorkorderTransportationWorkItemAddUpdateRequest;
import erp.workorder.dto.request.WorkorderTransportationWorkItemDeactivateRequestDTO;
import erp.workorder.enums.MachineryAttendanceStatus;
import erp.workorder.enums.MachineryRunningMode;
import erp.workorder.enums.Responses;

@Component
public class CustomValidationUtil {

	public CustomResponse validateWorkorderFilesRequest(SearchDTO search) {

		if (search.getWorkorderId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide workorderId.");
		}
		if (search.getFileTypeId() == null && (search.getGetAll() == null || !search.getGetAll())) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide fileTypeId.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddFilesRequest(SearchDTO search) {

		if (search.getWorkorderId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide workorderId.");
		}
		if (search.getFileTypeId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide fileTypeId.");
		}
		if (search.getIdsArr() == null && search.getIdsArr().size() == 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide fileIds.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateDeactivateFilesRequest(SearchDTO search) {

		if (search.getWorkorderId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide workorderId.");
		}
		if (search.getIdsArr() == null && search.getIdsArr().size() == 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide workorderFileMappingIds.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddWorkorderTncType(WoTncTypeDTO tncTypeDTO) {

		if (tncTypeDTO.getName() == null || tncTypeDTO.getName().isEmpty()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide name.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateUpdateWorkorderTncType(WoTncTypeDTO tncTypeDTO) {

		if (tncTypeDTO.getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide identity.");
		}
		if (tncTypeDTO.getName() == null || tncTypeDTO.getName().isEmpty()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide name.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateGetWorkorderAmendmentInvocationRequest(
			AmendWorkorderInvocationGetRequest clientRequestDTO) {

		if (clientRequestDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid site.");
		}
		if (clientRequestDTO.getPageNo() == null || clientRequestDTO.getPageNo() < 1) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid pageNo.");
		}
		if (clientRequestDTO.getPageSize() == null || clientRequestDTO.getPageSize() < 1) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid pageSize.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddOrUpdateWorkorderAmendmentInvocation(
			AmendWorkorderInvocationAddUpdateRequest clientRequestDTO) {

		if (clientRequestDTO.getWorkorderId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");
		}
		if (clientRequestDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid site.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateDeactivateWorkorderAmendmentInvocation(
			AmendWorkorderInvocationDeactivateRequest clientRequestDTO) {

		if (clientRequestDTO.getAmendWorkorderInvocationId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					"Provide valid amendWorkorderInvocationId.");
		}
		if (clientRequestDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid site.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateUpdateWorkorderAmendmentInvocationState(
			AmendWorkorderInvocationUpdateStateRequest clientRequestDTO) {

		if (clientRequestDTO.getAmendWorkorderInvocationId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					"Provide valid amendWorkorderInvocationId.");
		}
		if (clientRequestDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid site.");
		}
		if (clientRequestDTO.getStateId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid state.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAmendWorkorderInvocationNextPossibleStates(
			AmendWorkorderInvocationNextActionsRequest clientRequestDTO) {

		if (clientRequestDTO.getAmendWorkorderInvocationId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					"Provide valid amendWorkorderInvocationId.");
		}
		if (clientRequestDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid site.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddOrUpdateWorkorderConsultantWork(
			WorkorderConsultantWorkAddUpdateRequestDTO consultantWork) {

		if (consultantWork.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid site.");
		}
		if (consultantWork.getUserDetail() == null || consultantWork.getUserDetail().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}
		if (consultantWork.getWorkScope() == null || consultantWork.getWorkScope().isBlank()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid scope of work.");
		}
		if (consultantWork.getWorkorderId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");
		}
		if (consultantWork.getConsultantWorkId() == null && (consultantWork.getConsultantWorkItems() == null
				|| consultantWork.getConsultantWorkItems().size() < 1)) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid consultant work items.");
		}
		if (consultantWork.getConsultantWorkId() == null && !(consultantWork.getConsultantWorkItems() == null
				|| consultantWork.getConsultantWorkItems().size() < 1)) {
			int index = 0;
			for (WorkorderConsultantWorkItemAddUpdateRequest workItem : consultantWork.getConsultantWorkItems()) {
				if (workItem.getCategoryDescription() != null && workItem.getCategoryDescription().isBlank()) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item category description for item at index " + index);
				}
				if (workItem.getDescription() == null || workItem.getDescription().isBlank()) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item description for item at index " + index);
				}
				if (workItem.getQuantity() == null || workItem.getQuantity() < 0.0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item quantity for item at index " + index);
				}
				if (workItem.getRate() == null || workItem.getRate() < 0.0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item rate for item at index " + index);
				}
				if (workItem.getUnitId() == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item unitId for item at index " + index);
				}
				index++;
			}
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateGetWorkorderConsultantWork(WorkorderConsultantWorkGetRequestDTO consultantWorkDTO) {

		if (consultantWorkDTO.getWorkorderId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");
		}
		if (consultantWorkDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid siteId.");
		}
		if (consultantWorkDTO.getUserDetail() == null || consultantWorkDTO.getUserDetail().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateUpdateWorkorderConsultantWorkItem(
			WorkorderConsultantWorkItemAddUpdateRequest workItem) {

		if (workItem.getConsultantWorkId() == null && workItem.getConsultantWorkItemId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					"Provide valid consultantWorkId or consultantWorkItemId.");
		}
		if (workItem.getCategoryDescription() != null && workItem.getCategoryDescription().isBlank()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					"Provide valid item category description.");
		}
		if (workItem.getDescription() == null || workItem.getDescription().isBlank()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid item description.");
		}
		if (workItem.getQuantity() == null || workItem.getQuantity() < 0.0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid item quantity.");
		}
		if (workItem.getRate() == null || workItem.getRate() < 0.0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid item rate.");
		}
		if (workItem.getUnitId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid item unitId.");
		}
		if (workItem.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid siteId.");
		}
		if (workItem.getUserDetail() == null || workItem.getUserDetail().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateDeactivateWorkorderConsultantWorkItem(
			WorkorderConsultantWorkItemDeactivateRequestDTO consultantWorkItemDTO) {
		if (consultantWorkItemDTO.getConsultantWorkItemId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid consultantWorkItemId.");
		}
		if (consultantWorkItemDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid siteId.");
		}
		if (consultantWorkItemDTO.getUserDetail() == null || consultantWorkItemDTO.getUserDetail().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddOrUpdateWorkorderHiringMachineWork(
			WorkorderHiringMachineWorkAddUpdateRequestDTO hmWork) {

		if (hmWork.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid site.");
		}
		if (hmWork.getUserDetail() == null || hmWork.getUserDetail().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}
		if (hmWork.getWorkScope() == null || hmWork.getWorkScope().isBlank()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid scope of work.");
		}
		if (hmWork.getWorkorderId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");
		}
		if (hmWork.getHmWorkId() == null && (hmWork.getHmWorkItems() == null || hmWork.getHmWorkItems().size() < 1)) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid hiring machine items.");
		}
		if (hmWork.getHmWorkId() == null && !(hmWork.getHmWorkItems() == null || hmWork.getHmWorkItems().size() < 1)) {
			int index = 0;
			for (WorkorderHiringMachineWorkItemAddUpdateRequest workItem : hmWork.getHmWorkItems()) {

				if (workItem.getMachineCount() == null || workItem.getMachineCount() < 1) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item machineCount for item at index " + index);
				}
				if (workItem.getMachineCategoryId() == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item machineCategoryId for item at index " + index);
				}
				if (workItem.getThresholdApplicable() == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item thresholdApplicable for item at index " + index);
				} else if (workItem.getThresholdApplicable()) {

					if (workItem.getThresholdQuantity() == null || workItem.getThresholdQuantity() <= 0.0) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Provide valid item thresholdQuantity for item at index " + index);
					}
					if (workItem.getThresholdUnitId() == null) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Provide valid item thresholdUnitId for item at index " + index);
					}
					if (workItem.getPostThresholdRatePerUnit() == null || workItem.getThresholdQuantity() <= 0.0) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Provide valid item postThresholdRatePerUnit for item at index " + index);
					}
				}

				if (workItem.getIsMultiFuel() == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid isMultiFuel key at index " + index);
				}

				if (workItem.getDieselInclusive() == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid dieselInclusive key at index " + index);
				}
				if (!workItem.getDieselInclusive()) {

					if (workItem.getFuelHandlingCharge() == null || workItem.getFuelHandlingCharge() < 0.0) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Provide valid FuelHandlingCharge at index " + index);
					}
					if (workItem.getPrimaryEngineMileage() == null || workItem.getPrimaryEngineMileage() <= 0.0) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Provide valid Primary Engine Mileage at index " + index);
					}
					if (workItem.getPrimaryEngineMileageUnitId() == null
							|| workItem.getPrimaryEngineMileageUnitId() <= 0) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Provide valid Primary Engine Mileage Unit at index " + index);
					}
					if (workItem.getIsMultiFuel()) {
						if (workItem.getSecondaryEngineMileage() == null
								|| workItem.getSecondaryEngineMileage() <= 0.0) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Provide valid Secondary Engine Mileage at index " + index);
						}
						if (workItem.getSecondaryEngineMileageUnitId() == null
								|| workItem.getSecondaryEngineMileageUnitId() <= 0) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Provide valid Secondary Engine Mileage Unit at index " + index);
						}
					}
				}

				if (workItem.getDescription() == null || workItem.getDescription().isBlank()) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item description for item at index " + index);
				}
				if (workItem.getQuantity() == null || workItem.getQuantity() <= 0.0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item quantity for item at index " + index);
				}
				if (workItem.getMachineRateDetails() == null || workItem.getMachineRateDetails().isEmpty()) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide rate details at index " + index);
				}

				for (WorkorderHiringMachineRateDetailsAddUpdateRequest rateDetail : workItem.getMachineRateDetails()) {
					if (rateDetail == null || rateDetail.getRate() == null || rateDetail.getRate() <= 0.0) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Provide rate details at index " + index);
					}

//					if (rateDetail.getShift() == null) {
//						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//								"Provide shift at index " + index);
//					}
				}

				if (workItem.getRate() == null || workItem.getRate() <= 0.0) {
//					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//					"Provide valid item rate for item at index " + index);
					workItem.setRate(0.0);
				}
				if (workItem.getUnitId() == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item unitId for item at index " + index);
				}
				index++;
			}
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateGetWorkorderHiringMachineWork(WorkorderHiringMachineWorkGetRequestDTO hmWorkDTO) {

		if (hmWorkDTO.getWorkorderId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");
		}
		if (hmWorkDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid siteId.");
		}
		if (hmWorkDTO.getUserDetail() == null || hmWorkDTO.getUserDetail().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateDeactivateWorkorderHiringMachineWorkItem(
			WorkorderHiringMachineWorkItemDeactivateRequestDTO hmWorkItemDTO) {

		if (hmWorkItemDTO.getHmWorkItemId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid hmWorkItemId.");
		}
		if (hmWorkItemDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid siteId.");
		}
		if (hmWorkItemDTO.getUserDetail() == null || hmWorkItemDTO.getUserDetail().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateUpdateWorkorderHiringMachineWorkItem(
			WorkorderHiringMachineWorkItemAddUpdateRequest workItem) {

		if (workItem.getHmWorkId() == null && workItem.getHmWorkItemId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid hmWorkId or hmWorkItemId.");
		}
		if (workItem.getDescription() == null || workItem.getDescription().isBlank()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid item description.");
		}
		if (workItem.getQuantity() == null || workItem.getQuantity() <= 0.0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid item quantity.");
		}
		if (workItem.getMachineRateDetails() == null || workItem.getMachineRateDetails().isEmpty()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide rate details");
		}

		for (WorkorderHiringMachineRateDetailsAddUpdateRequest rateDetail : workItem.getMachineRateDetails()) {
			if (rateDetail == null || rateDetail.getRate() == null || rateDetail.getRate() <= 0.0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide rate details");
			}
		}
		if (workItem.getRate() == null || workItem.getRate() <= 0.0) {
			workItem.setRate(0.0);
//			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid item rate.");
		}
		if (workItem.getUnitId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid item unitId.");
		}

		if (workItem.getMachineCount() == null || workItem.getMachineCount() < 1) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid item machineCount");
		}
		if (workItem.getMachineCategoryId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid item machineCategoryId");
		}
		if (workItem.getThresholdApplicable() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid item thresholdApplicable");
		} else if (workItem.getThresholdApplicable()) {

			if (workItem.getThresholdQuantity() == null || workItem.getThresholdQuantity() <= 0.0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Provide valid item thresholdQuantity");
			}
			if (workItem.getThresholdUnitId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid item thresholdUnitId");
			}
			if (workItem.getPostThresholdRatePerUnit() == null || workItem.getThresholdQuantity() <= 0.0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Provide valid item postThresholdRatePerUnit");
			}
		}
		if (workItem.getIsMultiFuel() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid isMultiFuel key.");
		}

		if (workItem.getDieselInclusive() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid dieselInclusive key.");
		}
		if (!workItem.getDieselInclusive()) {

			if (workItem.getFuelHandlingCharge() == null || workItem.getFuelHandlingCharge() < 0.0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid FuelHandlingCharge.");
			}
			if (workItem.getPrimaryEngineMileage() == null || workItem.getPrimaryEngineMileage() <= 0.0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Provide valid Primary Engine Mileage.");
			}
			if (workItem.getPrimaryEngineMileageUnitId() == null || workItem.getPrimaryEngineMileageUnitId() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Provide valid Primary Engine Mileage Unit.");
			}
			if (workItem.getIsMultiFuel()) {
				if (workItem.getSecondaryEngineMileage() == null || workItem.getSecondaryEngineMileage() <= 0.0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid Secondary Engine Mileage.");
				}
				if (workItem.getSecondaryEngineMileageUnitId() == null
						|| workItem.getSecondaryEngineMileageUnitId() <= 0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid Secondary Engine Mileage Unit.");
				}
			}
		}

		if (workItem.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid siteId.");
		}
		if (workItem.getUserDetail() == null || workItem.getUserDetail().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddOrUpdateMachineDPR(MachineDprAddUpdateRequest requestDTO) {

		if (requestDTO.getUserDetail() == null || requestDTO.getUserDetail().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}
		if (requestDTO.getDated() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid date.");
		}
		if (requestDTO.getRunningMode() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide running mode.");
		}

		if (requestDTO.getMachineId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid machineId.");
		}
		if (requestDTO.getMachineType() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid machineType.");
		}
		if (requestDTO.getRunningMode().equals(MachineryRunningMode.TRIP)
				&& (requestDTO.getTripCount() == null || requestDTO.getTripCount() < 0)) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid trip count.");
		}
		if (requestDTO.getRunningMode().equals(MachineryRunningMode.SHIFTS)) {
			if (requestDTO.getPrimaryOpeningMeterReading() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Provide valid primary open meter reading.");
			}
			if (requestDTO.getPrimaryClosingMeterReading() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Provide valid primary close meter reading.");
			}
//			if (requestDTO.getPrimaryOpeningActualReading() == null) {
//				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//						"Provide valid primary open actual reading.");
//			}
//			if (requestDTO.getPrimaryClosingActualReading() == null) {
//				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//						"Provide valid primary close actual reading.");
//			}
			if (requestDTO.getPrimaryOpeningMeterReading() > requestDTO.getPrimaryClosingMeterReading()) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Primary opening actual reading cannot be greater than primary closing actual reading.");
			}
//			if (requestDTO.getSecondaryOpeningActualReading() != null
//					&& requestDTO.getSecondaryClosingActualReading() != null
//					&& requestDTO.getSecondaryOpeningActualReading() > requestDTO.getSecondaryClosingActualReading()) {
//				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//						"Secondary opening actual reading cannot be greater than secondary closing actual reading.");
//			}
		}
		if (requestDTO.getRunningMode().equals(MachineryRunningMode.TRIP)) {
			if (requestDTO.getPrimaryOpeningMeterReading() == null) {
				requestDTO.setPrimaryOpeningMeterReading(0D);
			}
			if (requestDTO.getPrimaryClosingMeterReading() == null) {
				requestDTO.setPrimaryClosingMeterReading(null);
			}
			if (requestDTO.getPrimaryOpeningActualReading() == null) {
				requestDTO.setPrimaryOpeningActualReading(0D);
			}
			if (requestDTO.getPrimaryClosingActualReading() == null) {
				requestDTO.setPrimaryClosingActualReading(0D);
			}

		}

		if (requestDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid siteId.");
		}
		if (requestDTO.getRemarks() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid remarks.");
		}

		if (requestDTO.getRunningMode().equals(MachineryRunningMode.SHIFTS) && requestDTO.getBreakdownHours() != null) {
			if (requestDTO.getBreakdownHours() <= 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide  valid Breakdown Hours.");
			} else if (requestDTO.getAttendanceStatus() != null
					&& requestDTO.getAttendanceStatus().equals(MachineryAttendanceStatus.PRESENT)
					&& requestDTO.getBreakdownHours() > 12) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Breakdown Hours Can't be greater than Present Attendence Status.");
			} else if (requestDTO.getAttendanceStatus() != null
					&& requestDTO.getAttendanceStatus().equals(MachineryAttendanceStatus.HALFDAY)
					&& requestDTO.getBreakdownHours() > 6) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Breakdown Hours Can't be greater than Halfday Attendence Status.");

			}
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateDeactivateMachineDprRequest(MachineDprDeactivateRequest requestDTO) {

		if (requestDTO.getUserDetail() == null || requestDTO.getUserDetail().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}
		if (requestDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid siteId.");
		}
		if (requestDTO.getDprId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid dprId.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateSearchRequestForWorkOrder(SearchDTO requestDTO) {

		if (requestDTO.getSiteId() == null || requestDTO.getSiteId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide site.");
		}
		if (requestDTO.getUserId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user.");
		}
		if (requestDTO.getRoleId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide role.");
		}
		if (requestDTO.getCompanyId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide companyId.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateGetMachineDprRequest(MachineDprGetRequest requestDTO) {

		if (requestDTO.getUserDetail() == null || requestDTO.getUserDetail().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}
		if (requestDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid siteId.");
		}
		if (requestDTO.getFromDate() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid from date.");
		}
		if (requestDTO.getToDate() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid to date.");
		}
		if (requestDTO.getMachineId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid machineId.");
		}
		if (requestDTO.getMachineType() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid machineType.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse importMachineDPRExcelRequest(MachineDPRImportExcelRequest requestDTO) {

		if (requestDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid siteId.");
		}

		if (requestDTO.getMachineId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid machineId.");
		}
		if (requestDTO.getMachineType() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid machineType.");
		}

		if (requestDTO.getUserDetail() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid userDetail.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddOrUpdateWorkorderTransportWork(
			WorkorderTransportationWorkAddUpdateRequestDTO transportWorkDTO) {
		if (transportWorkDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid site.");
		}
		if (transportWorkDTO.getUserDetail() == null || transportWorkDTO.getUserDetail().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}
		if (transportWorkDTO.getWorkScope() == null || transportWorkDTO.getWorkScope().isBlank()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid scope of work.");
		}
		if (transportWorkDTO.getWorkorderId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");
		}
		if (transportWorkDTO.getTransportWorkId() == null && (transportWorkDTO.getTransportWorkItems() == null
				|| transportWorkDTO.getTransportWorkItems().size() < 1)) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid transport work items.");
		}
		if (transportWorkDTO.getTransportWorkId() == null && !(transportWorkDTO.getTransportWorkItems() == null
				|| transportWorkDTO.getTransportWorkItems().size() < 1)) {
			int index = 0;
			for (WorkorderTransportationWorkItemAddUpdateRequest workItem : transportWorkDTO.getTransportWorkItems()) {

				if (workItem.getDescription() == null || workItem.getDescription().isBlank()) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item description for item at index " + index);
				}
				if (workItem.getQuantity() == null || workItem.getQuantity() < 0.0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item quantity for item at index " + index);
				}
				if (workItem.getRate() == null || workItem.getRate() < 0.0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item rate for item at index " + index);
				}
				if (workItem.getUnitId() == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item unitId for item at index " + index);
				}
				index++;
			}
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateGetWorkorderTransportWork(WorkorderTransportationWorkGetRequestDTO transportWorkDTO) {

		if (transportWorkDTO.getWorkorderId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");
		}
		if (transportWorkDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid siteId.");
		}
		if (transportWorkDTO.getUserDetail() == null || transportWorkDTO.getUserDetail().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateUpdateWorkorderTransportWorkItem(
			WorkorderTransportationWorkItemAddUpdateRequest workItem) {

		if (workItem.getTransportWorkId() == null && workItem.getTransportWorkItemId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					"Provide valid transportWorkId or transportWorkItemId.");
		}
		if (workItem.getDescription() == null || workItem.getDescription().isBlank()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid item description.");
		}
		if (workItem.getQuantity() == null || workItem.getQuantity() < 0.0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid item quantity.");
		}
		if (workItem.getRate() == null || workItem.getRate() < 0.0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid item rate.");
		}
		if (workItem.getUnitId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid item unitId.");
		}
		if (workItem.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid siteId.");
		}
		if (workItem.getUserDetail() == null || workItem.getUserDetail().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateDeactivateWorkorderTransportWorkItem(
			WorkorderTransportationWorkItemDeactivateRequestDTO transportWorkItemDTO) {
		if (transportWorkItemDTO.getTransportWorkItemId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid transportWorkItemId.");
		}
		if (transportWorkItemDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid siteId.");
		}
		if (transportWorkItemDTO.getUserDetail() == null || transportWorkItemDTO.getUserDetail().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateGetWorkorderWorkorderPayMilestones(WorkorderPayMilestoneGetRequest payTermReq) {

		if (payTermReq.getWorkorderId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");
		}
		if (payTermReq.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid siteId.");
		}
		if (payTermReq.getTokenDetails() == null || payTermReq.getTokenDetails().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateDeactivateWorkorderWorkorderPayMilestone(
			WorkorderPayMilestoneDeactivateRequest payTermReq) {

		if (payTermReq.getWorkorderPayMilestoneId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderPayMilestoneId.");
		}
		if (payTermReq.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid siteId.");
		}
		if (payTermReq.getTokenDetails() == null || payTermReq.getTokenDetails().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddUpdateWorkorderWorkorderPayMilestone(
			WorkorderPayMilestoneAddUpdateRequest payTermReq) {

		if (payTermReq.getDescription() == null || payTermReq.getDescription().isBlank()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid description.");
		}
		if (payTermReq.getIsPercentage() == null || payTermReq.getValue() == null || payTermReq.getValue() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid valuation.");
		}
		if (payTermReq.getIsPercentage() != null && payTermReq.getIsPercentage() && payTermReq.getValue() > 100) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid valuation.");
		}
		if (payTermReq.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid siteId.");
		}
		if (payTermReq.getTokenDetails() == null || payTermReq.getTokenDetails().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateDeactivateWorkorderLabourWorkItem(
			WorkorderLabourWorkItemDeactivateRequest labourWorkItemDTO) {

		if (labourWorkItemDTO.getLabourWorkItemId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid labourWorkItemId.");
		}
		if (labourWorkItemDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid siteId.");
		}
		if (labourWorkItemDTO.getUserDetail() == null || labourWorkItemDTO.getUserDetail().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddOrUpdateWorkorderLabourWork(WorkorderLabourWorkAddUpdateRequestDTO labourWorkDTO) {

		if (labourWorkDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid site.");
		}
		if (labourWorkDTO.getUserDetail() == null || labourWorkDTO.getUserDetail().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}
		if (labourWorkDTO.getWorkScope() == null || labourWorkDTO.getWorkScope().isBlank()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid scope of work.");
		}
		if (labourWorkDTO.getWorkorderId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");
		}
		if (labourWorkDTO.getLabourWorkId() == null
				&& (labourWorkDTO.getLabourWorkItems() == null || labourWorkDTO.getLabourWorkItems().size() < 1)) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid labour work items.");
		}
		if (labourWorkDTO.getLabourWorkId() == null
				&& !(labourWorkDTO.getLabourWorkItems() == null || labourWorkDTO.getLabourWorkItems().size() < 1)) {
			int index = 0;
			for (WorkorderLabourWorkItemAddUpdateRequest workItem : labourWorkDTO.getLabourWorkItems()) {

				if (workItem.getDescription() == null || workItem.getDescription().isBlank()) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item description for item at index " + index);
				}
				if (workItem.getLabourTypeId() == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item labourTypeId for item at index " + index);
				}
				if (workItem.getRate() == null || workItem.getRate() < 0.0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item rate for item at index " + index);
				}
				if (workItem.getFullDurationThreshold() == null || workItem.getFullDurationThreshold() < 0.0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item fullDurationThreshold for item at index " + index);
				}
				if (workItem.getHalfDurationThreshold() == null || workItem.getHalfDurationThreshold() < 0.0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item halfDurationThreshold for item at index " + index);
				}
				if (workItem.getUnitId() == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Provide valid item unitId for item at index " + index);
				}
				index++;
			}
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateUpdateWorkorderLabourWorkItem(WorkorderLabourWorkItemAddUpdateRequest workItem) {

		if (workItem.getLabourWorkId() == null && workItem.getLabourWorkItemId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					"Provide valid labourWorkId or labourWorkItemId.");
		}
		if (workItem.getDescription() == null || workItem.getDescription().isBlank()) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid item description.");
		}
		if (workItem.getLabourTypeId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid item labourTypeId.");
		}
		if (workItem.getRate() == null || workItem.getRate() < 0.0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid item rate.");
		}
		if (workItem.getFullDurationThreshold() == null || workItem.getFullDurationThreshold() < 0.0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					"Provide valid item fullDurationThreshold.");
		}
		if (workItem.getHalfDurationThreshold() == null || workItem.getHalfDurationThreshold() < 0.0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
					"Provide valid item halfDurationThreshold.");
		}
		if (workItem.getUnitId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid item unitId.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateGetWorkorderLabourWork(WorkorderLabourWorkGetRequestDTO labourWorkDTO) {

		if (labourWorkDTO.getWorkorderId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");
		}
		if (labourWorkDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid siteId.");
		}
		if (labourWorkDTO.getUserDetail() == null || labourWorkDTO.getUserDetail().getId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid user.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateGetWorkorderCloseRequest(WorkorderCloseGetRequest clientRequestDTO) {

		if (clientRequestDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid site.");
		}
		if (clientRequestDTO.getPageNo() == null || clientRequestDTO.getPageNo() < 1) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid pageNo.");
		}
		if (clientRequestDTO.getPageSize() == null || clientRequestDTO.getPageSize() < 1) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid pageSize.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddOrUpdateWorkorderClose(WorkorderCloseAddUpdateRequest clientRequestDTO) {

		if (clientRequestDTO.getWorkorderId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");
		}
		if (clientRequestDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid site.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateDeactivateWorkorderClose(WorkorderCloseDeactivateRequest clientRequestDTO) {

		if (clientRequestDTO.getWorkorderCloseId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderCloseId.");
		}
		if (clientRequestDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid site.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateUpdateWorkorderCloseState(WorkorderCloseUpdateStateRequest clientRequestDTO) {

		if (clientRequestDTO.getWorkorderCloseId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderCloseId.");
		}
		if (clientRequestDTO.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid site.");
		}
		if (clientRequestDTO.getStateId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid state.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse workorderBillInfoUpdateRequest(WorkorderBillInfoUpdateRequest requestDTO) {

		if (requestDTO.getId() == null || requestDTO.getId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorder Id.");
		}
		if (requestDTO.getSystemBillStartDate() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide System Bill Start Date.");
		}
		if (requestDTO.getPreviousBillAmount() == null || requestDTO.getPreviousBillAmount() < 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide Previous Bill Amount.");
		}
		if (requestDTO.getPreviousBillNo() == null || requestDTO.getPreviousBillNo() < 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide Previous Bill No.");
		}
		if (requestDTO.getSiteId() == null || requestDTO.getSiteId() <= 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid site.");
		}
		if (requestDTO.getApplicableIgst() == null || requestDTO.getApplicableIgst() < 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide Applicable Igst.");
		}
		if (requestDTO.getIsIgstOnly() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide Is Igst Only.");
		}
		if (requestDTO.getTotal() == null || requestDTO.getTotal() < 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide total");
		}
		if (requestDTO.getTotalDeduction() == null || requestDTO.getTotalDeduction() < 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide Total Deduction.");
		}
		if (requestDTO.getPayableAmount() == null || requestDTO.getPayableAmount() < 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide Payable Amount.");
		}

		if (requestDTO.getHireMachineRequest() != null && !requestDTO.getHireMachineRequest().isEmpty()) {
			Map<Long, List<WorkoderHireMachineRequest>> collect = requestDTO.getHireMachineRequest().stream()
					.collect(Collectors.groupingBy(o -> o.getMachineId()));
			for (WorkoderHireMachineRequest obj : requestDTO.getHireMachineRequest())
				if (collect.get(obj.getMachineId()).size() > 1) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Please select different machines.");
				}
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

}