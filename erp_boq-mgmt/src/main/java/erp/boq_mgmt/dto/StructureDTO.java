package erp.boq_mgmt.dto;

import java.util.Date;

public class StructureDTO {

	private Long id;

	private Integer groupId;

	private String name;

	private StructureTypeDTO type;

	private ChainageDTO startChainage;

	private ChainageDTO endChainage;

	private Long siteId;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

	private Integer companyId;

	private String groupName;
	
	private Integer structureCount;
	
	private Boolean isGroupBased;

	public StructureDTO() {
		super();
	}

	public StructureDTO(Long id, Integer groupId, String name, StructureTypeDTO type, ChainageDTO startChainage,
			ChainageDTO endChainage, Long siteId, Boolean isActive, Date createdOn, Long createdBy, Date modifiedOn,
			Long modifiedBy, Integer companyId, String groupName) {
		super();
		this.id = id;
		this.groupId = groupId;
		this.name = name;
		this.setType(type);
		this.startChainage = startChainage;
		this.endChainage = endChainage;
		this.siteId = siteId;
		this.setIsActive(isActive);
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
		this.companyId = companyId;
		this.groupName = groupName;
	}

	public StructureDTO(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public StructureTypeDTO getType() {
		return type;
	}

	public void setType(StructureTypeDTO type) {
		this.type = type;
	}

	public ChainageDTO getStartChainage() {
		return startChainage;
	}

	public void setStartChainage(ChainageDTO startChainage) {
		this.startChainage = startChainage;
	}

	public ChainageDTO getEndChainage() {
		return endChainage;
	}

	public void setEndChainage(ChainageDTO endChainage) {
		this.endChainage = endChainage;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getStructureCount() {
		return structureCount;
	}

	public void setStructureCount(Integer structureCount) {
		this.structureCount = structureCount;
	}

	public Boolean getIsGroupBased() {
		return isGroupBased;
	}

	public void setIsGroupBased(Boolean isGroupBased) {
		this.isGroupBased = isGroupBased;
	}

}
