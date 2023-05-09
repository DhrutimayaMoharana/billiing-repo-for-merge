package erp.boq_mgmt.dto.response;

import java.util.Date;
import java.util.List;

import erp.boq_mgmt.enums.ChainageSide;
import erp.boq_mgmt.enums.RfiMode;
import erp.boq_mgmt.enums.RfiWorkType;

public class RfiMainResponse {

	private Long id;

	private RfiWorkType type;

	private RfiMode mode;

	private Long parentId;

	private String uniqueCode;

	private Integer stateId;

	private String stateName;

	private String stateRgbColorCode;

	private WorkTypeBoqResponse boq;

	private IdNameDTO fromChainage;

	private IdNameDTO toChainage;

	private MinimalStructureResponse structure;

	private ChainageSide chainageSide;

	private String chainageInfo;

	private RfiCustomWorkItemResponseV2 customItem;

	private String workDescription;

	private Double length;

	private Double width;

	private Double height;

	private String layerInfo;

	private Double executableWorkQuantity;

	private Double clientExecutedQuantity;

	private Double actualExecutedQuantity;

	private WorkLayerFetchFinalSuccessListResponse workLayer;

	private Date submissionDate;

	private Date inspectionDate;

	private List<S3FileResponse> files;

	private List<RfiChecklistItemFetchFinalSuccessListResponse> checklistItems;

	private List<RfiMainCommentResponse> comments;

	private Date createdOn;

	private String lastUpdatedBy;

	private Date lastUpdatedOn;

	private Boolean isEditable;

	private Boolean isDeletable;

	private Boolean isFinalState;

	public RfiMainResponse() {
		super();
	}

	public RfiMainResponse(Long id, RfiWorkType type, RfiMode mode, Long parentId, String uniqueCode, Integer stateId,
			String stateName, String stateRgbColorCode, WorkTypeBoqResponse boq, IdNameDTO fromChainage,
			IdNameDTO toChainage, MinimalStructureResponse structure, ChainageSide chainageSide, String chainageInfo,
			RfiCustomWorkItemResponseV2 customItem, String workDescription, Double length, Double width, Double height,
			String layerInfo, Double executableWorkQuantity, Double clientExecutedQuantity,
			Double actualExecutedQuantity, WorkLayerFetchFinalSuccessListResponse workLayer, Date submissionDate,
			List<S3FileResponse> files, List<RfiChecklistItemFetchFinalSuccessListResponse> checklistItems,
			Date createdOn, String lastUpdatedBy, Date lastUpdatedOn) {
		super();
		this.id = id;
		this.type = type;
		this.mode = mode;
		this.chainageSide = chainageSide;
		this.parentId = parentId;
		this.uniqueCode = uniqueCode;
		this.stateId = stateId;
		this.stateName = stateName;
		this.stateRgbColorCode = stateRgbColorCode;
		this.setBoq(boq);
		this.fromChainage = fromChainage;
		this.toChainage = toChainage;
		this.setStructure(structure);
		this.chainageInfo = chainageInfo;
		this.customItem = customItem;
		this.workDescription = workDescription;
		this.length = length;
		this.width = width;
		this.height = height;
		this.layerInfo = layerInfo;
		this.executableWorkQuantity = executableWorkQuantity;
		this.clientExecutedQuantity = clientExecutedQuantity;
		this.actualExecutedQuantity = actualExecutedQuantity;
		this.workLayer = workLayer;
		this.submissionDate = submissionDate;
		this.files = files;
		this.checklistItems = checklistItems;
		this.createdOn = createdOn;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RfiWorkType getType() {
		return type;
	}

	public void setType(RfiWorkType type) {
		this.type = type;
	}

	public RfiMode getMode() {
		return mode;
	}

	public void setMode(RfiMode mode) {
		this.mode = mode;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
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

	public String getStateRgbColorCode() {
		return stateRgbColorCode;
	}

	public void setStateRgbColorCode(String stateRgbColorCode) {
		this.stateRgbColorCode = stateRgbColorCode;
	}

	public IdNameDTO getFromChainage() {
		return fromChainage;
	}

	public void setFromChainage(IdNameDTO fromChainage) {
		this.fromChainage = fromChainage;
	}

	public IdNameDTO getToChainage() {
		return toChainage;
	}

	public void setToChainage(IdNameDTO toChainage) {
		this.toChainage = toChainage;
	}

	public String getChainageInfo() {
		return chainageInfo;
	}

	public void setChainageInfo(String chainageInfo) {
		this.chainageInfo = chainageInfo;
	}

	public RfiCustomWorkItemResponseV2 getCustomItem() {
		return customItem;
	}

	public void setCustomItem(RfiCustomWorkItemResponseV2 customItem) {
		this.customItem = customItem;
	}

	public String getWorkDescription() {
		return workDescription;
	}

	public void setWorkDescription(String workDescription) {
		this.workDescription = workDescription;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public String getLayerInfo() {
		return layerInfo;
	}

	public void setLayerInfo(String layerInfo) {
		this.layerInfo = layerInfo;
	}

	public Double getExecutableWorkQuantity() {
		return executableWorkQuantity;
	}

	public void setExecutableWorkQuantity(Double executableWorkQuantity) {
		this.executableWorkQuantity = executableWorkQuantity;
	}

	public Double getClientExecutedQuantity() {
		return clientExecutedQuantity;
	}

	public void setClientExecutedQuantity(Double clientExecutedQuantity) {
		this.clientExecutedQuantity = clientExecutedQuantity;
	}

	public Double getActualExecutedQuantity() {
		return actualExecutedQuantity;
	}

	public void setActualExecutedQuantity(Double actualExecutedQuantity) {
		this.actualExecutedQuantity = actualExecutedQuantity;
	}

	public WorkLayerFetchFinalSuccessListResponse getWorkLayer() {
		return workLayer;
	}

	public void setWorkLayer(WorkLayerFetchFinalSuccessListResponse workLayer) {
		this.workLayer = workLayer;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public Date getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	public List<S3FileResponse> getFiles() {
		return files;
	}

	public void setFiles(List<S3FileResponse> files) {
		this.files = files;
	}

	public List<RfiChecklistItemFetchFinalSuccessListResponse> getChecklistItems() {
		return checklistItems;
	}

	public void setChecklistItems(List<RfiChecklistItemFetchFinalSuccessListResponse> checklistItems) {
		this.checklistItems = checklistItems;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public Boolean getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}

	public Boolean getIsDeletable() {
		return isDeletable;
	}

	public void setIsDeletable(Boolean isDeletable) {
		this.isDeletable = isDeletable;
	}

	public Boolean getIsFinalState() {
		return isFinalState;
	}

	public void setIsFinalState(Boolean isFinalState) {
		this.isFinalState = isFinalState;
	}

	public MinimalStructureResponse getStructure() {
		return structure;
	}

	public void setStructure(MinimalStructureResponse structure) {
		this.structure = structure;
	}

	public WorkTypeBoqResponse getBoq() {
		return boq;
	}

	public void setBoq(WorkTypeBoqResponse boq) {
		this.boq = boq;
	}

	public ChainageSide getChainageSide() {
		return chainageSide;
	}

	public void setChainageSide(ChainageSide chainageSide) {
		this.chainageSide = chainageSide;
	}

	public List<RfiMainCommentResponse> getComments() {
		return comments;
	}

	public void setComments(List<RfiMainCommentResponse> comments) {
		this.comments = comments;
	}

}
