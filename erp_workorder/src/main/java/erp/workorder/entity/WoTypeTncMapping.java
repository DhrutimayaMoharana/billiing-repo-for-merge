package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "wo_type_tnc_mapping")
public class WoTypeTncMapping implements Serializable {

	private static final long serialVersionUID = 7139705056178990921L;

	private Long id;

	private Long woTypeId;

	private WoTnc woTnc;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Integer companyId;

	public WoTypeTncMapping() {
		super();
	}

	public WoTypeTncMapping(Long id) {
		super();
		this.id = id;
	}

	public WoTypeTncMapping(Long id, Long woTypeId, WoTnc woTnc, Boolean isActive, Date createdOn, Long createdBy,
			Integer companyId) {
		super();
		this.id = id;
		this.woTypeId = woTypeId;
		this.woTnc = woTnc;
		this.setIsActive(isActive);
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.companyId = companyId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "wo_type_id")
	public Long getWoTypeId() {
		return woTypeId;
	}

	public void setWoTypeId(Long woTypeId) {
		this.woTypeId = woTypeId;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "wo_tnc_id")
	public WoTnc getWoTnc() {
		return woTnc;
	}

	public void setWoTnc(WoTnc woTnc) {
		this.woTnc = woTnc;
	}

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "created_by")
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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
}
