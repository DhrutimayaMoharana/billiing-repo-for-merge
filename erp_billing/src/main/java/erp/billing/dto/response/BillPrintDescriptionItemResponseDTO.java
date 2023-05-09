package erp.billing.dto.response;

public class BillPrintDescriptionItemResponseDTO {

	private String srNo;

	private String description;
	
	private Double appliedPercentage;

	private Double uptoDateIpcAmount;

	private Double uptoPreviousIpcAmount;

	private Double ipcAmount;

	private Boolean isHeading;
	
	private Boolean needColspan;

	public BillPrintDescriptionItemResponseDTO() {
		super();
	}

	public BillPrintDescriptionItemResponseDTO(String srNo, String description, Double appliedPercentage,
			Double uptoDateIpcAmount, Double uptoPreviousIpcAmount, Double ipcAmount, Boolean isHeading,
			Boolean needColspan) {
		super();
		this.srNo = srNo;
		this.description = description;
		this.appliedPercentage = appliedPercentage;
		this.uptoDateIpcAmount = uptoDateIpcAmount;
		this.uptoPreviousIpcAmount = uptoPreviousIpcAmount;
		this.ipcAmount = ipcAmount;
		this.isHeading = isHeading;
		this.needColspan = needColspan;
	}

	public String getSrNo() {
		return srNo;
	}

	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getUptoDateIpcAmount() {
		return uptoDateIpcAmount;
	}

	public void setUptoDateIpcAmount(Double uptoDateIpcAmount) {
		this.uptoDateIpcAmount = uptoDateIpcAmount;
	}

	public Double getUptoPreviousIpcAmount() {
		return uptoPreviousIpcAmount;
	}

	public void setUptoPreviousIpcAmount(Double uptoPreviousIpcAmount) {
		this.uptoPreviousIpcAmount = uptoPreviousIpcAmount;
	}

	public Double getIpcAmount() {
		return ipcAmount;
	}

	public void setIpcAmount(Double ipcAmount) {
		this.ipcAmount = ipcAmount;
	}

	public Boolean getIsHeading() {
		return isHeading;
	}

	public void setIsHeading(Boolean isHeading) {
		this.isHeading = isHeading;
	}

	public Double getAppliedPercentage() {
		return appliedPercentage;
	}

	public void setAppliedPercentage(Double appliedPercentage) {
		this.appliedPercentage = appliedPercentage;
	}

	public Boolean getNeedColspan() {
		return needColspan;
	}

	public void setNeedColspan(Boolean needColspan) {
		this.needColspan = needColspan;
	}

}
