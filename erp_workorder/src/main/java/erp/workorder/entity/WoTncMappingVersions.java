package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wo_tnc_mapping_versions")
public class WoTncMappingVersions implements Serializable {

	private static final long serialVersionUID = -1750634002616802583L;

	private Long id;

	private Long workorderVersionId;

	private Long termAndConditionId;

	private String description;

	private Integer sequenceNo;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	public WoTncMappingVersions() {
		super();
	}

	public WoTncMappingVersions(Long id, Long workorderVersionId, Long termAndConditionId, String description,
			Integer sequenceNo, Boolean isActive, Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.workorderVersionId = workorderVersionId;
		this.termAndConditionId = termAndConditionId;
		this.description = description;
		this.sequenceNo = sequenceNo;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
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

	@Column(name = "workorder_version_id")
	public Long getWorkorderVersionId() {
		return workorderVersionId;
	}

	public void setWorkorderVersionId(Long workorderVersionId) {
		this.workorderVersionId = workorderVersionId;
	}

	@Column(name = "term_condition_id")
	public Long getTermAndConditionId() {
		return termAndConditionId;
	}

	public void setTermAndConditionId(Long termAndConditionId) {
		this.termAndConditionId = termAndConditionId;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "created_by")
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "sequence_no")
	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

}
