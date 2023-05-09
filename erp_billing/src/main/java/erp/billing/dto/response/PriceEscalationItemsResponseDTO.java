package erp.billing.dto.response;

public class PriceEscalationItemsResponseDTO {

	private String month;

	private Double valueRe;

	private Double valueFi;

	private Double valueVe;

	public PriceEscalationItemsResponseDTO() {
		super();
	}

	public PriceEscalationItemsResponseDTO(String month, Double valueRe, Double valueFi, Double valueVe) {
		super();
		this.month = month;
		this.valueRe = valueRe;
		this.valueFi = valueFi;
		this.valueVe = valueVe;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Double getValueRe() {
		return valueRe;
	}

	public void setValueRe(Double valueRe) {
		this.valueRe = valueRe;
	}

	public Double getValueFi() {
		return valueFi;
	}

	public void setValueFi(Double valueFi) {
		this.valueFi = valueFi;
	}

	public Double getValueVe() {
		return valueVe;
	}

	public void setValueVe(Double valueVe) {
		this.valueVe = valueVe;
	}

}
