package cl.trabajando.api_ejemplo.services;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cl.trabajando.api_ejemplo.exceptions.TrabajandoException;
import cl.trabajando.api_ejemplo.helpers.TrabajandoRestBadRequestBuilder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;

/**
 * 
 * @author Cristian Gonzalez 9 ago. 2019
 *
 *         Este Servicio permite manupular JWT, la expiración por defecto es de
 *         20 minutos.
 * 
 */
@Service
public class JwtService {

    @Autowired
    private JwtBuilder jwtBuilder;

    /**
     * Estas variables definen la variación del tiempo de duración de un token,
     * recuerde que un token vencido no podrá ser parseado y Kong lo rechazará
     */
    static final long ONE_MINUTE_IN_MILLIS = 60000;
    static final long MINUTES = 20;

    private static Logger logger = LogManager.getLogger(JwtService.class);

    /**
     * Emite un token JWT
     * 
     * @param claims
     * @return
     * @throws JwtException
     * 
     */
    public String emitToken(Map<String, Object> claims) {
	Calendar date = Calendar.getInstance();
	Date fechaCreacion = new Date(date.getTimeInMillis());
	/**
	 * Modifique la fecha de exíracion para obtener tiempos de duracion menores en
	 * el JWT
	 */
	Date fechaExpiracion = new Date(date.getTimeInMillis() + (ONE_MINUTE_IN_MILLIS * MINUTES));
	return jwtBuilder.setIssuedAt(fechaCreacion).setExpiration(fechaExpiracion).addClaims(claims).compact();
    }

    @Autowired
    private JwtParser jwtParser;

    /**
     * Permite saber si un jwt es válido
     * 
     * @param jwt
     * @return
     * 
     */
    public boolean isSignedJwt(String jwt) {
	try {
	    jwtParser.parse(jwt);
	    return true;
	} catch (JwtException ex) {
	    logger.warn("No fue posible validar el JWT", ex);
	    return false;
	}
    }

    /**
     * Permite leer los claims de un jwt, los claim son un hashmap de para-valor que
     * puede contener información adicional en el jwt
     * 
     * @param jwt
     * @return
     * 
     */
    public Claims getJwtClaims(String jwt) {
	Object body = jwtParser.parse(jwt).getBody();
	if (body instanceof Claims) {
	    return (Claims) body;
	}
	throw new TrabajandoException("Formato JWT no soportado");
    }

    /**
     * Valida una cabecera Bearer de authorizacion
     * @param authorization
     * @return
     */
    public ResponseEntity<?> validarAutorizacion(String authorization) {
	try {
	    if (authorization.length() < 8 || !authorization.startsWith("Bearer ")) {
		return new TrabajandoRestBadRequestBuilder()
			.addError("authorization", "Se requiere encabezado Authorization Bearer.").buildBadRequest();
	    } else {
		//Implmentar en caso de que se utilice un servicio de lista negra
		/* 
		 * if(blackListService.validarToken(authorization)) { return null; }else {
		 * return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); }
		 */
		if(!isSignedJwt(authorization.substring(7))){
		    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}	
	    }
	    return null;
	} catch (Exception ex) {
	    logger.error("No se pudo autenticar utilizando JWT", ex);
	    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
    }
}