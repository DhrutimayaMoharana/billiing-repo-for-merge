package erp.billing.service;

import erp.billing.dto.CustomResponse;
import erp.billing.dto.request.WorkorderLabourAddUpdateRequest;
import erp.billing.dto.request.WorkorderLabourFetchRequest;

public interface WorkorderLabourService {

	CustomResponse addOrUpdateWorkorderLabour(WorkorderLabourAddUpdateRequest requestObj);

	CustomResponse getWorkorderLabourList(WorkorderLabourFetchRequest requestObj);

	CustomResponse getWorkorderLabourById(Integer id);

	CustomResponse deactivateWorkorderLabour(Integer id, Long userId);

}
