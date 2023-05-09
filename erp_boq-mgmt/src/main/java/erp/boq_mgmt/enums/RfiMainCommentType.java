package erp.boq_mgmt.enums;

public enum RfiMainCommentType {

	CONTRACTOR("Contractor"), SITE_ENGINEER("Site Engineer"), INDEPENDENT_ENGINEER("Independent Engineer");

	private String description;

	RfiMainCommentType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
