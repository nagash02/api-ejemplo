package cl.trabajando.api_ejemplo.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 
 * @author Cristian Gonzalez
 * 31 jul. 2019 
 * Usa este Pojo como Ejemplo de implementación de otros pojos
 */
public class HolaMundo  implements Serializable{
    
    
    /**
     * Recuerda que es importante que un pojo que va a ser transmitido o 
     * escrito debe tener una instancia
     * Serializable única 
     */
    private static final long serialVersionUID = -8632195665076752195L;
    
    private String mensaje;
    //esta anotación es necesaria cuando debes devolver campos long porque javascript y los 
    //lenguajes de frontend tienen un soporte menor de largo de decimales lo que hace que se redondee
    //al final 
    @JsonSerialize(using = ToStringSerializer.class)
    private long idLargo;
    
    /**
     * De esta forma pordemos formatear una fecha y evitamos que el formato json devuelva las fechas como 
     * expresión númerica
     */
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime fecha;
    //recuerda crear los gettter and setter con la herrmianeta de eclipse
    
    public HolaMundo() {
	//Constructor por defecto
    }
    
    public HolaMundo(LocalDateTime fecha) {
	this.fecha = fecha;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public long getIdLargo() {
        return idLargo;
    }

    public void setIdLargo(long idLargo) {
        this.idLargo = idLargo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    
    
}
