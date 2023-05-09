package erp.workorder.dto;

import java.util.Date;

public class DieselRateMappingDTO {

	private Long id;

	private Date date;

	private Double rate;

	private Long siteId;

	private Boolean isActive;

	private Date modifiedOn;

	private Long modifiedBy;

	public DieselRateMappingDTO() {
		super();
	}

	public DieselRateMappingDTO(Long id, Date date, Double rate, Long siteId, Boolean isActive, Date modifiedOn,
			Long modifiedBy) {
		super();
		this.id = id;
		this.date = date;
		this.rate = rate;
		this.siteId = siteId;
		this.isActive = isActive;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
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
}
