package erp.billing.entity;

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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "chainages")
public class Chainage implements Serializable {

	private static final long serialVersionUID = 6679924668477271943L;

	private Long id;

	private String name;

	private Boolean isActive;

	private ChainageTraverse previous;

	private ChainageTraverse next;

	private Long siteId;

	private Integer companyId;

	private Date modifiedOn;

	private Long modifiedBy;

	public Chainage() {
		super();
	}

	public Chainage(Long id) {
		super();
		this.id = id;
	}

	public Chainage(Long id, String name, Boolean isActive, ChainageTraverse previous, ChainageTraverse next,
			Long siteId, Integer companyId, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.name = name;
		this.isActive = isActive;
		this.previous = previous;
		this.next = next;
		this.siteId = siteId;
		this.companyId = companyId;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
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

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "site_id")
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "previous_id")
	public ChainageTraverse getPrevious() {
		return previous;
	}

	public void setPrevious(ChainageTraverse previous) {
		this.previous = previous;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "next_id")
	public ChainageTraverse getNext() {
		return next;
	}

	public void setNext(ChainageTraverse next) {
		this.next = next;
	}

	@Column(name = "company_id")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Column(name = "modified_by")
	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "modified_on")
	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

}
