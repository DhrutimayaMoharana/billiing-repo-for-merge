package erp.boq_mgmt.dto;

import java.util.Date;

public class DocumentTypeDTO {

	private Integer id;

	private String name;

	private String description;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Integer companyId;

	public DocumentTypeDTO() {
		super();
	}

	public DocumentTypeDTO(Integer id) {
		super();
		this.id = id;
	}

	public DocumentTypeDTO(Integer id, String name, String description, Boolean isActive, Date createdOn,
			Long createdBy, Integer companyId) {
		super();
		this.id = id;
		this.name = name;
		this.setDescription(description);
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.companyId = companyId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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
