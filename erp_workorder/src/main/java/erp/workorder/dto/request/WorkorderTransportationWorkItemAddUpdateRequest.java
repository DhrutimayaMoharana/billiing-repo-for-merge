package erp.workorder.dto.request;

import erp.workorder.dto.UserDetail;

public class WorkorderTransportationWorkItemAddUpdateRequest {

	private Long transportWorkId;

	private Long transportWorkItemId;

	private Long boqId;

	private String description;

	private Double quantity;

	private Double rate;

	private Long unitId;

	private String remark;

	private Long siteId;

	private UserDetail userDetail;

	public WorkorderTransportationWorkItemAddUpdateRequest() {
		super();
	}

	public Long getTransportWorkId() {
		return transportWorkId;
	}

	public void setTransportWorkId(Long transportWorkId) {
		this.transportWorkId = transportWorkId;
	}

	public Long getTransportWorkItemId() {
		return transportWorkItemId;
	}

	public void setTransportWorkItemId(Long transportWorkItemId) {
		this.transportWorkItemId = transportWorkItemId;
	}

	public Long getBoqId() {
		return boqId;
	}

	public void setBoqId(Long boqId) {
		this.boqId = boqId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
