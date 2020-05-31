package com.safetynet.exception;

import org.springframework.http.HttpStatus;

public class RessourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private HttpStatus httpStatus = HttpStatus.NOT_FOUND;
	private String ressource;

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getRessource() {
		return ressource;
	}

	/**
	 * Constructs a new runtime exception with the specified detail message.
	 * 
	 * @param message the detail message.
	 */
	public RessourceNotFoundException(HttpStatus httpStatus, String message, String ressource) {
		super(message);
		this.httpStatus = httpStatus;
		this.ressource = ressource;
	}

}