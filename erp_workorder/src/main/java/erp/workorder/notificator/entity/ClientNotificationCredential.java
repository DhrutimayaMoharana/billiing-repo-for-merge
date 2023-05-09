package erp.workorder.notificator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "client_notification_credentials")
public class ClientNotificationCredential {

	private Integer id;

	private String name;

	private String emailAddress;

	private String emailPassword;

	private String host;

	private String port;

	private Boolean isActive;

	private Integer companyId;

	public ClientNotificationCredential() {
		super();
	}

	public ClientNotificationCredential(Integer id, String name, String emailAddress, String emailPassword, String host,
			String port, Boolean isActive, Integer companyId) {
		super();
		this.id = id;
		this.name = name;
		this.emailAddress = emailAddress;
		this.emailPassword = emailPassword;
		this.host = host;
		this.port = port;
		this.isActive = isActive;
		this.companyId = companyId;
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

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "email_address")
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Column(name = "email_password")
	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	@Column(name = "host")
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Column(name = "port")
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "company_id")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}
