package erp.billing.dto;

import java.util.Date;

public class DebitNoteDTO {

	private Long id;

	private Long serialNo;

	private Long partyId;

	private Integer partyType;

	private Date fromDate;

	private Date toDate;

	private Double totalAmount;

	private Integer stateId;

	private Boolean isActive;

	private Long siteId;

	private Integer companyId;

	private Date createdOn;

	public DebitNoteDTO() {
		super();
	}

	public DebitNoteDTO(Long id) {
		super();
		this.id = id;
	}

	public DebitNoteDTO(Long id, Long serialNo, Long partyId, Integer partyType, Date fromDate, Date toDate,
			Double totalAmount, Integer stateId, Boolean isActive, Long siteId, Integer companyId, Date createdOn) {
		super();
		this.id = id;
		this.serialNo = serialNo;
		this.partyId = partyId;
		this.partyType = partyType;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.totalAmount = totalAmount;
		this.stateId = stateId;
		this.isActive = isActive;
		this.siteId = siteId;
		this.companyId = companyId;
		this.createdOn = createdOn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}

	public Long getPartyId() {
		return partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}

	public Integer getPartyType() {
		return partyType;
	}

	public void setPartyType(Integer partyType) {
		this.partyType = partyType;
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

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
