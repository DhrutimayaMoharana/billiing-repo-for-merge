package erp.billing.dao;

import java.util.List;

import erp.billing.entity.WorkorderLabourType;

public interface WorkorderLabourTypeDao {

	Integer saveWorkorderLabourType(WorkorderLabourType workorderLabourType);

	WorkorderLabourType fetchworkorderLabourTypeById(Integer id);

	Boolean updateWorkorderLabourType(WorkorderLabourType workorderLabourType);

	List<WorkorderLabourType> fetchWorkorderLabourTypeDaoList(Integer companyId);

	Boolean deactivateWorkorderLabourType(WorkorderLabourType workorderLabourType);

}
