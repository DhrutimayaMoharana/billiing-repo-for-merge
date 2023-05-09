package erp.workorder.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

public class SearchDTO {

	private List<Long> idsArr;

	private Set<Long> idsArrSet;

	private Long id;

	private Integer woTypeId;

	private Long workorderId;

	private Long woTncId;

	private Long structureId;

	private Long fromChainageId;

	private Long toChainageId;

	private Long siteId;

	private Integer companyId;

	private Boolean isActive;

	private Long contractorId;

	private Long userId;

	private Integer entityId;

	private Integer pageSize;

	private Integer pageNo;

	private MultipartFile[] files;

	private String rootpath;

	private String searchField;

	private String name;

	private Long unitId;

	private Long categoryId;

	private Integer stateId;

	private Boolean isApproved;

	private Boolean closedWorkorder;

	private List<WorkorderBoqWorkLocationDTO> locations;

	private Date fromDate;

	private Date toDate;

	private Boolean nonExistRender;

	private Integer roleId;

	private String userName;

	private Boolean getAll;

	private Integer fileTypeId;

	private Boolean isPlant;

	private Long structureTypeId;

	private Integer workorderBillInfoId;

	private Boolean filterWoTnC;

	public SearchDTO() {
		super();
	}

	public SearchDTO(Long siteId, Integer roleId, Integer entityId) {
		super();
		this.siteId = siteId;
		this.roleId = roleId;
		this.entityId = entityId;
	}

	public SearchDTO(Integer companyId, Long userId, Long siteId, Integer roleId, Integer entityId) {
		super();
		this.siteId = siteId;
		this.companyId = companyId;
		this.userId = userId;
		this.entityId = entityId;
		this.roleId = roleId;
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

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
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

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
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

	public Long getContractorId() {
		return contractorId;
	}

	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getStructureId() {
		return structureId;
	}

	public void setStructureId(Long structureId) {
		this.structureId = structureId;
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

	public Integer getWoTypeId() {
		return woTypeId;
	}

	public void setWoTypeId(Integer woTypeId) {
		this.woTypeId = woTypeId;
	}

	public Long getWoTncId() {
		return woTncId;
	}

	public void setWoTncId(Long woTncId) {
		this.woTncId = woTncId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	public String getRootpath() {
		return rootpath;
	}

	public void setRootpath(String rootpath) {
		this.rootpath = rootpath;
	}

	public List<WorkorderBoqWorkLocationDTO> getLocations() {
		return locations;
	}

	public void setLocations(List<WorkorderBoqWorkLocationDTO> locations) {
		this.locations = locations;
	}

	public Set<Long> getIdsArrSet() {
		return idsArrSet;
	}

	public void setIdsArrSet(Set<Long> idsArrSet) {
		this.idsArrSet = idsArrSet;
	}

	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Boolean getNonExistRender() {
		return nonExistRender;
	}

	public void setNonExistRender(Boolean nonExistRender) {
		this.nonExistRender = nonExistRender;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getGetAll() {
		return getAll;
	}

	public void setGetAll(Boolean getAll) {
		this.getAll = getAll;
	}

	public Integer getFileTypeId() {
		return fileTypeId;
	}

	public void setFileTypeId(Integer fileTypeId) {
		this.fileTypeId = fileTypeId;
	}

	public Boolean getIsPlant() {
		return isPlant;
	}

	public void setIsPlant(Boolean isPlant) {
		this.isPlant = isPlant;
	}

	public Long getStructureTypeId() {
		return structureTypeId;
	}

	public void setStructureTypeId(Long structureTypeId) {
		this.structureTypeId = structureTypeId;
	}

	public Boolean getClosedWorkorder() {
		return closedWorkorder;
	}

	public void setClosedWorkorder(Boolean closedWorkorder) {
		this.closedWorkorder = closedWorkorder;
	}

	public Integer getWorkorderBillInfoId() {
		return workorderBillInfoId;
	}

	public void setWorkorderBillInfoId(Integer workorderBillInfoId) {
		this.workorderBillInfoId = workorderBillInfoId;
	}

	public Boolean getFilterWoTnC() {
		return filterWoTnC;
	}

	public void setFilterWoTnC(Boolean filterWoTnC) {
		this.filterWoTnC = filterWoTnC;
	}

}
