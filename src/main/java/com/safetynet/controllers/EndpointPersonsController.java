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

/**
 * Controller in charge of managing the endpoint for the persons.
 */
@RestController
public class EndpointPersonsController {

	private static final Logger logger = LoggerFactory.getLogger(EndpointPersonsController.class);

	@Autowired
	private IPersonService personService;

	/**
	 * Method managing the GET "/persons" url HTTP request.
	 */
	@GetMapping(value = "/persons")
	public ResponseEntity<List<Person>> getAllPersons() {

		logger.info("Request : GET /persons");

		List<Person> persons = personService.getAllPersons();

		logger.info("Success : persons found");

		return new ResponseEntity<>(persons, HttpStatus.FOUND);
	}

	/**
	 * Method managing the GET "/persons/{id}" endpoint HTTP request.
	 * 
	 * @param id The id of the person to get
	 */
	@GetMapping(value = "/persons/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable String id) {

		logger.info("Request : GET /persons/{}", id);

		Person personToGet = personService.getPersonById(id);

		if (personToGet == null) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", id);
		}

		logger.info("Success : person {} found", id);

		return new ResponseEntity<>(personToGet, HttpStatus.FOUND);
	}

	/**
	 * Method managing the POST "/persons" endpoint HTTP request.
	 * 
	 * @param person The person to add
	 */
	@PostMapping(value = "/persons")
	public ResponseEntity<Person> addPerson(@RequestBody Person person) {

		logger.info("Request : POST /persons");

		// We check that the resource to add don't already exist
		if (personService.personExist(person)) {
			throw new RessourceAlreadyExistException(HttpStatus.BAD_REQUEST, "Error ressource already exist : ",
					person.getFirstName() + person.getLastName());
		}

		person.setIdPerson(person.getFirstName() + person.getLastName());

		personService.addPerson(person);

		// We check that the resource has been added
		if (!personService.personExist(person)) {
			throw new InternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the operation");
		}

		logger.info("Success : person {} added", person.getIdPerson());

		// We return the new resource location in the header
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(person.getIdPerson()).toUri();

		return ResponseEntity.created(location).build();
	}

	/**
	 * Method managing the PUT "/persons/{id}" endpoint HTTP request.
	 * 
	 * @param id     The id of the person to update
	 * 
	 * @param person The person to update
	 */
	@PutMapping(value = "/persons/{id}")
	public ResponseEntity<Person> updatePerson(@PathVariable String id, @RequestBody Person person) {

		logger.info("Request : PUT /persons/{}", id);

		// We check that the resource to update exist
		if (personService.getPersonById(id) == null) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", id);
		}

		if (personService.getPersonById(person.getFirstName() + person.getLastName()) == null) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ",
					person.getFirstName() + person.getLastName());
		}

		person.setIdPerson(id);

		personService.updatePerson(person);

		// We check that the resource has been updated
		if (!personService.getPersonById(id).equals(person)) {
			throw new InternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the operation");
		}

		logger.info("Success : person {} updated", id);

		return new ResponseEntity<>(person, HttpStatus.OK);
	}

	/**
	 * Method managing the DELETE "/persons/{id}" endpoint HTTP request.
	 * 
	 * @param id The id of the person to delete
	 */
	@DeleteMapping(value = "/persons/{id}")
	public ResponseEntity<Void> deletePerson(@PathVariable(value = "id") String id) {

		logger.info("Request : DELETE /persons/{}", id);

		// We check that the resource to delete exist
		if (personService.getPersonById(id) == null) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", id);
		}

		Person personDeleted = personService.deletePerson(id);

		// We check that the resource has been deleted
		if (personService.personExist(personDeleted)) {
			throw new InternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the operation");
		}

		logger.info("Success : person {} deleted", id);

		return new ResponseEntity<>(HttpStatus.GONE);
	}

}
