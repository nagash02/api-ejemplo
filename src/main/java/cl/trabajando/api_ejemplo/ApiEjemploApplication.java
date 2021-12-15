package cl.trabajando.api_ejemplo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cl.trabajando.api_ejemplo.config.GlobalConfiguration;
import cl.trabajando.api_ejemplo.config.JwtConfiguration;
import cl.trabajando.api_ejemplo.config.SecurityConfiguration;
import cl.trabajando.api_ejemplo.config.SwaggerConfiguracion;

@SpringBootApplication
public class ApiEjemploApplication {

    public static void main(String[] args) {
	Class<?>[] configClasses = { GlobalConfiguration.class, SecurityConfiguration.class,
		SwaggerConfiguracion.class, JwtConfiguration.class };
	SpringApplication.run(configClasses, args);
    }
//hello
}
