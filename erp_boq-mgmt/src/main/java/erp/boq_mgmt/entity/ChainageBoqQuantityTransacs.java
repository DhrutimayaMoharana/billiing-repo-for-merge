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
@Table(name = "chainage_boq_qty_transacs")
public class ChainageBoqQuantityTransacs implements Serializable {

	private static final long serialVersionUID = -4327082653964085558L;

	private Long id;

	private Long chainageId;

	private Long boqId;

	private Long highwayBoqId;

	private Long siteId;

	private Double lhsQuantity;

	private Double rhsQuantity;

	private String structureRemark;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Integer companyId;

	public ChainageBoqQuantityTransacs() {
		super();
	}

	public ChainageBoqQuantityTransacs(Long id) {
		super();
		this.id = id;
	}

	public ChainageBoqQuantityTransacs(Long id, Long chainageId, Long boqId, Long highwayBoqId, Long siteId,
			Double lhsQuantity, Double rhsQuantity, String structureRemark, Boolean isActive, Date createdOn,
			Long createdBy, Integer companyId) {
		super();
		this.id = id;
		this.chainageId = chainageId;
		this.boqId = boqId;
		this.highwayBoqId = highwayBoqId;
		this.siteId = siteId;
		this.structureRemark = structureRemark;
		this.lhsQuantity = lhsQuantity;
		this.rhsQuantity = rhsQuantity;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.companyId = companyId;
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

	@Column(name = "chainage_id")
	public Long getChainageId() {
		return chainageId;
	}

	public void setChainageId(Long chainageId) {
		this.chainageId = chainageId;
	}

	@Column(name = "boq_id")
	public Long getBoqId() {
		return boqId;
	}

	public void setBoqId(Long boqId) {
		this.boqId = boqId;
	}

	@Column(name = "site_id")
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	@Column(name = "lhs_quantity")
	public Double getLhsQuantity() {
		return lhsQuantity;
	}

	public void setLhsQuantity(Double lhsQuantity) {
		this.lhsQuantity = lhsQuantity;
	}

	@Column(name = "rhs_quantity")
	public Double getRhsQuantity() {
		return rhsQuantity;
	}

	public void setRhsQuantity(Double rhsQuantity) {
		this.rhsQuantity = rhsQuantity;
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

	@Column(name = "company_id")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Column(name = "highway_boq_id")
	public Long getHighwayBoqId() {
		return highwayBoqId;
	}

	public void setHighwayBoqId(Long highwayBoqId) {
		this.highwayBoqId = highwayBoqId;
	}

	@Column(name = "structure_remark")
	public String getStructureRemark() {
		return structureRemark;
	}

	public void setStructureRemark(String structureRemark) {
		this.structureRemark = structureRemark;
	}

}
