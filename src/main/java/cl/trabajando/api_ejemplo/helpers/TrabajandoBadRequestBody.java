package cl.trabajando.api_ejemplo.helpers;

import java.util.HashMap;
import java.util.List;

public class TrabajandoBadRequestBody {

    private HashMap<String, List<String>> errores;

    public HashMap<String, List<String>> getErrores() {
	return errores;
    }

    public void setErrores(HashMap<String, List<String>> errores) {
	this.errores = errores;
    }
}
