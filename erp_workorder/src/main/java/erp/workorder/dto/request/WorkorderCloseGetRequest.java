package erp.workorder.dto.request;

import erp.workorder.dto.UserDetail;
import erp.workorder.enums.WorkorderCloseType;

public class WorkorderCloseGetRequest {

	private Integer pageNo;

	private Integer pageSize;

	private Long siteId;

	private WorkorderCloseType closeType;

	private UserDetail userDetail;

	public WorkorderCloseGetRequest() {
		super();
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

	public WorkorderCloseType getCloseType() {
		return closeType;
	}

	public void setCloseType(WorkorderCloseType closeType) {
		this.closeType = closeType;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
