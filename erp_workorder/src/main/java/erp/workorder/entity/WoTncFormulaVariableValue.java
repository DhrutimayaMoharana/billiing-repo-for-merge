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

import com.fasterxml.jackson.annotation.JsonIgnore;

import erp.workorder.enums.VariableTypes;

@Entity
@Table(name = "wo_tnc_variable_values")
public class WoTncFormulaVariableValue implements Serializable {

	private static final long serialVersionUID = 4518926266287995761L;

	private Long id;

	private WoTncMapping woTnc;

	private WoFormulaVariable variable;

	private Unit unit;

	private VariableTypes type;

	private WorkorderMasterVariable masterVariable;

	private Boolean valueAtCreation;

	private Double value;

	private Date modifiedOn;

	private Long modifiedBy;

	public WoTncFormulaVariableValue() {
		super();
	}

	public WoTncFormulaVariableValue(Long id) {
		super();
		this.id = id;
	}

	public WoTncFormulaVariableValue(Long id, WoTncMapping woTnc, WoFormulaVariable variable, Unit unit,
			VariableTypes type, WorkorderMasterVariable masterVariable, Boolean valueAtCreation, Double value,
			Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.woTnc = woTnc;
		this.variable = variable;
		this.valueAtCreation = valueAtCreation;
		this.setMasterVariable(masterVariable);
		this.unit = unit;
		this.type = type;
		this.value = value;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
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

	@ManyToOne
	@JoinColumn(name = "wo_tnc_mapping_id")
	@JsonIgnore
	public WoTncMapping getWoTnc() {
		return woTnc;
	}

	public void setWoTnc(WoTncMapping woTnc) {
		this.woTnc = woTnc;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "variable_id")
	public WoFormulaVariable getVariable() {
		return variable;
	}

	public void setVariable(WoFormulaVariable variable) {
		this.variable = variable;
	}

	@Column(name = "value")
	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
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

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "master_variable_id")
	public WorkorderMasterVariable getMasterVariable() {
		return masterVariable;
	}

	public void setMasterVariable(WorkorderMasterVariable masterVariable) {
		this.masterVariable = masterVariable;
	}

}
