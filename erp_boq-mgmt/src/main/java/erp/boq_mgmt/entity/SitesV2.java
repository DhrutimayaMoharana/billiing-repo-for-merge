package erp.boq_mgmt.entity;

import java.io.Serializable;

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
public class SitesV2 implements Serializable {

	private static final long serialVersionUID = -1395098335205144793L;

	private Long id;

	private String name; // SITE-CODE

	private String description;

	private Boolean isActive;

	private Long startChainageId;

	private Long endChainageId;

	private Integer timezoneMinutes;

	private Integer companyId;

	private Company company;

	private SiteConcessionaire concessionaire;

	private SiteConsultant consultant; // INDEPENDENT ENGINEER

	private CompanyClient client;

	public SitesV2() {
	}

	public SitesV2(Long id) {
		this.id = id;
	}

	public SitesV2(Long id, String name, Boolean isActive, Integer companyId) {
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

	@Column(name = "timezone_minutes")
	public Integer getTimezoneMinutes() {
		return timezoneMinutes;
	}

	public void setTimezoneMinutes(Integer timezoneMinutes) {
		this.timezoneMinutes = timezoneMinutes;
	}

	@OneToOne
	@JoinColumn(name = "company_id", insertable = false, updatable = false)
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@OneToOne
	@JoinColumn(name = "site_concessionaire_id", insertable = false, updatable = false)
	public SiteConcessionaire getConcessionaire() {
		return concessionaire;
	}

	public void setConcessionaire(SiteConcessionaire concessionaire) {
		this.concessionaire = concessionaire;
	}

	@OneToOne
	@JoinColumn(name = "site_consultant_id", insertable = false, updatable = false)
	public SiteConsultant getConsultant() {
		return consultant;
	}

	public void setConsultant(SiteConsultant consultant) {
		this.consultant = consultant;
	}

	@OneToOne
	@JoinColumn(name = "client_id", insertable = false, updatable = false)
	public CompanyClient getClient() {
		return client;
	}

	public void setClient(CompanyClient client) {
		this.client = client;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
