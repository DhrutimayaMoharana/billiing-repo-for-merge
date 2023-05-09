package erp.workorder.feignClient.fallbacks;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.UtilityClient;

public class UtilityClientHystrixFallback implements UtilityClient {

	@Override
	public CustomResponse getFileS3(SearchDTO search) {

		return new CustomResponse(Responses.SERVICE_DOWN.getCode(), null, Responses.SERVICE_DOWN.toString());
	}
}
