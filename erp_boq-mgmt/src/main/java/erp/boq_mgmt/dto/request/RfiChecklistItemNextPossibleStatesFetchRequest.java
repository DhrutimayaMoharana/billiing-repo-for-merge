package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;

public class RfiChecklistItemNextPossibleStatesFetchRequest {

	private Integer rfiChecklistItemId;

	private UserDetail user;

	public RfiChecklistItemNextPossibleStatesFetchRequest() {
		super();
	}

	public Integer getRfiChecklistItemId() {
		return rfiChecklistItemId;
	}

	public void setRfiChecklistItemId(Integer rfiChecklistItemId) {
		this.rfiChecklistItemId = rfiChecklistItemId;
	}

	public UserDetail getUser() {
		return user;
	}

	public void setUser(UserDetail user) {
		this.user = user;
	}

}
