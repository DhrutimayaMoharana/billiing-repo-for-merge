package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;

public class BoqCostDefinitionLabourAddUpdateRequest {

	private Long id;

	private Long boqId;

	private Long boqCostDefinitionId;

	private Integer labourTypeId;

	private Integer unitId;

	private Double quantity;

	private Double rate;

	private Integer siteId;

	private UserDetail userDetail;

	public BoqCostDefinitionLabourAddUpdateRequest() {
		super();
	}

	public BoqCostDefinitionLabourAddUpdateRequest(Long id, Long boqId, Long boqCostDefinitionId, Integer labourTypeId,
			Integer unitId, Double quantity, Double rate, Integer siteId, UserDetail userDetail) {
		super();
		this.id = id;
		this.boqId = boqId;
		this.boqCostDefinitionId = boqCostDefinitionId;
		this.labourTypeId = labourTypeId;
		this.unitId = unitId;
		this.quantity = quantity;
		this.rate = rate;
		this.siteId = siteId;
		this.userDetail = userDetail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBoqId() {
		return boqId;
	}

	public void setBoqId(Long boqId) {
		this.boqId = boqId;
	}

	public Long getBoqCostDefinitionId() {
		return boqCostDefinitionId;
	}

	public void setBoqCostDefinitionId(Long boqCostDefinitionId) {
		this.boqCostDefinitionId = boqCostDefinitionId;
	}

	public Integer getLabourTypeId() {
		return labourTypeId;
	}

	public void setLabourTypeId(Integer labourTypeId) {
		this.labourTypeId = labourTypeId;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
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

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
