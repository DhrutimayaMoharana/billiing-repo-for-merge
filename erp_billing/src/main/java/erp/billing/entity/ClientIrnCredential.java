package erp.billing.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "client_irn_credential")
public class ClientIrnCredential {

	private Integer id;

	private String einvoiceName;

	private String einvoicePassword;

	private String gstHeroUserName;

	private String gstHeroPassword;

	private String gstHeroAccessToken;

	private Date gstHeroTokenExpiry;

	private String appKey;

	private Boolean forceRefreshAccessToken;

	private String clientId;

	private String clientSecret;

	private String gstin;

	private String sek;

	private String base64EncodedPem;

	private String einvoiceAuthToken;

	private Date einvoiceTokenExpiry;

	private Integer irnCancelTimeInMinutes;

	private Integer siteId;

	private Integer companyId;

	private Boolean isActive;

	private Date updatedOn;

	private Integer updatedBy;

	public ClientIrnCredential() {
		super();
	}

	public ClientIrnCredential(Integer id, String einvoiceName, String einvoicePassword, String gstHeroUserName,
			String gstHeroPassword, String gstHeroAccessToken, Date gstHeroTokenExpiry, String appKey,
			Boolean forceRefreshAccessToken, String clientId, String clientSecret, String gstin, String sek,
			String base64EncodedPem, String einvoiceAuthToken, Date einvoiceTokenExpiry, Integer siteId,
			Integer irnCancelTimeInMinutes, Integer companyId, Boolean isActive, Date updatedOn, Integer updatedBy) {
		super();
		this.id = id;
		this.einvoiceName = einvoiceName;
		this.einvoicePassword = einvoicePassword;
		this.gstHeroUserName = gstHeroUserName;
		this.gstHeroPassword = gstHeroPassword;
		this.gstHeroAccessToken = gstHeroAccessToken;
		this.gstHeroTokenExpiry = gstHeroTokenExpiry;
		this.appKey = appKey;
		this.forceRefreshAccessToken = forceRefreshAccessToken;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.gstin = gstin;
		this.sek = sek;
		this.base64EncodedPem = base64EncodedPem;
		this.einvoiceAuthToken = einvoiceAuthToken;
		this.einvoiceTokenExpiry = einvoiceTokenExpiry;
		this.irnCancelTimeInMinutes = irnCancelTimeInMinutes;
		this.siteId = siteId;
		this.companyId = companyId;
		this.isActive = isActive;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "einvoice_name")
	public String getEinvoiceName() {
		return einvoiceName;
	}

	public void setEinvoiceName(String einvoiceName) {
		this.einvoiceName = einvoiceName;
	}

	@Column(name = "einvoice_password")
	public String getEinvoicePassword() {
		return einvoicePassword;
	}

	public void setEinvoicePassword(String einvoicePassword) {
		this.einvoicePassword = einvoicePassword;
	}

	@Column(name = "gst_hero_user_name")
	public String getGstHeroUserName() {
		return gstHeroUserName;
	}

	public void setGstHeroUserName(String gstHeroUserName) {
		this.gstHeroUserName = gstHeroUserName;
	}

	@Column(name = "gst_hero_password")
	public String getGstHeroPassword() {
		return gstHeroPassword;
	}

	public void setGstHeroPassword(String gstHeroPassword) {
		this.gstHeroPassword = gstHeroPassword;
	}

	@Column(name = "gst_hero_access_token")
	public String getGstHeroAccessToken() {
		return gstHeroAccessToken;
	}

	public void setGstHeroAccessToken(String gstHeroAccessToken) {
		this.gstHeroAccessToken = gstHeroAccessToken;
	}

	@Column(name = "gst_hero_token_expiry")
	public Date getGstHeroTokenExpiry() {
		return gstHeroTokenExpiry;
	}

	public void setGstHeroTokenExpiry(Date gstHeroTokenExpiry) {
		this.gstHeroTokenExpiry = gstHeroTokenExpiry;
	}

	@Column(name = "app_key")
	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	@Column(name = "force_refresh_access_token")
	public Boolean getForceRefreshAccessToken() {
		return forceRefreshAccessToken;
	}

	public void setForceRefreshAccessToken(Boolean forceRefreshAccessToken) {
		this.forceRefreshAccessToken = forceRefreshAccessToken;
	}

	@Column(name = "client_id")
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Column(name = "client_secret")
	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	@Column(name = "gstin")
	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	@Column(name = "sek")
	public String getSek() {
		return sek;
	}

	public void setSek(String sek) {
		this.sek = sek;
	}

	@Column(name = "base64_encoded_pem")
	public String getBase64EncodedPem() {
		return base64EncodedPem;
	}

	public void setBase64EncodedPem(String base64EncodedPem) {
		this.base64EncodedPem = base64EncodedPem;
	}

	@Column(name = "einvoice_auth_token")
	public String getEinvoiceAuthToken() {
		return einvoiceAuthToken;
	}

	public void setEinvoiceAuthToken(String einvoiceAuthToken) {
		this.einvoiceAuthToken = einvoiceAuthToken;
	}

	@Column(name = "einvoice_token_expiry")
	public Date getEinvoiceTokenExpiry() {
		return einvoiceTokenExpiry;
	}

	public void setEinvoiceTokenExpiry(Date einvoiceTokenExpiry) {
		this.einvoiceTokenExpiry = einvoiceTokenExpiry;
	}

	@Column(name = "irn_cancel_time_in_minutes")
	public Integer getIrnCancelTimeInMinutes() {
		return irnCancelTimeInMinutes;
	}

	public void setIrnCancelTimeInMinutes(Integer irnCancelTimeInMinutes) {
		this.irnCancelTimeInMinutes = irnCancelTimeInMinutes;
	}

	@Column(name = "site_id")
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	@Column(name = "company_id")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "updated_on")
	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(name = "updated_by")
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

}
