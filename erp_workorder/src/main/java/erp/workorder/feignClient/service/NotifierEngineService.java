package erp.workorder.feignClient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import erp.workorder.dto.CustomResponse;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.NotifierEngineClient;
import erp.workorder.notificator.dto.SendEmailRequest;

@Component
public class NotifierEngineService {

	@Autowired
	private NotifierEngineClient notifierEngineClient;

	public static final String ENGINE_TOKEN = "not1f13r3ng1n3";

	public CustomResponse sendEmail(SendEmailRequest obj) {
		try {
			CustomResponse response = notifierEngineClient.sendEmail(obj, ENGINE_TOKEN);
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
