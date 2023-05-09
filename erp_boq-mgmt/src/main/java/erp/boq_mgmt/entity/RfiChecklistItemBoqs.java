package erp.boq_mgmt.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rfi_checklist_item_boqs")
public class RfiChecklistItemBoqs {

	private Long id;

	private Integer checklistItemId;

	private Long boqItemId;

	private Boolean isActive;

	private Date updatedOn;

	private Integer updatedBy;

	public RfiChecklistItemBoqs() {
		super();
	}

	public RfiChecklistItemBoqs(Long id, Integer checklistItemId, Long boqItemId, Boolean isActive, Date updatedOn,
			Integer updatedBy) {
		super();
		this.id = id;
		this.checklistItemId = checklistItemId;
		this.boqItemId = boqItemId;
		this.isActive = isActive;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "rfi_checklist_item_id")
	public Integer getChecklistItemId() {
		return checklistItemId;
	}

	public void setChecklistItemId(Integer checklistItemId) {
		this.checklistItemId = checklistItemId;
	}

	@Column(name = "boq_item_id")
	public Long getBoqItemId() {
		return boqItemId;
	}

	public void setBoqItemId(Long boqItemId) {
		this.boqItemId = boqItemId;
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

}
