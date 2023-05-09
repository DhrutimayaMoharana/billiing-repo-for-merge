package erp.billing.service;

import erp.billing.dto.CustomResponse;
import erp.billing.dto.request.WorkorderLabourDesignationAddUpdateRequest;

public interface WorkorderLabourDesignationService {

	CustomResponse addOrUpdateWorkorderLabourDesignation(WorkorderLabourDesignationAddUpdateRequest requestObj);

	CustomResponse getWorkorderLabourDesignationList(Integer companyId);

	CustomResponse deactivateWorkorderLabourDesignation(Integer id, Long userId);

	CustomResponse getWorkorderLabourDesignationById(Integer id);

}
