package erp.billing.dto;

import java.util.Date;

public class CompanyClientDTO {

	private Integer id;

	private String name;

	private String address;

	private Boolean isActive;

	private Date creatdeOn;

	private Integer createdBy;

	public CompanyClientDTO() {
		super();
	}

	public CompanyClientDTO(Integer id, String name, String address, Boolean isActive, Date creatdeOn,
			Integer createdBy) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.isActive = isActive;
		this.creatdeOn = creatdeOn;
		this.createdBy = createdBy;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatdeOn() {
		return creatdeOn;
	}

	public void setCreatdeOn(Date creatdeOn) {
		this.creatdeOn = creatdeOn;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

}
