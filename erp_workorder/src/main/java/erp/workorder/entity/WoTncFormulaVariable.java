package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import erp.workorder.enums.VariableTypes;

@Entity
@Table(name = "wo_tnc_formula_variables")
public class WoTncFormulaVariable implements Serializable {

	private static final long serialVersionUID = -1021984708877699421L;

	private Long id;

	private WoTnc termCondition;

	private WoFormulaVariable variable;

	private Unit unit;

	private VariableTypes type;

	private Boolean valueAtCreation;

	private WorkorderMasterVariable masterVariable;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	public WoTncFormulaVariable() {
		super();
	}

	public WoTncFormulaVariable(Long id) {
		super();
		this.id = id;
	}

	public WoTncFormulaVariable(Long id, WoTnc termCondition, WoFormulaVariable variable, Unit unit, VariableTypes type,
			Boolean valueAtCreation, WorkorderMasterVariable masterVariable, Boolean isActive, Date createdOn,
			Long createdBy) {
		super();
		this.id = id;
		this.termCondition = termCondition;
		this.variable = variable;
		this.unit = unit;
		this.type = type;
		this.valueAtCreation = valueAtCreation;
		this.setMasterVariable(masterVariable);
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "term_condition_id")
	@JsonIgnore
	public WoTnc getTermCondition() {
		return termCondition;
	}

	public void setTermCondition(WoTnc termCondition) {
		this.termCondition = termCondition;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "variable_id")
	public WoFormulaVariable getVariable() {
		return variable;
	}

	public void setVariable(WoFormulaVariable variable) {
		this.variable = variable;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unit_id")
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "variable_type")
	public VariableTypes getType() {
		return type;
	}

	public void setType(VariableTypes type) {
		this.type = type;
	}

	@Column(name = "value_at_creation")
	public Boolean getValueAtCreation() {
		return valueAtCreation;
	}

	public void setValueAtCreation(Boolean valueAtCreation) {
		this.valueAtCreation = valueAtCreation;
	}

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "created_by")
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "master_variable_id")
	public WorkorderMasterVariable getMasterVariable() {
		return masterVariable;
	}

	public void setMasterVariable(WorkorderMasterVariable masterVariable) {
		this.masterVariable = masterVariable;
	}

}
