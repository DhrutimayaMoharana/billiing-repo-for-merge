package erp.boq_mgmt.entity;

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
@Table(name = "rfi_checklist_item_boqs")
public class RfiChecklistItemBoqsV2 {

	private Long id;

	private RfiChecklistItemsV2 checklistItem;

	private Long boqItemId;

	private Boolean isActive;

	private Date updatedOn;

	private Integer updatedBy;

	public RfiChecklistItemBoqsV2() {
		super();
	}

	public RfiChecklistItemBoqsV2(Long id, RfiChecklistItemsV2 checklistItem, Long boqItemId, Boolean isActive,
			Date updatedOn, Integer updatedBy) {
		super();
		this.id = id;
		this.checklistItem = checklistItem;
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

	@OneToOne
	@JoinColumn(name = "rfi_checklist_item_id")
	public RfiChecklistItemsV2 getChecklistItem() {
		return checklistItem;
	}

	public void setChecklistItem(RfiChecklistItemsV2 checklistItem) {
		this.checklistItem = checklistItem;
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
