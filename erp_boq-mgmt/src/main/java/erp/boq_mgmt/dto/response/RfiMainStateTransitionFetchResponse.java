package erp.boq_mgmt.dto.response;

import java.util.Date;

public class RfiMainStateTransitionFetchResponse {

	private Long id;

	private Integer stateId;

	private String stateName;

	private Date createdOn;

	private Long userId;

	private String userName;

	private String userCode;

	private String remark;

	public RfiMainStateTransitionFetchResponse() {
		super();
	}

	public RfiMainStateTransitionFetchResponse(Long id, Integer stateId, String stateName, Date createdOn,
			Long userId, String userName, String userCode, String remark) {
		super();
		this.id = id;
		this.stateId = stateId;
		this.stateName = stateName;
		this.createdOn = createdOn;
		this.userId = userId;
		this.userName = userName;
		this.userCode = userCode;
		this.remark = remark;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
