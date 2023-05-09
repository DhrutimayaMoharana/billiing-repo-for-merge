package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.BoqMasterLmpsMachineryAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsMachineryDeactivateRequest;

public interface BoqMasterLmpsMachineryService {

	CustomResponse addBoqMasterLmpsMachinery(BoqMasterLmpsMachineryAddUpdateRequest requestDTO);

	CustomResponse updateBoqMasterLmpsMachinery(BoqMasterLmpsMachineryAddUpdateRequest requestDTO);

	CustomResponse getBoqMasterLmpsMachineryByBoqMasterLmpsId(Long boqMasterLmpsId);

	CustomResponse deactivateBoqMasterLmpsMachinery(BoqMasterLmpsMachineryDeactivateRequest requestDTO);

	CustomResponse getMachineryTrip();

}
