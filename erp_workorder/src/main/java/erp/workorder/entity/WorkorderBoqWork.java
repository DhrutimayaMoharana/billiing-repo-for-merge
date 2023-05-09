package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "wo_boq_work")
public class WorkorderBoqWork implements Serializable {

	private static final long serialVersionUID = -625210563204729143L;

	private Long id;

	private Long workorderId;

	private String workScope;

	private String annexureNote;

	private List<WorkorderBoqWorkLocation> locations;

	private List<WorkorderBoqWorkQtyMapping> boqWorkQty;

	private Boolean isActive;

	private Integer version;

	private Long siteId;

	private Date modifiedOn;

	private Long modifiedBy;

	public WorkorderBoqWork() {
		super();
	}

	public WorkorderBoqWork(Long id) {
		super();
		this.id = id;
	}

	public WorkorderBoqWork(Long id, Long workorderId, String workScope, String annexureNote,
			List<WorkorderBoqWorkLocation> locations, List<WorkorderBoqWorkQtyMapping> boqWorkQty, Boolean isActive,
			Integer version, Long siteId, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.setWorkorderId(workorderId);
		this.workScope = workScope;
		this.annexureNote = annexureNote;
		this.locations = locations;
		this.boqWorkQty = boqWorkQty;
		this.isActive = isActive;
		this.version = version;
		this.siteId = siteId;
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

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "boqWork", orphanRemoval = false)
	public List<WorkorderBoqWorkLocation> getLocations() {
		return locations;
	}

	public void setLocations(List<WorkorderBoqWorkLocation> locations) {
		this.locations = locations;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "boqWork", orphanRemoval = false)
	public List<WorkorderBoqWorkQtyMapping> getBoqWorkQty() {
		return boqWorkQty;
	}

	public void setBoqWorkQty(List<WorkorderBoqWorkQtyMapping> boqWorkQty) {
		this.boqWorkQty = boqWorkQty;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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

	@Column(name = "work_scope")
	public String getWorkScope() {
		return workScope;
	}

	public void setWorkScope(String workScope) {
		this.workScope = workScope;
	}

	@Column(name = "site_id")
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	@Column(name = "annexure_note")
	public String getAnnexureNote() {
		return annexureNote;
	}

	public void setAnnexureNote(String annexureNote) {
		this.annexureNote = annexureNote;
	}

	@Column(name = "workorder_id")
	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

}
