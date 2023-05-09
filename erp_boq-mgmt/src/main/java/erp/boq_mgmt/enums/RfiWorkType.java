package erp.boq_mgmt.enums;

public enum RfiWorkType {

	Highway("HWY"), Structure("STR");

	private String code;

	RfiWorkType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
