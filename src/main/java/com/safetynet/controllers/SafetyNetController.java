package com.safetynet.controllers;

import java.net.URI;
import java.util.Collection;
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

import com.safetynet.entities.Firestation;
import com.safetynet.entities.MedicalRecord;
import com.safetynet.entities.Person;
import com.safetynet.model.IFirestationModel;
import com.safetynet.model.IMedicalRecordModel;
import com.safetynet.model.IPersonModel;
import com.safetynet.util.IInputReader;
import com.safetynet.util.JsonFileInputReaderImpl;

@RestController
public class SafetyNetController {

	private static final Logger logger = LoggerFactory.getLogger(SafetyNetController.class);

	@Autowired
	private IPersonModel personModel;

	@Autowired
	private IFirestationModel firestationModel;
	
	@Autowired
	private IMedicalRecordModel medicalRecordModel;

	
	// @Autowired
	// private IInputReader jsonFileInputReader;

	// private static List<Person> listPersons;
	//private List<Person> listPersons;
	private List<Firestation> listFirestations;
	private List<MedicalRecord> listMedicalRecords;

	// @Autowired
	public SafetyNetController(IInputReader inputReader) {
		logger.info("Constructeur SafetyNetController info");
		//this.listPersons = inputReader.readIntitialListPersons();
		
		//initialisation : possibilité 4 (appelé dans le constructeur du controller)
		//inputReader.readIntitialListPersons();
		
		this.listFirestations = inputReader.readIntitialListFirestations();
		
		this.listMedicalRecords = inputReader.readIntitialListMedicalRecords();
	}

	//persons
	@GetMapping(value = "/persons")
	public ResponseEntity<Collection<Person>> getAllPersons() {
		Collection<Person> persons = personModel.getAllPersons();
		logger.info("Success : persons list found");
		return new ResponseEntity<>(persons, HttpStatus.FOUND);
	}

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

	@PostMapping(value = "/persons")
	public ResponseEntity<Person> addPerson(@RequestBody Person person) {

		if (personModel.getPersonById(person.getFirstName() + person.getLastName()) != null) {
			logger.error("Error : person already exist");
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
		//listPersons = personModel.getAllPersons();

		
		//if (!listPersons.contains(personToAdd)) {
		if (!personModel.getAllPersons().contains(personToAdd)) {
			logger.error("Error : person not added");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
			return new ResponseEntity<>(person, HttpStatus.NOT_FOUND);
		}

		personToUpdate.setAddress(person.getAddress());
		personToUpdate.setCity(person.getCity());
		personToUpdate.setZip(person.getZip());
		personToUpdate.setPhone(person.getPhone());
		personToUpdate.setEmail(person.getEmail());

		Person personUpdated = personModel.updatePerson(personToUpdate);
		//listPersons = personModel.getAllPersons();
		/*
		if (!personUpdated.getAddress().equals(personToUpdate.getAddress())) {
			logger.error("Error : person not updated");
			return new ResponseEntity<>(person, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		*/
		return new ResponseEntity<>(personUpdated, HttpStatus.OK);
	}

	@DeleteMapping(value = "/persons/{id}")
	public ResponseEntity<Void> deletePerson(@PathVariable(value = "id") String id) {
		Person personToDelete = personModel.getPersonById(id);

		if (personToDelete == null) {
			logger.error("Error : person does not exist");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Person personDeleted = personModel.deletePerson(id);
		//listPersons = personModel.getAllPersons();

		//if (listPersons.contains(personDeleted)) {
		if (personModel.getAllPersons().contains(personDeleted)) {
			logger.error("Error : person not deleted");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("Success : person deleted");
		return new ResponseEntity<>(HttpStatus.GONE);
	}

	
	
	//firestations
	@GetMapping(value = "/firestations")
	public ResponseEntity<Collection<Firestation>> getAllFirestations() {
		Collection<Firestation> firestations = firestationModel.getAllFirestations();
		logger.info("Success : firestations list found");
		return new ResponseEntity<>(firestations, HttpStatus.FOUND);
	}
	
	@PostMapping(value = "/firestations")
	public ResponseEntity<Firestation> addFirestation(@RequestBody Firestation firestationMapping) {

		if (firestationModel.getFirestationById(firestationMapping.getAddress() + firestationMapping.getStation()) != null) {
			logger.error("Error : firestation mapping already exist");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Firestation firestationMappingToAdd = new Firestation();
		
		firestationMappingToAdd.setIdFirestation(firestationMapping.getAddress()+firestationMapping.getStation());
		firestationMappingToAdd.setAddress(firestationMapping.getAddress());
		firestationMappingToAdd.setStation(firestationMapping.getStation());
		
		firestationModel.addFirestation(firestationMappingToAdd);
		listFirestations = firestationModel.getAllFirestations();

		if (!listFirestations.contains(firestationMappingToAdd)) {
			logger.error("Error : firestation mapping not added");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("Success : firestation mapping added");
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(firestationMappingToAdd.getIdFirestation()).toUri();

		return ResponseEntity.created(location).build();
		// return new ResponseEntity<>(personAdded, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping(value = "/firestations/{adress}")
	public ResponseEntity<Firestation> updateFirestation(@PathVariable String adress, @RequestBody Firestation firestation) {

		Firestation firestationToUpdate = firestationModel.getFirestationByAdress(adress);

		if (firestationToUpdate == null) {
			logger.error("Error : firestation adress does not exist");
			return new ResponseEntity<>(firestation, HttpStatus.NOT_FOUND);
		}

		firestationToUpdate.setStation(firestation.getStation());
		firestationToUpdate.setIdFirestation(firestation.getAddress()+firestation.getStation());
		
		Firestation firestationUpdated = firestationModel.updateFirestation(firestationToUpdate);
		listFirestations = firestationModel.getAllFirestations();

		return new ResponseEntity<>(firestationUpdated, HttpStatus.OK);
	}
	
	
	/*
	@GetMapping(value = "/MedicalRecords")
	public List<MedicalRecord> listMedicalRecords() {
		return listMedicalRecords;
	}
	*/
	@GetMapping(value = "/medicalRecords")
	public ResponseEntity<Collection<MedicalRecord>> getAllMedicalRecords() {
		Collection<MedicalRecord> medicalRecords = medicalRecordModel.getAllMedicalRecords();
		logger.info("Success : mdeicalRecrods list found");
		return new ResponseEntity<>(medicalRecords, HttpStatus.FOUND);
	}
	
}
