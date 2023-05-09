package erp.billing.enums;

public enum EngineStateActions {

	None(1), Final_Success(2), Final_Reject(3);

	private int stateActionId;

	EngineStateActions(Integer stateActionId) {
		this.stateActionId = stateActionId;
	}

	public int getStateActionId() {
		return stateActionId;
	}

}
