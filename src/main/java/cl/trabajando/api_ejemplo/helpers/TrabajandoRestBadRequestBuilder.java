package cl.trabajando.api_ejemplo.helpers;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.databind.JsonMappingException;
/**
 * 
 * @author Cristian Gonzalez 
 * 9 ago. 2019 
 *
 * Clase helper para poder personalizar errores y construir mensajes de error propietarios
 */
public class TrabajandoRestBadRequestBuilder {
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String APPLICATION_VND_TRABAJANDO_SPRING_VALIDATION_JSON = "application/vnd.trabajando.spring-validation+json";
    HashMap<String, List<String>> errores;

    private static final Pattern jacksonPathReferencePattern = Pattern.compile("\\[([^\\]]+)\\]");

    public TrabajandoRestBadRequestBuilder() {
	errores = new HashMap<String, List<String>>();
    }

    public TrabajandoRestBadRequestBuilder putAllErrors(BindingResult brs) {
	if (brs.hasErrors()) {
	    errores.putAll(brs.getGlobalErrors().stream()// sigue
		    .collect(groupingBy(ObjectError::getObjectName, mapping(ObjectError::getDefaultMessage, toList()))))// sigue
	    ;
	    errores.putAll(brs.getFieldErrors().stream()// sigue
		    .collect(groupingBy(FieldError::getField, mapping(FieldError::getDefaultMessage, toList()))))// sigue
	    ;
	}
	return this;
    }

    public TrabajandoRestBadRequestBuilder addError(String field, String msg) {
	if (errores.containsKey(field)) {
	    errores.get(field).add(msg);
	} else {
	    errores.put(field, Arrays.asList(msg));
	}
	return this;
    }

    public TrabajandoRestBadRequestBuilder addError(JsonMappingException ex, String msg) {
	List<String> binderPathComponents = new ArrayList<>();
	Matcher matcher = jacksonPathReferencePattern.matcher(ex.getPathReference().replaceAll("\"", ""));
	while (matcher.find()) {
	    binderPathComponents.add(matcher.group(1));
	}
	return addError(String.join(".", binderPathComponents).replaceAll(".(\\d+)", "[$1]"), msg);
    }

    public ResponseEntity<TrabajandoBadRequestBody> buildBadRequest() {
	TrabajandoBadRequestBody body = new TrabajandoBadRequestBody();
	body.setErrores(errores);
	HttpHeaders headers = new HttpHeaders();
	headers.add(CONTENT_TYPE_HEADER, APPLICATION_VND_TRABAJANDO_SPRING_VALIDATION_JSON);
	return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
    }
}
