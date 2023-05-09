package erp.billing.dto.response;

import java.util.List;

import erp.billing.dto.BoqItemDTO;

public class BillItemResponseDTO {

	private Long id;

	private Long billId;

	private Long structureTypeId;

	private String structureTypeName;

	private BoqItemDTO boq;

	private IdNameResponseDTO unit;

	private String vendorDescription;

	private Double totalQuantity;

	private Double billedQuantity;

	private Double balanceQuantity;

	private Double currentBillQuantity;

	private Double rate;

	private String remark;

	List<ChainageStretchBillItemResponseDTO> subItems;

	private String boqSubcatMergeDescription;

	public BillItemResponseDTO() {
		super();
	}

	public BillItemResponseDTO(Long id, Long billId, Long structureTypeId, String structureTypeName, BoqItemDTO boq,
			IdNameResponseDTO unit, String vendorDescription, String boqSubcatMergeDescription, Double totalQuantity,
			Double billedQuantity, Double balanceQuantity, Double currentBillQuantity, Double rate, String remark,
			List<ChainageStretchBillItemResponseDTO> subItems) {
		super();
		this.id = id;
		this.billId = billId;
		this.structureTypeId = structureTypeId;
		this.structureTypeName = structureTypeName;
		this.boq = boq;
		this.unit = unit;
		this.vendorDescription = vendorDescription;
		this.totalQuantity = totalQuantity;
		this.billedQuantity = billedQuantity;
		this.balanceQuantity = balanceQuantity;
		this.currentBillQuantity = currentBillQuantity;
		this.rate = rate;
		this.remark = remark;
		this.boqSubcatMergeDescription = boqSubcatMergeDescription;
		this.subItems = subItems;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public BoqItemDTO getBoq() {
		return boq;
	}

	public void setBoq(BoqItemDTO boq) {
		this.boq = boq;
	}

	public Double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Double getBilledQuantity() {
		return billedQuantity;
	}

	public void setBilledQuantity(Double billedQuantity) {
		this.billedQuantity = billedQuantity;
	}

	public Double getBalanceQuantity() {
		return balanceQuantity;
	}

	public void setBalanceQuantity(Double balanceQuantity) {
		this.balanceQuantity = balanceQuantity;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public List<ChainageStretchBillItemResponseDTO> getSubItems() {
		return subItems;
	}

	public void setSubItems(List<ChainageStretchBillItemResponseDTO> subItems) {
		this.subItems = subItems;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getCurrentBillQuantity() {
		return currentBillQuantity;
	}

	public void setCurrentBillQuantity(Double currentBillQuantity) {
		this.currentBillQuantity = currentBillQuantity;
	}

	public String getVendorDescription() {
		return vendorDescription;
	}

	public void setVendorDescription(String vendorDescription) {
		this.vendorDescription = vendorDescription;
	}

	public String getBoqSubcatMergeDescription() {
		return boqSubcatMergeDescription;
	}

	public void setBoqSubcatMergeDescription(String boqSubcatMergeDescription) {
		this.boqSubcatMergeDescription = boqSubcatMergeDescription;
	}

	public IdNameResponseDTO getUnit() {
		return unit;
	}

	public void setUnit(IdNameResponseDTO unit) {
		this.unit = unit;
	}

	public Long getStructureTypeId() {
		return structureTypeId;
	}

	public void setStructureTypeId(Long structureTypeId) {
		this.structureTypeId = structureTypeId;
	}

	public String getStructureTypeName() {
		return structureTypeName;
	}

	public void setStructureTypeName(String structureTypeName) {
		this.structureTypeName = structureTypeName;
	}

}
