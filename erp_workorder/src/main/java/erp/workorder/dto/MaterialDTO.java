package erp.workorder.dto;

import java.util.Date;

public class MaterialDTO {
	
	private Long id;
	
	private String name;
	
	private String specification;
	
	private Boolean isActive;
	
	private Date createdOn;
	
	private UnitDTO unit;
	
	private Integer companyId;
	
	// extra fields
	
	private Double weightedRate;

	public MaterialDTO() {
		super();
	}

	public MaterialDTO(Long id) {
		super();
		this.id = id;
	}

	public MaterialDTO(Long id, String name, String specification, Boolean isActive, Date createdOn, UnitDTO unit,
			Integer companyId) {
		super();
		this.id = id;
		this.name = name;
		this.specification = specification;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.unit = unit;
		this.companyId = companyId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public UnitDTO getUnit() {
		return unit;
	}

	public void setUnit(UnitDTO unit) {
		this.unit = unit;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Double getWeightedRate() {
		return weightedRate;
	}

	public void setWeightedRate(Double weightedRate) {
		this.weightedRate = weightedRate;
	}

}
