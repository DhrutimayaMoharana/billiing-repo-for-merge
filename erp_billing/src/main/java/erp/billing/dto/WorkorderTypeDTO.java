package erp.billing.dto;

import java.util.Date;

public class WorkorderTypeDTO {

	private Long id;

	private String name;

	private String code;

	private Boolean isActive;

	private Date modifiedOn;

	private Long modifiedBy;

	// extra properties

	private Long siteId;

	public WorkorderTypeDTO() {
		super();
	}

	public WorkorderTypeDTO(Long id) {
		super();
		this.id = id;
	}

	public WorkorderTypeDTO(Long id, String name, String code, Boolean isActive, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.name = name;
		this.setCode(code);
		this.isActive = isActive;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
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

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
