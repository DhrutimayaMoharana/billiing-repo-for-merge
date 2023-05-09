package erp.workorder.dto;

import java.util.Date;

public class WoFormulaVariableDTO {

	private Long id;
	
	private String name;
	
	private Date createdOn;
	
	private Long createdBy;

	private Integer companyId;
	
	public WoFormulaVariableDTO() {
		super();
	}

	public WoFormulaVariableDTO(Long id) {
		super();
		this.id = id;
	}

	public WoFormulaVariableDTO(Long id, String name, Date createdOn, Long createdBy, Integer companyId) {
		super();
		this.id = id;
		this.name = name;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.setCompanyId(companyId);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
}
