package erp.billing.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import erp.billing.dto.CustomResponse;
import erp.billing.dto.SearchDTO;
import erp.billing.feignClient.fallbacks.AuthorityClientHystrixFallback;

@FeignClient(name = "AuthorityService", fallback = AuthorityClientHystrixFallback.class)
public interface AuthorityClient {

	@RequestMapping(method = RequestMethod.POST, value = "/authentication/entity_authorities", consumes = "application/json")
	public CustomResponse getRoleEntityAuthorities(SearchDTO search);

}
