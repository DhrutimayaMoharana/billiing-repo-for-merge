package erp.boq_mgmt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import erp.boq_mgmt.enums.ChainageSide;
import erp.boq_mgmt.enums.RfiMode;
import erp.boq_mgmt.enums.RfiWorkType;

@Entity
@Table(name = "rfi_main")
public class RfiMain implements Serializable {

	private static final long serialVersionUID = 4712902979447751380L;

	private Long id;

	private RfiWorkType type;

	private RfiMode mode;

	private Long parentId;

	private String uniqueCode;

	private Integer stateId;

	private Long boqId;

	private Long fromChainageId;

	private Long toChainageId;

	private Long structureId;

	private ChainageSide side;

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

	private Date submissionDate;

	private Integer siteId;

	private Boolean isActive;

	private Date createdOn;

	private Integer createdBy;

	private Date updatedOn;

	private Integer updatedBy;

	private ChainageTraverse fromChainage;

	private ChainageTraverse toChainage;

	private Structure structure;

	private EngineState state;

	private BoqItem boq;

	private RfiCustomWorkItems customWorkItem;

	private WorkLayer workLayer;

	private SitesV2 site;

	private User updatedByObj;

	public RfiMain() {
		super();
	}

	public RfiMain(Long id, RfiWorkType type, RfiMode mode, Long parentId, String uniqueCode, Integer stateId,
			Long boqId, Long fromChainageId, Long toChainageId, Long structureId,

			ChainageSide side, String chainageInfo, Long customItemId, String workDescription, Double length,
			Double width, Double height, String layerInfo, Integer workLayerId, Double executableWorkQuantity,
			Double clientExecutedQuantity, Double actualExecutedQuantity, Date submissionDate, Integer siteId,
			Boolean isActive, Date createdOn, Integer createdBy, Date updatedOn, Integer updatedBy) {
		super();
		this.id = id;
		this.type = type;
		this.mode = mode;
		this.side = side;
		this.parentId = parentId;
		this.uniqueCode = uniqueCode;
		this.stateId = stateId;
		this.boqId = boqId;
		this.fromChainageId = fromChainageId;
		this.toChainageId = toChainageId;
		this.structureId = structureId;
		this.chainageInfo = chainageInfo;
		this.customItemId = customItemId;
		this.workDescription = workDescription;
		this.length = length;
		this.width = width;
		this.height = height;
		this.layerInfo = layerInfo;
		this.workLayerId = workLayerId;
		this.setExecutableWorkQuantity(executableWorkQuantity);
		this.clientExecutedQuantity = clientExecutedQuantity;
		this.actualExecutedQuantity = actualExecutedQuantity;
		this.submissionDate = submissionDate;
		this.siteId = siteId;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "type")
	public RfiWorkType getType() {
		return type;
	}

	public void setType(RfiWorkType type) {
		this.type = type;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "mode")
	public RfiMode getMode() {
		return mode;
	}

	public void setMode(RfiMode mode) {
		this.mode = mode;
	}

	@Column(name = "parent_id")
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(name = "unique_code")
	public String getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	@Column(name = "engine_state_id")
	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	@Column(name = "boq_id")
	public Long getBoqId() {
		return boqId;
	}

	public void setBoqId(Long boqId) {
		this.boqId = boqId;
	}

	@Column(name = "from_chainage_id")
	public Long getFromChainageId() {
		return fromChainageId;
	}

	public void setFromChainageId(Long fromChainageId) {
		this.fromChainageId = fromChainageId;
	}

	@Column(name = "to_chainage_id")
	public Long getToChainageId() {
		return toChainageId;
	}

	public void setToChainageId(Long toChainageId) {
		this.toChainageId = toChainageId;
	}

	@Column(name = "structure_id")
	public Long getStructureId() {
		return structureId;
	}

	public void setStructureId(Long structureId) {
		this.structureId = structureId;
	}

	@Column(name = "custom_work_item_id")
	public Long getCustomItemId() {
		return customItemId;
	}

	public void setCustomItemId(Long customItemId) {
		this.customItemId = customItemId;
	}

	@Column(name = "work_description")
	public String getWorkDescription() {
		return workDescription;
	}

