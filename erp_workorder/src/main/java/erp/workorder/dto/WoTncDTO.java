package erp.workorder.dto;

import java.util.Date;
import java.util.List;

public class WoTncDTO {

	private Long id;

	private WoTncTypeDTO type;

	private String code;

	private String name;

	private String description;

	private String formula;

	private List<WoTncFormulaVariableDTO> formulaVariables;

	private Boolean isActive;

	private Date modifiedOn;

	private Long modifiedBy;

	private Integer companyId;

	// extra fields

	private Long mappingId;

	public WoTncDTO() {
		super();
	}

	public WoTncDTO(Long id) {
		super();
		this.id = id;
	}

	public WoTncDTO(Long id, WoTncTypeDTO type, String code, String name, String description, String formula,
			List<WoTncFormulaVariableDTO> formulaVariables, Boolean isActive, Date modifiedOn, Long modifiedBy,
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WoTncTypeDTO getType() {
		return type;
	}

	public void setType(WoTncTypeDTO type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public List<WoTncFormulaVariableDTO> getFormulaVariables() {
		return formulaVariables;
	}

	public void setFormulaVariables(List<WoTncFormulaVariableDTO> formulaVariables) {
		this.formulaVariables = formulaVariables;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getMappingId() {
		return mappingId;
	}

	public void setMappingId(Long mappingId) {
		this.mappingId = mappingId;
	}

}
