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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.safetynet.entities.endpoints.Person;
import com.safetynet.exception.RessourceAlreadyExistException;
import com.safetynet.exception.RessourceNotFoundException;
import com.safetynet.exception.InternalServerErrorException;
import com.safetynet.model.endpoints.IPersonModel;

@RestController
public class EndpointPersonsController {

	private static final Logger logger = LoggerFactory.getLogger(EndpointPersonsController.class);

	@Autowired
	private IPersonModel personModel;

	@GetMapping(value = "/persons")
	public ResponseEntity<List<Person>> getAllPersons() {

		List<Person> persons = personModel.getAllPersons();

		logger.info("Success : persons list found");

		return new ResponseEntity<>(persons, HttpStatus.FOUND);
	}

	@GetMapping(value = "/persons/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable String id) {

		Person personToGet = personModel.getPersonById(id);

		if (personToGet == null) {
			logger.error("Error : person {} not found", id);
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", id);
		}

		logger.info("Success : person {} found", id);

		return new ResponseEntity<>(personToGet, HttpStatus.FOUND);
	}

	@PostMapping(value = "/persons")
	public ResponseEntity<Person> addPerson(@RequestBody Person person) {

		if (personModel.personExist(person)) {
			logger.error("Error : person already exist");
			throw new RessourceAlreadyExistException(HttpStatus.BAD_REQUEST, "Error ressource already exist : ",
					person.getFirstName() + person.getLastName());
		}

		person.setIdPerson(person.getFirstName() + person.getLastName());

		personModel.addPerson(person);

		if (!personModel.personExist(person)) {
			logger.error("Error : person not added");
			throw new InternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the operation");
		}

		logger.info("Success : person added");
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(person.getIdPerson()).toUri();

		return ResponseEntity.created(location).build();
		// return new ResponseEntity<>(personAdded, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PutMapping(value = "/persons/{id}")
	public ResponseEntity<Person> updatePerson(@PathVariable String id, @RequestBody Person person) {

		if (personModel.getPersonById(id) == null) {
			logger.error("Error : person does not exist");
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", id);
		}

		person.setIdPerson(id);

		// Person personUpdated = personModel.updatePerson(person);
		personModel.updatePerson(person);
		/*
		 * if (!personUpdated.getAddress().equals(personToUpdate.getAddress())) {
		 * logger.error("Error : person not updated"); return new
		 * ResponseEntity<>(person, HttpStatus.INTERNAL_SERVER_ERROR); }
		 */
		return new ResponseEntity<>(person, HttpStatus.OK);
	}

	@DeleteMapping(value = "/persons/{id}")
	public ResponseEntity<Void> deletePerson(@PathVariable(value = "id") String id) {

		if (personModel.getPersonById(id) == null) {
			logger.error("Error : person does not exist");
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", id);
		}

		Person personDeleted = personModel.deletePerson(id);

		if (personModel.personExist(personDeleted)) {
			logger.error("Error : person not deleted");
			throw new InternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the operation");
		}

		logger.info("Success : person deleted");
		return new ResponseEntity<>(HttpStatus.GONE);
	}

}
