package erp.billing.dto.response;

import java.util.Date;
import java.util.List;

import erp.billing.dto.BillTypeDTO;
import erp.billing.dto.EngineStateDTO;
import erp.billing.dto.WorkorderDTO;

public class BillAndItemsDTO {

	private Long id;

	private BillTypeDTO type;

	private Integer billNo;

	private Date fromDate;

	private Date toDate;

	private List<BillItemResponseDTO> items;

	private List<StructureTypeBillBoqResponse> structureTypeItems;

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

	private Double billedAmount;

	private Double billedAmountAfterGst;

	private String billNoText;

	private List<BillMachineCategoryResponse> machineCategories;

	private List<BillPayMilestoneResponse> billPayMilestones;
	
	private Double fuelDebitAmount;

	public BillAndItemsDTO() {
		super();
	}

	public BillAndItemsDTO(Long id, BillTypeDTO type, Integer billNo, Date fromDate, Date toDate,
			List<BillItemResponseDTO> items, List<StructureTypeBillBoqResponse> structureTypeItems,
			WorkorderDTO workorder, String taxInvoiceNo, Date taxInvoiceDate, Double applicableIgst, Boolean isIgstOnly,
			EngineStateDTO state, Long siteId, Boolean isActive, Date createdOn, Long createdBy, Date modifiedOn,
			Long modifiedBy) {
		super();
		this.id = id;
		this.type = type;
		this.billNo = billNo;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.items = items;
		this.structureTypeItems = structureTypeItems;
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

	public Double getBilledAmount() {
		return billedAmount;
	}

	public void setBilledAmount(Double billedAmount) {
		this.billedAmount = billedAmount;
	}

	public Double getBilledAmountAfterGst() {
		return billedAmountAfterGst;
	}

	public void setBilledAmountAfterGst(Double billedAmountAfterGst) {
		this.billedAmountAfterGst = billedAmountAfterGst;
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

	public Integer getBillNo() {
		return billNo;
	}

	public void setBillNo(Integer billNo) {
		this.billNo = billNo;
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

	public List<BillItemResponseDTO> getItems() {
		return items;
	}

	public void setItems(List<BillItemResponseDTO> items) {
		this.items = items;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public String getBillNoText() {
		return billNoText;
	}

	public void setBillNoText(String billNoText) {
		this.billNoText = billNoText;
	}

	public List<StructureTypeBillBoqResponse> getStructureTypeItems() {
		return structureTypeItems;
	}

	public void setStructureTypeItems(List<StructureTypeBillBoqResponse> structureTypeItems) {
		this.structureTypeItems = structureTypeItems;
	}

	public List<BillMachineCategoryResponse> getMachineCategories() {
		return machineCategories;
	}

	public void setMachineCategories(List<BillMachineCategoryResponse> machineCategories) {
		this.machineCategories = machineCategories;
	}

	public List<BillPayMilestoneResponse> getBillPayMilestones() {
		return billPayMilestones;
	}

	public void setBillPayMilestones(List<BillPayMilestoneResponse> billPayMilestones) {
		this.billPayMilestones = billPayMilestones;
	}

	public Double getFuelDebitAmount() {
		return fuelDebitAmount;
	}

	public void setFuelDebitAmount(Double fuelDebitAmount) {
		this.fuelDebitAmount = fuelDebitAmount;
	}

}
