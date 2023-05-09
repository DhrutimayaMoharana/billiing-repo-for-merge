package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;

public class RfiCustomWorkItemNextPossibleStatesFetchRequest {

	private Long rfiCustomWorkItemId;

	private UserDetail user;

	public RfiCustomWorkItemNextPossibleStatesFetchRequest() {
		super();
	}

	public Long getRfiCustomWorkItemId() {
		return rfiCustomWorkItemId;
	}

	public void setRfiCustomWorkItemId(Long rfiCustomWorkItemId) {
		this.rfiCustomWorkItemId = rfiCustomWorkItemId;
	}

	public UserDetail getUser() {
		return user;
	}

	public void setUser(UserDetail user) {
		this.user = user;
	}

}
