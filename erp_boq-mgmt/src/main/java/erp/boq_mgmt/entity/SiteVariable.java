package erp.boq_mgmt.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "site_variables")
public class SiteVariable implements Serializable {

	private static final long serialVersionUID = 5474633732537554469L;

	private Integer id;

	private String name;

	private Integer variableTypeId;

	private Boolean isActive;

	public SiteVariable() {
		super();
	}

	public SiteVariable(Integer id, String name, Integer variableTypeId, Boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.variableTypeId = variableTypeId;
		this.isActive = isActive;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "variable_type_id")
	public Integer getVariableTypeId() {
		return variableTypeId;
	}

	public void setVariableTypeId(Integer variableTypeId) {
		this.variableTypeId = variableTypeId;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
