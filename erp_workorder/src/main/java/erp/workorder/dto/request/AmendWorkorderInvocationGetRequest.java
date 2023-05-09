package erp.workorder.dto.request;

import erp.workorder.dto.UserDetail;

public class AmendWorkorderInvocationGetRequest {
	
	private Integer pageNo;

	private Integer pageSize;

	private Long siteId;

	private UserDetail userDetail;

	public AmendWorkorderInvocationGetRequest() {
		super();
	}

	public AmendWorkorderInvocationGetRequest(Integer pageNo, Integer pageSize, Long siteId, UserDetail userDetail) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.siteId = siteId;
		this.userDetail = userDetail;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
