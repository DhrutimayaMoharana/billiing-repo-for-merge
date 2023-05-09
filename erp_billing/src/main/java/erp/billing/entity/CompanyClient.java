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
@Table(name = "company_clients")
public class CompanyClient implements Serializable {

	private static final long serialVersionUID = -5837541460493155850L;

	private Integer id;
	
	private String name;
	
	private String address;
	
	private Boolean isActive;
	
	private Date creatdeOn;
	
	private Integer createdBy;

	public CompanyClient() {
		super();
	}

	public CompanyClient(Integer id, String name, String address, Boolean isActive, Date creatdeOn, Integer createdBy) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.isActive = isActive;
		this.creatdeOn = creatdeOn;
		this.createdBy = createdBy;
	}

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "is_Active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "created_on")
	public Date getCreatdeOn() {
		return creatdeOn;
	}

	public void setCreatdeOn(Date creatdeOn) {
		this.creatdeOn = creatdeOn;
	}

	@Column(name = "created_by")
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

}
