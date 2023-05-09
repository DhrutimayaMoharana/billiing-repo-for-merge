package erp.workorder.service;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.WoTncDTO;

public interface WorkorderTncService {

	CustomResponse getWoTypeWiseTermsConditions(SearchDTO search);

	CustomResponse getWoTermsConditions(SearchDTO search);

	CustomResponse addWoTermsCondition(WoTncDTO woTncDTO);

	CustomResponse getVariableTypes(SearchDTO search);

	CustomResponse getWoTermsConditionById(SearchDTO search);

	CustomResponse getTncTypewiseTermsConditions(SearchDTO search);

	CustomResponse removeWoTermsCondition(SearchDTO search);

	CustomResponse updateWoTermsCondition(WoTncDTO woTncDTO);

	CustomResponse getWorkorderMasterVariables();

}
