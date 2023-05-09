package erp.workorder.enums;

public enum EngineStates {

	Draft(1), Wait_for_Approval(2), Approved(3), Issued(23), Expired(38);

	private int value;

	EngineStates(Integer value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
