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
@Table(name = "rfi_main_executed_quantity_transac")
public class RfiMainExecutedQuantityTransaction implements Serializable {

	private static final long serialVersionUID = 3233807607491080006L;

	private Long id;

	private Long rfiMainId;

	private Double clientExecutedQuantity;

	private Double actualExecutedQuantity;

	private Boolean isActive;

	private Date updatedOn;

	private Integer updatedBy;

	public RfiMainExecutedQuantityTransaction() {
		super();
	}

	public RfiMainExecutedQuantityTransaction(Long id, Long rfiMainId, Double clientExecutedQuantity,
			Double actualExecutedQuantity, Boolean isActive, Date updatedOn, Integer updatedBy) {
		super();
		this.id = id;
		this.rfiMainId = rfiMainId;
		this.clientExecutedQuantity = clientExecutedQuantity;
		this.actualExecutedQuantity = actualExecutedQuantity;
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

	@Column(name = "client_executed_quantity")
	public Double getClientExecutedQuantity() {
		return clientExecutedQuantity;
	}

	public void setClientExecutedQuantity(Double clientExecutedQuantity) {
		this.clientExecutedQuantity = clientExecutedQuantity;
	}

	@Column(name = "actual_executed_quantity")
	public Double getActualExecutedQuantity() {
		return actualExecutedQuantity;
	}

	public void setActualExecutedQuantity(Double actualExecutedQuantity) {
		this.actualExecutedQuantity = actualExecutedQuantity;
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
