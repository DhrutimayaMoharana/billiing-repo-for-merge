package erp.billing.feignClient.fallbacks;

import erp.billing.dto.AuthorityDTO;
import erp.billing.dto.CustomResponse;
import erp.billing.dto.SearchDTO;
import erp.billing.enums.Responses;
import erp.billing.feignClient.AuthorityClient;

public class AuthorityClientHystrixFallback implements AuthorityClient {

	@Override
	public CustomResponse getRoleEntityAuthorities(SearchDTO search) {
		return new CustomResponse(Responses.SERVICE_DOWN.getCode(), new AuthorityDTO(),
				Responses.SERVICE_DOWN.toString());
	}

}
