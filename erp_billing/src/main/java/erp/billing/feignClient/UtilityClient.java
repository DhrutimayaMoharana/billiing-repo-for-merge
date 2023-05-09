package erp.billing.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import erp.billing.dto.CustomResponse;
import erp.billing.dto.SearchDTO;
import erp.billing.feignClient.dto.IrnCancelRequest;
import erp.billing.feignClient.dto.IrnGenerateRequest;
import erp.billing.feignClient.fallbacks.UtilityClientHystrixFallback;

@FeignClient(name = "UtilityService", fallback = UtilityClientHystrixFallback.class)
public interface UtilityClient {

	@RequestMapping(method = RequestMethod.POST, value = "/s3_files/b64_encoded", consumes = "application/json")
	public CustomResponse getFileS3(SearchDTO search);

	@RequestMapping(method = RequestMethod.POST, value = "/irn_govt/v1/generate", consumes = "application/json")
	public CustomResponse generateIrn(IrnGenerateRequest requestDTO);

	@RequestMapping(method = RequestMethod.POST, value = "/irn_govt/v1/cancel", consumes = "application/json")
	public CustomResponse cancelIrn(IrnCancelRequest requestDTO);

	@RequestMapping(method = RequestMethod.POST, value = "/irn_gst_hero/v1/generate", consumes = "application/json")
	public CustomResponse generateIrnByGstHero(IrnGenerateRequest requestDTO);

	@RequestMapping(method = RequestMethod.POST, value = "/irn_gst_hero/v1/cancel", consumes = "application/json")
	public CustomResponse cancelIrnByGstHero(IrnCancelRequest requestDTO);

}
