package erp.billing.dto.response;

import java.util.Date;
import java.util.List;

public class WorkorderBillInfoResponse {

	private Long id;

	private Date startDate;

	private Date systemBillStartDate;

	private Double previousBillAmount = 0.0;

	private Integer previousBillNo = 0;

	private Boolean isBillAvailable;

	private Double total = 0.0;

	private Double totalDeduction = 0.0;

	private Double payableAmount = 0.0;

	private Double applicableIgst = 0.0;

	private Boolean isIgstOnly = false;

	private List<BillDeductionResponseDto> billDeductionResponse = null;

	public WorkorderBillInfoResponse() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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

	public Boolean getIsBillAvailable() {
		return isBillAvailable;
	}

	public void setIsBillAvailable(Boolean isBillAvailable) {
		this.isBillAvailable = isBillAvailable;
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

	public List<BillDeductionResponseDto> getBillDeductionResponse() {
		return billDeductionResponse;
	}

	public void setBillDeductionResponse(List<BillDeductionResponseDto> billDeductionResponse) {
		this.billDeductionResponse = billDeductionResponse;
	}

}
