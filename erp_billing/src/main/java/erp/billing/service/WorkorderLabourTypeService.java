package erp.billing.service;

import erp.billing.dto.CustomResponse;
import erp.billing.dto.request.WorkorderLabourTypeAddUpdateRequest;

public interface WorkorderLabourTypeService {

	CustomResponse addOrUpdateWorkorderLabourType(WorkorderLabourTypeAddUpdateRequest requestObj);

	CustomResponse getWorkorderLabourTypeList(Integer companyId);

	CustomResponse deactivateWorkorderLabourType(Integer id, Long userId);

	CustomResponse getWorkorderLabourTypeById(Integer id);

}
