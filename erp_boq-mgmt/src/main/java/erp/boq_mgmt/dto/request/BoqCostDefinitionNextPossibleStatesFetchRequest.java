package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;

public class BoqCostDefinitionNextPossibleStatesFetchRequest {

	private Long boqCostDefinitionId;

	private UserDetail user;

	public BoqCostDefinitionNextPossibleStatesFetchRequest() {
		super();
	}

	public Long getBoqCostDefinitionId() {
		return boqCostDefinitionId;
	}

	public void setBoqCostDefinitionId(Long boqCostDefinitionId) {
		this.boqCostDefinitionId = boqCostDefinitionId;
	}

	public UserDetail getUser() {
		return user;
	}

	public void setUser(UserDetail user) {
		this.user = user;
	}

}
