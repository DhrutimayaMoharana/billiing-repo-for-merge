package erp.workorder.feignClient.fallbacks;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import erp.workorder.dto.CustomResponse;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.NotifierEngineClient;
import erp.workorder.notificator.dto.SendEmailRequest;

public class NotifierEngineClientHystrixFallback implements NotifierEngineClient {

	@Override
	public CustomResponse sendEmail(@RequestBody(required = true) SendEmailRequest emailRequest,
			@RequestHeader("auth-head") String token) {

		return new CustomResponse(Responses.SERVICE_DOWN.getCode(), null, Responses.SERVICE_DOWN.toString());

	}

}
