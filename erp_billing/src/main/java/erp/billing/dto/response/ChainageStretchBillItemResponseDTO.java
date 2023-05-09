package erp.billing.dto.response;

public class ChainageStretchBillItemResponseDTO {

	private Long id;

	private Long billItemId;

	private IdNameResponseDTO fromChainage;

	private IdNameResponseDTO toChainage;

	private Double quantity;

	public ChainageStretchBillItemResponseDTO() {
		super();
	}

	public ChainageStretchBillItemResponseDTO(Long id, Long billItemId, IdNameResponseDTO fromChainage, IdNameResponseDTO toChainage,
			Double quantity) {
		super();
		this.id = id;
		this.billItemId = billItemId;
		this.fromChainage = fromChainage;
		this.toChainage = toChainage;
		this.quantity = quantity;
	}

	public IdNameResponseDTO getFromChainage() {
		return fromChainage;
	}

	public void setFromChainage(IdNameResponseDTO fromChainage) {
		this.fromChainage = fromChainage;
	}

	public IdNameResponseDTO getToChainage() {
		return toChainage;
	}

	public void setToChainage(IdNameResponseDTO toChainage) {
		this.toChainage = toChainage;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Long getBillItemId() {
		return billItemId;
	}

	public void setBillItemId(Long billItemId) {
		this.billItemId = billItemId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
