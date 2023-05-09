package erp.boq_mgmt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "wo_boq_work_qty_mapping")
public class WorkorderBoqWorkQtyMapping implements Serializable {

	private static final long serialVersionUID = -5838671773752890416L;

	private Long id;

	private Long structureTypeId;

	private Long structureId;

	private WorkorderBoqWork boqWork;

	private BoqItem boq;

	private String description;

	private String vendorDescription;

	private Double rate;

	private Double quantity;

	private Boolean isActive;

	private Integer version;

	private Date modifiedOn;

	private Long modifiedBy;

	public WorkorderBoqWorkQtyMapping() {
		super();
	}

	public WorkorderBoqWorkQtyMapping(Long id, Long structureTypeId, Long structureId, WorkorderBoqWork boqWork,
			BoqItem boq, String description, String vendorDescription, Double rate, Double quantity, Boolean isActive,
			Integer version, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.structureTypeId = structureTypeId;
		this.structureId = structureId;
		this.boqWork = boqWork;
		this.boq = boq;
		this.setVendorDescription(vendorDescription);
		this.description = description;
		this.rate = rate;
		this.quantity = quantity;
		this.isActive = isActive;
		this.version = version;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "boq_work_id")
	public WorkorderBoqWork getBoqWork() {
		return boqWork;
	}

	public void setBoqWork(WorkorderBoqWork boqWork) {
		this.boqWork = boqWork;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "boq_id")
	public BoqItem getBoq() {
		return boq;
	}

	public void setBoq(BoqItem boq) {
		this.boq = boq;
	}

	@Column(name = "rate")
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Column(name = "quantity")
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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

	@Column(name = "vendor_description")
	public String getVendorDescription() {
		return vendorDescription;
	}

	public void setVendorDescription(String vendorDescription) {
		this.vendorDescription = vendorDescription;
	}

	@Column(name = "boq_description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "structure_type_id")
	public Long getStructureTypeId() {
		return structureTypeId;
	}

	public void setStructureTypeId(Long structureTypeId) {
		this.structureTypeId = structureTypeId;
	}

	@Column(name = "structure_id")
	public Long getStructureId() {
		return structureId;
	}

	public void setStructureId(Long structureId) {
		this.structureId = structureId;
	}

}
