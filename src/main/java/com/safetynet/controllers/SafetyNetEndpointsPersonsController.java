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
public class SafetyNetEndpointsPersonsController {

	private static final Logger logger = LoggerFactory.getLogger(SafetyNetEndpointsPersonsController.class);

	@Autowired
	private IPersonModel personModel;

	@GetMapping(value = "/persons")
	public ResponseEntity<List<Person>> getAllPersons() {

		List<Person> persons = personModel.getAllPersons();

		logger.info("Success : persons list found");

		return new ResponseEntity<>(persons, HttpStatus.FOUND);
	}
	
	/*
	//V1 : sans message d'erreur
	@GetMapping(value = "/persons/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable String id) {

		Person personToGet = personModel.getPersonById(id);

		if (personToGet == null) {
			logger.error("Error : person not found");
			// return ResponseEntity.noContent().build();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		logger.info("Success : person found");

		return new ResponseEntity<>(personToGet, HttpStatus.FOUND);
	}
	*/
	/*
	//V2 : avec message d'erreur mais ResponsEntity non paramétré
	@GetMapping(value = "/persons/{id}")
	public ResponseEntity getPersonById(@PathVariable String id) {

		Person personToGet = personModel.getPersonById(id);

		if (personToGet == null) {
			logger.error("Error : person not found");
			// return ResponseEntity.noContent().build();
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Error : person not found");
		}

		logger.info("Success : person found");

		return ResponseEntity
				.status(HttpStatus.FOUND)
				.body(personToGet);
	}
	*/
	/*
	//V3 : avec message d'erreur et ResponseEntity paramétré par un ? ou Object
	@GetMapping(value = "/persons/{id}")
	public ResponseEntity<?> getPersonById(@PathVariable String id) {

		Person personToGet = personModel.getPersonById(id);

		if (personToGet == null) {
			logger.error("Error : person {} not found",id);
			// return ResponseEntity.noContent().build();
			return new ResponseEntity<>("Error : person not found", HttpStatus.NOT_FOUND);
		}

		logger.info("Success : person {} found",id);

		return new ResponseEntity<>(personToGet, HttpStatus.FOUND);
	}
	*/
	//V4 : en lancant une exception
	@GetMapping(value = "/persons/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable String id) {

		Person personToGet = personModel.getPersonById(id);

		if (personToGet == null) {
			logger.error("Error : person {} not found",id);
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", id);
		}

		logger.info("Success : person {} found",id);

		return new ResponseEntity<>(personToGet, HttpStatus.FOUND);
	}
	
	@PostMapping(value = "/persons")
	public ResponseEntity<Person> addPerson(@RequestBody Person person) {

		if (personModel.personExist(person)) {
			logger.error("Error : person already exist");
			throw new RessourceAlreadyExistException(HttpStatus.BAD_REQUEST, "Error ressource already exist : ",
					person.getFirstName() + person.getLastName());
		}

		Person personToAdd = new Person();
		personToAdd.setIdPerson(person.getFirstName() + person.getLastName());
		personToAdd.setFirstName(person.getFirstName());
		personToAdd.setLastName(person.getLastName());
		personToAdd.setAddress(person.getAddress());
		personToAdd.setCity(person.getCity());
		personToAdd.setZip(person.getZip());
		personToAdd.setPhone(person.getPhone());
		personToAdd.setEmail(person.getEmail());

		personModel.addPerson(personToAdd);

		if (!personModel.personExist(personToAdd)) {
			logger.error("Error : person not added");
			throw new InternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the operation");
		}

		logger.info("Success : person added");
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(personToAdd.getIdPerson()).toUri();

		return ResponseEntity.created(location).build();
		// return new ResponseEntity<>(personAdded, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PutMapping(value = "/persons/{id}")
	public ResponseEntity<Person> updatePerson(@PathVariable String id, @RequestBody Person person) {

		Person personToUpdate = personModel.getPersonById(id);

		if (personToUpdate == null) {
			logger.error("Error : person does not exist");
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", id);
		}

		personToUpdate.setAddress(person.getAddress());
		personToUpdate.setCity(person.getCity());
		personToUpdate.setZip(person.getZip());
		personToUpdate.setPhone(person.getPhone());
		personToUpdate.setEmail(person.getEmail());

		Person personUpdated = personModel.updatePerson(personToUpdate);
		/*
		 * if (!personUpdated.getAddress().equals(personToUpdate.getAddress())) {
		 * logger.error("Error : person not updated"); return new
		 * ResponseEntity<>(person, HttpStatus.INTERNAL_SERVER_ERROR); }
		 */
		return new ResponseEntity<>(personUpdated, HttpStatus.OK);
	}

	@DeleteMapping(value = "/persons/{id}")
	public ResponseEntity<Void> deletePerson(@PathVariable(value = "id") String id) {
		Person personToDelete = personModel.getPersonById(id);

		if (personToDelete == null) {
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
