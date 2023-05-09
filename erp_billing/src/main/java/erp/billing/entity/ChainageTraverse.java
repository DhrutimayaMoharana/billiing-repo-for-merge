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
@Table(name = "chainages")
public class ChainageTraverse implements Serializable {
	
	private static final long serialVersionUID = 1139433637834867817L;

	private Long id;
	
	private String name;
	
	private Boolean isActive;
	
	private Long siteId;
	
	private Integer companyId;
	
	private Date modifiedOn;
	
	private Long modifiedBy;

	public ChainageTraverse() {
		super();
	}

	public ChainageTraverse(Long id) {
		super();
		this.id = id;
	}

	public ChainageTraverse(Long id, String name, Boolean isActive, Long siteId, Integer companyId,
			Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.name = name;
		this.isActive = isActive;
		this.siteId = siteId;
		this.companyId = companyId;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name="site_id")
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

	@Column(name="modified_on")
	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Column(name="modified_by")
	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}
