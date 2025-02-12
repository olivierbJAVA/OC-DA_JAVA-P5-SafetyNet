package com.safetynet.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Default controller of the SafetyNet application.
 */
@RestController
public class DefaultController {

	private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);

	/**
	 * Method in charge of managing the "/" url HTTP request.
	 */
	@GetMapping("/")
	public ResponseEntity<String> index() {
		logger.info("Start of services OK");
		return new ResponseEntity<>("Welcome to the SafetyNet API !<br/><br/>Server Status is " + HttpStatus.OK.name()
				+ ".<br/><br/>Please find the documentation at the following address : http://localhost:8080/swagger-ui.html",
				HttpStatus.OK);
	}

}