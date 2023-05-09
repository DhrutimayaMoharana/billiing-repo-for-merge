package erp.boq_mgmt.dto.request;

import java.util.List;

import erp.boq_mgmt.dto.UserDetail;

public class StructureDocumentDeactivateRequest {

	private List<Long> fileIds;

	private Long structureDocumentId;
	
	private Boolean removeFilesOnly;

	private Long siteId;
	
	private UserDetail user;

	public StructureDocumentDeactivateRequest() {
		super();
	}
	
	public List<Long> getFileIds() {
		return fileIds;
	}

	public void setFileIds(List<Long> fileIds) {
		this.fileIds = fileIds;
	}

	public Long getStructureDocumentId() {
		return structureDocumentId;
	}

	public void setStructureDocumentId(Long structureDocumentId) {
		this.structureDocumentId = structureDocumentId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public UserDetail getUser() {
		return user;
	}

	public void setUser(UserDetail user) {
		this.user = user;
	}

	public Boolean getRemoveFilesOnly() {
		return removeFilesOnly;
	}

	public void setRemoveFilesOnly(Boolean removeFilesOnly) {
		this.removeFilesOnly = removeFilesOnly;
	}

}
