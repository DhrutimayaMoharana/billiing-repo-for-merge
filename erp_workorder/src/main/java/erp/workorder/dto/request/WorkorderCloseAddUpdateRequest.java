package erp.workorder.dto.request;

import java.util.Date;

import erp.workorder.dto.UserDetail;
import erp.workorder.enums.WorkorderCloseType;

public class WorkorderCloseAddUpdateRequest {

	private Long id;

	private Long workorderId;

	private WorkorderCloseType closeType;

	private Date dated;

	private String remarks;

	private Long siteId;

	private UserDetail userDetail;

	public WorkorderCloseAddUpdateRequest() {
		super();
	}

	public WorkorderCloseAddUpdateRequest(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	public WorkorderCloseType getCloseType() {
		return closeType;
	}

	public void setCloseType(WorkorderCloseType closeType) {
		this.closeType = closeType;
	}

	public Date getDated() {
		return dated;
	}

	public void setDated(Date dated) {
		this.dated = dated;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
