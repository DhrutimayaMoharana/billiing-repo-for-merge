package erp.billing.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "equipments")
public class Equipment implements Serializable {
	
	private static final long serialVersionUID = -2198976031199873703L;

	private Long id;
	
	private String assetCode;
	
	private String regNo;
	
	private EquipmentCategory category;
	
	private Boolean isOwned;
	
	private Integer type;
	
	private Boolean isActive;
	
	private Long siteId;
	
	private Integer companyId;

	public Equipment() {
		super();
	}

	public Equipment(Long id) {
		super();
		this.id = id;
	}

	public Equipment(Long id, String assetCode, String regNo, EquipmentCategory category, Boolean isOwned, Integer type,
			Boolean isActive, Long siteId, Integer companyId) {
		super();
		this.id = id;
		this.assetCode = assetCode;
		this.regNo = regNo;
		this.category = category;
		this.isOwned = isOwned;
		this.type = type;
		this.isActive = isActive;
		this.siteId = siteId;
		this.companyId = companyId;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="asset_code")
	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	@Column(name="reg_no")
	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}


	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EquipmentCategoryId")
	public EquipmentCategory getCategory() {
		return category;
	}

	public void setCategory(EquipmentCategory category) {
		this.category = category;
	}


	@Column(name="is_owned")
	public Boolean getIsOwned() {
		return isOwned;
	}

	public void setIsOwned(Boolean isOwned) {
		this.isOwned = isOwned;
	}


	@Column(name="type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


	@Column(name="IsActive")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}


	@Column(name="Site_id")
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	@Column(name="company_id")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}
