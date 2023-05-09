package erp.billing.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "smm_transport_mode")
public class TransportMode {

	private Short id;

	private String name;

	private Boolean isActive;

	private Date updatedOn;

	public TransportMode() {
		super();
	}

	public TransportMode(Short id) {
		super();
		this.id = id;
	}

	public TransportMode(Short id, String name, Boolean isActive, Date updatedOn) {
		super();
		this.id = id;
		this.name = name;
		this.isActive = isActive;
		this.updatedOn = updatedOn;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Short getId() {
		return id;
	}

	public void setId(Short id) {
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

	@Column(name = "updated_on")
	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

}
