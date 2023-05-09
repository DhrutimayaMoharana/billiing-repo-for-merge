package erp.boq_mgmt.dto.request;

import java.util.List;

import erp.boq_mgmt.dto.UserDetail;
import erp.boq_mgmt.enums.WorkType;

public class ProjectPlanBoqAddUpdateRequest {

	private Integer siteId;

	private WorkType workType;

	private Long boqId;

	private Long structureTypeId;

	private Long structureId;

	List<ProjectPlanBoqDistributionAddUpdateRequest> distributionList;

	private UserDetail userDetail;

	public ProjectPlanBoqAddUpdateRequest() {
		super();
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public WorkType getWorkType() {
		return workType;
	}

	public void setWorkType(WorkType workType) {
		this.workType = workType;
	}

	public Long getBoqId() {
		return boqId;
	}

	public void setBoqId(Long boqId) {
		this.boqId = boqId;
	}

	public Long getStructureTypeId() {
		return structureTypeId;
	}

	public void setStructureTypeId(Long structureTypeId) {
		this.structureTypeId = structureTypeId;
	}

	public Long getStructureId() {
		return structureId;
	}

	public void setStructureId(Long structureId) {
		this.structureId = structureId;
	}

	public List<ProjectPlanBoqDistributionAddUpdateRequest> getDistributionList() {
		return distributionList;
	}

	public void setDistributionList(List<ProjectPlanBoqDistributionAddUpdateRequest> distributionList) {
		this.distributionList = distributionList;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
