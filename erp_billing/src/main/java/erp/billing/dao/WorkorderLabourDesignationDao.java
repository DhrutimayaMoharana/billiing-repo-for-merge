package erp.billing.dao;

import java.util.List;

import erp.billing.entity.WorkorderLabourDesignation;

public interface WorkorderLabourDesignationDao {

	Integer saveWorkorderLabourDesignation(WorkorderLabourDesignation workorderLabourDesignation);

	WorkorderLabourDesignation fetchworkorderLabourDesignationById(Integer id);

	Boolean updateWorkorderLabourDesignation(WorkorderLabourDesignation workorderLabourDesignation);

	List<WorkorderLabourDesignation> fetchWorkorderLabourDesignationDaoList(Integer companyId);

	Boolean deactivateWorkorderLabourDesignation(WorkorderLabourDesignation workorderLabourDesignation);

}
