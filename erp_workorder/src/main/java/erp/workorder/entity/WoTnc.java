package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "wo_tnc")
public class WoTnc implements Serializable {

	private static final long serialVersionUID = -5795589582159565080L;

	private Long id;

	private WoTncType type;

	private String code;

	private String name;

	private String description;

	private String formula;

	private List<WoTncFormulaVariable> formulaVariables;

	private Boolean isActive;

	private Date modifiedOn;

	private Long modifiedBy;

	private Integer companyId;

	public WoTnc() {
		super();
	}

	public WoTnc(Long id) {
		super();
		this.id = id;
	}

	public WoTnc(Long id, WoTncType type, String code, String name, String description, String formula,
			List<WoTncFormulaVariable> formulaVariables, Boolean isActive, Date modifiedOn, Long modifiedBy,
			Integer companyId) {
		super();
		this.id = id;
		this.type = type;
		this.code = code;
		this.name = name;
		this.description = description;
		this.formula = formula;
		this.formulaVariables = formulaVariables;
		this.isActive = isActive;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
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

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id")
	public WoTncType getType() {
		return type;
	}

	public void setType(WoTncType type) {
		this.type = type;
	}

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "formula")
	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "termCondition", orphanRemoval = false)
	public List<WoTncFormulaVariable> getFormulaVariables() {
		return formulaVariables;
	}

	public void setFormulaVariables(List<WoTncFormulaVariable> formulaVariables) {
		this.formulaVariables = formulaVariables;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "company_id")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}