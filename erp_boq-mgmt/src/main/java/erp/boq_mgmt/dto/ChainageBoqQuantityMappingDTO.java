package erp.boq_mgmt.dto;

import java.util.Date;

public class ChainageBoqQuantityMappingDTO {

	private Long id;

	private ChainageDTO chainage;

	private BoqItemDTO boq;

	private HighwayBoqMappingDTO highwayBoq;

	private Long siteId;

	private Double lhsQuantity;

	private Double rhsQuantity;

	private String structureRemark;

	private Boolean isActive;

	private Date modifiedOn;

	private Long modifiedBy;

	private Integer companyId;

//	extra fields

	private Double length;

	private Double area;

	private Double volume;

	public ChainageBoqQuantityMappingDTO(Long id, ChainageDTO chainage, BoqItemDTO boq, HighwayBoqMappingDTO highwayBoq,
			Long siteId, Double lhsQuantity, Double rhsQuantity, String structureRemark, Boolean isActive,
			Date modifiedOn, Long modifiedBy, Integer companyId) {
		super();
		this.id = id;
		this.chainage = chainage;
		this.boq = boq;
		this.highwayBoq = highwayBoq;
		this.siteId = siteId;
		this.lhsQuantity = lhsQuantity;
		this.rhsQuantity = rhsQuantity;
		this.isActive = isActive;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
		this.companyId = companyId;
		this.structureRemark = structureRemark;
	}

	public ChainageBoqQuantityMappingDTO() {
		super();
	}

	public ChainageBoqQuantityMappingDTO(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ChainageDTO getChainage() {
		return chainage;
	}

	public void setChainage(ChainageDTO chainage) {
		this.chainage = chainage;
	}

	public BoqItemDTO getBoq() {
		return boq;
	}

	public void setBoq(BoqItemDTO boq) {
		this.boq = boq;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Double getLhsQuantity() {
		return lhsQuantity;
	}

	public void setLhsQuantity(Double lhsQuantity) {
		this.lhsQuantity = lhsQuantity;
	}

	public Double getRhsQuantity() {
		return rhsQuantity;
	}

	public void setRhsQuantity(Double rhsQuantity) {
		this.rhsQuantity = rhsQuantity;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public HighwayBoqMappingDTO getHighwayBoq() {
		return highwayBoq;
	}

	public void setHighwayBoq(HighwayBoqMappingDTO highwayBoq) {
		this.highwayBoq = highwayBoq;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public String getStructureRemark() {
		return structureRemark;
	}

	public void setStructureRemark(String structureRemark) {
		this.structureRemark = structureRemark;
	}

}
