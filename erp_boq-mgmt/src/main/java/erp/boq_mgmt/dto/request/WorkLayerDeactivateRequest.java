package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;

public class WorkLayerDeactivateRequest {

	private Integer id;

	private UserDetail userDetail;

	public WorkLayerDeactivateRequest() {
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
