package erp.boq_mgmt.dto.request;

import java.util.Date;
import java.util.List;

import erp.boq_mgmt.dto.UserDetail;

public class StructureDocumentRequest {

	private Long id;

	private Long structureId;

	private Integer typeId;

	private Integer subtypeId;

	private String subtypeName;

	private String reference;

	private Integer docStatusId;

	private String docStatusName;

	private Date date;

	private String remark;

	private List<Long> fileIds;

	private Long siteId;

	private UserDetail user;

	public StructureDocumentRequest() {
		super();
	}

	public StructureDocumentRequest(Long id, Long structureId, Integer typeId, Integer subtypeId, String subtypeName,
			String reference, Integer docStatusId, String docStatusName, Date date, String remark, List<Long> fileIds,
			Long siteId, UserDetail user) {
		super();
		this.id = id;
		this.structureId = structureId;
		this.typeId = typeId;
		this.subtypeId = subtypeId;
		this.subtypeName = subtypeName;
		this.reference = reference;
		this.docStatusId = docStatusId;
		this.docStatusName = docStatusName;
		this.date = date;
		this.remark = remark;
		this.fileIds = fileIds;
		this.siteId = siteId;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStructureId() {
		return structureId;
	}

	public void setStructureId(Long structureId) {
		this.structureId = structureId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getSubtypeId() {
		return subtypeId;
	}

	public void setSubtypeId(Integer subtypeId) {
		this.subtypeId = subtypeId;
	}

	public String getSubtypeName() {
		return subtypeName;
	}

	public void setSubtypeName(String subtypeName) {
		this.subtypeName = subtypeName;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Integer getDocStatusId() {
		return docStatusId;
	}

	public void setDocStatusId(Integer docStatusId) {
		this.docStatusId = docStatusId;
	}

	public String getDocStatusName() {
		return docStatusName;
	}

	public void setDocStatusName(String docStatusName) {
		this.docStatusName = docStatusName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Long> getFileIds() {
		return fileIds;
	}

	public void setFileIds(List<Long> fileIds) {
		this.fileIds = fileIds;
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

}
