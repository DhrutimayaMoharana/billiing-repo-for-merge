package erp.billing.util;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import erp.billing.dto.BillDTO;
import erp.billing.dto.BillDeductionMappingDTO;
import erp.billing.dto.CustomResponse;
import erp.billing.dto.SearchDTO;
import erp.billing.dto.UnitDTO;
import erp.billing.dto.UserDetail;
import erp.billing.dto.request.BillMachineMapRequest;
import erp.billing.dto.request.ClientInvoiceAddUpdateRequest;
import erp.billing.dto.request.ClientInvoiceProductAddRequest;
import erp.billing.dto.request.ClientIrnCancelRequest;
import erp.billing.dto.request.WorkorderLabourAddUpdateRequest;
import erp.billing.dto.request.WorkorderLabourDepartmentAddUpdateRequest;
import erp.billing.dto.request.WorkorderLabourDesignationAddUpdateRequest;
import erp.billing.dto.request.WorkorderLabourTypeAddUpdateRequest;
import erp.billing.entity.BillBoqQuantityItem;
import erp.billing.entity.WorkorderBoqWorkQtyMapping;
import erp.billing.entity.WorkorderHiringMachineWorkItemMapping;
import erp.billing.enums.Responses;

@Component
public class CustomValidationUtil {

	public CustomResponse validateUserDetail(UserDetail userDetail) {
		if (userDetail == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide User Detail.");

		if (userDetail.getId() == null || userDetail.getId() <= 0)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide User Id.");

		if (userDetail.getRoleId() == null || userDetail.getRoleId() <= 0)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide User Role Id.");

		if (userDetail.getCompanyId() == null || userDetail.getCompanyId() <= 0)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide Company Id.");

		return new CustomResponse(Responses.SUCCESS.getCode(), true, Responses.SUCCESS.toString());

	}

	public CustomResponse validateAddHighwayBillObject(BillDTO obj, List<WorkorderBoqWorkQtyMapping> woBoqs,
			List<BillBoqQuantityItem> qtyItems) {

		if (obj.getType() == null || obj.getType().getId() == null || obj.getBoq() == null || obj.getFromDate() == null
				|| obj.getToDate() == null || obj.getSiteId() == null || obj.getCreatedBy() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		} else {
			if (obj.getBoq().getVendorDescription() == null || obj.getBoq().getBoq() == null
					|| obj.getBoq().getBoq().getId() == null || obj.getBoq().getQtyItem() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						Responses.BAD_REQUEST.toString() + " in BOQ");
			} else {
				if (obj.getBoq().getQtyItem().getFromChainage() == null
						|| obj.getBoq().getQtyItem().getFromChainage().getId() == null
						|| obj.getBoq().getQtyItem().getToChainage() == null
						|| obj.getBoq().getQtyItem().getFromChainage().getId() == null
						|| obj.getBoq().getQtyItem().getToChainage().getId() == null
						|| obj.getBoq().getQtyItem().getQuantity() == null
						|| obj.getBoq().getQtyItem().getQuantity().doubleValue() == 0.0
						|| obj.getBoq().getQtyItem().getRate() == null
						|| obj.getBoq().getQtyItem().getRate().doubleValue() <= 0.0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							Responses.BAD_REQUEST.toString() + " in BOQ");
				}
			}
		}
		if (obj.getToDate().before(obj.getFromDate())) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "To date must be greater than From date.");
		}
		if (woBoqs != null) {
			boolean presentInWoBoq = false;
			for (WorkorderBoqWorkQtyMapping woBoq : woBoqs) {
//				Double totalQuantity = woBoq.getQuantity();
				Double billedQuantity = 0.0;
				if (woBoq.getBoq().getId().equals(obj.getBoq().getBoq().getId())
						&& woBoq.getVendorDescription().equals(obj.getBoq().getVendorDescription())) {
					presentInWoBoq = true;
					obj.getBoq().setUnit(new UnitDTO(woBoq.getUnit().getId()));
					if (qtyItems != null) {
						for (BillBoqQuantityItem qtyItem : qtyItems) {
							if (obj.getBoq().getQtyItem().getId() != null
									&& qtyItem.getId().equals(obj.getBoq().getQtyItem().getId())) {
								continue;
							}
							if (qtyItem.getBillBoqItem().getBoq().getId().equals(woBoq.getBoqId()) && woBoq
									.getVendorDescription().equals(qtyItem.getBillBoqItem().getVendorDescription())) {
								billedQuantity += qtyItem.getQuantity();
							}
						}
						if (billedQuantity + obj.getBoq().getQtyItem().getQuantity() < 0.0)
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Cumulative BOQ quantities cannot be lesser than zero.");
//						if (totalQuantity - billedQuantity - obj.getBoq().getQtyItem().getQuantity() < 0.0)
//							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//									"Cumulative BOQ bill quantities cannot be greater than workorder boq quantity.");
					}
					break;
				}
			}
			if (!presentInWoBoq) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"This BOQ is not present in workorder work.");
			}
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddStructureBillObject(BillDTO obj, List<WorkorderBoqWorkQtyMapping> woBoqs,
			List<BillBoqQuantityItem> qtyItems) {

		if (obj.getType() == null || obj.getType().getId() == null || obj.getBoq() == null || obj.getFromDate() == null
				|| obj.getToDate() == null || obj.getSiteId() == null || obj.getCreatedBy() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		} else {
			if (obj.getBoq().getStructureTypeId() == null || obj.getBoq().getVendorDescription() == null
					|| obj.getBoq().getBoq() == null || obj.getBoq().getBoq().getId() == null
					|| obj.getBoq().getQtyItem() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						Responses.BAD_REQUEST.toString() + " in BOQ");
			} else {
				if (obj.getBoq().getQtyItem().getFromChainage() == null
						|| obj.getBoq().getQtyItem().getFromChainage().getId() == null
						|| obj.getBoq().getQtyItem().getToChainage() == null
						|| obj.getBoq().getQtyItem().getFromChainage().getId() == null
						|| obj.getBoq().getQtyItem().getToChainage().getId() == null
						|| obj.getBoq().getQtyItem().getQuantity() == null
						|| obj.getBoq().getQtyItem().getQuantity().doubleValue() == 0.0
						|| obj.getBoq().getQtyItem().getRate() == null
						|| obj.getBoq().getQtyItem().getRate().doubleValue() <= 0.0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							Responses.BAD_REQUEST.toString() + " in BOQ");
				}
			}
		}
		if (obj.getToDate().before(obj.getFromDate())) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "To date must be greater than From date.");
		}
		if (woBoqs != null) {
			boolean presentInWoBoq = false;
			for (WorkorderBoqWorkQtyMapping woBoq : woBoqs) {
//				Double totalQuantity = woBoq.getQuantity();
				Double billedQuantity = 0.0;
				if (woBoq.getStructureTypeId().equals(obj.getBoq().getStructureTypeId())
						&& woBoq.getBoq().getId().equals(obj.getBoq().getBoq().getId())
						&& woBoq.getVendorDescription().equals(obj.getBoq().getVendorDescription())) {
					presentInWoBoq = true;
					obj.getBoq().setUnit(new UnitDTO(woBoq.getUnit().getId()));
					if (qtyItems != null) {
						for (BillBoqQuantityItem qtyItem : qtyItems) {
							if (obj.getBoq().getQtyItem().getId() != null
									&& qtyItem.getId().equals(obj.getBoq().getQtyItem().getId())) {
								continue;
							}
							if (qtyItem.getBillBoqItem().getBoq().getId().equals(woBoq.getBoqId()) && woBoq
									.getVendorDescription().equals(qtyItem.getBillBoqItem().getVendorDescription())) {
								billedQuantity += qtyItem.getQuantity();
							}
						}
						if (billedQuantity + obj.getBoq().getQtyItem().getQuantity() < 0.0)
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Cumulative BOQ quantities cannot be lesser than zero.");
//						if (totalQuantity - billedQuantity - obj.getBoq().getQtyItem().getQuantity() < 0.0)
//							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//									"Cumulative BOQ bill quantities cannot be greater than workorder boq quantity.");
					}
					break;
				}
			}
			if (!presentInWoBoq) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"This BOQ is not present in workorder work.");
			}
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateUpdateHighwayBillObject(BillDTO obj, List<WorkorderBoqWorkQtyMapping> woBoqs,
			List<BillBoqQuantityItem> qtyItems) {

		if (obj.getId() == null || obj.getType() == null || obj.getType().getId() == null || obj.getBoq() == null
				|| obj.getFromDate() == null || obj.getToDate() == null || obj.getSiteId() == null
				|| obj.getCreatedBy() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		} else {
			if (obj.getBoq().getVendorDescription() == null || obj.getBoq().getBoq() == null
					|| obj.getBoq().getBoq().getId() == null || obj.getBoq().getQtyItem() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						Responses.BAD_REQUEST.toString() + " in BOQ");
			} else {
				if (obj.getBoq().getQtyItem().getFromChainage() == null
						|| obj.getBoq().getQtyItem().getFromChainage().getId() == null
						|| obj.getBoq().getQtyItem().getToChainage() == null
						|| obj.getBoq().getQtyItem().getFromChainage().getId() == null
						|| obj.getBoq().getQtyItem().getToChainage().getId() == null
						|| obj.getBoq().getQtyItem().getQuantity() == null
						|| obj.getBoq().getQtyItem().getQuantity().doubleValue() == 0.0
						|| obj.getBoq().getQtyItem().getRate() == null
						|| obj.getBoq().getQtyItem().getRate().doubleValue() <= 0.0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							Responses.BAD_REQUEST.toString() + " in BOQ");
				}
			}
		}
		if (obj.getToDate().before(obj.getFromDate())) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "To date must be greater than From date.");
		}
		if (woBoqs != null) {
			boolean presentInWoBoq = false;
			for (WorkorderBoqWorkQtyMapping woBoq : woBoqs) {
//				Double totalQuantity = woBoq.getQuantity();
				Double billedQuantity = 0.0;
				if (woBoq.getBoq().getId().equals(obj.getBoq().getBoq().getId())
						&& woBoq.getVendorDescription().equals(obj.getBoq().getVendorDescription())) {
					presentInWoBoq = true;
					obj.getBoq().setUnit(new UnitDTO(woBoq.getUnit().getId()));
					if (qtyItems != null) {
						for (BillBoqQuantityItem qtyItem : qtyItems) {
							if (obj.getBoq().getQtyItem().getId() != null
									&& qtyItem.getId().equals(obj.getBoq().getQtyItem().getId())) {
								continue;
							}
							if (qtyItem.getBillBoqItem().getBoq().getId().equals(woBoq.getBoqId()) && woBoq
									.getVendorDescription().equals(qtyItem.getBillBoqItem().getVendorDescription())) {
								billedQuantity += qtyItem.getQuantity();
							}
						}
						if (billedQuantity + obj.getBoq().getQtyItem().getQuantity() < 0.0)
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Cumulative BOQ quantities cannot be lesser than zero.");
//						if (totalQuantity - billedQuantity - obj.getBoq().getQtyItem().getQuantity() < 0.0)
//							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//									"Cumulative BOQ bill quantities cannot be greater than workorder boq quantity.");
					}
					break;
				}
			}
			if (!presentInWoBoq) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"This BOQ is not present in workorder work.");
			}
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateUpdateStructureBillObject(BillDTO obj, List<WorkorderBoqWorkQtyMapping> woBoqs,
			List<BillBoqQuantityItem> qtyItems) {

		if (obj.getId() == null || obj.getType() == null || obj.getType().getId() == null || obj.getBoq() == null
				|| obj.getFromDate() == null || obj.getToDate() == null || obj.getSiteId() == null
				|| obj.getCreatedBy() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		} else {
			if (obj.getBoq().getStructureTypeId() == null || obj.getBoq().getVendorDescription() == null
					|| obj.getBoq().getBoq() == null || obj.getBoq().getBoq().getId() == null
					|| obj.getBoq().getQtyItem() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						Responses.BAD_REQUEST.toString() + " in BOQ");
			} else {
				if (obj.getBoq().getQtyItem().getFromChainage() == null
						|| obj.getBoq().getQtyItem().getFromChainage().getId() == null
						|| obj.getBoq().getQtyItem().getToChainage() == null
						|| obj.getBoq().getQtyItem().getFromChainage().getId() == null
						|| obj.getBoq().getQtyItem().getToChainage().getId() == null
						|| obj.getBoq().getQtyItem().getQuantity() == null
						|| obj.getBoq().getQtyItem().getQuantity().doubleValue() == 0.0
						|| obj.getBoq().getQtyItem().getRate() == null
						|| obj.getBoq().getQtyItem().getRate().doubleValue() <= 0.0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							Responses.BAD_REQUEST.toString() + " in BOQ");
				}
			}
		}
		if (obj.getToDate().before(obj.getFromDate())) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "To date must be greater than From date.");
		}
		if (woBoqs != null) {
			boolean presentInWoBoq = false;
			for (WorkorderBoqWorkQtyMapping woBoq : woBoqs) {
//				Double totalQuantity = woBoq.getQuantity();
				Double billedQuantity = 0.0;
				if (woBoq.getStructureTypeId().equals(obj.getBoq().getStructureTypeId())
						&& woBoq.getBoq().getId().equals(obj.getBoq().getBoq().getId())
						&& woBoq.getVendorDescription().equals(obj.getBoq().getVendorDescription())) {
					presentInWoBoq = true;
					obj.getBoq().setUnit(new UnitDTO(woBoq.getUnit().getId()));
					if (qtyItems != null) {
						for (BillBoqQuantityItem qtyItem : qtyItems) {
							if (obj.getBoq().getQtyItem().getId() != null
									&& qtyItem.getId().equals(obj.getBoq().getQtyItem().getId())) {
								continue;
							}
							if (qtyItem.getBillBoqItem().getBoq().getId().equals(woBoq.getBoqId()) && woBoq
									.getVendorDescription().equals(qtyItem.getBillBoqItem().getVendorDescription())) {
								billedQuantity += qtyItem.getQuantity();
							}
						}
						if (billedQuantity + obj.getBoq().getQtyItem().getQuantity() < 0.0)
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Cumulative BOQ quantities cannot be lesser than zero.");
//						if (totalQuantity - billedQuantity - obj.getBoq().getQtyItem().getQuantity() < 0.0)
//							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//									"Cumulative BOQ bill quantities cannot be greater than workorder boq quantity.");
					}
					break;
				}
			}
			if (!presentInWoBoq) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"This BOQ is not present in workorder work.");
			}
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddOrUpdateBillDeductionMapObject(BillDeductionMappingDTO mapObj) {

//		boolean isValidate = true;
//		if (mapObj.getBillId() == null || mapObj.getDeduction() == null || mapObj.getDeduction().getName() == null
//				|| mapObj.getIsPercentage() == null || mapObj.getValue() == null)
//			isValidate = false;
//		return isValidate;

		if (mapObj.getBillId() == null || mapObj.getBillId() <= 0)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide Bill id.");

		if (mapObj.getDeduction() != null
				&& (mapObj.getDeduction().getName() == null || mapObj.getDeduction().getName().isEmpty()))
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide name.");

		if (mapObj.getIsPercentage() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide is Percentage.");

		if (mapObj.getSiteId() == null || mapObj.getSiteId() <= 0)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide  Site");

		if (mapObj.getValue() == null || mapObj.getValue() < 0)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide value.");

		return new CustomResponse(Responses.SUCCESS.getCode(), true, "OK.");

	}

	public CustomResponse validateGetFilesRequest(SearchDTO search) {

		if (search.getBillId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide billId.");
		}
		if (search.getFileTypeId() == null && (search.getGetAll() == null || !search.getGetAll())) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide fileTypeId.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddFilesRequest(SearchDTO search) {

		if (search.getBillId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide billId.");
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

		if (search.getBillId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide billId.");
		}
		if (search.getIdsArr() == null && search.getIdsArr().size() == 0) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide billFileMappingIds.");
		}

		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddHiringBillObject(BillDTO obj,
			List<WorkorderHiringMachineWorkItemMapping> machineItems) {

		obj.getHireMachinery().removeAll(Collections.singleton(null));

		if (obj.getType() == null || obj.getType().getId() == null || obj.getHireMachinery() == null
				|| obj.getFromDate() == null || obj.getToDate() == null || obj.getSiteId() == null
				|| obj.getCreatedBy() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		} else {
			if (machineItems != null) {
				for (WorkorderHiringMachineWorkItemMapping item : machineItems) {
					Long categoryId = item.getMachineCatgeoryId();
//					Integer machineCount = item.getMachineCount();
					HashMap<Date, Date> datesPair = new HashMap<>();
					Set<Long> distinctMachineIds = new HashSet<>();
//					int requestMachineCount = 0;
					for (BillMachineMapRequest requestItem : obj.getHireMachinery()) {

						if (!distinctMachineIds.contains(requestItem.getMachineId())) {
							distinctMachineIds.add(requestItem.getMachineId());
						} else {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Please select different machines.");
						}

						if (requestItem.getFromDate() != null && requestItem.getToDate() != null
								&& (DateUtil.dateWithoutTime(requestItem.getFromDate())
										.before(DateUtil.dateWithoutTime(obj.getFromDate()))
										|| DateUtil.dateWithoutTime(requestItem.getToDate())
												.after(DateUtil.dateWithoutTime(obj.getToDate())))) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Machine from-date and to-date must lie between bill date.");
						}

						if (DateUtil.dateWithoutTime(requestItem.getToDate())
								.before(DateUtil.dateWithoutTime(requestItem.getFromDate()))) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Machine to-date must be greater than from-date.");
						}

						if (requestItem.getMachineType() == null || requestItem.getMachineId() == null) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Provide valid machine info.");
						}

						if (requestItem.getMachineCategoryId().equals(categoryId)) {
//							requestMachineCount++;
							datesPair.put(requestItem.getFromDate(), requestItem.getToDate());
						}
					}

					if (distinctMachineIds.isEmpty()) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Please select atleast one machine.");
					}

					int maxMachineCollision = 0;
					for (Date fromDate : datesPair.keySet()) {
						Date toDate = datesPair.get(fromDate);
						int collisions = 0;
						for (Date childFromDate : datesPair.keySet()) {
							Date childToDate = datesPair.get(childFromDate);
							if (!DateUtil.dateWithoutTime(fromDate).after(DateUtil.dateWithoutTime(childToDate))
									&& !DateUtil.dateWithoutTime(childFromDate)
											.after(DateUtil.dateWithoutTime(toDate))) {
								collisions++;
							}
						}
						if (collisions > maxMachineCollision) {
							maxMachineCollision = collisions;
						}
					}
//					if (maxMachineCollision > machineCount) {
//						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//								"Machine count exceeds for same period of time.");
//					}
//					if (requestMachineCount < machineCount) {
//						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Mention all machines.");
//					}
				}
			}
		}
		if (obj.getToDate().before(obj.getFromDate())) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "To date must be greater than From date.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateUpdateHiringBillObject(BillDTO obj,
			List<WorkorderHiringMachineWorkItemMapping> machineItems) {

		if (obj.getId() == null || obj.getType() == null || obj.getType().getId() == null
				|| obj.getHireMachinery() == null || obj.getFromDate() == null || obj.getToDate() == null
				|| obj.getSiteId() == null || obj.getCreatedBy() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		} else {
			if (machineItems != null) {

				for (WorkorderHiringMachineWorkItemMapping item : machineItems) {
					Long categoryId = item.getMachineCatgeoryId();
//					Integer machineCount = item.getMachineCount();
					HashMap<Date, Date> datesPair = new HashMap<>();
					Set<Long> distinctMachineIds = new HashSet<>();
//					int requestMachineCount = 0;
					for (BillMachineMapRequest requestItem : obj.getHireMachinery()) {
						if (!distinctMachineIds.contains(requestItem.getMachineId())) {
							distinctMachineIds.add(requestItem.getMachineId());
						} else {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Please select different machines.");
						}

						if (requestItem.getFromDate() != null && requestItem.getToDate() != null
								&& (DateUtil.dateWithoutTime(requestItem.getFromDate())
										.before(DateUtil.dateWithoutTime(obj.getFromDate()))
										|| DateUtil.dateWithoutTime(requestItem.getToDate())
												.after(DateUtil.dateWithoutTime(obj.getToDate())))) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Machine from-date and to-date must be lie between bill date.");
						}

						if (DateUtil.dateWithoutTime(requestItem.getToDate())
								.before(DateUtil.dateWithoutTime(requestItem.getFromDate()))) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Machine to-date must be greater than from-date.");
						}

						if (requestItem.getMachineType() == null || requestItem.getMachineId() == null) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Provide valid machine info.");
						}

						if (requestItem.getMachineCategoryId().equals(categoryId)) {
//							requestMachineCount++;
							datesPair.put(requestItem.getFromDate(), requestItem.getToDate());
						}
					}

					int maxMachineCollision = 0;
					for (Date fromDate : datesPair.keySet()) {
						Date toDate = datesPair.get(fromDate);
						int collisions = 0;
						for (Date childFromDate : datesPair.keySet()) {
							Date childToDate = datesPair.get(childFromDate);
							if (!DateUtil.dateWithoutTime(fromDate).after(DateUtil.dateWithoutTime(childToDate))
									&& !DateUtil.dateWithoutTime(childFromDate)
											.after(DateUtil.dateWithoutTime(toDate))) {
								collisions++;
							}
						}
						if (collisions > maxMachineCollision) {
							maxMachineCollision = collisions;
						}
					}
//					if (maxMachineCollision > machineCount) {
//						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//								"Machine count exceeds for same period of time.");
//					}

//					if (requestMachineCount < machineCount) {
//						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Mention all machines.");
//					}
				}
			}
		}
		if (obj.getToDate().before(obj.getFromDate())) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "To date must be greater than From date.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse validateAddConsultancyBillObject(BillDTO obj) {

		if (obj.getType() == null || obj.getType().getId() == null || obj.getWorkorderPayMilestoneIds() == null
				|| obj.getWorkorderPayMilestoneIds().size() <= 0 || obj.getFromDate() == null || obj.getToDate() == null
				|| obj.getSiteId() == null || obj.getCreatedBy() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		}

		if (obj.getToDate().before(obj.getFromDate())) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "To date must be greater than From date.");
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
	}

	public CustomResponse addLabourDepartment(WorkorderLabourDepartmentAddUpdateRequest obj) {

		if (obj.getName() == null || obj.getName().isEmpty())
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide name.");

		if (obj.getCompanyId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide comapny id.");

		if (obj.getUserId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide user id.");

		return new CustomResponse(Responses.SUCCESS.getCode(), true, "OK.");
	}

	public CustomResponse updateLabourDepartment(WorkorderLabourDepartmentAddUpdateRequest obj) {

		if (obj.getId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide id.");

		if (obj.getName() == null || obj.getName().isEmpty())
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide name.");

		if (obj.getCompanyId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide comapny id.");

		if (obj.getUserId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide user id.");

		return new CustomResponse(Responses.SUCCESS.getCode(), true, "OK.");
	}

	public CustomResponse addLabourType(WorkorderLabourTypeAddUpdateRequest obj) {

		if (obj.getName() == null || obj.getName().isEmpty())
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide name.");

		if (obj.getCompanyId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide comapny id.");

		if (obj.getUserId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide user id.");

		return new CustomResponse(Responses.SUCCESS.getCode(), true, "OK.");
	}

	public CustomResponse updateLabourType(WorkorderLabourTypeAddUpdateRequest obj) {

		if (obj.getId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide id.");

		if (obj.getName() == null || obj.getName().isEmpty())
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide name.");

		if (obj.getCompanyId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide comapny id.");

		if (obj.getUserId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide user id.");

		return new CustomResponse(Responses.SUCCESS.getCode(), true, "OK.");
	}

	public CustomResponse addLabourDesignation(WorkorderLabourDesignationAddUpdateRequest obj) {

		if (obj.getName() == null || obj.getName().isEmpty())
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide name.");

		if (obj.getCompanyId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide comapny id.");

		if (obj.getUserId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide user id.");

		return new CustomResponse(Responses.SUCCESS.getCode(), true, "OK.");
	}

	public CustomResponse updateLabourDesignation(WorkorderLabourDesignationAddUpdateRequest obj) {

		if (obj.getId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide id.");

		if (obj.getName() == null || obj.getName().isEmpty())
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide name.");

		if (obj.getCompanyId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide comapny id.");

		if (obj.getUserId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide user id.");

		return new CustomResponse(Responses.SUCCESS.getCode(), true, "OK.");
	}

	public CustomResponse addWorkorderLabour(WorkorderLabourAddUpdateRequest obj) {

		if (obj.getCode() == null || obj.getCode().isEmpty())
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide code.");

		if (obj.getName() == null || obj.getName().isEmpty())
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide name.");

		if (obj.getSiteId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide site id.");

		if (obj.getTypeId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide type id.");

		if (obj.getUserId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide user id.");

		return new CustomResponse(Responses.SUCCESS.getCode(), true, "OK.");
	}

	public CustomResponse updateWorkorderLabour(WorkorderLabourAddUpdateRequest obj) {

		if (obj.getId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide id.");

		if (obj.getCode() == null || obj.getCode().isEmpty())
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide code.");

		if (obj.getName() == null || obj.getName().isEmpty())
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide name.");

		if (obj.getSiteId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide site id.");

		if (obj.getTypeId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide type id.");

		if (obj.getUserId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide user id.");

		return new CustomResponse(Responses.SUCCESS.getCode(), true, "OK.");
	}

	public CustomResponse addOrUpdateClientInvoice(ClientInvoiceAddUpdateRequest obj) {

		if (obj.getInvoiceDate() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide invoice date.");

		if (obj.getInvoiceType() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide Invoice Type");

		if (obj.getSupplyType() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide Supply Type");

		if (obj.getSiteId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide site.");

		if (obj.getStateId() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide stateId.");

		if (obj.getSupplyDate() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide supply date.");

		if (obj.getSupplyStateId() == null || obj.getSupplyStateId() <= 0)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide supply place.");

		if (obj.getReverseCharge() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide reverse charge.");

//		if (obj.getTransportMode() == null)
//			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide transport mode.");

//		if (obj.getVehicleNo() == null)
//			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide vehicle no.");

		if (obj.getProducts() == null || obj.getProducts().size() < 1)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide atleast one product.");
		else {
			for (ClientInvoiceProductAddRequest product : obj.getProducts()) {
				if (product.getGstManagementId() == null || product.getGstManagementId() <= 0)
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide product.");
				if (product.getIsIgst() == null)
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide isIgst.");
				if ((product.getIsIgst()
						&& (product.getApplicableCgstPercentage() > 0 || product.getApplicableSgstPercentage() > 0))
						|| (!product.getIsIgst() && (product.getApplicableIgstPercentage() > 0)))
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide correct GST.");
				if (product.getIsIgst()) {
					if (product.getApplicableIgstPercentage() == null || product.getApplicableIgstPercentage() < 0.0)
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide IGST Percentage.");
					if (product.getApplicableIgstAmount() == null || product.getApplicableIgstAmount() < 0.0)
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide IGST Amount.");
				} else {

					if (product.getApplicableSgstPercentage() == null || product.getApplicableSgstPercentage() < 0.0)
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide SGST Percentage.");
					if (product.getApplicableSgstAmount() == null || product.getApplicableSgstAmount() < 0.0)
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide SGST Amount.");

					if (product.getApplicableCgstPercentage() == null || product.getApplicableCgstPercentage() < 0.0)
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide CGST Percentage.");
					if (product.getApplicableCgstAmount() == null || product.getApplicableCgstAmount() < 0.0)
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide CGST Amount.");
				}

				if (product.getAmount() == null || product.getAmount() < 0.0)
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide amount.");
			}
		}
		if (obj.getBillingCityId() == null || obj.getBillingCityId() <= 0)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide Billing City Id");

		if (obj.getBillingZipCode() == null || obj.getBillingZipCode().isBlank())
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide Billing ZipCode");

		if (obj.getGstNo() == null || obj.getGstNo().isBlank())
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide GST no.");

		if (obj.getUpdatedBy() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide user id.");

		return new CustomResponse(Responses.SUCCESS.getCode(), true, "OK.");
	}

	public CustomResponse clientIrnCancel(ClientIrnCancelRequest obj) {

		if (obj.getCancelReason() == null)
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide Cancel Reason.");

		if (obj.getCancelRemark() == null || obj.getCancelRemark().isEmpty())
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, "Provide Cancel Remark.");

		return new CustomResponse(Responses.SUCCESS.getCode(), true, "OK.");
	}

}
