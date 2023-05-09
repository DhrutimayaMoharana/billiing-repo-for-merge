package erp.workorder.dto.request;

public class BillDeductionMappingDTO {

	private Long id;

	private Integer deductionId;

	private String deductionName;

	private Boolean isPercentage;

	private Double value;

	public BillDeductionMappingDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDeductionId() {
		return deductionId;
	}

	public void setDeductionId(Integer deductionId) {
		this.deductionId = deductionId;
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
