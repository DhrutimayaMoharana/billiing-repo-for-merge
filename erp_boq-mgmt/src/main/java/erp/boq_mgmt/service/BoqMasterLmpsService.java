package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.UserDetail;
import erp.boq_mgmt.dto.request.BoqMasterLmpsAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsDeactivateRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsFetchRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsFinalSuccessFetchRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsNextPossibleStatesFetchRequest;
import erp.boq_mgmt.dto.request.UndefinedMasterLmpsBoqsFetchRequest;

public interface BoqMasterLmpsService {

	CustomResponse addBoqMasterLmps(BoqMasterLmpsAddUpdateRequest requestDTO);

	CustomResponse updateBoqMasterLmps(BoqMasterLmpsAddUpdateRequest requestDTO);

	CustomResponse getBoqMasterLmpsById(Long id);

	CustomResponse getBoqMasterLmps(BoqMasterLmpsFetchRequest requestDTO);

	CustomResponse deactivateBoqMasterLmps(BoqMasterLmpsDeactivateRequest requestDTO);

	CustomResponse getNextPossibleStates(BoqMasterLmpsNextPossibleStatesFetchRequest requestObj);

	CustomResponse getBoqMasterLmpsStateTransitionByBoqMasterLmpsId(Long boqMasterLmpsId);

	CustomResponse getBoqMasterLmpsFinalSuccessList(BoqMasterLmpsFinalSuccessFetchRequest requestDTO);

	CustomResponse getUndefinedMasterLmpsBoqs(UndefinedMasterLmpsBoqsFetchRequest requestDTO);

	CustomResponse boqMasterLmpsSaveAsDraft(Long boqId, UserDetail userDetail);

	CustomResponse getBoqMasterLmpsIdByBoqId(Long boqId);

}
