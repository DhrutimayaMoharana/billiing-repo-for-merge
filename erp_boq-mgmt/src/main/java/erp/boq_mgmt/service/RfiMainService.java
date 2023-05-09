package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.RfiBoqGetExecutableQuantityRequest;
import erp.boq_mgmt.dto.request.RfiMainAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiMainByStateActionFetchRequest;
import erp.boq_mgmt.dto.request.RfiMainDeactivateRequest;
import erp.boq_mgmt.dto.request.RfiMainExportSummaryRequest;
import erp.boq_mgmt.dto.request.RfiMainFetchRequest;
import erp.boq_mgmt.dto.request.RfiMainNextPossibleStatesFetchRequest;

public interface RfiMainService {

	CustomResponse getRfiWorkTypes();

	CustomResponse getRfiModes();

	CustomResponse deactivateRfiMain(RfiMainDeactivateRequest requestDTO);

	CustomResponse getAllRfiMain(RfiMainFetchRequest requestDTO);

	CustomResponse getRfiMainById(Long id, Integer companyId);

	CustomResponse getPrintRfiMainById(Long id, Integer companyId);

	CustomResponse updateRfiMain(RfiMainAddUpdateRequest requestDTO);

	CustomResponse addRfiMain(RfiMainAddUpdateRequest requestDTO);

	CustomResponse getWorkItemExecutableQuantity(RfiBoqGetExecutableQuantityRequest requestDTO);

	CustomResponse getNextPossibleStates(RfiMainNextPossibleStatesFetchRequest requestObj);

	CustomResponse getRfiMainListByStateAction(RfiMainByStateActionFetchRequest requestDTO);

	CustomResponse getRfiCommentTypes();

	CustomResponse exportRfiMainSummary(RfiMainExportSummaryRequest requestDTO);

	CustomResponse getRfiMainStateTransitionByRfiMainId(Long rfiMainId);

}
