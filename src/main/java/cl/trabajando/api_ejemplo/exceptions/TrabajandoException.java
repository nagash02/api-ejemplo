package cl.trabajando.api_ejemplo.exceptions;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class TrabajandoException extends RuntimeException {

    private static final long serialVersionUID = -7448287938918515070L;

    private Throwable rootCause;
    private long code;
    private String fullStackTrace;
    private String simpleStackTrace;

    /**
     * Default Constructor
     *
     */
    public TrabajandoException() {
    }

    /**
     * 
     * @param message
     */
    public TrabajandoException(String message) {
	super("[" + UUID.randomUUID().toString() + "] - " + message);
    }

    /**
     * 
     * @param message
     * @param errCode
     */
    public TrabajandoException(String message, long errCode) {
	super("[" + UUID.randomUUID().toString() + "] - " + message + "[" + String.valueOf(errCode) + "]");
	setCode(errCode);
    }

    /**
     * 
     * @param message
     * @param cause
     * @param errCode
     */
    public TrabajandoException(String message, Throwable cause, long errCode) {
	super("[" + UUID.randomUUID().toString() + "] - " + message, cause);
	setCode(errCode);
	rootCause = ExceptionUtils.getRootCause(cause);
    }

    /**
     * 
     * @param cause
     */
    public TrabajandoException(Exception cause) {
	super(cause);
	rootCause = ExceptionUtils.getRootCause(cause);
    }

    /**
     * 
     * @param message
     * @param cause
     */
    public TrabajandoException(String message, Throwable cause) {
	super("[" + UUID.randomUUID().toString() + "] - " + message, cause);
	rootCause = ExceptionUtils.getRootCause(cause);
    }

    // Getters
    public long getCode() {
	return code;
    }

    public Throwable getRootCause() {
	return rootCause;
    }

    public String getFullStackTrace() {
	if (fullStackTrace == null) {
	    fullStackTrace = ExceptionUtils.getStackTrace(this);
	}
	return fullStackTrace;
    }

    /**
     * 
     * @return
     */
    public String getSimpleStackTrace() {
	if (simpleStackTrace == null) {
	    StringBuilder sb = new StringBuilder();
	    List<Throwable> throwables = ExceptionUtils.getThrowableList(this);
	    sb.append(ExceptionUtils.getMessage(throwables.remove(0)));
	    for (Throwable thr : throwables) {
		sb.append(", caused by ");
		sb.append(ExceptionUtils.getMessage(thr));
	    }
	    simpleStackTrace = sb.toString();
	}
	return simpleStackTrace;
    }

    /**
     * 
     * @param e
     * @return 
     */
    public static String printFullStackTrace(Throwable e) {
	return ExceptionUtils.getStackTrace(e);
    }

    /**
     * 
     * @param 
     */
    public void setCode(long v) {
	this.code = v;
    }

    /**
     * Override toString method for more convenience output
     * 
     * @return a detailed message of this exception
     * 
     **/
    @Override
    public String toString() {
	String messageStr;

	messageStr = getMessage();
	if (messageStr == null) {
	    messageStr = "";
	}
	if (getCode() > 0) {
	    messageStr = messageStr + "[" + getCode() + "]";
	}
	if (getCause() != null) {
	    if (getRootCause() != null) {
		messageStr = messageStr + ", Cause Exception: " + getCause() + ", Root Cause Exception: "
			+ getRootCause();
	    } else {
		messageStr = messageStr + ", Cause and Root Exception: " + getCause();
	    }
	}
	return messageStr;
    }
}
