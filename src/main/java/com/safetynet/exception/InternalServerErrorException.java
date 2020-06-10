package com.safetynet.exception;

import org.springframework.http.HttpStatus;

/**
 * Class for the InternalServerErrorException.
 */
public class InternalServerErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	/**
	 * Constructs a new runtime exception with the specified detail message.
	 * 
	 * @param httpStatus the httpStatus
	 * 
	 * @param message    the detail message
	 */
	public InternalServerErrorException(HttpStatus httpStatus, String message) {
		super(message);
		this.httpStatus = httpStatus;
	}

}
