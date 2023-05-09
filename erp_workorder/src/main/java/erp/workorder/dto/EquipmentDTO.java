package erp.workorder.dto;

public class EquipmentDTO {

	private Long id;
	
	private String assetCode;
	
	private String regNo;
	
	private EquipmentCategoryDTO category;
	
	private Boolean isOwned;
	
	private Integer type;
	
	private Boolean isActive;
	
	private Long siteId;
	
	private Integer companyId;

	public EquipmentDTO() {
		super();
	}

	public EquipmentDTO(Long id) {
		super();
		this.id = id;
	}

	public EquipmentDTO(Long id, String assetCode, String regNo, EquipmentCategoryDTO category, Boolean isOwned,
			Integer type, Boolean isActive, Long siteId, Integer companyId) {
		super();
		this.id = id;
		this.assetCode = assetCode;
		this.regNo = regNo;
		this.category = category;
		this.isOwned = isOwned;
		this.type = type;
		this.isActive = isActive;
		this.siteId = siteId;
		this.companyId = companyId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public EquipmentCategoryDTO getCategory() {
		return category;
	}

	public void setCategory(EquipmentCategoryDTO category) {
		this.category = category;
	}

	public Boolean getIsOwned() {
		return isOwned;
	}

	public void setIsOwned(Boolean isOwned) {
		this.isOwned = isOwned;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

}
