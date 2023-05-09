package erp.billing.dao;

import java.util.List;

import erp.billing.dto.request.WorkorderLabourFetchRequest;
import erp.billing.entity.WorkorderLabour;

public interface WorkorderLabourDao {

	Integer saveWorkorderLabour(WorkorderLabour workorderLabour);

	WorkorderLabour fetchworkorderLabourById(Integer id);

	Boolean updateWorkorderLabourDepartment(WorkorderLabour workorderLabour);

	List<WorkorderLabour> fetchWorkorderLabourDaoList(WorkorderLabourFetchRequest requestObj);

	Boolean deactivateWorkorderLabourDepartment(WorkorderLabour workorderLabour);

}
