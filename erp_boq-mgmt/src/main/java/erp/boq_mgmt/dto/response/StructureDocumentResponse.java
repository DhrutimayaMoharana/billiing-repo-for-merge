package erp.boq_mgmt.dto.response;

import java.util.Date;
import java.util.List;

import erp.boq_mgmt.dto.NextStateTransitDTO;
import erp.boq_mgmt.dto.S3FileDTO;

public class StructureDocumentResponse {

	private Long id;

	private Integer typeId;

	private String typeName;

	private Integer subtypeId;

	private String subtypeName;

	private String reference;

	private Integer docStatusId;

	private String docStatusName;

	private Date date;

	private String remark;

	private Integer stateId;

	private String stateName;

	private List<S3FileDTO> files;

	private Boolean isEditable;

	private List<NextStateTransitDTO> nextStates;

	private Boolean inFinalState;
	
	private Integer version;

	public StructureDocumentResponse() {
		super();
	}

	public StructureDocumentResponse(Long id, Integer typeId, String typeName, Integer subtypeId, String subtypeName,
			String reference, Integer docStatusId, String docStatusName, Date date, String remark, Integer stateId,
			String stateName, List<S3FileDTO> files, Boolean isEditable, List<NextStateTransitDTO> nextStates,
			Boolean inFinalState, Integer version) {
		super();
		this.id = id;
		this.typeId = typeId;
		this.typeName = typeName;
		this.subtypeId = subtypeId;
		this.subtypeName = subtypeName;
		this.reference = reference;
		this.docStatusId = docStatusId;
		this.docStatusName = docStatusName;
		this.date = date;
		this.remark = remark;
		this.stateId = stateId;
		this.stateName = stateName;
		this.files = files;
		this.isEditable = isEditable;
		this.nextStates = nextStates;
		this.inFinalState = inFinalState;
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<S3FileDTO> getFiles() {
		return files;
	}

	public void setFiles(List<S3FileDTO> files) {
		this.files = files;
	}

	public Boolean getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}

	public List<NextStateTransitDTO> getNextStates() {
		return nextStates;
	}

	public void setNextStates(List<NextStateTransitDTO> nextStates) {
		this.nextStates = nextStates;
	}

	public Boolean getInFinalState() {
		return inFinalState;
	}

	public void setInFinalState(Boolean inFinalState) {
		this.inFinalState = inFinalState;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
