package erp.workorder.dto;

import java.util.Date;

public class HighwayMeasurementImportDTO {

	private Date date;

	private String code;

	private String standardBookCode;

	private String description;

	private String fromChainage;

	private String toChainage;

	private String chainageSide;

	private Double quantity;

	private String remark;

	private String workorderNo;

	public HighwayMeasurementImportDTO() {
		super();
	}

	public HighwayMeasurementImportDTO(Date date, String code, String standardBookCode, String description,
			String fromChainage, String toChainage, String chainageSide, Double quantity, String remark,
			String workorderNo) {
		super();
		this.date = date;
		this.code = code;
		this.standardBookCode = standardBookCode;
		this.description = description;
		this.fromChainage = fromChainage;
		this.toChainage = toChainage;
		this.chainageSide = chainageSide;
		this.quantity = quantity;
		this.remark = remark;
		this.workorderNo = workorderNo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStandardBookCode() {
		return standardBookCode;
	}

	public void setStandardBookCode(String standardBookCode) {
		this.standardBookCode = standardBookCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFromChainage() {
		return fromChainage;
	}

	public void setFromChainage(String fromChainage) {
		this.fromChainage = fromChainage;
	}

	public String getToChainage() {
		return toChainage;
	}

	public void setToChainage(String toChainage) {
		this.toChainage = toChainage;
	}

	public String getChainageSide() {
		return chainageSide;
	}

	public void setChainageSide(String chainageSide) {
		this.chainageSide = chainageSide;
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

	public String getWorkorderNo() {
		return workorderNo;
	}

	public void setWorkorderNo(String workorderNo) {
		this.workorderNo = workorderNo;
	}

}
