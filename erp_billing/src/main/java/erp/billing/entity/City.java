package erp.billing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "cities")
public class City {

	private Integer id;

	private String name;

	private Integer stateId;

	private Boolean isActive;

	private CountryState state;

	public City() {
		super();
	}

	public City(Integer id) {
		super();
		this.id = id;
	}

	public City(Integer id, String name, Integer stateId, Boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.stateId = stateId;
		this.isActive = isActive;
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

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "state_id")
	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumn(name = "state_id", insertable = false, updatable = false)
	public CountryState getState() {
		return state;
	}

	public void setState(CountryState state) {
		this.state = state;
	}

}
