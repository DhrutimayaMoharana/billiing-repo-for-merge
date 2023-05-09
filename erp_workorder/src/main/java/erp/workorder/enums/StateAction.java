package erp.workorder.enums;

public enum StateAction {

	None(1), Final_Success(2), Final_Reject(3), Approval(4), Accept(5), Draft(6), Reinspection(7);

	private int value;

	StateAction(int typeId) {
		this.value = typeId;
	}

	public int getValue() {
		return value;
	}

}
