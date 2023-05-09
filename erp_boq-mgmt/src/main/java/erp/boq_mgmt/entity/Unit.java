package erp.boq_mgmt.entity;

import java.io.Serializable;

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
@Table(name = "unit")
public class Unit implements Serializable {

	private static final long serialVersionUID = -1840439959395292609L;

	private Long id;

	private String name;

	private String description;

	private UnitType type;

	private Boolean isActive;

	private Integer companyId;

	private GovtUnit govtUnit;

	public Unit() {
	}

	public Unit(Long id) {
		this.id = id;
	}

	public Unit(Long id, String name, String description, UnitType type, Boolean isActive, Integer companyId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.isActive = isActive;
		this.companyId = companyId;
	}

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type")
	public UnitType getType() {
		return type;
	}

	public void setType(UnitType type) {
		this.type = type;
	}

	@Column(name = "unit_name", length = 128)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "status")
	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "company_id", nullable = false)
	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@OneToOne
	@JoinColumn(name = "govt_unit_id")
	public GovtUnit getGovtUnit() {
		return govtUnit;
	}

	public void setGovtUnit(GovtUnit govtUnit) {
		this.govtUnit = govtUnit;
	}

}
