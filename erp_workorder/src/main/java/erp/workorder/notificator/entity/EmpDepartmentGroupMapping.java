package erp.workorder.notificator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emp_department_group_mapping")
public class EmpDepartmentGroupMapping {

	private Integer id;

	private Integer departmentGroupId;

	private Integer employeeId;

	private Boolean isActive;

	public EmpDepartmentGroupMapping() {
		super();
	}

	public EmpDepartmentGroupMapping(Integer id, Integer departmentGroupId, Integer employeeId, Boolean isActive) {
		super();
		this.id = id;
		this.departmentGroupId = departmentGroupId;
		this.employeeId = employeeId;
		this.isActive = isActive;
	}

	public EmpDepartmentGroupMapping(Integer id) {
		super();
		this.id = id;
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

	@Column(name = "emp_department_group_id")
	public Integer getDepartmentGroupId() {
		return departmentGroupId;
	}

	public void setDepartmentGroupId(Integer departmentGroupId) {
		this.departmentGroupId = departmentGroupId;
	}

	@Column(name = "emp_id")
	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
