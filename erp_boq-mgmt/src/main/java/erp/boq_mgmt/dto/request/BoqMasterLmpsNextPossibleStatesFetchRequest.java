package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;

public class BoqMasterLmpsNextPossibleStatesFetchRequest {

	private Long boqMasterLmpsId;

	private UserDetail user;

	public BoqMasterLmpsNextPossibleStatesFetchRequest() {
		super();
	}

	public Long getBoqMasterLmpsId() {
		return boqMasterLmpsId;
	}

	public void setBoqMasterLmpsId(Long boqMasterLmpsId) {
		this.boqMasterLmpsId = boqMasterLmpsId;
	}

	public UserDetail getUser() {
		return user;
	}

	public void setUser(UserDetail user) {
		this.user = user;
	}

}
