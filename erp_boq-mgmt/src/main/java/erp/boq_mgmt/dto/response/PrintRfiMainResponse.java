package erp.boq_mgmt.dto.response;

import java.util.Date;
import java.util.List;

import erp.boq_mgmt.enums.ChainageSide;
import erp.boq_mgmt.enums.RfiMode;
import erp.boq_mgmt.enums.RfiWorkType;

public class PrintRfiMainResponse {

	private Long id;

	private RfiWorkType type;

	private RfiMode mode;

	private Long parentId;

	private String uniqueCode;

	private Integer stateId;

	private String stateName;

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

	private List<S3FileResponse> files;

	private List<RfiChecklistItemFetchFinalSuccessListResponse> checklistItems;

	private List<RfiMainCommentResponse> comments;

	private Date submissionDate;

	private String lastUpdatedBy;

	private Date lastUpdatedOn;

	private String clientName;

	private String clientLogoUrl;

	private String contractorLogoUrl;

	private String concessionaireName;

	private String independentEngineerName;

	private String contractorName;

	private String siteDescription;

	public PrintRfiMainResponse() {
		super();
	}

	public PrintRfiMainResponse(Long id, RfiWorkType type, RfiMode mode, Long parentId, String uniqueCode,
			Integer stateId, String stateName, WorkTypeBoqResponse boq, IdNameDTO fromChainage, IdNameDTO toChainage,
			MinimalStructureResponse structure, ChainageSide chainageSide, String chainageInfo,
			RfiCustomWorkItemResponseV2 customItem, String workDescription, Double length, Double width, Double height,
			String layerInfo, Double executableWorkQuantity, Double clientExecutedQuantity,
			Double actualExecutedQuantity, List<S3FileResponse> files,
			List<RfiChecklistItemFetchFinalSuccessListResponse> checklistItems, Date submissionDate,
			String lastUpdatedBy, Date lastUpdatedOn, String clientName, String clientLogoUrl, String contractorLogoUrl,
			String concessionaireName, String independentEngineerName, String contractorName, String siteDescription) {
		super();
		this.id = id;
		this.type = type;
		this.mode = mode;
		this.chainageSide = chainageSide;
		this.parentId = parentId;
		this.contractorLogoUrl = contractorLogoUrl;
		this.uniqueCode = uniqueCode;
		this.stateId = stateId;
		this.stateName = stateName;
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
		this.files = files;
		this.checklistItems = checklistItems;
		this.submissionDate = submissionDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedOn = lastUpdatedOn;
		this.clientName = clientName;
		this.concessionaireName = concessionaireName;
		this.independentEngineerName = independentEngineerName;
		this.contractorName = contractorName;
		this.siteDescription = siteDescription;
		this.clientLogoUrl = clientLogoUrl;
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

	public String getChainageInfo() {
		return chainageInfo;
	}

	public void setChainageInfo(String chainageInfo) {
		this.chainageInfo = chainageInfo;
	}

	public WorkTypeBoqResponse getBoq() {
		return boq;
	}

	public void setBoq(WorkTypeBoqResponse boq) {
		this.boq = boq;
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

	public MinimalStructureResponse getStructure() {
		return structure;
	}

	public void setStructure(MinimalStructureResponse structure) {
		this.structure = structure;
	}

	public ChainageSide getChainageSide() {
		return chainageSide;
	}

	public void setChainageSide(ChainageSide chainageSide) {
		this.chainageSide = chainageSide;
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

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getConcessionaireName() {
		return concessionaireName;
	}

	public void setConcessionaireName(String concessionaireName) {
		this.concessionaireName = concessionaireName;
	}

	public String getIndependentEngineerName() {
		return independentEngineerName;
	}

	public void setIndependentEngineerName(String independentEngineerName) {
		this.independentEngineerName = independentEngineerName;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getSiteDescription() {
		return siteDescription;
	}

	public void setSiteDescription(String siteDescription) {
		this.siteDescription = siteDescription;
	}

	public String getClientLogoUrl() {
		return clientLogoUrl;
	}

	public void setClientLogoUrl(String clientLogoUrl) {
		this.clientLogoUrl = clientLogoUrl;
	}

	public String getContractorLogoUrl() {
		return contractorLogoUrl;
	}

	public void setContractorLogoUrl(String contractorLogoUrl) {
		this.contractorLogoUrl = contractorLogoUrl;
	}

	public List<RfiMainCommentResponse> getComments() {
		return comments;
	}

	public void setComments(List<RfiMainCommentResponse> comments) {
		this.comments = comments;
	}

}
