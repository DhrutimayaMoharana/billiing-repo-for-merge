package erp.billing.exceptionHandler;

import java.io.IOException;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException.GatewayTimeout;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import erp.billing.dto.CustomResponse;

@RestControllerAdvice
public class GlobalExceptionHandlerController {

	@ExceptionHandler(value = { InternalServerError.class, NullPointerException.class,
			ConversionNotSupportedException.class, HttpMessageNotWritableException.class,
			IllegalArgumentException.class, GatewayTimeout.class, IOException.class })
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> handleInternalServerError(Exception ex) {

		ex.printStackTrace();
		CustomResponse response = new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
				"Error processing the request! Caused by " + ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class, BindException.class,
			HttpMediaTypeNotAcceptableException.class, HttpMediaTypeNotSupportedException.class,
			HttpMessageNotReadableException.class, MethodArgumentNotValidException.class,
			MissingServletRequestParameterException.class, MissingServletRequestPartException.class,
			TypeMismatchException.class })
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> handleBadRequest(Exception ex) {

		ex.printStackTrace();
		CustomResponse response = new CustomResponse(HttpStatus.BAD_REQUEST.value(), null,
				"Error processing the request! Caused by " + ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
