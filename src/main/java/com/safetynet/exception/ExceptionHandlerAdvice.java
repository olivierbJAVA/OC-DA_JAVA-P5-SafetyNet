package com.safetynet.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = { "com.safetynet" })
public class ExceptionHandlerAdvice {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

	@ExceptionHandler(RessourceNotFoundException.class)
	public ResponseEntity<String> handleException(RessourceNotFoundException e) {

		logger.error("Error : ressource {} not found", e.getRessource());

		return new ResponseEntity<>(e.getMessage() + e.getRessource(), e.getHttpStatus());
	}

	@ExceptionHandler(RessourceAlreadyExistException.class)
	public ResponseEntity<String> handleException(RessourceAlreadyExistException e) {

		logger.error("Error : ressource {} already exist", e.getRessource());

		return new ResponseEntity<>(e.getMessage() + e.getRessource(), e.getHttpStatus());
	}

	@ExceptionHandler(InternalServerErrorException.class)
	public ResponseEntity<String> handleException(InternalServerErrorException e) {

		logger.error("Error during the operation");

		return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
	}

}
