package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.UserDetail;
import erp.boq_mgmt.dto.request.BoqCostDefinitionUpdateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionAddRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionDeactivateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionFetchRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionFinalSuccessFetchRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionNextPossibleStatesFetchRequest;
import erp.boq_mgmt.dto.request.UndefinedCostDefinitionBoqsFetchRequest;

public interface BoqCostDefinitionService {

	CustomResponse addBoqCostDefinition(BoqCostDefinitionAddRequest requestDTO);

	CustomResponse updateBoqCostDefinition(BoqCostDefinitionUpdateRequest requestDTO);

	CustomResponse getBoqCostDefinitionById(Long id);

	CustomResponse getBoqCostDefinition(BoqCostDefinitionFetchRequest requestDTO);

	CustomResponse deactivateBoqCostDefinition(BoqCostDefinitionDeactivateRequest requestDTO);

	CustomResponse getNextPossibleStates(BoqCostDefinitionNextPossibleStatesFetchRequest requestObj);

	CustomResponse getBoqCostDefinitionStateTransitionByBoqCostDefinitionId(Long boqCostDefinitionId);

	CustomResponse getBoqCostDefinitionFinalSuccessList(BoqCostDefinitionFinalSuccessFetchRequest requestDTO);

	CustomResponse getUndefinedCostDefinitionBoqs(UndefinedCostDefinitionBoqsFetchRequest requestDTO);

	CustomResponse boqCostDefinitionSaveAsDraft(Long boqId, Integer siteId, UserDetail userDetail);

}
