package cl.trabajando.api_ejemplo.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.trabajando.api_ejemplo.model.DatoEjemplo;
import cl.trabajando.api_ejemplo.validators.EjemploValidator.EjemploValidatorHelper;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = EjemploValidatorHelper.class)
public @interface EjemploValidator {

    String message() default "No es posible agregar el dato, favor intente más tarde (COD : 1)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class EjemploValidatorHelper implements ConstraintValidator<EjemploValidator, DatoEjemplo> {

	private static Logger logger = LogManager.getLogger(EjemploValidatorHelper.class);

	@Override
	public boolean isValid(DatoEjemplo dato, ConstraintValidatorContext ctx) {
	    // TODO: implementar sus validaciones de negocio aqui
	    if (dato.getId() < 5) {
		logger.warn("el id es menor que 5");
		ctx.disableDefaultConstraintViolation();
		ctx.buildConstraintViolationWithTemplate("Ejemplo de dato valido el id es menor que 5")
			.addPropertyNode("id").addConstraintViolation(); //con esta línea indicamos que campo falla
		return false;
	    }

	    return true;
	}

    }
}
