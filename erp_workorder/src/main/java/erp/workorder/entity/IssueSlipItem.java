package erp.workorder.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "store_slip_items")
public class IssueSlipItem implements Serializable {

	private static final long serialVersionUID = 9023442011599870989L;

	private Long id;

	private IssueSlip issueSlip;

	private Long equipmentId;

	private Long plantId;

	private Double issuedQuantity;

	private Double returnedQuantity;

	public IssueSlipItem() {
		super();
	}

	public IssueSlipItem(Long id, IssueSlip issueSlip, Long equipmentId, Long plantId, Double issuedQuantity,
			Double returnedQuantity) {
		super();
		this.id = id;
		this.issueSlip = issueSlip;
		this.equipmentId = equipmentId;
		this.plantId = plantId;
		this.issuedQuantity = issuedQuantity;
		this.returnedQuantity = returnedQuantity;
	}

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne
	@JoinColumn(name = "store_slip_id")
	public IssueSlip getIssueSlip() {
		return issueSlip;
	}

	public void setIssueSlip(IssueSlip issueSlip) {
		this.issueSlip = issueSlip;
	}

	@Column(name = "equipment_id")
	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	@Column(name = "plant_id")
	public Long getPlantId() {
		return plantId;
	}

	public void setPlantId(Long plantId) {
		this.plantId = plantId;
	}

	@Column(name = "quantity_issued")
	public Double getIssuedQuantity() {
		return issuedQuantity;
	}

	public void setIssuedQuantity(Double issuedQuantity) {
		this.issuedQuantity = issuedQuantity;
	}

	@Column(name = "returned_quantity")
	public Double getReturnedQuantity() {
		return returnedQuantity;
	}

	public void setReturnedQuantity(Double returnedQuantity) {
		this.returnedQuantity = returnedQuantity;
	}

}
