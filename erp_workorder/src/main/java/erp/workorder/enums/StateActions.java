package erp.workorder.enums;

public enum StateActions {

	None(1), FinalSuccess(2), FinalReject(3), Approval(4), Accept(5), Draft(6), Reinspection(7), Renew(8);

	private int stateActionId;

	StateActions(Integer id) {
		this.stateActionId = id;
	}

	public int getStateActionId() {
		return stateActionId;
	}

}
