package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;
import erp.boq_mgmt.enums.RfiMode;
import erp.boq_mgmt.enums.RfiWorkType;

public class RfiBoqGetExecutableQuantityRequest {

	private RfiMode rfiMode;

	private RfiWorkType rfiWorkType;

	private Long rfiMainId;

	private Long boqId;

	private Integer workLayerId;

	private Long fromChainageId;

	private Long toChainageId;

	private Long structureId;

	private Long siteId;

	private UserDetail userDetail;

	public RfiBoqGetExecutableQuantityRequest() {
		super();
	}

	public RfiBoqGetExecutableQuantityRequest(RfiMode rfiMode, RfiWorkType rfiWorkType, Long rfiMainId, Long boqId,
			Integer workLayerId, Long fromChainageId, Long toChainageId, Long structureId, Long siteId,
			UserDetail userDetail) {
		super();
		this.rfiMode = rfiMode;
		this.rfiWorkType = rfiWorkType;
		this.rfiMainId = rfiMainId;
		this.boqId = boqId;
		this.workLayerId = workLayerId;
		this.fromChainageId = fromChainageId;
		this.toChainageId = toChainageId;
		this.structureId = structureId;
		this.siteId = siteId;
		this.userDetail = userDetail;
	}

	public Long getBoqId() {
		return boqId;
	}

	public void setBoqId(Long boqId) {
		this.boqId = boqId;
	}

	public Long getFromChainageId() {
		return fromChainageId;
	}

	public void setFromChainageId(Long fromChainageId) {
		this.fromChainageId = fromChainageId;
	}

	public Long getToChainageId() {
		return toChainageId;
	}

	public void setToChainageId(Long toChainageId) {
		this.toChainageId = toChainageId;
	}

	public Long getStructureId() {
		return structureId;
	}

	public void setStructureId(Long structureId) {
		this.structureId = structureId;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	public RfiMode getRfiMode() {
		return rfiMode;
	}

	public void setRfiMode(RfiMode rfiMode) {
		this.rfiMode = rfiMode;
	}

	public RfiWorkType getRfiWorkType() {
		return rfiWorkType;
	}

	public void setRfiWorkType(RfiWorkType rfiWorkType) {
		this.rfiWorkType = rfiWorkType;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Long getRfiMainId() {
		return rfiMainId;
	}

	public void setRfiMainId(Long rfiMainId) {
		this.rfiMainId = rfiMainId;
	}

	public Integer getWorkLayerId() {
		return workLayerId;
	}

	public void setWorkLayerId(Integer workLayerId) {
		this.workLayerId = workLayerId;
	}

}
