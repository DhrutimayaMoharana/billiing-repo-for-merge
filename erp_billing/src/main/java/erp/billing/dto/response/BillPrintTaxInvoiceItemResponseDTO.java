package erp.billing.dto.response;

public class BillPrintTaxInvoiceItemResponseDTO {
	
	private Integer srNo;
	
	private String particularDescription;
	
	private Double amount;

	public BillPrintTaxInvoiceItemResponseDTO() {
		super();
	}

	public BillPrintTaxInvoiceItemResponseDTO(Integer srNo, String particularDescription, Double amount) {
		super();
		this.srNo = srNo;
		this.particularDescription = particularDescription;
		this.amount = amount;
	}

	public Integer getSrNo() {
		return srNo;
	}

	public void setSrNo(Integer srNo) {
		this.srNo = srNo;
	}

	public String getParticularDescription() {
		return particularDescription;
	}

	public void setParticularDescription(String particularDescription) {
		this.particularDescription = particularDescription;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
