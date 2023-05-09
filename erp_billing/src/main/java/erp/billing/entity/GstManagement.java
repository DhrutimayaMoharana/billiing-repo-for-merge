package erp.billing.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import erp.billing.enums.GstTypeEnum;

@Entity
@Table(name = "smm_gst_management")
public class GstManagement {

	private Integer id;

	private GstTypeEnum type;

	private String code;

	private String name;

	private Integer applicableGst;

	private Date validFrom;

	private Integer companyId;

	private Boolean isActive;

	private Date updatedOn;

	private Integer updatedBy;

	public GstManagement() {
		super();
	}

	public GstManagement(Integer id) {
		super();
		this.id = id;
	}

	public GstManagement(Integer id, GstTypeEnum type, String code, String name, Integer applicableGst, Date validFrom,
			Integer companyId, Boolean isActive, Date updatedOn, Integer updatedBy) {
		super();
		this.id = id;
		this.type = type;
		this.code = code;
		this.name = name;
		this.applicableGst = applicableGst;
		this.validFrom = validFrom;
		this.companyId = companyId;
		this.isActive = isActive;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
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

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "type")
	public GstTypeEnum getType() {
		return type;
	}

	public void setType(GstTypeEnum type) {
		this.type = type;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "applicable_gst")
	public Integer getApplicableGst() {
		return applicableGst;
	}

	public void setApplicableGst(Integer applicableGst) {
		this.applicableGst = applicableGst;
	}

	@Column(name = "valid_from")
	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
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
