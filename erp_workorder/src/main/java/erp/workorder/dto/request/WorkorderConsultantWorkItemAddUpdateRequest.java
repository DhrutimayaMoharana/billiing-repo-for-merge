package erp.workorder.dto.request;

import erp.workorder.dto.UserDetail;

public class WorkorderConsultantWorkItemAddUpdateRequest {

	private Long consultantWorkId;

	private Long consultantWorkItemId;

	private String categoryDescription;

	private String description;

	private Long chainageId;

	private Double quantity;

	private Double rate;

	private Long unitId;

	private String remark;

	private Long siteId;

	private UserDetail userDetail;

	public WorkorderConsultantWorkItemAddUpdateRequest() {
		super();
	}

	public WorkorderConsultantWorkItemAddUpdateRequest(Long consultantWorkId, Long consultantWorkItemId,
			String categoryDescription, String description, Long chainageId, Double quantity, Double rate, Long unitId,
			String remark) {
		super();
		this.consultantWorkId = consultantWorkId;
		this.consultantWorkItemId = consultantWorkItemId;
		this.categoryDescription = categoryDescription;
		this.description = description;
		this.chainageId = chainageId;
		this.quantity = quantity;
		this.rate = rate;
		this.unitId = unitId;
		this.remark = remark;
	}

	public Long getConsultantWorkItemId() {
		return consultantWorkItemId;
	}

	public void setConsultantWorkItemId(Long consultantWorkItemId) {
		this.consultantWorkItemId = consultantWorkItemId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getChainageId() {
		return chainageId;
	}

	public void setChainageId(Long chainageId) {
		this.chainageId = chainageId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Long getConsultantWorkId() {
		return consultantWorkId;
	}

	public void setConsultantWorkId(Long consultantWorkId) {
		this.consultantWorkId = consultantWorkId;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

}
