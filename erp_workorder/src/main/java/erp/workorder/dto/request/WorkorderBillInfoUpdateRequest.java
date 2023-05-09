package erp.workorder.dto.request;

import java.util.Date;
import java.util.List;

import erp.workorder.dto.UserDetail;

public class WorkorderBillInfoUpdateRequest {

	private Long id;

	private Date systemBillStartDate;

	private Double previousBillAmount;

	private Integer previousBillNo;

	private Long siteId;

	private Double applicableIgst;

	private Boolean isIgstOnly;

	private Double total;

	private Double totalDeduction;

	private Double payableAmount;

	private List<WorkoderHireMachineRequest> hireMachineRequest;

	private List<BillDeductionMappingDTO> billDeductionRequest;

	private UserDetail userDetail;

	private Object machineObj;

	public WorkorderBillInfoUpdateRequest() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getSystemBillStartDate() {
		return systemBillStartDate;
	}

	public void setSystemBillStartDate(Date systemBillStartDate) {
		this.systemBillStartDate = systemBillStartDate;
	}

	public Double getPreviousBillAmount() {
		return previousBillAmount;
	}

	public void setPreviousBillAmount(Double previousBillAmount) {
		this.previousBillAmount = previousBillAmount;
	}

	public Integer getPreviousBillNo() {
		return previousBillNo;
	}

	public void setPreviousBillNo(Integer previousBillNo) {
		this.previousBillNo = previousBillNo;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
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

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getTotalDeduction() {
		return totalDeduction;
	}

	public void setTotalDeduction(Double totalDeduction) {
		this.totalDeduction = totalDeduction;
	}

	public Double getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(Double payableAmount) {
		this.payableAmount = payableAmount;
	}

	public List<BillDeductionMappingDTO> getBillDeductionRequest() {
		return billDeductionRequest;
	}

	public void setBillDeductionRequest(List<BillDeductionMappingDTO> billDeductionRequest) {
		this.billDeductionRequest = billDeductionRequest;
	}

	public List<WorkoderHireMachineRequest> getHireMachineRequest() {
		return hireMachineRequest;
	}

	public void setHireMachineRequest(List<WorkoderHireMachineRequest> hireMachineRequest) {
		this.hireMachineRequest = hireMachineRequest;
	}

	public Object getMachineObj() {
		return machineObj;
	}

	public void setMachineObj(Object machineObj) {
		this.machineObj = machineObj;
	}

}
