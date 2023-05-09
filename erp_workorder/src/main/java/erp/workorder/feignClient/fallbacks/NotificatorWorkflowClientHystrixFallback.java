package erp.workorder.feignClient.fallbacks;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import erp.workorder.dto.CustomResponse;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.NotificatorWorkflowClient;

public class NotificatorWorkflowClientHystrixFallback implements NotificatorWorkflowClient {

	@Override
	public CustomResponse getWorkflowRuleByEntityIdAndStateId(
			@PathVariable(value = "entityId", required = true) Integer entityId,
			@PathVariable(value = "eventStateId", required = true) Integer eventStateId,
			@PathVariable(value = "companyId", required = true) Integer companyId,
			@PathVariable(value = "siteId", required = true) Integer siteId, @RequestHeader("auth-head") String token) {

		return new CustomResponse(Responses.SERVICE_DOWN.getCode(), null, Responses.SERVICE_DOWN.toString());
	}

}
