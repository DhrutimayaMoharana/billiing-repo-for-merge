package erp.workorder.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class WorkorderDTO {

	private Long id;

	private String subject;

	private String uniqueNo;

	private String referenceWorkorderNo;

	private Date startDate;

	private Date endDate;

	private ContractorDTO contractor;

	private WorkorderContractorDTO woContractor;

	private WorkorderTypeDTO type;

	private EngineStateDTO state;

	private String remarks;

	private Integer version;

	private Date systemBillStartDate;

	private Double previousBillAmount;

	private Integer previousBillNo;

	private String systemGeneratedWorkInfo;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

	private Long siteId;

	private Integer companyId;

	private Boolean isExpiryExtendable;

	// Extra fields

	private List<Long> contractorCategories;

	private Set<CategoryItemDTO> categories;

	private SiteDTO site;

	private Boolean isBoqWorkSaved;

	public WorkorderDTO() {
		super();
	}

	public WorkorderDTO(Long id) {
		super();
		this.id = id;
	}

	public WorkorderDTO(Long id, String subject, String uniqueNo, String referenceWorkorderNo, Date startDate,
			Date endDate, ContractorDTO contractor, WorkorderContractorDTO woContractor, WorkorderTypeDTO type,
			EngineStateDTO state, String remarks, Integer version, Date systemBillStartDate, Double previousBillAmount,
			Integer previousBillNo, String systemGeneratedWorkInfo, Boolean isActive, Date createdOn, Long createdBy,
			Date modifiedOn, Long modifiedBy, Long siteId, Integer companyId, Boolean isExpiryExtendable) {
		super();
		this.id = id;
		this.subject = subject;
		this.uniqueNo = uniqueNo;
		this.referenceWorkorderNo = referenceWorkorderNo;
		this.startDate = startDate;
		this.endDate = endDate;
		this.contractor = contractor;
		this.woContractor = woContractor;
		this.type = type;
		this.state = state;
		this.remarks = remarks;
		this.version = version;
		this.systemBillStartDate = systemBillStartDate;
		this.previousBillAmount = previousBillAmount;
		this.previousBillNo = previousBillNo;
		this.systemGeneratedWorkInfo = systemGeneratedWorkInfo;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
		this.siteId = siteId;
		this.companyId = companyId;
		this.isExpiryExtendable = isExpiryExtendable;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUniqueNo() {
		return uniqueNo;
	}

	public void setUniqueNo(String uniqueNo) {
		this.uniqueNo = uniqueNo;
	}

	public ContractorDTO getContractor() {
		return contractor;
	}

	public void setContractor(ContractorDTO contractor) {
		this.contractor = contractor;
	}

	public WorkorderTypeDTO getType() {
		return type;
	}

	public void setType(WorkorderTypeDTO type) {
		this.type = type;
	}

	public EngineStateDTO getState() {
		return state;
	}

	public void setState(EngineStateDTO state) {
		this.state = state;
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

	public WorkorderContractorDTO getWoContractor() {
		return woContractor;
	}

	public void setWoContractor(WorkorderContractorDTO woContractor) {
		this.woContractor = woContractor;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<Long> getContractorCategories() {
		return contractorCategories;
	}

	public void setContractorCategories(List<Long> contractorCategories) {
		this.contractorCategories = contractorCategories;
	}

	public Set<CategoryItemDTO> getCategories() {
		return categories;
	}

	public void setCategories(Set<CategoryItemDTO> categories) {
		this.categories = categories;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public SiteDTO getSite() {
		return site;
	}

	public void setSite(SiteDTO site) {
		this.site = site;
	}

	public Boolean getIsBoqWorkSaved() {
		return isBoqWorkSaved;
	}

	public void setIsBoqWorkSaved(Boolean isBoqWorkSaved) {
		this.isBoqWorkSaved = isBoqWorkSaved;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getReferenceWorkorderNo() {
		return referenceWorkorderNo;
	}

	public void setReferenceWorkorderNo(String referenceWorkorderNo) {
		this.referenceWorkorderNo = referenceWorkorderNo;
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

	public Date getSystemBillStartDate() {
		return systemBillStartDate;
	}

	public void setSystemBillStartDate(Date systemBillStartDate) {
		this.systemBillStartDate = systemBillStartDate;
	}

	public Double getPreviousBillAmount() {
		return previousBillAmount;
	}

	public void setPreviousBillAmount(Double previousBillAmount) {
		this.previousBillAmount = previousBillAmount;
	}

	public Integer getPreviousBillNo() {
		return previousBillNo;
	}

	public void setPreviousBillNo(Integer previousBillNo) {
		this.previousBillNo = previousBillNo;
	}

	public String getSystemGeneratedWorkInfo() {
		return systemGeneratedWorkInfo;
	}

	public void setSystemGeneratedWorkInfo(String systemGeneratedWorkInfo) {
		this.systemGeneratedWorkInfo = systemGeneratedWorkInfo;
	}

	public Boolean getIsExpiryExtendable() {
		return isExpiryExtendable;
	}

	public void setIsExpiryExtendable(Boolean isExpiryExtendable) {
		this.isExpiryExtendable = isExpiryExtendable;
	}

}
