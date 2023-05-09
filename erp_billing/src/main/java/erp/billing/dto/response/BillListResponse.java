package erp.billing.dto.response;

import java.util.List;

import erp.billing.dto.BillDTO;

public class BillListResponse {

	private List<BillDTO> data;

	private Double issuedAmount;

	public BillListResponse() {
		super();
	}

	public BillListResponse(List<BillDTO> data, Double issuedAmount) {
		super();
		this.setData(data);
		this.issuedAmount = issuedAmount;
	}

	public Double getIssuedAmount() {
		return issuedAmount;
	}

	public void setIssuedAmount(Double issuedAmount) {
		this.issuedAmount = issuedAmount;
	}

	public List<BillDTO> getData() {
		return data;
	}

	public void setData(List<BillDTO> data) {
		this.data = data;
	}

}
