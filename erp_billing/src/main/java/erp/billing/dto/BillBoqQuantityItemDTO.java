package erp.billing.dto;

import java.util.Date;

public class BillBoqQuantityItemDTO {

	private Long id;

	private Long billBoqItemId;

	private ChainageDTO fromChainage;

	private ChainageDTO toChainage;

	private Double quantity;

	private Double rate;

	private Long siteId;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

	public BillBoqQuantityItemDTO() {
		super();
	}

	public BillBoqQuantityItemDTO(Long id) {
		super();
		this.id = id;
	}

	public BillBoqQuantityItemDTO(Long id, Long billBoqItemId, ChainageDTO fromChainage, ChainageDTO toChainage,
			Double quantity, Double rate, Long siteId, Boolean isActive, Date createdOn, Long createdBy,
			Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.billBoqItemId = billBoqItemId;
		this.fromChainage = fromChainage;
		this.toChainage = toChainage;
		this.quantity = quantity;
		this.rate = rate;
		this.siteId = siteId;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBillBoqItemId() {
		return billBoqItemId;
	}

	public void setBillBoqItemId(Long billBoqItemId) {
		this.billBoqItemId = billBoqItemId;
	}

	public ChainageDTO getFromChainage() {
		return fromChainage;
	}

	public void setFromChainage(ChainageDTO fromChainage) {
		this.fromChainage = fromChainage;
	}

	public ChainageDTO getToChainage() {
		return toChainage;
	}

	public void setToChainage(ChainageDTO toChainage) {
		this.toChainage = toChainage;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

}
