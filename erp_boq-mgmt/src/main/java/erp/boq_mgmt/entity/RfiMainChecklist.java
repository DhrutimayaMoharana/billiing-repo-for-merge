package erp.boq_mgmt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rfi_main_checklists")
public class RfiMainChecklist implements Serializable {

	private static final long serialVersionUID = -8711861885907940757L;

	private Long id;

	private Long rfiMainId;

	private Integer checklistItemId;

	private Boolean isActive;

	private Date updatedOn;

	private Integer updatedBy;

	private RfiChecklistItems checklistItem;

	public RfiMainChecklist() {
		super();
	}

	public RfiMainChecklist(Long id, Long rfiMainId, Integer checklistItemId, Boolean isActive, Date updatedOn,
			Integer updatedBy) {
		super();
		this.id = id;
		this.rfiMainId = rfiMainId;
		this.checklistItemId = checklistItemId;
		this.isActive = isActive;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
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

	@Column(name = "rfi_main_id")
	public Long getRfiMainId() {
		return rfiMainId;
	}

	public void setRfiMainId(Long rfiMainId) {
		this.rfiMainId = rfiMainId;
	}

	@Column(name = "checklist_item_id")
	public Integer getChecklistItemId() {
		return checklistItemId;
	}

	public void setChecklistItemId(Integer checklistItemId) {
		this.checklistItemId = checklistItemId;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "updated_on")
	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(name = "updated_by")
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	@OneToOne
	@JoinColumn(name = "checklist_item_id", updatable = false, insertable = false)
	public RfiChecklistItems getChecklistItem() {
		return checklistItem;
	}

	public void setChecklistItem(RfiChecklistItems checklistItem) {
		this.checklistItem = checklistItem;
	}

}
