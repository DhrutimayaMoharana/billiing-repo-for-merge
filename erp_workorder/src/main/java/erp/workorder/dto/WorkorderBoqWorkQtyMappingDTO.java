package erp.workorder.dto;

import java.util.Date;

public class WorkorderBoqWorkQtyMappingDTO {

	private Long id;

	private Long structureTypeId;

	private Long structureId;

	private BoqItemDTO boq;

	private String description;

	private String vendorDescription;

	private UnitDTO unit;

	private Double rate;

	private Double quantity;

	private Boolean isActive;

	private Integer version;

	private Date modifiedOn;

	private Long modifiedBy;

	public WorkorderBoqWorkQtyMappingDTO() {
		super();
	}

	public WorkorderBoqWorkQtyMappingDTO(Long id) {
		super();
		this.id = id;
	}

	public WorkorderBoqWorkQtyMappingDTO(Long id, Long structureTypeId, Long structureId, BoqItemDTO boq, UnitDTO unit,
			String description, String vendorDescription, Double rate, Double quantity, Boolean isActive,
			Integer version, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.structureTypeId = structureTypeId;
		this.structureId = structureId;
		this.boq = boq;
		this.setVendorDescription(vendorDescription);
		this.description = description;
		this.setUnit(unit);
		this.rate = rate;
		this.quantity = quantity;
		this.isActive = isActive;
		this.version = version;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BoqItemDTO getBoq() {
		return boq;
	}

	public void setBoq(BoqItemDTO boq) {
		this.boq = boq;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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

	public String getVendorDescription() {
		return vendorDescription;
	}

	public void setVendorDescription(String vendorDescription) {
		this.vendorDescription = vendorDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UnitDTO getUnit() {
		return unit;
	}

	public void setUnit(UnitDTO unit) {
		this.unit = unit;
	}

	public Long getStructureTypeId() {
		return structureTypeId;
	}

	public void setStructureTypeId(Long structureTypeId) {
		this.structureTypeId = structureTypeId;
	}

	public Long getStructureId() {
		return structureId;
	}

	public void setStructureId(Long structureId) {
		this.structureId = structureId;
	}

}
