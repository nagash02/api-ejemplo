package cl.trabajando.api_ejemplo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import cl.trabajando.api_ejemplo.exceptions.TrabajandoException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * @author Crsitian Gonzalez
 * 9 ago. 2019 
 * 
 * Clase que contiene la configuracion para el uso de Json Web Token en los servicios rest.
 * Esto permite que la seguridad y validacion de la invocación de un api sea delegada a Kong
 *
 */
@Configuration
public class JwtConfiguration {

    /**
     * Estos valores son extraidos desde la configuracion
     */
    private static final String JWT_ALGORITHM = "trabajando.jwt.algorithm";
    private static final String JWT_SECRET = "trabajando.jwt.secret";
    private static final String JWT_ISSUER = "trabajando.jwt.issuer";

    @Bean
    public SignatureAlgorithm jwtAlgorithm(Environment env) {
	String strAlgorithm = env.getProperty(JWT_ALGORITHM, String.class, "NULL");
	return SignatureAlgorithm.forName(strAlgorithm);
    }

    @Bean
    public JwtParser jwtParser(Environment env) {
	String jwtSecretString = env.getProperty(JWT_SECRET, String.class, "NULL");
	if (jwtSecretString.length() < 32) {
	    throw new TrabajandoException(JWT_SECRET + "=el secreto debe tener 32 caracteres");
	}
	String jwtIssuerString = env.getProperty(JWT_ISSUER, String.class, "");
	if (jwtIssuerString.isEmpty()) {
	    throw new TrabajandoException(JWT_ISSUER + "=Debe especificar un jwt issuer");
	}
	return Jwts.parser().requireIssuer(jwtIssuerString).setSigningKey(jwtSecretString.getBytes());
    }

    @Bean
    public JwtBuilder jwtBuilder(Environment env) {
	String jwtSecretString = env.getProperty(JWT_SECRET, String.class, "NULL");
	if (jwtSecretString.length() < 32) {
	    throw new TrabajandoException(JWT_SECRET + "=el secreto debe tener 32 caracteres");
	}
	String jwtIssuerString = env.getProperty(JWT_ISSUER, String.class, "");
	if (jwtIssuerString.isEmpty()) {
	    throw new TrabajandoException(JWT_ISSUER + "=Debe especificar un jwt issuer");
	}
	return Jwts.builder().setIssuer(jwtIssuerString).signWith(jwtAlgorithm(env), jwtSecretString.getBytes());
    }
}
