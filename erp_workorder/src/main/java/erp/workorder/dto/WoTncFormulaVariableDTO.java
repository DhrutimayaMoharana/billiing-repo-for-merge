package erp.workorder.dto;

import java.util.Date;

import erp.workorder.enums.VariableTypes;

public class WoTncFormulaVariableDTO {

	private Long id;

	private WoFormulaVariableDTO variable;

	private UnitDTO unit;

	private VariableTypes type;

	private Boolean valueAtCreation;

	private WorkorderMasterVariableDTO masterVariable;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	public WoTncFormulaVariableDTO() {
		super();
	}

	public WoTncFormulaVariableDTO(Long id) {
		super();
		this.id = id;
	}

	public WoTncFormulaVariableDTO(Long id, WoFormulaVariableDTO variable, UnitDTO unit, VariableTypes type,
			Boolean valueAtCreation, WorkorderMasterVariableDTO masterVariable, Boolean isActive, Date createdOn,
			Long createdBy) {
		super();
		this.id = id;
		this.variable = variable;
		this.unit = unit;
		this.type = type;
		this.valueAtCreation = valueAtCreation;
		this.setMasterVariable(masterVariable);
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public WorkorderMasterVariableDTO getMasterVariable() {
		return masterVariable;
	}

	public void setMasterVariable(WorkorderMasterVariableDTO masterVariable) {
		this.masterVariable = masterVariable;
	}

}
