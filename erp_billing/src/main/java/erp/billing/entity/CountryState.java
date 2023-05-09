package erp.billing.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "states")
public class CountryState implements Serializable {

	private static final long serialVersionUID = -5959822661437371898L;

	private Long id;

	private String stateCode;

	private String name;

	public CountryState() {
		super();
	}

	public CountryState(Long id) {
		super();
		this.id = id;
	}

	public CountryState(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	@Column(name = "state_code")
	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
