package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import erp.workorder.enums.ChainageSide;

@Entity
@Table(name = "highway_boq_dpr")
public class HighwayBoqMeasurement implements Serializable {

	private static final long serialVersionUID = -5564668260830302948L;

	private Long id;

	private Date date;

	private BoqItem boq;
	
	private String description;

	private Workorder workorder;

	private Chainage fromChainage;

	private Chainage toChainage;

	private ChainageSide chainageSide;

	private Double quantity;

	private String remark;

	private EngineState state;

	private Date modifiedOn;

	private Long modifiedBy;

	private Long siteId;

	private Integer companyId;

	public HighwayBoqMeasurement() {
		super();
	}

	public HighwayBoqMeasurement(Long id) {
		super();
		this.id = id;
	}

	public HighwayBoqMeasurement(Long id, Date date, BoqItem boq, String description, Workorder workorder, Chainage fromChainage,
			Chainage toChainage, ChainageSide chainageSide, Double quantity, String remark, EngineState state, Date modifiedOn,
			Long modifiedBy, Long siteId, Integer companyId) {
		super();
		this.id = id;
		this.date = date;
		this.boq = boq;
		this.description = description;
		this.workorder = workorder;
		this.fromChainage = fromChainage;
		this.toChainage = toChainage;
		this.chainageSide = chainageSide;
		this.quantity = quantity;
		this.remark = remark;
		this.setState(state);
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
		this.siteId = siteId;
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

	@Column(name = "date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "boq_id")
	public BoqItem getBoq() {
		return boq;
	}

	public void setBoq(BoqItem boq) {
		this.boq = boq;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "workorder_id")
	public Workorder getWorkorder() {
		return workorder;
	}

	public void setWorkorder(Workorder workorder) {
		this.workorder = workorder;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "from_chainage_id")
	public Chainage getFromChainage() {
		return fromChainage;
	}

	public void setFromChainage(Chainage fromChainage) {
		this.fromChainage = fromChainage;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "to_chainage_id")
	public Chainage getToChainage() {
		return toChainage;
	}

	public void setToChainage(Chainage toChainage) {
		this.toChainage = toChainage;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "chainage_side")
	public ChainageSide getChainageSide() {
		return chainageSide;
	}

	public void setChainageSide(ChainageSide chainageSide) {
		this.chainageSide = chainageSide;
	}

	@Column(name = "quantity")
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@Column(name = "site_id")
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	@Column(name = "company_id")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "state_id")
	public EngineState getState() {
		return state;
	}

	public void setState(EngineState state) {
		this.state = state;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
