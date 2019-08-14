package cl.trabajando.api_ejemplo.controllers.controller_advices;

import javax.validation.ValidationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import cl.trabajando.api_ejemplo.helpers.TrabajandoBadRequestBody;
import cl.trabajando.api_ejemplo.helpers.TrabajandoErrorApi;
import cl.trabajando.api_ejemplo.helpers.TrabajandoRestBadRequestBuilder;

@RestControllerAdvice
public class ExceptionsAdvice {
    private static Logger logger = LogManager.getLogger(ExceptionsAdvice.class);
    private static String GLOSA_PARAMETROS = "parametros :";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TrabajandoBadRequestBody> handleValidationException(
	    MethodArgumentNotValidException bindingException) {
	return new TrabajandoRestBadRequestBuilder().putAllErrors(bindingException.getBindingResult())
		.buildBadRequest();
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<TrabajandoBadRequestBody> handleBindException(BindException bindingException) {
	return new TrabajandoRestBadRequestBuilder().putAllErrors(bindingException.getBindingResult())
		.buildBadRequest();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<TrabajandoBadRequestBody> handleNotReadlableException(HttpMessageNotReadableException ex) {
	TrabajandoRestBadRequestBuilder badRequestBuilder = new TrabajandoRestBadRequestBuilder();
	if (ex.getCause() instanceof InvalidFormatException) {
	    InvalidFormatException iex = (InvalidFormatException) ex.getCause();
	    badRequestBuilder.addError(iex, String.format("valor inválido: %s", iex.getValue()));
	    badRequestBuilder.addError(iex, iex.getMessage());
	} else if (ex.getCause() instanceof JsonMappingException) {
	    badRequestBuilder.addError((JsonMappingException) ex.getCause(), ex.getMessage());
	} else {
	    badRequestBuilder.addError("body", ex.getMessage());
	}
	return badRequestBuilder.buildBadRequest();
    }

    @ExceptionHandler({ HttpClientErrorException.class })
    public ResponseEntity<Object> handleAll(HttpClientErrorException ex, WebRequest request) {
	TrabajandoErrorApi error = new TrabajandoErrorApi(HttpStatus.INTERNAL_SERVER_ERROR,
		"Error de API: Ha Ocurrido un Error Interno del Servidor.", ex.getCause().getLocalizedMessage());
	logger.error("Ha Ocurrido un Error Interno del Servidor: " + request.getDescription(true) + GLOSA_PARAMETROS
		+ request.getParameterMap().toString(), ex);
	return new ResponseEntity<Object>(error, new HttpHeaders(), error.getStatus());
    }

    @ExceptionHandler({ ValidationException.class })
    public ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
	String causes = ex.getLocalizedMessage() + " causa raiz :" + ex.getCause().getLocalizedMessage();
	TrabajandoErrorApi error = new TrabajandoErrorApi(HttpStatus.INTERNAL_SERVER_ERROR,
		"Ha Ocurrido un Error Interno del Servidor al realizar una validación.", causes);
	logger.error("Ha Ocurrido un Error Interno del Servidor al realizar una validación: "
		+ request.getDescription(true) + GLOSA_PARAMETROS + request.getParameterMap().toString(), ex);
	return new ResponseEntity<Object>(error, new HttpHeaders(), error.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<TrabajandoBadRequestBody> handleGenericException(Exception ex, WebRequest request) {
	logger.error("Ha Ocurrido un Error Interno del Servidor: " + request.getDescription(true) + GLOSA_PARAMETROS
		+ request.getParameterMap().toString(), ex);
	TrabajandoRestBadRequestBuilder badRequestBuilder = new TrabajandoRestBadRequestBuilder();
	badRequestBuilder.addError("body", ex.getCause().getLocalizedMessage());
	return badRequestBuilder.buildBadRequest();
    }
}
