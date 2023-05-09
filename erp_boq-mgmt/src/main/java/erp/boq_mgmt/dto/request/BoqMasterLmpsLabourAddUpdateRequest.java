package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;

public class BoqMasterLmpsLabourAddUpdateRequest {

	private Long id;

	private Long boqId;

	private Long boqMasterLmpsId;

	private Integer labourTypeId;

	private Integer unitId;

	private Double quantity;

	private UserDetail userDetail;

	public BoqMasterLmpsLabourAddUpdateRequest() {
		super();
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

	public Long getBoqMasterLmpsId() {
		return boqMasterLmpsId;
	}

	public void setBoqMasterLmpsId(Long boqMasterLmpsId) {
		this.boqMasterLmpsId = boqMasterLmpsId;
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

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
