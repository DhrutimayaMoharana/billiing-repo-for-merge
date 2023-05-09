package erp.workorder.service;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.WoTncMappingRequestDTO;

public interface WorkorderTncMapService {

	CustomResponse getWoTermsConditionsByWoId(SearchDTO search);

	CustomResponse mapWoTermsConditions(WoTncMappingRequestDTO woTncsMap);

	CustomResponse addUpdateWoTermsCondition(WoTncMappingRequestDTO woTncsMap);

	CustomResponse removeWoTermsCondition(SearchDTO search);

}
