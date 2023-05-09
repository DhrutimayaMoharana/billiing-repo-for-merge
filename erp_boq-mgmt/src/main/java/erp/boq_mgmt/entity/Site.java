package erp.boq_mgmt.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sites")
public class Site implements java.io.Serializable {

	private static final long serialVersionUID = -1395098335205144793L;

	private Long id;

	private String name; // SITE-CODE

	private Boolean isActive;

	private Long startChainageId;

	private Long endChainageId;

	private Date startDate;

	private Date endDate;

	private Integer companyId;

	private Company company;

	public Site() {
	}

	public Site(Long id) {
		this.id = id;
	}

	public Site(Long id, String name, Boolean isActive, Integer companyId) {
		this.id = id;
		this.name = name;
		this.isActive = isActive;
		this.companyId = companyId;
	}

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "code")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "company_id")
	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Column(name = "start_chainage_id")
	public Long getStartChainageId() {
		return startChainageId;
	}

	public void setStartChainageId(Long startChainageId) {
		this.startChainageId = startChainageId;
	}

	@Column(name = "end_chainage_id")
	public Long getEndChainageId() {
		return endChainageId;
	}

	public void setEndChainageId(Long endChainageId) {
		this.endChainageId = endChainageId;
	}

	@Column(name = "start_date")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "end_date")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@OneToOne
	@JoinColumn(name = "company_id", insertable = false, updatable = false)
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}