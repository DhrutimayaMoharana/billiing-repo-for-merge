package erp.billing.feignClient.dto;

import erp.billing.dto.UserDetail;

public class IrnCancelRequest {

	private String irn;

	private String cnlRsn;

	private String cnlRem;

	private Integer siteId;

	private UserDetail userDetail;

	public IrnCancelRequest(String irn, String cnlRsn, String cnlRem, UserDetail userDetail) {
		super();
		this.irn = irn;
		this.cnlRsn = cnlRsn;
		this.cnlRem = cnlRem;
		this.userDetail = userDetail;
	}

	public String getIrn() {
		return irn;
	}

	public void setIrn(String irn) {
		this.irn = irn;
	}

	public String getCnlRsn() {
		return cnlRsn;
	}

	public void setCnlRsn(String cnlRsn) {
		this.cnlRsn = cnlRsn;
	}

	public String getCnlRem() {
		return cnlRem;
	}

	public void setCnlRem(String cnlRem) {
		this.cnlRem = cnlRem;
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
