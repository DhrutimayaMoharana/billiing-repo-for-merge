package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;

public class BoqMasterLmpsLabourDeactivateRequest {

	private Long id;

	private UserDetail userDetail;

	public BoqMasterLmpsLabourDeactivateRequest() {
		super();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