	public void setWorkDescription(String workDescription) {
		this.workDescription = workDescription;
	}

	@Column(name = "length")
	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	@Column(name = "width")
	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	@Column(name = "height")
	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	@Column(name = "layer_info")
	public String getLayerInfo() {
		return layerInfo;
	}

	public void setLayerInfo(String layerInfo) {
		this.layerInfo = layerInfo;
	}

	@Column(name = "work_layer_id")
	public Integer getWorkLayerId() {
		return workLayerId;
	}

	public void setWorkLayerId(Integer workLayerId) {
		this.workLayerId = workLayerId;
	}

	@Column(name = "executable_work_quantity")
	public Double getExecutableWorkQuantity() {
		return executableWorkQuantity;
	}

	public void setExecutableWorkQuantity(Double executableWorkQuantity) {
		this.executableWorkQuantity = executableWorkQuantity;
	}

	@Column(name = "client_executed_quantity")
	public Double getClientExecutedQuantity() {
		return clientExecutedQuantity;
	}

	public void setClientExecutedQuantity(Double clientExecutedQuantity) {
		this.clientExecutedQuantity = clientExecutedQuantity;
	}

	@Column(name = "actual_executed_quantity")
	public Double getActualExecutedQuantity() {
		return actualExecutedQuantity;
	}

	public void setActualExecutedQuantity(Double actualExecutedQuantity) {
		this.actualExecutedQuantity = actualExecutedQuantity;
	}

	@Column(name = "submission_date")
	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	@Column(name = "site_id")
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "created_by")
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "updated_on")
	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(name = "updated_by")
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "chainage_info")
	public String getChainageInfo() {
		return chainageInfo;
	}

	public void setChainageInfo(String chainageInfo) {
		this.chainageInfo = chainageInfo;
	}

	@OneToOne
	@JoinColumn(name = "from_chainage_id", insertable = false, updatable = false)
	public ChainageTraverse getFromChainage() {
		return fromChainage;
	}

	public void setFromChainage(ChainageTraverse fromChainage) {
		this.fromChainage = fromChainage;
	}

	@OneToOne
	@JoinColumn(name = "to_chainage_id", insertable = false, updatable = false)
	public ChainageTraverse getToChainage() {
		return toChainage;
	}

	public void setToChainage(ChainageTraverse toChainage) {
		this.toChainage = toChainage;
	}

	@OneToOne
	@JoinColumn(name = "engine_state_id", insertable = false, updatable = false)
	public EngineState getState() {
		return state;
	}

	public void setState(EngineState state) {
		this.state = state;
	}

	@OneToOne
	@JoinColumn(name = "boq_id", insertable = false, updatable = false)
	public BoqItem getBoq() {
		return boq;
	}

	public void setBoq(BoqItem boq) {
		this.boq = boq;
	}

	@OneToOne
	@JoinColumn(name = "custom_work_item_id", insertable = false, updatable = false)
	public RfiCustomWorkItems getCustomWorkItem() {
		return customWorkItem;
	}

	public void setCustomWorkItem(RfiCustomWorkItems customWorkItem) {
		this.customWorkItem = customWorkItem;
	}

	@OneToOne
	@JoinColumn(name = "updated_by", insertable = false, updatable = false)
	public User getUpdatedByObj() {
		return updatedByObj;
	}

	public void setUpdatedByObj(User updatedByObj) {
		this.updatedByObj = updatedByObj;
	}

	@OneToOne
	@JoinColumn(name = "site_id", insertable = false, updatable = false)
	public SitesV2 getSite() {
		return site;
	}

	public void setSite(SitesV2 site) {
		this.site = site;
	}

	@OneToOne
	@JoinColumn(name = "structure_id", insertable = false, updatable = false)
	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "chainage_side")
	public ChainageSide getSide() {
		return side;
	}

	public void setSide(ChainageSide side) {
		this.side = side;
	}

	@OneToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "work_layer_id", insertable = false, updatable = false)
	public WorkLayer getWorkLayer() {
		return workLayer;
	}

	public void setWorkLayer(WorkLayer workLayer) {
		this.workLayer = workLayer;
	}

}
