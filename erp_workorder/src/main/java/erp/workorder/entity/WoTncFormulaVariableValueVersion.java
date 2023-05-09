package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import erp.workorder.enums.VariableTypes;

@Entity
@Table(name = "wo_tnc_variable_values_versions")
public class WoTncFormulaVariableValueVersion implements Serializable {

	private static final long serialVersionUID = 4518926266287995761L;

	private Long id;

	private Long woTncVersionId;

	private Long variableId;

	private Long unitId;

	private VariableTypes type;

	private Integer masterVariableId;

	private Boolean valueAtCreation;

	private Double value;

	private Date createdOn;

	private Long createdBy;

	public WoTncFormulaVariableValueVersion() {
		super();
	}

	public WoTncFormulaVariableValueVersion(Long id, Long woTncVersionId, Long variableId, Long unitId,
			VariableTypes type, Integer masterVariableId, Boolean valueAtCreation, Double value, Date createdOn,
			Long createdBy) {
		super();
		this.id = id;
		this.woTncVersionId = woTncVersionId;
		this.variableId = variableId;
		this.unitId = unitId;
		this.type = type;
		this.masterVariableId = masterVariableId;
		this.valueAtCreation = valueAtCreation;
		this.value = value;
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

	@Column(name = "wo_tnc_mapping_id")
	public Long getWoTncVersionId() {
		return woTncVersionId;
	}

	public void setWoTncVersionId(Long woTncVersionId) {
		this.woTncVersionId = woTncVersionId;
	}

	@Column(name = "variable_id")
	public Long getVariableId() {
		return variableId;
	}

	public void setVariableId(Long variableId) {
		this.variableId = variableId;
	}

	@Column(name = "value")
	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
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

	@Column(name = "unit_id")
	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
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

	@Column(name = "master_variable_id")
	public Integer getMasterVariableId() {
		return masterVariableId;
	}

	public void setMasterVariableId(Integer masterVariableId) {
		this.masterVariableId = masterVariableId;
	}

}
