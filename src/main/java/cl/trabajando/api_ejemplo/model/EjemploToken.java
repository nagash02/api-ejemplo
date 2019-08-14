package cl.trabajando.api_ejemplo.model;

import java.io.Serializable;

/**
 * 
 * @author Cristian Gonzalez
 * 13 ago. 2019 
 *
 * Este es un pojo usado para el ejemplo de uso de JWT
 */
public class EjemploToken implements Serializable {

    
    private static final long serialVersionUID = 5527207309360457371L;
    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
    
}
