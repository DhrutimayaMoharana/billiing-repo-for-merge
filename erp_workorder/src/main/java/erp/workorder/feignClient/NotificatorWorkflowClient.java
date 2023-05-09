package erp.workorder.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import erp.workorder.dto.CustomResponse;
import erp.workorder.feignClient.fallbacks.NotificatorWorkflowClientHystrixFallback;

@FeignClient(name = "NotificatorWorkflowService", fallback = NotificatorWorkflowClientHystrixFallback.class)
public interface NotificatorWorkflowClient {

	@RequestMapping(method = RequestMethod.GET, value = "/workflow_rule/v1/get/{entityId}/{eventStateId}/{companyId}/{siteId}", consumes = "application/json")
	public CustomResponse getWorkflowRuleByEntityIdAndStateId(
			@PathVariable(value = "entityId", required = true) Integer entityId,
			@PathVariable(value = "eventStateId", required = true) Integer eventStateId,
			@PathVariable(value = "companyId", required = true) Integer companyId,
			@PathVariable(value = "siteId", required = true) Integer siteId, @RequestHeader("auth-head") String token);
}
