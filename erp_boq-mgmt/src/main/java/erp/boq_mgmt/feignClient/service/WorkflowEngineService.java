package erp.boq_mgmt.feignClient.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.EngineStateDTO;
import erp.boq_mgmt.dto.EntityStateMapDTO;
import erp.boq_mgmt.dto.StateTransitionDTO;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.feignClient.WorkflowEngineClient;

@Component
public class WorkflowEngineService {

	@Autowired
	private WorkflowEngineClient workflowEngineClient;

	public static final String ENGINE_TOKEN = "d3v3lop3r";

	public List<StateTransitionDTO> getStateTransition(Integer entityId, Long siteId, Integer stateId, Integer roleId,
			Integer companyId) {
		try {
			CustomResponse response = workflowEngineClient.getStateTransition(entityId,
					siteId != null ? siteId.intValue() : null, stateId, roleId, companyId, ENGINE_TOKEN);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			if (response != null && response.getStatus().equals(Responses.SUCCESS.getCode())
					&& response.getData() != null) {
				return (List<StateTransitionDTO>) mapper.convertValue(response.getData(),
						new TypeReference<List<StateTransitionDTO>>() {
						});
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public EngineStateDTO getEntityInitialState(Integer entityId, Integer companyId) {
		try {
			CustomResponse response = workflowEngineClient.getEntityInitialState(entityId, companyId, ENGINE_TOKEN);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			if (response != null && response.getStatus().equals(Responses.SUCCESS.getCode())
					&& response.getData() != null) {
				return (EngineStateDTO) mapper.convertValue(response.getData(), new TypeReference<EngineStateDTO>() {
				});
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Boolean findIfEntityInEditableState(Integer entityId, Integer companyId, Integer stateId) {
		try {
			CustomResponse response = workflowEngineClient.findIfEntityInEditableState(entityId, companyId, stateId,
					ENGINE_TOKEN);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			if (response != null && response.getStatus().equals(Responses.SUCCESS.getCode())
					&& response.getData() != null) {
				return (Boolean) mapper.convertValue(response.getData(), new TypeReference<Boolean>() {
				});
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<EntityStateMapDTO> getEntityStatesByCompanyId(Integer entityId, Integer companyId) {
		try {
			CustomResponse response = workflowEngineClient.getEntityStatesByCompanyId(entityId, companyId,
					ENGINE_TOKEN);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			if (response != null && response.getStatus().equals(Responses.SUCCESS.getCode())
					&& response.getData() != null) {
				return (List<EntityStateMapDTO>) mapper.convertValue(response.getData(),
						new TypeReference<List<EntityStateMapDTO>>() {
						});
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public EntityStateMapDTO getEntityStateMap(Integer entityId, Integer stateId, Integer companyId) {
		try {
			CustomResponse response = workflowEngineClient.getEntityStateMap(entityId, stateId, companyId,
					ENGINE_TOKEN);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			if (response != null && response.getStatus().equals(Responses.SUCCESS.getCode())
					&& response.getData() != null) {
				return (EntityStateMapDTO) mapper.convertValue(response.getData(),
						new TypeReference<EntityStateMapDTO>() {
						});
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<EngineStateDTO> getEntityFinalStateMultiple(Integer entityId, Integer companyId) {
		try {
			CustomResponse response = workflowEngineClient.getEntityFinalStateMultiple(entityId, companyId,
					ENGINE_TOKEN);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			if (response != null && response.getStatus().equals(Responses.SUCCESS.getCode())
					&& response.getData() != null) {
				return (List<EngineStateDTO>) mapper.convertValue(response.getData(),
						new TypeReference<List<EngineStateDTO>>() {
						});
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public EntityStateMapDTO getEntityStateByFinalAndStateAction(Integer entityId, Integer stateActionId,
			Integer companyId) {
		try {
			CustomResponse response = workflowEngineClient.getEntityStateByFinalAndStateAction(entityId, stateActionId,
					companyId, ENGINE_TOKEN);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			if (response != null && response.getStatus().equals(Responses.SUCCESS.getCode())
					&& response.getData() != null) {
				return (EntityStateMapDTO) mapper.convertValue(response.getData(),
						new TypeReference<EntityStateMapDTO>() {
						});
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
