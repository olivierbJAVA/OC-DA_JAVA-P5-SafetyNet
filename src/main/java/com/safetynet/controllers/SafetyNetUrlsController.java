package com.safetynet.controllers;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.safetynet.entities.FirestationMapping;
import com.safetynet.entities.MedicalRecord;
import com.safetynet.entities.Person;
import com.safetynet.model.IFirestationMappingModel;
import com.safetynet.model.IMedicalRecordModel;
import com.safetynet.model.IPersonModel;
import com.safetynet.util.IInputReader;

@RestController
public class SafetyNetUrlsController {

	private static final Logger logger = LoggerFactory.getLogger(SafetyNetUrlsController.class);

	@Autowired
	private IPersonModel personModel;

	@Autowired
	private IFirestationMappingModel firestationMappingModel;
	
	@Autowired
	private IMedicalRecordModel medicalRecordModel;

	
	
	@GetMapping(value = "/communityEmail")
	public ResponseEntity<String> getPersonById(@RequestParam String city) {
		/*
		List<Person> persons = personModel.getAllPersons();
		
		@GetMapping(value = "/persons")
		public ResponseEntity<List<Person>> getAllPersons() {
			List<Person> persons = personModel.getAllPersons();
			logger.info("Success : persons list found");
			return new ResponseEntity<>(persons, HttpStatus.FOUND);
		}
		*/
		
		
		/*
		Person personToGet = personModel.getPersonById(id);
		if (personToGet == null) {
			logger.error("Error : person not found");
			// return ResponseEntity.noContent().build();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		logger.info("Success : person found");
		
		return new ResponseEntity<>(personToGet, HttpStatus.FOUND);
		*/
		return new ResponseEntity<>("la valeur du param "+city+"", HttpStatus.FOUND);

	}

}
