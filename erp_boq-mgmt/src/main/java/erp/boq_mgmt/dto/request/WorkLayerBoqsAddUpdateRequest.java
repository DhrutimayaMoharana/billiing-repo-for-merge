package erp.boq_mgmt.dto.request;

import java.util.Set;

import javax.persistence.Column;

import erp.boq_mgmt.dto.UserDetail;

public class WorkLayerBoqsAddUpdateRequest {

	private Integer id;

	private String code;

	private String name;

	private String description;

	private Set<Long> boqItemIds;

	private Integer stateId;

	private String remark;

	private UserDetail userDetail;

	public WorkLayerBoqsAddUpdateRequest() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Long> getBoqItemIds() {
		return boqItemIds;
	}

	public void setBoqItemIds(Set<Long> boqItemIds) {
		this.boqItemIds = boqItemIds;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
