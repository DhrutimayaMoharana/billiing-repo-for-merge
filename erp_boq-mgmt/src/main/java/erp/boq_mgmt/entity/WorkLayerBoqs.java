package erp.boq_mgmt.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "work_layer_boqs")
public class WorkLayerBoqs {

	private Long id;

	private Integer workLayerId;

	private Long boqItemId;

	private Boolean isActive;

	private Date updatedOn;

	private Integer updatedBy;

	public WorkLayerBoqs() {
		super();
	}

	public WorkLayerBoqs(Long id, Integer workLayerId, Long boqItemId, Boolean isActive, Date updatedOn,
			Integer updatedBy) {
		super();
		this.id = id;
		this.workLayerId = workLayerId;
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

	@Column(name = "work_layer_id")
	public Integer getWorkLayerId() {
		return workLayerId;
	}

	public void setWorkLayerId(Integer workLayerId) {
		this.workLayerId = workLayerId;
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
