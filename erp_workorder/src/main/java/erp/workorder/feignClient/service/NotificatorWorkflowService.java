package erp.workorder.feignClient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import erp.workorder.dto.CustomResponse;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.NotificatorWorkflowClient;

@Component
public class NotificatorWorkflowService {

	@Autowired
	private NotificatorWorkflowClient notificatorWorkflowClient;

	public static final String ENGINE_TOKEN = "not1f1c4torworkflow3ng1n3";

	public CustomResponse getWorkflowRuleByEntityIdAndStateId(Integer entityId, Integer eventStateId, Integer companyId,
			Integer siteId) {
		try {
			CustomResponse response = notificatorWorkflowClient.getWorkflowRuleByEntityIdAndStateId(entityId,
					eventStateId, companyId, siteId, ENGINE_TOKEN);
			if (response != null && response.getStatus().equals(Responses.SUCCESS.getCode())
					&& response.getData() != null) {
				return response;
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
