package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import erp.workorder.enums.MaterialIssueType;

@Entity
@Table(name = "wo_material_config")
public class WorkorderMaterialConfig implements Serializable {

	private static final long serialVersionUID = -4357576904435260419L;

	private Long id;

	private Long workorderId;

	private MaterialGroup materialGroup;

	private MaterialIssueType issyeType;

	private Boolean isActive;

	private Date modifiedOn;

	private Long modifiedBy;

	@Transient
	private Long siteId;

	public WorkorderMaterialConfig() {
		super();
	}

	public WorkorderMaterialConfig(Long id) {
		super();
		this.id = id;
	}

	public WorkorderMaterialConfig(Long id, Long workorderId, MaterialGroup materialGroup, MaterialIssueType issyeType,
			Boolean isActive, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.workorderId = workorderId;
		this.materialGroup = materialGroup;
		this.issyeType = issyeType;
		this.isActive = isActive;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "material_group_id")
	public MaterialGroup getMaterialGroup() {
		return materialGroup;
	}

	public void setMaterialGroup(MaterialGroup materialGroup) {
		this.materialGroup = materialGroup;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "issue_type")
	public MaterialIssueType getIssyeType() {
		return issyeType;
	}

	public void setIssyeType(MaterialIssueType issyeType) {
		this.issyeType = issyeType;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "modified_on")
	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Column(name = "modified_by")
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

	@Column(name = "workorder_id")
	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

}
