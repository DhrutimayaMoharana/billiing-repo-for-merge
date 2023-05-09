package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.BoqMasterLmpsMaterialAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsMaterialDeactivateRequest;
import erp.boq_mgmt.dto.request.MaterialFetchRequest;

public interface BoqMasterLmpsMaterialService {

	CustomResponse addBoqMasterLmpsMaterial(BoqMasterLmpsMaterialAddUpdateRequest requestDTO);

	CustomResponse updateBoqMasterLmpsMaterial(BoqMasterLmpsMaterialAddUpdateRequest requestDTO);

	CustomResponse getBoqMasterLmpsMaterialByBoqMasterLmpsId(Long boqMasterLmpsId);

	CustomResponse deactivateBoqMasterLmpsMaterial(BoqMasterLmpsMaterialDeactivateRequest requestDTO);

	CustomResponse getMaterialList(MaterialFetchRequest requestDTO);

}
