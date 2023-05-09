package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;
import erp.boq_mgmt.enums.MachineryRangeType;
import erp.boq_mgmt.enums.MachineryTrip;

public class BoqMasterLmpsMachineryAddUpdateRequest {

	private Long id;

	private Long boqId;

	private Long boqMasterLmpsId;

	private Long machineryCategoryId;

	private Integer siteVariableId;

	private MachineryTrip machineryTrip;

	private Integer unitId;

	private Integer afterUnitId;

	private MachineryRangeType rangeType;

	private Integer rangeUnitId;

	private Double rangeQuantity;

	private UserDetail userDetail;

	public BoqMasterLmpsMachineryAddUpdateRequest() {
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

	public Long getMachineryCategoryId() {
		return machineryCategoryId;
	}

	public void setMachineryCategoryId(Long machineryCategoryId) {
		this.machineryCategoryId = machineryCategoryId;
	}

	public Integer getSiteVariableId() {
		return siteVariableId;
	}

	public void setSiteVariableId(Integer siteVariableId) {
		this.siteVariableId = siteVariableId;
	}

	public MachineryTrip getMachineryTrip() {
		return machineryTrip;
	}

	public void setMachineryTrip(MachineryTrip machineryTrip) {
		this.machineryTrip = machineryTrip;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Integer getAfterUnitId() {
		return afterUnitId;
	}

	public void setAfterUnitId(Integer afterUnitId) {
		this.afterUnitId = afterUnitId;
	}

	public MachineryRangeType getRangeType() {
		return rangeType;
	}

	public void setRangeType(MachineryRangeType rangeType) {
		this.rangeType = rangeType;
	}

	public Integer getRangeUnitId() {
		return rangeUnitId;
	}

	public void setRangeUnitId(Integer rangeUnitId) {
		this.rangeUnitId = rangeUnitId;
	}

	public Double getRangeQuantity() {
		return rangeQuantity;
	}

	public void setRangeQuantity(Double rangeQuantity) {
		this.rangeQuantity = rangeQuantity;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
