package erp.boq_mgmt.dto;

public class ChainageBoqQtyImportDTO {

	private String chainage;

	private Double LHS;

	private Double RHS;

	private String structureRemark;

	public ChainageBoqQtyImportDTO() {
		super();
	}

	public ChainageBoqQtyImportDTO(String chainage, Double lHS, Double rHS, String structureRemark) {
		super();
		this.chainage = chainage;
		LHS = lHS;
		RHS = rHS;
		this.structureRemark = structureRemark;
	}

	public String getChainage() {
		return chainage;
	}

	public void setChainage(String chainage) {
		this.chainage = chainage;
	}

	public Double getLHS() {
		return LHS;
	}

	public void setLHS(Double lHS) {
		LHS = lHS;
	}

	public Double getRHS() {
		return RHS;
	}

	public void setRHS(Double rHS) {
		RHS = rHS;
	}

	public String getStructureRemark() {
		return structureRemark;
	}

	public void setStructureRemark(String structureRemark) {
		this.structureRemark = structureRemark;
	}

}
