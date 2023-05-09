package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;

public class WorkLayerNextPossibleStatesFetchRequest {

	private Integer workLayerId;

	private UserDetail user;

	public WorkLayerNextPossibleStatesFetchRequest() {
		super();
	}

	public Integer getWorkLayerId() {
		return workLayerId;
	}

	public void setWorkLayerId(Integer workLayerId) {
		this.workLayerId = workLayerId;
	}

	public UserDetail getUser() {
		return user;
	}

	public void setUser(UserDetail user) {
		this.user = user;
	}

}
