package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;
import erp.boq_mgmt.enums.MachineryRangeType;
import erp.boq_mgmt.enums.MachineryTrip;

public class BoqCostDefinitionMachineryAddUpdateRequest {

	private Long id;

	private Long boqId;

	private Long boqCostDefinitionId;

	private Long machineryCategoryId;

	private Integer leadSiteVariableId;

	private MachineryTrip machineryTrip;

	private Integer unitId;

	private Double rate;

	private Integer afterUnitId;

	private Double afterRate;

	private MachineryRangeType rangeType;

	private Integer rangeUnitId;

	private Double rangeQuantity;

	private Integer siteId;

	private UserDetail userDetail;

	public BoqCostDefinitionMachineryAddUpdateRequest() {
		super();
	}

	public BoqCostDefinitionMachineryAddUpdateRequest(Long id, Long boqId, Long boqCostDefinitionId,
			Long machineryCategoryId, Integer leadSiteVariableId, MachineryTrip machineryTrip, Integer unitId,
			Double rate, Integer afterUnitId, Double afterRate, MachineryRangeType rangeType, Integer rangeUnitId,
			Double rangeQuantity, Integer siteId, UserDetail userDetail) {
		super();
		this.id = id;
		this.boqId = boqId;
		this.boqCostDefinitionId = boqCostDefinitionId;
		this.machineryCategoryId = machineryCategoryId;
		this.leadSiteVariableId = leadSiteVariableId;
		this.machineryTrip = machineryTrip;
		this.unitId = unitId;
		this.rate = rate;
		this.afterUnitId = afterUnitId;
		this.afterRate = afterRate;
		this.rangeType = rangeType;
		this.rangeUnitId = rangeUnitId;
		this.rangeQuantity = rangeQuantity;
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

	public Long getMachineryCategoryId() {
		return machineryCategoryId;
	}

	public void setMachineryCategoryId(Long machineryCategoryId) {
		this.machineryCategoryId = machineryCategoryId;
	}

	public Integer getLeadSiteVariableId() {
		return leadSiteVariableId;
	}

	public void setLeadSiteVariableId(Integer leadSiteVariableId) {
		this.leadSiteVariableId = leadSiteVariableId;
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

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getAfterUnitId() {
		return afterUnitId;
	}

	public void setAfterUnitId(Integer afterUnitId) {
		this.afterUnitId = afterUnitId;
	}

	public Double getAfterRate() {
		return afterRate;
	}

	public void setAfterRate(Double afterRate) {
		this.afterRate = afterRate;
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
