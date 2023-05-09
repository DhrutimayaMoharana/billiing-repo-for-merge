package erp.workorder.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonStringType;

@Entity
@Table(name = "company_config")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class CompanyConfig {

	private Integer id;

	private Boolean enableWorkorderExpiryMail;

	private Set<Integer> daysBeforeMailRequiredForWorkorderExpiry;

	private Company company;

	private Boolean isActive;

	public CompanyConfig() {
		super();
	}

	public CompanyConfig(Integer id, Boolean enableWorkorderExpiryMail,
			Set<Integer> daysBeforeMailRequiredForWorkorderExpiry, Company company, Boolean isActive) {
		super();
		this.id = id;
		this.enableWorkorderExpiryMail = enableWorkorderExpiryMail;
		this.daysBeforeMailRequiredForWorkorderExpiry = daysBeforeMailRequiredForWorkorderExpiry;
		this.company = company;
		this.isActive = isActive;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "enable_workorder_expiry_mail")
	public Boolean getEnableWorkorderExpiryMail() {
		return enableWorkorderExpiryMail;
	}

	public void setEnableWorkorderExpiryMail(Boolean enableWorkorderExpiryMail) {
		this.enableWorkorderExpiryMail = enableWorkorderExpiryMail;
	}

	@Type(type = "json")
	@Column(name = "days_before_mail_required_for_workorder_expire")
	public Set<Integer> getDaysBeforeMailRequiredForWorkorderExpiry() {
		return daysBeforeMailRequiredForWorkorderExpiry;
	}

	public void setDaysBeforeMailRequiredForWorkorderExpiry(Set<Integer> daysBeforeMailRequiredForWorkorderExpiry) {
		this.daysBeforeMailRequiredForWorkorderExpiry = daysBeforeMailRequiredForWorkorderExpiry;
	}

	@OneToOne
	@JoinColumn(name = "company_id")
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
