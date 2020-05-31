package com.safetynet.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = { "com.safetynet" })
public class ExceptionHandlerAdvice {

	@ExceptionHandler(RessourceNotFoundException.class)
	public ResponseEntity<String> handleException(RessourceNotFoundException e) {
		// log exception
		return new ResponseEntity<>(e.getMessage()+e.getRessource(), e.getHttpStatus());
	}
	
	@ExceptionHandler(RessourceAlreadyExistException.class)
	public ResponseEntity<String> handleException(RessourceAlreadyExistException e) {
		// log exception
		return new ResponseEntity<>(e.getMessage()+e.getRessource(), e.getHttpStatus());
	}
	
	@ExceptionHandler(InternalServerErrorException.class)
	public ResponseEntity<String> handleException(InternalServerErrorException e) {
		// log exception
		return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
	}
	
}
