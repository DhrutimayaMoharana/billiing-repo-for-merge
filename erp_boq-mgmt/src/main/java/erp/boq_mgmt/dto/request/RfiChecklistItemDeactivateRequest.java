package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;

public class RfiChecklistItemDeactivateRequest {

	private Integer id;

	private UserDetail userDetail;

	public RfiChecklistItemDeactivateRequest() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
