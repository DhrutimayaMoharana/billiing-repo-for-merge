package erp.workorder.dto.request;

import java.util.Date;

import erp.workorder.dto.UserDetail;

public class AmendWorkorderInvocationAddUpdateRequest {

	private Long id;

	private Long workorderId;

	private Date dated;

	private String subject;

	private Long siteId;

	private UserDetail userDetail;

	public AmendWorkorderInvocationAddUpdateRequest() {
		super();
	}

	public AmendWorkorderInvocationAddUpdateRequest(Long id) {
		super();
		this.id = id;
	}

	public AmendWorkorderInvocationAddUpdateRequest(Long id, Long workorderId, Date dated, String subject, Long siteId,
			UserDetail userDetail) {
		super();
		this.id = id;
		this.workorderId = workorderId;
		this.dated = dated;
		this.subject = subject;
		this.siteId = siteId;
		this.userDetail = userDetail;
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

	public Date getDated() {
		return dated;
	}

	public void setDated(Date dated) {
		this.dated = dated;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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
