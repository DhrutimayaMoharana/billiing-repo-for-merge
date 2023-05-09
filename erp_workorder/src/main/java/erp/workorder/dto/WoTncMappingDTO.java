package erp.workorder.dto;

import java.util.Date;
import java.util.List;

public class WoTncMappingDTO {
	
	private Long id;
	
	private WoTncDTO termAndCondition;
	
	private String description;
	
	private List<WoTncFormulaVariableValueDTO> variableValues;
	
	private Integer sequenceNo;
	
	private Boolean isActive;
	
	private Date modifiedOn;
	
	private Long modifiedBy;
	
	// extra variables
	
	private String replacedDescription;

	public WoTncMappingDTO() {
		super();
	}

	public WoTncMappingDTO(Long id) {
		super();
		this.id = id;
	}

	public WoTncMappingDTO(Long id, WoTncDTO termAndCondition, String description,
			List<WoTncFormulaVariableValueDTO> variableValues, Integer sequenceNo, Boolean isActive, Date modifiedOn,
			Long modifiedBy) {
		super();
		this.id = id;
		this.termAndCondition = termAndCondition;
		this.description = description;
		this.variableValues = variableValues;
		this.sequenceNo = sequenceNo;
		this.isActive = isActive;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WoTncDTO getTermAndCondition() {
		return termAndCondition;
	}

	public void setTermAndCondition(WoTncDTO termAndCondition) {
		this.termAndCondition = termAndCondition;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<WoTncFormulaVariableValueDTO> getVariableValues() {
		return variableValues;
	}

	public void setVariableValues(List<WoTncFormulaVariableValueDTO> variableValues) {
		this.variableValues = variableValues;
	}

	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public String getReplacedDescription() {
		return replacedDescription;
	}

	public void setReplacedDescription(String replacedDescription) {
		this.replacedDescription = replacedDescription;
	}

}
