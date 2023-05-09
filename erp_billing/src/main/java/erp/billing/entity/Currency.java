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
@Table(name = "currencies")
public class Currency implements Serializable {
	
	private static final long serialVersionUID = -4809148994742053351L;

	private Long id;
	
	private String name;
	
	private String code;
	
	private Boolean isActive;
	
	private Date createdOn;

	public Currency() {
		super();
	}

	public Currency(Long id) {
		super();
		this.id = id;
	}

	public Currency(Long id, String name, String code, Boolean isActive, Date createdOn) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.isActive = isActive;
		this.createdOn = createdOn;
	}

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
