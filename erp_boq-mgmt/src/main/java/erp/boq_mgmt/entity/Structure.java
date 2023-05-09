package erp.boq_mgmt.entity;

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
@Table(name = "structure")
public class Structure implements Serializable {

	private static final long serialVersionUID = -42180280061668087L;

	private Long id;

	private Integer groupId;

	private String name;

	private StructureType type;

	private Chainage startChainage;

	private Chainage endChainage;

	private Long siteId;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

	private Integer companyId;

	private StructureGroup group;

	public Structure() {
		super();
	}

	public Structure(Long id) {
		super();
		this.id = id;
	}

	public Structure(Long id, Integer groupId, String name, StructureType type, Chainage startChainage,
			Chainage endChainage, Long siteId, Boolean isActive, Date createdOn, Long createdBy, Date modifiedOn,
			Long modifiedBy, Integer companyId) {
		super();
		this.id = id;
		this.groupId = groupId;
		this.name = name;
		this.type = type;
		this.startChainage = startChainage;
		this.endChainage = endChainage;
		this.siteId = siteId;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
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

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "site_id")
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
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

	@Column(name = "modified_on")
	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Column(name = "modified_by")
	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
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

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id")
	public StructureType getType() {
		return type;
	}

	public void setType(StructureType type) {
		this.type = type;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "start_chainage_id")
	public Chainage getStartChainage() {
		return startChainage;
	}

	public void setStartChainage(Chainage startChainage) {
		this.startChainage = startChainage;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "end_chainage_id")
	public Chainage getEndChainage() {
		return endChainage;
	}

	public void setEndChainage(Chainage endChainage) {
		this.endChainage = endChainage;
	}

	@Column(name = "group_id")
	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@OneToOne
	@JoinColumn(name = "group_id", insertable = false, updatable = false)
	public StructureGroup getGroup() {
		return group;
	}

	public void setGroup(StructureGroup group) {
		this.group = group;
	}

}
