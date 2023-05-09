package erp.boq_mgmt.dto;

import java.util.Date;

public class AuthorityDTO {

	private Integer id;

	private Boolean add;

	private Boolean view;

	private Boolean remove;

	private Boolean update;

	private Boolean export;

	private Boolean isActive;

	private Integer companyId;

	private Date createdOn;

	private Integer createdBy;

	public AuthorityDTO() {
		super();
	}

	public AuthorityDTO(Integer id) {
		super();
		this.id = id;
	}

	public AuthorityDTO(Integer id, Boolean add, Boolean view, Boolean remove, Boolean update, Boolean export,
			Boolean isActive, Integer companyId, Date createdOn, Integer createdBy) {
		super();
		this.id = id;
		this.add = add;
		this.view = view;
		this.remove = remove;
		this.update = update;
		this.export = export;
		this.setIsActive(isActive);
		this.companyId = companyId;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getAdd() {
		return add;
	}

	public void setAdd(Boolean add) {
		this.add = add;
	}

	public Boolean getView() {
		return view;
	}

	public void setView(Boolean view) {
		this.view = view;
	}

	public Boolean getRemove() {
		return remove;
	}

	public void setRemove(Boolean remove) {
		this.remove = remove;
	}

	public Boolean getUpdate() {
		return update;
	}

	public void setUpdate(Boolean update) {
		this.update = update;
	}

	public Boolean getExport() {
		return export;
	}

	public void setExport(Boolean export) {
		this.export = export;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
