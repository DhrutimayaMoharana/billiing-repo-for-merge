package erp.workorder.dto;

import java.util.Date;

import erp.workorder.enums.MaterialIssueType;

public class WorkorderMaterialConfigDTO {

	private Long id;

	private Long workorderId;

	private MaterialGroupDTO material;

	private MaterialIssueType issyeType;

	private Boolean isActive;

	private Date modifiedOn;

	private Long modifiedBy;

	// extra fields

	private Long siteId;

	private Integer companyId;

	public WorkorderMaterialConfigDTO() {
		super();
	}

	public WorkorderMaterialConfigDTO(Long id, Long workorderId, MaterialGroupDTO material, MaterialIssueType issyeType,
			Boolean isActive, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.workorderId = workorderId;
		this.material = material;
		this.issyeType = issyeType;
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

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	public MaterialGroupDTO getMaterial() {
		return material;
	}

	public void setMaterial(MaterialGroupDTO material) {
		this.material = material;
	}

	public MaterialIssueType getIssyeType() {
		return issyeType;
	}

	public void setIssyeType(MaterialIssueType issyeType) {
		this.issyeType = issyeType;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}
