package erp.boq_mgmt.exception.handler;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException.GatewayTimeout;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.enums.Responses;

@RestControllerAdvice
public class GlobalExceptionHandlerController {
	

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(value = { InternalServerError.class, NullPointerException.class,
			ConversionNotSupportedException.class, HttpMessageNotWritableException.class,
			IllegalArgumentException.class, GatewayTimeout.class, IOException.class })
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> handleInternalServerError(Exception ex) {

		CustomResponse response = new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), ex.getMessage(),
				"Error processing the request!");
		LOGGER.error(ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class, BindException.class,
			HttpMediaTypeNotAcceptableException.class, HttpMediaTypeNotSupportedException.class,
			HttpMessageNotReadableException.class, HttpRequestMethodNotSupportedException.class,
			MethodArgumentNotValidException.class, MissingServletRequestParameterException.class,
			MissingServletRequestPartException.class, TypeMismatchException.class })
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> handleBadRequest(Exception ex) {

		CustomResponse response = new CustomResponse(Responses.BAD_REQUEST.getCode(), ex.getMessage(), "Invalid request!");
		LOGGER.error(ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
