package erp.workorder.dto;

import java.util.Date;

public class ChainageDTO {

	private Long id;

	private String name;

	private Boolean isActive;

	private Integer numericChainageValue;

	private ChainageTraverseDTO previous;

	private ChainageTraverseDTO next;

	private Long siteId;

	private Integer companyId;

	private Date modifiedOn;

	private Long modifiedBy;

	public ChainageDTO() {
		super();
	}

	public ChainageDTO(Long id) {
		super();
		this.id = id;
	}

	public ChainageDTO(Long id, String name, Boolean isActive, Integer numericChainageValue,
			ChainageTraverseDTO previous, ChainageTraverseDTO next, Long siteId, Integer companyId, Date modifiedOn,
			Long modifiedBy) {
		super();
		this.id = id;
		this.name = name;
		this.isActive = isActive;
		this.previous = previous;
		this.next = next;
		this.siteId = siteId;
		this.companyId = companyId;
		this.modifiedOn = modifiedOn;
		this.numericChainageValue = numericChainageValue;
		this.modifiedBy = modifiedBy;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public ChainageTraverseDTO getPrevious() {
		return previous;
	}

	public void setPrevious(ChainageTraverseDTO previous) {
		this.previous = previous;
	}

	public ChainageTraverseDTO getNext() {
		return next;
	}

	public void setNext(ChainageTraverseDTO next) {
		this.next = next;
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

	public Integer getNumericChainageValue() {
		return numericChainageValue;
	}

	public void setNumericChainageValue(Integer numericChainageValue) {
		this.numericChainageValue = numericChainageValue;
	}

}
