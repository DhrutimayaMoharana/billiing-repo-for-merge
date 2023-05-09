package erp.billing.dao;

import java.util.List;

import erp.billing.entity.WorkorderLabourDepartment;

public interface WorkorderLabourDepartmentDao {

	Integer saveWorkorderLabourDepartment(WorkorderLabourDepartment workorderLabourDepartment);

	WorkorderLabourDepartment fetchworkorderLabourDepartmentById(Integer id);

	Boolean updateWorkorderLabourDepartment(WorkorderLabourDepartment workorderLabourDepartment);

	List<WorkorderLabourDepartment> fetchWorkorderLabourDepartmentDaoList(Integer companyId);

	Boolean deactivateWorkorderLabourDepartment(WorkorderLabourDepartment workorderLabourDepartment);

}
