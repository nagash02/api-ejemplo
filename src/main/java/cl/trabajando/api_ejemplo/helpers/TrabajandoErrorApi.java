package cl.trabajando.api_ejemplo.helpers;

import org.springframework.http.HttpStatus;

public class TrabajandoErrorApi {
    private HttpStatus status;
    private String mensaje;
    private String causa;

    public TrabajandoErrorApi(HttpStatus status, String mensaje, String causa) {
	super();
	this.setStatus(status);
	this.mensaje = mensaje;
	this.setCausa(causa);
    }

    public HttpStatus getStatus() {
	return status;
    }

    public void setStatus(HttpStatus status) {
	this.status = status;
    }

    public String getMensaje() {
	return mensaje;
    }

    public void setMensaje(String mensaje) {
	this.mensaje = mensaje;
    }

    public String getCausa() {
	return causa;
    }

    public void setCausa(String causa) {
	this.causa = causa;
    }
}
