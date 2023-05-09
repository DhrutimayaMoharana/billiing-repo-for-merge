package erp.boq_mgmt.feignClient.fallbacks;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.feignClient.UtilityClient;

public class UtilityClientHystrixFallback implements UtilityClient {

	@Override
	public CustomResponse getFileS3(SearchDTO search) {

		return new CustomResponse(Responses.SERVICE_DOWN.getCode(), null, Responses.SERVICE_DOWN.toString());
	}
}
