package cl.trabajando.api_ejemplo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import cl.trabajando.api_ejemplo.model.DatoEjemplo;
import cl.trabajando.api_ejemplo.model.EjemploToken;
import cl.trabajando.api_ejemplo.model.HolaMundo;
import cl.trabajando.api_ejemplo.services.ApiEjemploService;
import cl.trabajando.api_ejemplo.services.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author Cristian Gonzalez 12 ago. 2019 Este es un ejemplo de controlador con
 *         las funciones posibles para su api recuerde documentar lo mejor
 *         posible con swagger su servicio
 */
@RestController()
@Api(tags = "Ejemplo", value = "Ejemplo de tags para descipcion")
public class MainController {

    @Autowired
    private ApiEjemploService ejemploService;

    @Autowired
    private JwtService jwtService;

    /**
     * Ejemplo de como responder un objeto
     * 
     * @return
     */
    @ApiOperation(value = "Esto es un ejemplo de retorno de pojo desde un ser servicio", notes = "Trate de Entregar la mayor info posible")
    @ApiResponses({ @ApiResponse(code = 200, message = "Mensaje correcto", response = HolaMundo.class),
	    @ApiResponse(code = 400, message = "Esto es el resultado de una validación de negocio", response = HttpStatus.class),
	    @ApiResponse(code = 409, message = "Error en la aplicacion que fue capturada por un try", response = HttpStatus.class) })
    @GetMapping(value = "/v1/mensaje")
    public ResponseEntity<?> getHolaMundo() {
	return new ResponseEntity<>(ejemploService.getHolaMundo(), HttpStatus.OK);
    }

    /**
     * Ejemplo de como responder un status code especifico
     */
    @ApiOperation(value = "Esto es un ejemplo de retorno de un status code, cuando no es necesario devolver datos", notes = "")
    @ApiResponses({ @ApiResponse(code = 200, message = "Ejecucion Correcta", response = HttpStatus.class),
	    @ApiResponse(code = 400, message = "Hay un problema con una validacion de negocio", response = HttpStatus.class),
	    @ApiResponse(code = 409, message = "Error en la aplicacion capturada con un try", response = HttpStatus.class) })
    @GetMapping(value = "/v1/status")
    public ResponseEntity<?> getStatrusCode() {
	// status code bad gatway
	return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    /**
     * Ejemplo de uso de path variable para entregar parámetros
     * 
     * @param id
     * @return
     */
    @ApiOperation(value = "EJemplo de uso de path variable", notes = "")
    @ApiResponses({ @ApiResponse(code = 200, message = "Mensaje correcto", response = HttpStatus.class),
	    @ApiResponse(code = 400, message = "Error de negocio o validación", response = HttpStatus.class),
	    @ApiResponse(code = 404, message = "Objeto no encontrado", response = HttpStatus.class),
	    @ApiResponse(code = 409, message = "Error controlado por un try", response = HttpStatus.class) })
    @GetMapping(value = "/v1/mensaje/{idEjemplo:.*}")
    public ResponseEntity<?> getHolaMundoById(
	    @ApiParam(name = "idEjemplo", value = "Esto es un id de ejemplo", required = true) @PathVariable("idEjemplo") String idEjemplo) {
	return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }

    /**
     * Este es un ejemplo de dato post agregado con una validacion de negocio en
     * validator
     * 
     * @param dato
     * @return
     */
    @ApiOperation(value = "Ejemplo de uso de path variable", notes = "")
    @ApiResponses({ @ApiResponse(code = 201, message = "Dato creado correctamente", response = HttpStatus.class),
	    @ApiResponse(code = 400, message = "Error de negocio o validación", response = HttpStatus.class),
	    @ApiResponse(code = 409, message = "Error controlado por un try", response = HttpStatus.class) })
    @PostMapping(value = "/v1/datos")
    public ResponseEntity<?> agregarDato(@Validated @RequestBody DatoEjemplo dato) {
	ejemploService.insertarDato(dato);
	// si falla la validacion no entra a este controlador y devuelve un status code
	// 400
	return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Ejemplo de emisión de un token JWT
     */
    @ApiOperation(value = "Ejemplo de emisión de token jwt", notes = "")
    @ApiResponses({ @ApiResponse(code = 200, message = "token emitido", response = HttpStatus.class),
	    @ApiResponse(code = 400, message = "Error de negocio o validación", response = HttpStatus.class),
	    @ApiResponse(code = 409, message = "Error controlado por un try", response = HttpStatus.class) })
    @PostMapping("/v1/token")
    public ResponseEntity<?> emitirToken() {
	Map<String, Object> claims = new HashMap<>();
	claims.put("holamundo", "hola mundo");
	String token = jwtService.emitToken(claims);
	EjemploToken ejemplo = new EjemploToken();
	ejemplo.setJwt(token);
	return new ResponseEntity<>(ejemplo, HttpStatus.OK);
    }

    /**
     * Ejemplo de validacion de un token JWT
     */
    @ApiOperation(value = "Ejemplo de emisión de token jwt", notes = "")
    @ApiResponses({ @ApiResponse(code = 200, message = "token emitido", response = HttpStatus.class),
	    @ApiResponse(code = 400, message = "Error de negocio o validación", response = HttpStatus.class),
	    @ApiResponse(code = 409, message = "Error controlado por un try", response = HttpStatus.class) })
    @GetMapping("/v1/token")
    public ResponseEntity<?> validarToken(
	    @RequestHeader(required = true, name = "Authorization") String authorization) {
	ResponseEntity<?> respuesta = jwtService.validarAutorizacion(authorization);
	if (respuesta != null) {
	    return respuesta;
	} else {
	    // Su codigo aquí
	    return new ResponseEntity<>(HttpStatus.OK);
	}
    }

}
