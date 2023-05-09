package erp.billing.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chainage_boq_qty_mapping")
public class ChainageBoqQuantityMapping implements Serializable {
	
	private static final long serialVersionUID = -1978925923827025164L;

	private Long id;
	
	private Chainage chainage;
	
	private BoqItem boq;
	
	private HighwayBoqMapping highwayBoq;
	
	private Long siteId;
	
	private Double lhsQuantity;
	
	private Double rhsQuantity;
	
	private Boolean isActive;
	
	private Date modifiedOn;
	
	private Long modifiedBy;
	
	private Integer companyId;

	public ChainageBoqQuantityMapping() {
		super();
	}

	public ChainageBoqQuantityMapping(Long id) {
		super();
		this.id = id;
	}

	public ChainageBoqQuantityMapping(Long id, Chainage chainage, BoqItem boq, HighwayBoqMapping highwayBoq, Long siteId, Double lhsQuantity,
			Double rhsQuantity, Boolean isActive, Date modifiedOn, Long modifiedBy, Integer companyId) {
		super();
		this.id = id;
		this.chainage = chainage;
		this.boq = boq;
		this.highwayBoq = highwayBoq;
		this.siteId = siteId;
		this.lhsQuantity = lhsQuantity;
		this.rhsQuantity = rhsQuantity;
		this.setIsActive(isActive);
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
		this.companyId = companyId;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "chainage_id")
	public Chainage getChainage() {
		return chainage;
	}

	public void setChainage(Chainage chainage) {
		this.chainage = chainage;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "boq_id")
	public BoqItem getBoq() {
		return boq;
	}

	public void setBoq(BoqItem boq) {
		this.boq = boq;
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

	@Column(name = "company_id")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "highway_boq_id")
	public HighwayBoqMapping getHighwayBoq() {
		return highwayBoq;
	}

	public void setHighwayBoq(HighwayBoqMapping highwayBoq) {
		this.highwayBoq = highwayBoq;
	}

	

}
