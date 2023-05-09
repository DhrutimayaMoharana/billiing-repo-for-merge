package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;

public class BoqCostDefinitionDeactivateRequest {

	private Long id;

	private Integer siteId;

	private UserDetail userDetail;

	public BoqCostDefinitionDeactivateRequest() {
		super();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
