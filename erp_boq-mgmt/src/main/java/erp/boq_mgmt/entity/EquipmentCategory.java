package erp.boq_mgmt.entity;

import java.io.Serializable;

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
@Table(name = "equipment_category")
public class EquipmentCategory implements Serializable {

	private static final long serialVersionUID = 750289974799659530L;

	private Long id;

	private String name;

	private String abbreviation;

	private Double operatingCost;

	private Boolean isMultiFuel;

	private Unit primaryReadingUnit;

	private Unit secondaryReadingUnit;

	private Boolean isActive;

	private Integer companyId;

	public EquipmentCategory() {
		super();
	}

	public EquipmentCategory(Long id, String name, String abbreviation, Double operatingCost, Boolean isActive,
			Integer companyId) {
		super();
		this.id = id;
		this.name = name;
		this.abbreviation = abbreviation;
		this.operatingCost = operatingCost;
		this.isActive = isActive;
		this.companyId = companyId;
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

	@Column(name = "abbreviation")
	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	@Column(name = "IsActive")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "company_id")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Column(name = "operator_cost")
	public Double getOperatingCost() {
		return operatingCost;
	}

	public void setOperatingCost(Double operatingCost) {
		this.operatingCost = operatingCost;
	}

	@Column(name = "isMultiFuel")
	public Boolean getIsMultiFuel() {
		return isMultiFuel;
	}

	public void setIsMultiFuel(Boolean isMultiFuel) {
		this.isMultiFuel = isMultiFuel;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumn(name = "OutputUnitId")
	public Unit getPrimaryReadingUnit() {
		return primaryReadingUnit;
	}

	public void setPrimaryReadingUnit(Unit primaryReadingUnit) {
		this.primaryReadingUnit = primaryReadingUnit;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumn(name = "secondOutputUnitId")
	public Unit getSecondaryReadingUnit() {
		return secondaryReadingUnit;
	}

	public void setSecondaryReadingUnit(Unit secondaryReadingUnit) {
		this.secondaryReadingUnit = secondaryReadingUnit;
	}

}
