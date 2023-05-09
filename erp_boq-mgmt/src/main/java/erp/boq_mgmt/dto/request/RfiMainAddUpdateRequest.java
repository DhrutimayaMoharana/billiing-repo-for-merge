package erp.boq_mgmt.dto.request;

import java.util.List;

import erp.boq_mgmt.dto.UserDetail;
import erp.boq_mgmt.enums.ChainageSide;
import erp.boq_mgmt.enums.RfiMode;
import erp.boq_mgmt.enums.RfiWorkType;

public class RfiMainAddUpdateRequest {

	private Long id;

	private RfiWorkType type;

	private RfiMode mode;

	private Long parentId;

	private Integer stateId;

	private String stateRemark;

	private Long boqId;

	private Long fromChainageId;

	private Long toChainageId;

	private Long structureId;

	private ChainageSide chainageSide;

	private String chainageInfo;

	private Long customItemId;

	private String workDescription;

	private Double length;

	private Double width;

	private Double height;

	private String layerInfo;

	private Integer workLayerId;

	private Double executableWorkQuantity;

	private Double clientExecutedQuantity;

	private Double actualExecutedQuantity;

	private List<Long> s3FileIds;

	private List<Integer> checklistItemIds;

	private List<RfiMainCommentAddUpdateRequest> rfiMainComments;

	private Integer siteId;

	private UserDetail userDetail;

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

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getStateRemark() {
		return stateRemark;
	}

	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
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

	public Long getCustomItemId() {
		return customItemId;
	}

	public void setCustomItemId(Long customItemId) {
		this.customItemId = customItemId;
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

	public Integer getWorkLayerId() {
		return workLayerId;
	}

	public void setWorkLayerId(Integer workLayerId) {
		this.workLayerId = workLayerId;
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

	public List<Long> getS3FileIds() {
		return s3FileIds;
	}

	public void setS3FileIds(List<Long> s3FileIds) {
		this.s3FileIds = s3FileIds;
	}

	public List<Integer> getChecklistItemIds() {
		return checklistItemIds;
	}

	public void setChecklistItemIds(List<Integer> checklistItemIds) {
		this.checklistItemIds = checklistItemIds;
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

	public String getChainageInfo() {
		return chainageInfo;
	}

	public void setChainageInfo(String chainageInfo) {
		this.chainageInfo = chainageInfo;
	}

	public ChainageSide getChainageSide() {
		return chainageSide;
	}

	public void setChainageSide(ChainageSide chainageSide) {
		this.chainageSide = chainageSide;
	}

	public List<RfiMainCommentAddUpdateRequest> getRfiMainComments() {
		return rfiMainComments;
	}

	public void setRfiMainComments(List<RfiMainCommentAddUpdateRequest> rfiMainComments) {
		this.rfiMainComments = rfiMainComments;
	}

}
