package erp.billing.dto.response;

import java.util.List;

public class PriceEscalationResponseDTO {

	private Double valueFo;

	List<PriceEscalationItemsResponseDTO> items;

	private Double totalRe;

	private Double totalVe;

	public PriceEscalationResponseDTO() {
		super();
	}

	public PriceEscalationResponseDTO(Double valueFo, List<PriceEscalationItemsResponseDTO> items, Double totalRe,
			Double totalVe) {
		super();
		this.valueFo = valueFo;
		this.items = items;
		this.totalRe = totalRe;
		this.totalVe = totalVe;
	}

	public List<PriceEscalationItemsResponseDTO> getItems() {
		return items;
	}

	public void setItems(List<PriceEscalationItemsResponseDTO> items) {
		this.items = items;
	}

	public Double getTotalRe() {
		return totalRe;
	}

	public void setTotalRe(Double totalRe) {
		this.totalRe = totalRe;
	}

	public Double getTotalVe() {
		return totalVe;
	}

	public void setTotalVe(Double totalVe) {
		this.totalVe = totalVe;
	}

	public Double getValueFo() {
		return valueFo;
	}

	public void setValueFo(Double valueFo) {
		this.valueFo = valueFo;
	}

}
