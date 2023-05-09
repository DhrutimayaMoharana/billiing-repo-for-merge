package erp.boq_mgmt.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class SearchDTO {

	private List<Long> idsArr;

	private Long id;

	private Long siteId;

	private Integer companyId;

	private String description;

	private Long boqId;

	private Long highwayBoqId;

	private Long borewellBoqId;

	private Long genericBoqId;

	private Boolean status;

	private Boolean isActive;

	private Long catBoqId;

	private Long userId;

	private Integer entityId;

	private Long cbqId;

	private Integer pageSize;

	private Integer pageNo;

	private String searchField;

	private Long chainageId;

	private Long fromChainageId;

	private Long toChainageId;

	private Long structureId;

	private Long categoryId;

	private Long subcategoryId;

	private Long contractorId;

	private Long typeId;

	private String name;

	private Long unitId;

	private MultipartFile[] files;

	private String rootpath;

	private String code;

	private String standardBookCode;

	private Boolean isChainageNotNull;

	private String vendorDescription;

	private Integer roleId;

	private Integer documentSubtypeId;

	private Integer docStatusId;

	private Long structureDocumentId;

	private Integer version;

	private Integer stateId;

	private Long structureTypeId;

	private Integer structureGroupId;

	private Integer workType;

	private Integer unitTypeId;

	private Integer workOrderTypeId;

	public SearchDTO() {
		super();
	}

	public SearchDTO(Long siteId, Integer roleId, Integer entityId) {
		super();
		this.siteId = siteId;
		this.roleId = roleId;
		this.entityId = entityId;
	}

	public List<Long> getIdsArr() {
		return idsArr;
	}

	public void setIdsArr(List<Long> idsArr) {
		this.idsArr = idsArr;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Long getBoqId() {
		return boqId;
	}

	public void setBoqId(Long boqId) {
		this.boqId = boqId;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public Long getChainageId() {
		return chainageId;
	}

	public void setChainageId(Long chainageId) {
		this.chainageId = chainageId;
	}

	public Long getCbqId() {
		return cbqId;
	}

	public void setCbqId(Long cbqId) {
		this.cbqId = cbqId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public Long getStructureId() {
		return structureId;
	}

	public void setStructureId(Long structureId) {
		this.structureId = structureId;
	}

	public Long getCatBoqId() {
		return catBoqId;
	}

	public void setCatBoqId(Long catBoqId) {
		this.catBoqId = catBoqId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getSubcategoryId() {
		return subcategoryId;
	}

	public void setSubcategoryId(Long subcategoryId) {
		this.subcategoryId = subcategoryId;
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

	public String getRootpath() {
		return rootpath;
	}

	public void setRootpath(String rootpath) {
		this.rootpath = rootpath;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getHighwayBoqId() {
		return highwayBoqId;
	}

	public void setHighwayBoqId(Long highwayBoqId) {
		this.highwayBoqId = highwayBoqId;
	}

	public String getStandardBookCode() {
		return standardBookCode;
	}

	public void setStandardBookCode(String standardBookCode) {
		this.standardBookCode = standardBookCode;
	}

	public Long getContractorId() {
		return contractorId;
	}

	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Boolean getIsChainageNotNull() {
		return isChainageNotNull;
	}

	public void setIsChainageNotNull(Boolean isChainageNotNull) {
		this.isChainageNotNull = isChainageNotNull;
	}

	public String getVendorDescription() {
		return vendorDescription;
	}

	public void setVendorDescription(String vendorDescription) {
		this.vendorDescription = vendorDescription;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public Integer getDocumentSubtypeId() {
		return documentSubtypeId;
	}

	public void setDocumentSubtypeId(Integer documentSubtypeId) {
		this.documentSubtypeId = documentSubtypeId;
	}

	public Integer getDocStatusId() {
		return docStatusId;
	}

	public void setDocStatusId(Integer docStatusId) {
		this.docStatusId = docStatusId;
	}

	public Long getStructureDocumentId() {
		return structureDocumentId;
	}

	public void setStructureDocumentId(Long structureDocumentId) {
		this.structureDocumentId = structureDocumentId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Long getStructureTypeId() {
		return structureTypeId;
	}

	public void setStructureTypeId(Long structureTypeId) {
		this.structureTypeId = structureTypeId;
	}

	public Integer getStructureGroupId() {
		return structureGroupId;
	}

	public void setStructureGroupId(Integer structureGroupId) {
		this.structureGroupId = structureGroupId;
	}

	public Long getBorewellBoqId() {
		return borewellBoqId;
	}

	public void setBorewellBoqId(Long borewellBoqId) {
		this.borewellBoqId = borewellBoqId;
	}

	public Integer getWorkType() {
		return workType;
	}

	public void setWorkType(Integer workType) {
		this.workType = workType;
	}

	public Integer getUnitTypeId() {
		return unitTypeId;
	}

	public void setUnitTypeId(Integer unitTypeId) {
		this.unitTypeId = unitTypeId;
	}

	public Long getGenericBoqId() {
		return genericBoqId;
	}

	public void setGenericBoqId(Long genericBoqId) {
		this.genericBoqId = genericBoqId;
	}

	public Integer getWorkOrderTypeId() {
		return workOrderTypeId;
	}

	public void setWorkOrderTypeId(Integer workOrderTypeId) {
		this.workOrderTypeId = workOrderTypeId;
	}

}
