package erp.workorder.feignClient.fallbacks;

import erp.workorder.dto.AuthorityDTO;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.AuthorityClient;

public class AuthorityClientHystrixFallback implements AuthorityClient {

	@Override
	public CustomResponse getRoleEntityAuthorities(SearchDTO search) {

		return new CustomResponse(Responses.SERVICE_DOWN.getCode(), new AuthorityDTO(),
				Responses.SERVICE_DOWN.toString());
	}

}
