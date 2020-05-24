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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.safetynet.entities.Firestation;
import com.safetynet.entities.MedicalRecord;
import com.safetynet.entities.Person;
import com.safetynet.model.IFirestationModel;
import com.safetynet.model.IPersonModel;
import com.safetynet.util.IInputReader;
import com.safetynet.util.JsonFileInputReaderImpl;

@RestController
public class SafetyNetController {

	private static final Logger logger = LoggerFactory.getLogger(SafetyNetController.class);

	@Autowired
	private IPersonModel personModel;

	private IFirestationModel firestationModel; 
	//@Autowired
	//private IInputReader jsonFileInputReader;
	
	// private static List<Person> listPersons;
	private List<Person> listPersons;
	private List<Firestation> listFirestations;
	private List<MedicalRecord> listMedicalRecords;
	
	//@Autowired
	public SafetyNetController(IInputReader inputReader) {
		logger.info("Constructeur SafetyNetController info");
		this.listPersons = inputReader.readIntitialListPersons();
		this.listFirestations = inputReader.readIntitialListFirestations();
		this.listMedicalRecords = inputReader.readIntitialListMedicalRecords();

	}

	@GetMapping(value = "/persons")
	public List<Person> getAllPersons() {
		return personModel.getAllPersons();
	}

	@GetMapping(value = "/persons/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable String id) {
		if(personModel.getPersonById(id) == null) {
			logger.error("Error : person not found");
			//return ResponseEntity.noContent().build();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			logger.info("Success : person found");
			return new ResponseEntity<>(personModel.getPersonById(id), HttpStatus.FOUND);
		}
	}
	/*
	@GetMapping(value = "/Persons/{id}")
	public Person getPersonById(@PathVariable String id) {
		return personModel.getPersonById(id);
	}
	*/
	/*
	@PostMapping(value = "/Persons")
	public ResponseEntity<Void> addPerson(@RequestBody Person person) {
		Person personToAdd = new Person();
		personToAdd.setIdPerson(person.getFirstName() + person.getLastName());
		personToAdd.setFirstName(person.getFirstName());
		personToAdd.setLastName(person.getLastName());
		personToAdd.setAddress(person.getAddress());
		personToAdd.setCity(person.getCity());
		personToAdd.setZip(person.getZip());
		personToAdd.setPhone(person.getPhone());
		personToAdd.setEmail(person.getEmail());

		Person personAdded = personModel.addPerson(personToAdd);
		listPersons = personModel.getAllPersons();
        
		if (personAdded == null) {
            return ResponseEntity.noContent().build();
        }
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(personAdded.getIdPerson())
                .toUri();
        return ResponseEntity.created(location).build();
	
	}
	*/
	
	@PostMapping(value = "/persons")
	public ResponseEntity<Person> addPerson(@RequestBody Person person) {
		
		if (personModel.getPersonById(person.getFirstName() + person.getLastName()) != null) {
			logger.error("Error : person already in the list");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
		listPersons = personModel.getAllPersons();

		if (listPersons.contains(personToAdd)) {
			logger.info("Success : person added to the list");
			URI location = ServletUriComponentsBuilder
	                .fromCurrentRequest()
	                .path("/{id}")
	                .buildAndExpand(personToAdd.getIdPerson())
	                .toUri();
		
			return ResponseEntity.created(location).build();	
		} else {
			logger.error("Error : person not added to the list");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);	
		}

	}
	
	@DeleteMapping(value = "/persons/{id}")
	public ResponseEntity<Void> deletePerson(@PathVariable(value = "id") String id) {
		Person personToDelete = personModel.getPersonById(id);
		
		if (personToDelete == null) {
			logger.error("Error : person not in the list");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
		}
				
		personModel.deletePerson(id);
		listPersons = personModel.getAllPersons();
	
		if (!listPersons.contains(personToDelete)) {
			logger.info("Success : person deleted from the list");
			return new ResponseEntity<>(HttpStatus.GONE);	
		} else {
			logger.info("Error : person not deleted from the list");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);	
		}
	
	}
	
	@GetMapping(value = "/firestations")
	public List<Firestation> listFirestations() {
		return listFirestations;
	}
	
	/*
	@PostMapping(value = "/firstations")
	public ResponseEntity<Firestation> addFirestation(@RequestBody Firestation firestation) {
		
		if (firestationModel.getFirestationById(idFirestation) != null) {
			logger.error("Error : person already in the list");
			return ResponseEntity.noContent().build();
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
		listPersons = personModel.getAllPersons();

		if (listPersons.contains(personToAdd)) {
			logger.info("Success : person added to the list");
			URI location = ServletUriComponentsBuilder
	                .fromCurrentRequest()
	                .path("/{id}")
	                .buildAndExpand(personToAdd.getIdPerson())
	                .toUri();
		
			return ResponseEntity.created(location).build();	
		} else {
			logger.error("Error : person not added to the list");
			return ResponseEntity.noContent().build();
		}

	}
	*/
	
	@GetMapping(value = "/MedicalRecords")
	public List<MedicalRecord> listMedicalRecords() {
		return listMedicalRecords;
	}


}
