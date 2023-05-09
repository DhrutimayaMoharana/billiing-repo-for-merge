package erp.workorder.dto;

import java.util.Date;

import erp.workorder.enums.ChainageSide;

public class HighwayBoqMeasurementDTO {

	private Long id;

	private Date date;

	private BoqItemDTO boq;
	
	private String description;

	private WorkorderDTO workorder;

	private ChainageDTO fromChainage;

	private ChainageDTO toChainage;

	private ChainageSide chainageSide;

	private Double quantity;

	private String remark;

	private EngineStateDTO state;

	private Date modifiedOn;

	private Long modifiedBy;

	private Long siteId;

	private Integer companyId;

	public HighwayBoqMeasurementDTO() {
		super();
	}

	public HighwayBoqMeasurementDTO(Long id) {
		super();
		this.id = id;
	}

	public HighwayBoqMeasurementDTO(Long id, Date date, BoqItemDTO boq, String description, WorkorderDTO workorder,
			ChainageDTO fromChainage, ChainageDTO toChainage, ChainageSide chainageSide, Double quantity, String remark,
			EngineStateDTO state, Date modifiedOn, Long modifiedBy, Long siteId, Integer companyId) {
		super();
		this.id = id;
		this.date = date;
		this.boq = boq;
		this.description = description;
		this.workorder = workorder;
		this.fromChainage = fromChainage;
		this.toChainage = toChainage;
		this.chainageSide = chainageSide;
		this.quantity = quantity;
		this.remark = remark;
		this.state = state;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
		this.siteId = siteId;
		this.companyId = companyId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BoqItemDTO getBoq() {
		return boq;
	}

	public void setBoq(BoqItemDTO boq) {
		this.boq = boq;
	}

	public WorkorderDTO getWorkorder() {
		return workorder;
	}

	public void setWorkorder(WorkorderDTO workorder) {
		this.workorder = workorder;
	}

	public ChainageDTO getFromChainage() {
		return fromChainage;
	}

	public void setFromChainage(ChainageDTO fromChainage) {
		this.fromChainage = fromChainage;
	}

	public ChainageDTO getToChainage() {
		return toChainage;
	}

	public void setToChainage(ChainageDTO toChainage) {
		this.toChainage = toChainage;
	}

	public ChainageSide getChainageSide() {
		return chainageSide;
	}

	public void setChainageSide(ChainageSide chainageSide) {
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

	public EngineStateDTO getState() {
		return state;
	}

	public void setState(EngineStateDTO state) {
		this.state = state;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
