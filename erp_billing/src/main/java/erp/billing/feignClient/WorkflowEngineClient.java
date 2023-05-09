package erp.billing.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import erp.billing.dto.CustomResponse;
import erp.billing.feignClient.fallbacks.WorkflowEngineClientHystrixFallback;

@FeignClient(name = "WorkflowEngineService", fallback = WorkflowEngineClientHystrixFallback.class)
public interface WorkflowEngineClient {

	@RequestMapping(method = RequestMethod.GET, value = "/state_transition/{entityId}", consumes = "application/json")
	public CustomResponse getStateTransition(@PathVariable(name = "entityId", required = true) Integer entityId,
			@RequestParam(name = "siteId", required = true) Integer siteId,
			@RequestParam(name = "stateId", required = false) Integer stateId,
			@RequestParam(name = "roleId", required = false) Integer roleId,
			@RequestParam(name = "companyId", required = false) Integer companyId,
			@RequestHeader("auth-head") String token);

	@RequestMapping(method = RequestMethod.GET, value = "/entity_state/v1/initial/{entityId}/{companyId}", consumes = "application/json")
	public CustomResponse getEntityInitialState(@PathVariable(name = "entityId", required = true) Integer entityId,
			@PathVariable(name = "companyId", required = true) Integer companyId,
			@RequestHeader("auth-head") String token);

	@RequestMapping(method = RequestMethod.GET, value = "/entity_state/v1/if_editable/{entityId}/{companyId}/{stateId}", consumes = "application/json")
	public CustomResponse findIfEntityInEditableState(
			@PathVariable(name = "entityId", required = true) Integer entityId,
			@PathVariable(name = "companyId", required = true) Integer companyId,
			@PathVariable(name = "stateId", required = true) Integer stateId, @RequestHeader("auth-head") String token);

	@RequestMapping(method = RequestMethod.GET, value = "/entity_state/v1/all/{entityId}/{companyId}", consumes = "application/json")
	public CustomResponse getEntityStatesByCompanyId(@PathVariable(name = "entityId", required = true) Integer entityId,
			@PathVariable(name = "companyId", required = true) Integer companyId,
			@RequestHeader("auth-head") String token);

	@RequestMapping(method = RequestMethod.GET, value = "/entity_state/v1/{entityId}/{stateId}/{companyId}", consumes = "application/json")
	public CustomResponse getEntityStateMap(@PathVariable(name = "entityId", required = true) Integer entityId,
			@PathVariable(name = "stateId", required = true) Integer stateId,
			@PathVariable(name = "companyId", required = true) Integer companyId,
			@RequestHeader("auth-head") String token);

	@RequestMapping(method = RequestMethod.GET, value = "/entity_state/v1/final/{entityId}/{companyId}", consumes = "application/json")
	public CustomResponse getEntityFinalStateMultiple(
			@PathVariable(name = "entityId", required = true) Integer entityId,
			@PathVariable(name = "companyId", required = true) Integer companyId,
			@RequestHeader("auth-head") String token);

	@RequestMapping(method = RequestMethod.GET, value = "/entity_state/v1/final/{entityId}/{companyId}/{stateActionId}", consumes = "application/json")
	public CustomResponse getEntityStateByFinalAndStateAction(
			@PathVariable(name = "entityId", required = true) Integer entityId,
			@PathVariable(name = "stateActionId", required = true) Integer stateActionId,
			@PathVariable(name = "companyId", required = true) Integer companyId,
			@RequestHeader("auth-head") String token);

}
