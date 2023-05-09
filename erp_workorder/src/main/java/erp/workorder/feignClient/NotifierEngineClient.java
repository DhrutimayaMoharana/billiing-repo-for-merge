package erp.workorder.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import erp.workorder.dto.CustomResponse;
import erp.workorder.feignClient.fallbacks.NotifierEngineClientHystrixFallback;
import erp.workorder.notificator.dto.SendEmailRequest;

@FeignClient(name = "NotifierEngineService", fallback = NotifierEngineClientHystrixFallback.class)
public interface NotifierEngineClient {

	@RequestMapping(method = RequestMethod.POST, value = "/email/v1/send", consumes = "application/json")
	public CustomResponse sendEmail(@RequestBody(required = true) SendEmailRequest emailRequest,
			@RequestHeader("auth-head") String token);
}
