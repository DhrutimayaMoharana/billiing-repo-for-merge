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
@Table(name = "boq_lmps_cost_definitions")
public class BoqCostDefinition {

	private Long id;

	private Long boqId;

	private Double expectedOutputValue;

	private Boolean foamworkIncluded;

	private Integer foamworkSiteVariableId;

	private Boolean overheadIncluded;

	private Integer overheadSiteVariableId;

	private Boolean contractorProfitIncluded;

	private Integer contractorProfitSiteVariableId;

	private Double extraExpenseAmount;

	private String extraExpenseRemark;

	private EngineState state;

	private Integer siteId;

	private Integer companyId;

	private Boolean isActive;

	private Date createdOn;

	private Integer createdBy;

	private Date updatedOn;

	private Integer updatedBy;

	private User updatedByUser;

	private BoqItem boq;

	public BoqCostDefinition() {
		super();
	}

	public BoqCostDefinition(Long id, Long boqId, Double expectedOutputValue, Boolean foamworkIncluded,
			Integer foamworkSiteVariableId, Boolean overheadIncluded, Integer overheadSiteVariableId,
			Boolean contractorProfitIncluded, Integer contractorProfitSiteVariableId, Double extraExpenseAmount,
			String extraExpenseRemark, EngineState state, Integer siteId, Integer companyId, Boolean isActive,
			Date createdOn, Integer createdBy, Date updatedOn, Integer updatedBy) {
		super();
		this.id = id;
		this.boqId = boqId;
		this.expectedOutputValue = expectedOutputValue;
		this.foamworkIncluded = foamworkIncluded;
		this.foamworkSiteVariableId = foamworkSiteVariableId;
		this.overheadIncluded = overheadIncluded;
		this.overheadSiteVariableId = overheadSiteVariableId;
		this.contractorProfitIncluded = contractorProfitIncluded;
		this.contractorProfitSiteVariableId = contractorProfitSiteVariableId;
		this.extraExpenseAmount = extraExpenseAmount;
		this.extraExpenseRemark = extraExpenseRemark;
		this.state = state;
		this.siteId = siteId;
		this.companyId = companyId;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
	}


	public BoqCostDefinition(Long id2, Long boqId2, Double expectedOutputValue2, Boolean foamworkIncluded2,
			Double foamworkSiteVariableId2, Boolean overheadIncluded2, Double overheadSiteVariableId2,
			Boolean contractorProfitIncluded2, Double contractorProfitSiteVariableId2, Double extraExpenseAmount2,
			String extraExpenseRemark2, EngineState engineState, Integer siteId2, Integer companyId2, boolean b,
			Date date, int intValue, Date date2, int intValue2) {
		// TODO Auto-generated constructor stub
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

	@Column(name = "boq_id")
	public Long getBoqId() {
		return boqId;
	}

	public void setBoqId(Long boqId) {
		this.boqId = boqId;
	}

	@Column(name = "expected_output_value")
	public Double getExpectedOutputValue() {
		return expectedOutputValue;
	}

	public void setExpectedOutputValue(Double expectedOutputValue) {
		this.expectedOutputValue = expectedOutputValue;
	}

	@Column(name = "foamwork_included")
	public Boolean getFoamworkIncluded() {
		return foamworkIncluded;
	}

	public void setFoamworkIncluded(Boolean foamworkIncluded) {
		this.foamworkIncluded = foamworkIncluded;
	}

	@Column(name = "foamwork_site_variable_id")
	public Integer getFoamworkSiteVariableId() {
		return foamworkSiteVariableId;
	}

	public void setFoamworkSiteVariableId(Integer foamworkSiteVariableId) {
		this.foamworkSiteVariableId = foamworkSiteVariableId;
	}

	@Column(name = "overhead_included")
	public Boolean getOverheadIncluded() {
		return overheadIncluded;
	}

	public void setOverheadIncluded(Boolean overheadIncluded) {
		this.overheadIncluded = overheadIncluded;
	}

	@Column(name = "overhead_site_variable_id")
	public Integer getOverheadSiteVariableId() {
		return overheadSiteVariableId;
	}

	public void setOverheadSiteVariableId(Integer overheadSiteVariableId) {
		this.overheadSiteVariableId = overheadSiteVariableId;
	}

	@Column(name = "contractor_profit_included")
	public Boolean getContractorProfitIncluded() {
		return contractorProfitIncluded;
	}

	public void setContractorProfitIncluded(Boolean contractorProfitIncluded) {
		this.contractorProfitIncluded = contractorProfitIncluded;
	}

	@Column(name = "contractor_profit_site_variable_id")
	public Integer getContractorProfitSiteVariableId() {
		return contractorProfitSiteVariableId;
	}

	public void setContractorProfitSiteVariableId(Integer contractorProfitSiteVariableId) {
		this.contractorProfitSiteVariableId = contractorProfitSiteVariableId;
	}

	@Column(name = "extra_expense_amount")
	public Double getExtraExpenseAmount() {
		return extraExpenseAmount;
	}

	public void setExtraExpenseAmount(Double extraExpenseAmount) {
		this.extraExpenseAmount = extraExpenseAmount;
	}

	@Column(name = "extra_expense_remark")
	public String getExtraExpenseRemark() {
		return extraExpenseRemark;
	}

	public void setExtraExpenseRemark(String extraExpenseRemark) {
		this.extraExpenseRemark = extraExpenseRemark;
	}

	@OneToOne
	@JoinColumn(name = "engine_state_id")
	public EngineState getState() {
		return state;
	}

	public void setState(EngineState state) {
		this.state = state;
	}

	@Column(name = "site_id")
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	@Column(name = "company_id")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
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

	@OneToOne
	@JoinColumn(name = "updated_by", insertable = false, updatable = false)
	public User getUpdatedByUser() {
		return updatedByUser;
	}

	public void setUpdatedByUser(User updatedByUser) {
		this.updatedByUser = updatedByUser;
	}

	@OneToOne
	@JoinColumn(name = "boq_id", insertable = false, updatable = false)
	public BoqItem getBoq() {
		return boq;
	}

	public void setBoq(BoqItem boq) {
		this.boq = boq;
	}

}
