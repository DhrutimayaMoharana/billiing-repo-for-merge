package erp.workorder.feignClient.fallbacks;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import erp.workorder.dto.CustomResponse;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.WorkflowEngineClient;

public class WorkflowEngineClientHystrixFallback implements WorkflowEngineClient {

	@Override
	public CustomResponse getStateTransition(@PathVariable(name = "entityId", required = true) Integer entityId,
			@RequestParam(name = "siteId", required = true) Integer siteId,
			@RequestParam(name = "stateId", required = false) Integer stateId,
			@RequestParam(name = "roleId", required = false) Integer roleId,
			@RequestParam(name = "companyId", required = false) Integer companyId,
			@RequestHeader("auth-head") String token) {

		return new CustomResponse(Responses.SERVICE_DOWN.getCode(), null, Responses.SERVICE_DOWN.toString());
	}

	@Override
	public CustomResponse getEntityInitialState(@PathVariable(name = "entityId", required = true) Integer entityId,
			@PathVariable(name = "companyId", required = true) Integer companyId,
			@RequestHeader("auth-head") String token) {

		return new CustomResponse(Responses.SERVICE_DOWN.getCode(), null, Responses.SERVICE_DOWN.toString());
	}

	@Override
	public CustomResponse findIfEntityInEditableState(
			@PathVariable(name = "entityId", required = true) Integer entityId,
			@PathVariable(name = "companyId", required = true) Integer companyId,
			@PathVariable(name = "stateId", required = true) Integer stateId,
			@RequestHeader("auth-head") String token) {

		return new CustomResponse(Responses.SERVICE_DOWN.getCode(), null, Responses.SERVICE_DOWN.toString());
	}

	@Override
	public CustomResponse getEntityStatesByCompanyId(@PathVariable(name = "entityId", required = true) Integer entityId,
			@PathVariable(name = "companyId", required = true) Integer companyId,
			@RequestHeader("auth-head") String token) {

		return new CustomResponse(Responses.SERVICE_DOWN.getCode(), null, Responses.SERVICE_DOWN.toString());
	}

	@Override
	public CustomResponse getEntityStateMap(@PathVariable(name = "entityId", required = true) Integer entityId,
			@PathVariable(name = "stateId", required = true) Integer stateId,
			@PathVariable(name = "companyId", required = true) Integer companyId,
			@RequestHeader("auth-head") String token) {

		return new CustomResponse(Responses.SERVICE_DOWN.getCode(), null, Responses.SERVICE_DOWN.toString());
	}

	@Override
	public CustomResponse getEntityFinalStateMultiple(
			@PathVariable(name = "entityId", required = true) Integer entityId,
			@PathVariable(name = "companyId", required = true) Integer companyId,
			@RequestHeader("auth-head") String token) {

		return new CustomResponse(Responses.SERVICE_DOWN.getCode(), null, Responses.SERVICE_DOWN.toString());
	}

	@Override
	public CustomResponse getEntityStateByFinalAndStateAction(
			@PathVariable(name = "entityId", required = true) Integer entityId,
			@PathVariable(name = "stateActionId", required = true) Integer stateActionId,
			@PathVariable(name = "companyId", required = true) Integer companyId,
			@RequestHeader("auth-head") String token) {

		return new CustomResponse(Responses.SERVICE_DOWN.getCode(), null, Responses.SERVICE_DOWN.toString());
	}
}
