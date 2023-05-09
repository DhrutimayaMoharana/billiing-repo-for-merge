package erp.workorder.dto;

public class EquipmentCategoryDTO {

	private Long id;

	private String name;

	private String abbreviation;

	private Double operatingCost;

	private Boolean isMultiFuel;

	private Long primaryReadingUnitId;

	private String primaryReadingUnitName;

	private Long secondaryReadingUnitId;

	private String secondaryReadingUnitName;

	public EquipmentCategoryDTO() {
		super();
	}

	public EquipmentCategoryDTO(Long id) {
		super();
		this.id = id;
	}

	public EquipmentCategoryDTO(Long id, String name, String abbreviation, Double operatingCost, Boolean isMultiFuel,
			Long primaryReadingUnitId, String primaryReadingUnitName, Long secondaryReadingUnitId,
			String secondaryReadingUnitName) {
		super();
		this.id = id;
		this.name = name;
		this.abbreviation = abbreviation;
		this.operatingCost = operatingCost;
		this.setIsMultiFuel(isMultiFuel);
		this.primaryReadingUnitId = primaryReadingUnitId;
		this.primaryReadingUnitName = primaryReadingUnitName;
		this.secondaryReadingUnitId = secondaryReadingUnitId;
		this.secondaryReadingUnitName = secondaryReadingUnitName;
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

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public Double getOperatingCost() {
		return operatingCost;
	}

	public void setOperatingCost(Double operatingCost) {
		this.operatingCost = operatingCost;
	}

	public Long getPrimaryReadingUnitId() {
		return primaryReadingUnitId;
	}

	public void setPrimaryReadingUnitId(Long primaryReadingUnitId) {
		this.primaryReadingUnitId = primaryReadingUnitId;
	}

	public String getPrimaryReadingUnitName() {
		return primaryReadingUnitName;
	}

	public void setPrimaryReadingUnitName(String primaryReadingUnitName) {
		this.primaryReadingUnitName = primaryReadingUnitName;
	}

	public Long getSecondaryReadingUnitId() {
		return secondaryReadingUnitId;
	}

	public void setSecondaryReadingUnitId(Long secondaryReadingUnitId) {
		this.secondaryReadingUnitId = secondaryReadingUnitId;
	}

	public String getSecondaryReadingUnitName() {
		return secondaryReadingUnitName;
	}

	public void setSecondaryReadingUnitName(String secondaryReadingUnitName) {
		this.secondaryReadingUnitName = secondaryReadingUnitName;
	}

	public Boolean getIsMultiFuel() {
		return isMultiFuel;
	}

	public void setIsMultiFuel(Boolean isMultiFuel) {
		this.isMultiFuel = isMultiFuel;
	}

}
