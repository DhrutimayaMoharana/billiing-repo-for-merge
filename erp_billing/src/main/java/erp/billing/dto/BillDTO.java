package erp.billing.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

import erp.billing.dto.request.BillMachineMapRequest;

public class BillDTO {

	private Long id;

	private BillTypeDTO type;

	private Integer billNo;

	private Date fromDate;

	private Date toDate;

	private WorkorderDTO workorder;

	private String taxInvoiceNo;

	private Date taxInvoiceDate;

	private Double applicableIgst;

	private Boolean isIgstOnly;

	private EngineStateDTO state;

	private Long siteId;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

//	extra fields

	private Double billedAmount;

	private BillBoqItemDTO boq;

	private Boolean isEditable;

	private List<NextStateTransitDTO> nextStates;

	private Boolean inFinalState;

	private String billNoText;

	private Integer companyId;

	private List<BillMachineMapRequest> hireMachinery;

	private Set<Long> workorderPayMilestoneIds;

	public BillDTO() {
		super();
	}

	public BillDTO(Long id) {
		super();
		this.id = id;
	}

	public BillDTO(Long id, BillTypeDTO type, Integer billNo, Date fromDate, Date toDate, WorkorderDTO workorder,
			String taxInvoiceNo, Date taxInvoiceDate, Double applicableIgst, Boolean isIgstOnly, EngineStateDTO state,
			Long siteId, Boolean isActive, Date createdOn, Long createdBy, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.type = type;
		this.billNo = billNo;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.workorder = workorder;
		this.taxInvoiceNo = taxInvoiceNo;
		this.taxInvoiceDate = taxInvoiceDate;
		this.applicableIgst = applicableIgst;
		this.isIgstOnly = isIgstOnly;
		this.state = state;
		this.siteId = siteId;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BillTypeDTO getType() {
		return type;
	}

	public void setType(BillTypeDTO type) {
		this.type = type;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public WorkorderDTO getWorkorder() {
		return workorder;
	}

	public void setWorkorder(WorkorderDTO workorder) {
		this.workorder = workorder;
	}

	public EngineStateDTO getState() {
		return state;
	}

	public void setState(EngineStateDTO state) {
		this.state = state;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getBillNo() {
		return billNo;
	}

	public void setBillNo(Integer billNo) {
		this.billNo = billNo;
	}

	public Double getBilledAmount() {
		return billedAmount;
	}

	public void setBilledAmount(Double billedAmount) {
		this.billedAmount = billedAmount;
	}

	public BillBoqItemDTO getBoq() {
		return boq;
	}

	public void setBoq(BillBoqItemDTO boq) {
		this.boq = boq;
	}

	public String getTaxInvoiceNo() {
		return taxInvoiceNo;
	}

	public void setTaxInvoiceNo(String taxInvoiceNo) {
		this.taxInvoiceNo = taxInvoiceNo;
	}

	public Date getTaxInvoiceDate() {
		return taxInvoiceDate;
	}

	public void setTaxInvoiceDate(Date taxInvoiceDate) {
		this.taxInvoiceDate = taxInvoiceDate;
	}

	public Double getApplicableIgst() {
		return applicableIgst;
	}

	public void setApplicableIgst(Double applicableIgst) {
		this.applicableIgst = applicableIgst;
	}

	public Boolean getIsIgstOnly() {
		return isIgstOnly;
	}

	public void setIsIgstOnly(Boolean isIgstOnly) {
		this.isIgstOnly = isIgstOnly;
	}

	public Boolean getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}

	public List<NextStateTransitDTO> getNextStates() {
		return nextStates;
	}

	public void setNextStates(List<NextStateTransitDTO> nextStates) {
		this.nextStates = nextStates;
	}

	public Boolean getInFinalState() {
		return inFinalState;
	}

	public void setInFinalState(Boolean inFinalState) {
		this.inFinalState = inFinalState;
	}

	public String getBillNoText() {
		return billNoText;
	}

	public void setBillNoText(String billNoText) {
		this.billNoText = billNoText;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public List<BillMachineMapRequest> getHireMachinery() {
		return hireMachinery;
	}

	public void setHireMachinery(List<BillMachineMapRequest> hireMachinery) {
		this.hireMachinery = hireMachinery;
	}

	public Set<Long> getWorkorderPayMilestoneIds() {
		return workorderPayMilestoneIds;
	}

	public void setWorkorderPayMilestoneIds(Set<Long> workorderPayMilestoneIds) {
		this.workorderPayMilestoneIds = workorderPayMilestoneIds;
	}

}
