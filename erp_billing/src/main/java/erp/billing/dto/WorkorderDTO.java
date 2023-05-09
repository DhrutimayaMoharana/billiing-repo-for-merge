package erp.billing.dto;

import java.util.Date;
import java.util.Set;

public class WorkorderDTO {

	private Long id;

	private String subject;

	private String uniqueNo;

	private Date startDate;

	private Date endDate;

	private ContractorDTO contractor;

	private WorkorderContractorDTO woContractor;

	private WorkorderTypeDTO type;

	private EngineStateDTO state;

	private Date systemBillStartDate;

	private Double previousBillAmount;

	private Integer previousBillNo;

	private Boolean isActive;

	private Integer version;

	private Date modifiedOn;

	private Long modifiedBy;

	private Long siteId;

	private Integer companyId;

	// extra fields
	private Set<CategoryItemDTO> categories;

	public WorkorderDTO() {
		super();
	}

	public WorkorderDTO(Long id) {
		super();
		this.id = id;
	}

	public WorkorderDTO(Long id, String subject, String uniqueNo, Date startDate, Date endDate,
			ContractorDTO contractor, WorkorderContractorDTO woContractor, WorkorderTypeDTO type, EngineStateDTO state,
			Date systemBillStartDate, Double previousBillAmount, Integer previousBillNo, Boolean isActive,
			Integer version, Date modifiedOn, Long modifiedBy, Long siteId, Integer companyId) {
		super();
		this.id = id;
		this.subject = subject;
		this.uniqueNo = uniqueNo;
		this.startDate = startDate;
		this.endDate = endDate;
		this.contractor = contractor;
		this.woContractor = woContractor;
		this.type = type;
		this.state = state;
		this.systemBillStartDate = systemBillStartDate;
		this.previousBillAmount = previousBillAmount;
		this.previousBillNo = previousBillNo;
		this.isActive = isActive;
		this.version = version;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
		this.siteId = siteId;
		this.companyId = companyId;
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

	public Set<CategoryItemDTO> getCategories() {
		return categories;
	}

	public void setCategories(Set<CategoryItemDTO> categories) {
		this.categories = categories;
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

}
