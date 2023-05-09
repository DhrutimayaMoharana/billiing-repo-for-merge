package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.BoqMasterLmpsLabourAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsLabourDeactivateRequest;

public interface BoqMasterLmpsLabourService {

	CustomResponse addBoqMasterLmpsLabour(BoqMasterLmpsLabourAddUpdateRequest requestDTO);

	CustomResponse updateBoqMasterLmpsLabour(BoqMasterLmpsLabourAddUpdateRequest requestDTO);

	CustomResponse getBoqMasterLmpsLabourByBoqMasterLmpsId(Long boqMasterLmpsId);

	CustomResponse deactivateBoqMasterLmpsLabour(BoqMasterLmpsLabourDeactivateRequest requestDTO);

}
