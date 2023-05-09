package erp.boq_mgmt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project_plan_boq_distribution")
public class ProjectPlanBoqDistribution implements Serializable {

	private static final long serialVersionUID = 5262547865813726239L;

	private Long id;

	private Long projectPlanBoqId;

	private Integer year;

	private Integer month;

	private Double quantityDistributed;

	private Date updatedOn;

	private Integer updatedBy;

	public ProjectPlanBoqDistribution() {
		super();
	}

	public ProjectPlanBoqDistribution(Long id, Long projectPlanBoqId, Integer year, Integer month,
			Double quantityDistributed, Date updatedOn, Integer updatedBy) {
		super();
		this.id = id;
		this.projectPlanBoqId = projectPlanBoqId;
		this.year = year;
		this.month = month;
		this.quantityDistributed = quantityDistributed;
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

	@Column(name = "project_plan_boq_id")
	public Long getProjectPlanBoqId() {
		return projectPlanBoqId;
	}

	public void setProjectPlanBoqId(Long projectPlanBoqId) {
		this.projectPlanBoqId = projectPlanBoqId;
	}

	@Column(name = "year")
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name = "month")
	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	@Column(name = "quantity_distributed")
	public Double getQuantityDistributed() {
		return quantityDistributed;
	}

	public void setQuantityDistributed(Double quantityDistributed) {
		this.quantityDistributed = quantityDistributed;
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
