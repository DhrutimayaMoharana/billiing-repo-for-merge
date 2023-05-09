package erp.boq_mgmt.dto;

public class StructureBoqQtyImportDTO {

	private String standardBookCode;

	private String code;

	private String description;

	private String vendorDescription;

	private String unit;

	private Double rate;

	private Double maxRate;

	private Double quantity;

	private String remark;

	private Integer excelRowNo;

	public StructureBoqQtyImportDTO() {
		super();
	}

	public StructureBoqQtyImportDTO(String code, String standardBookCode, String description, String vendorDescription,
			String unit, Double rate, Double maxRate, Double quantity, String remark, Integer excelRowNo) {
		super();
		this.standardBookCode = standardBookCode;
		this.code = code;
		this.description = description;
		this.vendorDescription = vendorDescription;
		this.unit = unit;
		this.rate = rate;
		this.maxRate = maxRate;
		this.quantity = quantity;
		this.remark = remark;
		this.excelRowNo = excelRowNo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStandardBookCode() {
		return standardBookCode;
	}

	public void setStandardBookCode(String standardBookCode) {
		this.standardBookCode = standardBookCode;
	}

	public Double getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(Double maxRate) {
		this.maxRate = maxRate;
	}

	public String getVendorDescription() {
		return vendorDescription;
	}

	public void setVendorDescription(String vendorDescription) {
		this.vendorDescription = vendorDescription;
	}

	public Integer getExcelRowNo() {
		return excelRowNo;
	}

	public void setExcelRowNo(Integer excelRowNo) {
		this.excelRowNo = excelRowNo;
	}

}
