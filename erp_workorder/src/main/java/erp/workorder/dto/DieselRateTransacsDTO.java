package erp.workorder.dto;

import java.util.Date;

public class DieselRateTransacsDTO {
	
	private Long id;

	private Date date;

	private Double rate;

	private Long siteId;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	public DieselRateTransacsDTO() {
		super();
	}

	public DieselRateTransacsDTO(Long id, Date date, Double rate, Long siteId, Boolean isActive, Date createdOn,
			Long createdBy) {
		super();
		this.id = id;
		this.date = date;
		this.rate = rate;
		this.siteId = siteId;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
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
}
