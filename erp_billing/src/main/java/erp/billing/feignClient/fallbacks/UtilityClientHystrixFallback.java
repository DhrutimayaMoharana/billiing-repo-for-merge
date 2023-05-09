package erp.billing.feignClient.fallbacks;

import erp.billing.dto.CustomResponse;
import erp.billing.dto.SearchDTO;
import erp.billing.enums.Responses;
import erp.billing.feignClient.UtilityClient;
import erp.billing.feignClient.dto.IrnCancelRequest;
import erp.billing.feignClient.dto.IrnGenerateRequest;

public class UtilityClientHystrixFallback implements UtilityClient {

	@Override
	public CustomResponse getFileS3(SearchDTO search) {
		return new CustomResponse(Responses.SERVICE_DOWN.getCode(), null, Responses.SERVICE_DOWN.toString());
	}

	@Override
	public CustomResponse generateIrn(IrnGenerateRequest requestDTO) {
		return new CustomResponse(Responses.SERVICE_DOWN.getCode(), null, Responses.SERVICE_DOWN.toString());
	}

	@Override
	public CustomResponse cancelIrn(IrnCancelRequest requestDTO) {
		return new CustomResponse(Responses.SERVICE_DOWN.getCode(), null, Responses.SERVICE_DOWN.toString());

	}

	@Override
	public CustomResponse generateIrnByGstHero(IrnGenerateRequest requestDTO) {
		return new CustomResponse(Responses.SERVICE_DOWN.getCode(), null, Responses.SERVICE_DOWN.toString());
	}

	@Override
	public CustomResponse cancelIrnByGstHero(IrnCancelRequest requestDTO) {
		return new CustomResponse(Responses.SERVICE_DOWN.getCode(), null, Responses.SERVICE_DOWN.toString());
	}
}
