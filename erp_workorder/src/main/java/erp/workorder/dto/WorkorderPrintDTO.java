package erp.workorder.dto;

public class WorkorderPrintDTO {

	private Object basicDetail;

	private Object boqWork;

	private Object consultantWork;

	private Object hireMachineWork;

	private Object transportWork;

	private Object labourWork;

	private Object tncs;

	private Object site;

	private Double amount;

	public WorkorderPrintDTO() {
		super();
	}

	public Object getBasicDetail() {
		return basicDetail;
	}

	public void setBasicDetail(Object basicDetail) {
		this.basicDetail = basicDetail;
	}

	public Object getTncs() {
		return tncs;
	}

	public void setTncs(Object tncs) {
		this.tncs = tncs;
	}

	public Object getSite() {
		return site;
	}

	public void setSite(Object site) {
		this.site = site;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Object getConsultantWork() {
		return consultantWork;
	}

	public void setConsultantWork(Object consultantWork) {
		this.consultantWork = consultantWork;
	}

	public Object getBoqWork() {
		return boqWork;
	}

	public void setBoqWork(Object boqWork) {
		this.boqWork = boqWork;
	}

	public Object getHireMachineWork() {
		return hireMachineWork;
	}

	public void setHireMachineWork(Object hireMachineWork) {
		this.hireMachineWork = hireMachineWork;
	}

	public Object getTransportWork() {
		return transportWork;
	}

	public void setTransportWork(Object transportWork) {
		this.transportWork = transportWork;
	}

	public Object getLabourWork() {
		return labourWork;
	}

	public void setLabourWork(Object labourWork) {
		this.labourWork = labourWork;
	}

}
