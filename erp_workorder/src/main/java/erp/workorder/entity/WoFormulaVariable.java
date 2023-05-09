package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wo_formula_variables")
public class WoFormulaVariable implements Serializable {

	private static final long serialVersionUID = -436846434072155604L;

	private Long id;

	private String name;

	private Date createdOn;

	private Long createdBy;

	private Integer companyId;

	public WoFormulaVariable() {
		super();
	}

	public WoFormulaVariable(Long id) {
		super();
		this.id = id;
	}

	public WoFormulaVariable(Long id, String name, Date createdOn, Long createdBy, Integer companyId) {
		super();
		this.id = id;
		this.name = name;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.setCompanyId(companyId);
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

	@Column(name = "company_id")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}
