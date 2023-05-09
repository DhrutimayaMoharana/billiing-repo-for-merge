package erp.billing.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "store_issue_slip")
public class IssueSlip implements Serializable {

	private static final long serialVersionUID = 8743110339182743795L;

	private Long id;

	private Date dateOn;

	private Integer materialDepartmentId;

	private Boolean isActive;

	private Long siteId;

	public IssueSlip() {
		super();
	}

	public IssueSlip(Long id, Date dateOn, Integer materialDepartmentId, Boolean isActive, Long siteId) {
		super();
		this.id = id;
		this.dateOn = dateOn;
		this.materialDepartmentId = materialDepartmentId;
		this.isActive = isActive;
		this.siteId = siteId;
	}

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "date_on")
	public Date getDateOn() {
		return dateOn;
	}

	public void setDateOn(Date dateOn) {
		this.dateOn = dateOn;
	}

	@Column(name = "department_id")
	public Integer getMaterialDepartmentId() {
		return materialDepartmentId;
	}

	public void setMaterialDepartmentId(Integer materialDepartmentId) {
		this.materialDepartmentId = materialDepartmentId;
	}

	@Column(name = "status")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "site_id")
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

}
