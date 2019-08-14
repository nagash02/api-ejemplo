package cl.trabajando.api_ejemplo.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

//coleccion que alojará el dato en la bd Mongo
@Document(collection = "datos")
public class DatoEjemploMongo implements Serializable {

    /**
     * recuerde que debe seleccionar la opcion de generar para este dato en su IDE
     */
    private static final long serialVersionUID = -5337083887150972070L;
    // corresponde al id de mongo
    @Id
    private String id;
    // indica que este campo esta indexado y permite solo una instancia del dato
    @Indexed(name = "ind_ejemplo", unique = true)
    private Integer codigo;
    private String nombre;
    private String direccion;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public Integer getCodigo() {
	return codigo;
    }

    public void setCodigo(Integer codigo) {
	this.codigo = codigo;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public String getDireccion() {
	return direccion;
    }

    public void setDireccion(String direccion) {
	this.direccion = direccion;
    }

}
