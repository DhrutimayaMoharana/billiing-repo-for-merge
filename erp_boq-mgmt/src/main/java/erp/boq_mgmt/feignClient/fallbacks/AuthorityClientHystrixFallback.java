package erp.boq_mgmt.feignClient.fallbacks;

import erp.boq_mgmt.dto.AuthorityDTO;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.feignClient.AuthorityClient;

public class AuthorityClientHystrixFallback implements AuthorityClient {

	@Override
	public CustomResponse getRoleEntityAuthorities(SearchDTO search) {

		return new CustomResponse(Responses.SERVICE_DOWN.getCode(), new AuthorityDTO(),
				Responses.SERVICE_DOWN.toString());
	}

}