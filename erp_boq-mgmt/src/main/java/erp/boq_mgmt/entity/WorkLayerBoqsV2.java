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
@Table(name = "work_layer_boqs")
public class WorkLayerBoqsV2 {

	private Long id;

	private WorkLayerV2 workLayer;

	private Long boqItemId;

	private Boolean isActive;

	private Date updatedOn;

	private Integer updatedBy;

	public WorkLayerBoqsV2() {
		super();
	}

	public WorkLayerBoqsV2(Long id, WorkLayerV2 workLayer, Long boqItemId, Boolean isActive, Date updatedOn,
			Integer updatedBy) {
		super();
		this.id = id;
		this.workLayer = workLayer;
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
	@JoinColumn(name = "work_layer_id")
	public WorkLayerV2 getWorkLayer() {
		return workLayer;
	}

	public void setWorkLayer(WorkLayerV2 workLayer) {
		this.workLayer = workLayer;
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
