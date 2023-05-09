package erp.workorder.dto.response;

public class BillDeductionResponseDto {

	private Long id;

	private String deductionName;

	private Boolean isPercentage;

	private Double value;

	public BillDeductionResponseDto() {
		super();
	}

	public BillDeductionResponseDto(Long id, String deductionName, Boolean isPercentage, Double value) {
		super();
		this.id = id;
		this.deductionName = deductionName;
		this.isPercentage = isPercentage;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeductionName() {
		return deductionName;
	}

	public void setDeductionName(String deductionName) {
		this.deductionName = deductionName;
	}

	public Boolean getIsPercentage() {
		return isPercentage;
	}

	public void setIsPercentage(Boolean isPercentage) {
		this.isPercentage = isPercentage;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
