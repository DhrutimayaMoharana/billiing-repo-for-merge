package erp.boq_mgmt.dto.response;

import erp.boq_mgmt.enums.MachineryRangeType;
import erp.boq_mgmt.enums.MachineryTrip;

public class BoqCostDefinitionMachineryResponse {

	private Long id;

	private Long boqCostDefinitionId;

	private Long machineryCategoryId;

	private String machineryCategoryName;

	private Integer machineryTripId;

	private MachineryTrip machineryTrip;

	private Integer unitId;

	private String unitName;

	private Double rate;

	private Integer afterUnitId;

	private String afterUnitName;

	private Double afterRate;

	private MachineryRangeType rangeType;

	private Integer rangeUnitId;

	private String rangeUnitName;

	private Double rangeQuantity;

	private Double amount;

	public BoqCostDefinitionMachineryResponse() {
		super();
	}

	public BoqCostDefinitionMachineryResponse(Long id, Long boqCostDefinitionId, Long machineryCategoryId,
			String machineryCategoryName, Integer machineryTripId, MachineryTrip machineryTrip, Integer unitId,
			String unitName, Double rate, Integer afterUnitId, String afterUnitName, Double afterRate,
			MachineryRangeType rangeType, Integer rangeUnitId, String rangeUnitName, Double rangeQuantity,
			Double amount) {
		super();
		this.id = id;
		this.boqCostDefinitionId = boqCostDefinitionId;
		this.machineryCategoryId = machineryCategoryId;
		this.machineryCategoryName = machineryCategoryName;
		this.machineryTripId = machineryTripId;
		this.machineryTrip = machineryTrip;
		this.unitId = unitId;
		this.unitName = unitName;
		this.rate = rate;
		this.afterUnitId = afterUnitId;
		this.afterUnitName = afterUnitName;
		this.afterRate = afterRate;
		this.rangeType = rangeType;
		this.rangeUnitId = rangeUnitId;
		this.rangeUnitName = rangeUnitName;
		this.rangeQuantity = rangeQuantity;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getMachineryCategoryName() {
		return machineryCategoryName;
	}

	public void setMachineryCategoryName(String machineryCategoryName) {
		this.machineryCategoryName = machineryCategoryName;
	}

	public Integer getMachineryTripId() {
		return machineryTripId;
	}

	public void setMachineryTripId(Integer machineryTripId) {
		this.machineryTripId = machineryTripId;
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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

	public String getAfterUnitName() {
		return afterUnitName;
	}

	public void setAfterUnitName(String afterUnitName) {
		this.afterUnitName = afterUnitName;
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

	public String getRangeUnitName() {
		return rangeUnitName;
	}

	public void setRangeUnitName(String rangeUnitName) {
		this.rangeUnitName = rangeUnitName;
	}

	public Double getRangeQuantity() {
		return rangeQuantity;
	}

	public void setRangeQuantity(Double rangeQuantity) {
		this.rangeQuantity = rangeQuantity;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
