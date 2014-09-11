package org.softgreen.ubigeo.exception;

public class IllegalResultException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public IllegalResultException(String message, Throwable cause) {
        super(message, cause);
    }
    public IllegalResultException(String message) {
        super(message);
    }
}