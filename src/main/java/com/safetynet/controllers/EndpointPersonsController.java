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
import com.safetynet.service.endpoints.IPersonService;
import com.safetynet.exception.InternalServerErrorException;

@RestController
public class EndpointPersonsController {

	private static final Logger logger = LoggerFactory.getLogger(EndpointPersonsController.class);

	@Autowired
	private IPersonService personModel;

	@GetMapping(value = "/persons")
	public ResponseEntity<List<Person>> getAllPersons() {

		logger.info("Request : GET /persons");

		List<Person> persons = personModel.getAllPersons();

		logger.info("Success : persons found");

		return new ResponseEntity<>(persons, HttpStatus.FOUND);
	}

	@GetMapping(value = "/persons/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable String id) {

		logger.info("Request : GET /persons/{}", id);

		Person personToGet = personModel.getPersonById(id);

		if (personToGet == null) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", id);
		}

		logger.info("Success : person {} found", id);

		return new ResponseEntity<>(personToGet, HttpStatus.FOUND);
	}

	@PostMapping(value = "/persons")
	public ResponseEntity<Person> addPerson(@RequestBody Person person) {

		logger.info("Request : POST /persons");

		if (personModel.personExist(person)) {
			throw new RessourceAlreadyExistException(HttpStatus.BAD_REQUEST, "Error ressource already exist : ",
					person.getFirstName() + person.getLastName());
		}

		person.setIdPerson(person.getFirstName() + person.getLastName());

		personModel.addPerson(person);

		if (!personModel.personExist(person)) {
			throw new InternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the operation");
		}

		logger.info("Success : person {} added", person.getIdPerson());

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(person.getIdPerson()).toUri();

		return ResponseEntity.created(location).build();
		// return new ResponseEntity<>(personAdded, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PutMapping(value = "/persons/{id}")
	public ResponseEntity<Person> updatePerson(@PathVariable String id, @RequestBody Person person) {

		logger.info("Request : PUT /persons/{}", id);

		if (personModel.getPersonById(id) == null) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", id);
		}

		if (personModel.getPersonById(person.getFirstName() + person.getLastName()) == null) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ",
					person.getFirstName() + person.getLastName());
		}

		person.setIdPerson(id);

		personModel.updatePerson(person);

		if (!personModel.getPersonById(id).equals(person)) {
			throw new InternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the operation");
		}

		logger.info("Success : person {} updated", id);

		return new ResponseEntity<>(person, HttpStatus.OK);
	}

	@DeleteMapping(value = "/persons/{id}")
	public ResponseEntity<Void> deletePerson(@PathVariable(value = "id") String id) {

		logger.info("Request : DELETE /persons/{}", id);

		if (personModel.getPersonById(id) == null) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", id);
		}

		Person personDeleted = personModel.deletePerson(id);

		if (personModel.personExist(personDeleted)) {
			throw new InternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the operation");
		}

		logger.info("Success : person {} deleted", id);

		return new ResponseEntity<>(HttpStatus.GONE);
	}

}
