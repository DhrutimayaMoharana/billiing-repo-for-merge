package erp.billing.service;

import erp.billing.dto.CustomResponse;
import erp.billing.dto.request.WorkorderLabourDepartmentAddUpdateRequest;

public interface WorkorderLabourDepartmentService {

	CustomResponse addOrUpdateWorkorderLabourDepartment(WorkorderLabourDepartmentAddUpdateRequest requestObj);

	CustomResponse getWorkorderLabourDepartmentList(Integer companyId);

	CustomResponse deactivateWorkorderLabourDepartment(Integer id, Long userId);

	CustomResponse getWorkorderLabourDepartmentById(Integer id);

}
