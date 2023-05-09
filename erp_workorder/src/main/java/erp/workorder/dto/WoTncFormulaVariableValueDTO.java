package erp.workorder.dto;

import java.util.Date;

import erp.workorder.enums.VariableTypes;

public class WoTncFormulaVariableValueDTO {

	private Long id;

	private WoFormulaVariableDTO variable;

	private UnitDTO unit;

	private VariableTypes type;

	private WorkorderMasterVariableDTO masterVariable;

	private Boolean valueAtCreation;

	private Double value;

	private Date modifiedOn;

	private Long modifiedBy;

	public WoTncFormulaVariableValueDTO() {
		super();
	}

	public WoTncFormulaVariableValueDTO(Long id) {
		super();
		this.id = id;
	}

	public WoTncFormulaVariableValueDTO(Long id, WoFormulaVariableDTO variable, UnitDTO unit, VariableTypes type,
			WorkorderMasterVariableDTO masterVariable, Boolean valueAtCreation, Double value, Date modifiedOn,
			Long modifiedBy) {
		super();
		this.id = id;
		this.variable = variable;
		this.setMasterVariable(masterVariable);
		this.valueAtCreation = valueAtCreation;
		this.setUnit(unit);
		this.setType(type);
		this.value = value;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WoFormulaVariableDTO getVariable() {
		return variable;
	}

	public void setVariable(WoFormulaVariableDTO variable) {
		this.variable = variable;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public UnitDTO getUnit() {
		return unit;
	}

	public void setUnit(UnitDTO unit) {
		this.unit = unit;
	}

	public VariableTypes getType() {
		return type;
	}

	public void setType(VariableTypes type) {
		this.type = type;
	}

	public Boolean getValueAtCreation() {
		return valueAtCreation;
	}

	public void setValueAtCreation(Boolean valueAtCreation) {
		this.valueAtCreation = valueAtCreation;
	}

	public WorkorderMasterVariableDTO getMasterVariable() {
		return masterVariable;
	}

	public void setMasterVariable(WorkorderMasterVariableDTO masterVariable) {
		this.masterVariable = masterVariable;
	}

}
