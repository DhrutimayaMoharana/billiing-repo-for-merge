package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;

public class RfiMainNextPossibleStatesFetchRequest {

	private Long rfiMainId;

	private Integer siteId;

	private UserDetail user;

	public RfiMainNextPossibleStatesFetchRequest() {
		super();
	}

	public Long getRfiMainId() {
		return rfiMainId;
	}

	public void setRfiMainId(Long rfiMainId) {
		this.rfiMainId = rfiMainId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public UserDetail getUser() {
		return user;
	}

	public void setUser(UserDetail user) {
		this.user = user;
	}

}
