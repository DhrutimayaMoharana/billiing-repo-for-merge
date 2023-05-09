package erp.boq_mgmt.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.feignClient.fallbacks.UtilityClientHystrixFallback;

@FeignClient(name = "UtilityService", fallback = UtilityClientHystrixFallback.class)
public interface UtilityClient {

	@RequestMapping(method = RequestMethod.POST, value = "/s3_files/b64_encoded", consumes = "application/json")
	public CustomResponse getFileS3(SearchDTO search);
}
