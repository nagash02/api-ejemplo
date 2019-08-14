package cl.trabajando.api_ejemplo.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import cl.trabajando.api_ejemplo.validators.EjemploValidator;

/**
 * 
 * @author Cristian Gonzalez 12 ago. 2019
 *
 *         Este pojo es un ejemplo para captura de datos y validaciones es usado
 *         para mostrar ejemplo de validator y de mybatis
 */
@JsonPropertyOrder({ "id", "valor" }) //esta anotacion permite definir el orden de los atributos del json
@EjemploValidator
public class DatoEjemplo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 241930280133689945L;
    @NotNull
    private Integer id;
    @NotEmpty
    private String valor;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getValor() {
	return valor;
    }

    public void setValor(String valor) {
	this.valor = valor;
    }

}
