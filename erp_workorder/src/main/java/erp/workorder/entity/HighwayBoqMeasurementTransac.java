package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import erp.workorder.enums.ChainageSide;

@Entity
@Table(name = "highway_boq_dpr_transacs")
public class HighwayBoqMeasurementTransac implements Serializable {

	private static final long serialVersionUID = -7019618433606635006L;

	private Long id;

	private Date date;

	private Long boqId;
	
	private String description;

	private Long workorderId;

	private Long fromChainageId;

	private Long toChainageId;

	private ChainageSide chainageSide;

	private Double quantity;

	private String remark;

	private Integer stateId;

	private Date createdOn;

	private Long createdBy;

	private Long siteId;

	private Integer companyId;

	public HighwayBoqMeasurementTransac() {
		super();
	}

	public HighwayBoqMeasurementTransac(Long id) {
		super();
		this.id = id;
	}

	public HighwayBoqMeasurementTransac(Long id, Date date, Long boqId, String description, Long workorderId, Long fromChainageId,
			Long toChainageId, ChainageSide chainageSide, Double quantity, String remark, Integer stateId,
			Date createdOn, Long createdBy, Long siteId, Integer companyId) {
		super();
		this.id = id;
		this.date = date;
		this.boqId = boqId;
		this.description = description;
		this.workorderId = workorderId;
		this.fromChainageId = fromChainageId;
		this.toChainageId = toChainageId;
		this.chainageSide = chainageSide;
		this.quantity = quantity;
		this.remark = remark;
		this.stateId = stateId;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
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

	@Column(name = "boq_id")
	public Long getBoqId() {
		return boqId;
	}

	public void setBoqId(Long boqId) {
		this.boqId = boqId;
	}

	@Column(name = "workorder_id")
	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	@Column(name = "from_chainage_id")
	public Long getFromChainageId() {
		return fromChainageId;
	}

	public void setFromChainageId(Long fromChainageId) {
		this.fromChainageId = fromChainageId;
	}

	@Column(name = "to_chainage_id")
	public Long getToChainageId() {
		return toChainageId;
	}

	public void setToChainageId(Long toChainageId) {
		this.toChainageId = toChainageId;
	}

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

	@Column(name = "state_id")
	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
