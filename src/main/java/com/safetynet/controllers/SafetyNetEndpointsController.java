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

import com.safetynet.entities.FirestationMapping;
import com.safetynet.entities.MedicalRecord;
import com.safetynet.entities.Person;
import com.safetynet.model.IFirestationMappingModel;
import com.safetynet.model.IMedicalRecordModel;
import com.safetynet.model.IPersonModel;

@RestController
public class SafetyNetEndpointsController {

	private static final Logger logger = LoggerFactory.getLogger(SafetyNetEndpointsController.class);

	@Autowired
	private IPersonModel personModel;

	@Autowired
	private IFirestationMappingModel firestationMappingModel;
	
	@Autowired
	private IMedicalRecordModel medicalRecordModel;

	

	//persons
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
			logger.error("Error : person not found");
			// return ResponseEntity.noContent().build();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		logger.info("Success : person found");
		
		return new ResponseEntity<>(personToGet, HttpStatus.FOUND);
	}

	@PostMapping(value = "/persons")
	public ResponseEntity<Person> addPerson(@RequestBody Person person) {
	
		if (personModel.personInList(person)) {
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

		if (!personModel.personInList(personToAdd)) {
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

		if (personModel.personInList(personDeleted)) {
			logger.error("Error : person not deleted");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("Success : person deleted");
		return new ResponseEntity<>(HttpStatus.GONE);
	}

	
	
	//firestation mappings
	@GetMapping(value = "/firestations")
	public ResponseEntity<List<FirestationMapping>> getAllFirestationMappings() {
		
		List<FirestationMapping> firestationMappings = firestationMappingModel.getAllFirestationMappings();
		
		logger.info("Success : firestation mappings list found");
		
		return new ResponseEntity<>(firestationMappings, HttpStatus.FOUND);
	}
	
	@PostMapping(value = "/firestations")
	public ResponseEntity<FirestationMapping> addFirestationMapping(@RequestBody FirestationMapping firestationMapping) {

		if (firestationMappingModel.firestationMappingInList(firestationMapping)) {
			logger.error("Error : firestation mapping already exist");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		FirestationMapping firestationMappingToAdd = new FirestationMapping();
		
		firestationMappingToAdd.setAddress(firestationMapping.getAddress());
		firestationMappingToAdd.setStation(firestationMapping.getStation());
		
		firestationMappingModel.addFirestationMapping(firestationMappingToAdd);

		if (!firestationMappingModel.firestationMappingInList(firestationMappingToAdd)) {
			logger.error("Error : firestation mapping not added");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("Success : firestation mapping added");
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(firestationMappingToAdd.getAddress()).toUri();

		return ResponseEntity.created(location).build();
	}
	
	@PutMapping(value = "/firestations/{address}")
	public ResponseEntity<FirestationMapping> updateFirestationMapping(@PathVariable String address, @RequestBody FirestationMapping firestationMapping) {

		FirestationMapping firestationMappingToUpdate = firestationMappingModel.getFirestationMappingByAdress(address);

		if (firestationMappingToUpdate == null) {
			logger.error("Error : firestation mapping adress does not exist");
			return new ResponseEntity<>(firestationMapping, HttpStatus.NOT_FOUND);
		}

		firestationMappingToUpdate.setStation(firestationMapping.getStation());
			
		FirestationMapping firestationMappingUpdated = firestationMappingModel.updateFirestationMapping(firestationMappingToUpdate);
		
		return new ResponseEntity<>(firestationMappingUpdated, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/firestations/{address}")
	public ResponseEntity<Void> deleteFirestationMapping(@PathVariable(value = "address") String address) {
		
		FirestationMapping firestationMappingToDelete = firestationMappingModel.getFirestationMappingByAdress(address);

		if (firestationMappingToDelete == null) {
			logger.error("Error : firestation mapping address does not exist");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		FirestationMapping firestationMappingDeleted = firestationMappingModel.deleteFirestationMapping(address);

		if (firestationMappingModel.firestationMappingInList(firestationMappingDeleted)) {
			logger.error("Error : firestation mapping not deleted");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("Success : firestation mapping deleted");
		return new ResponseEntity<>(HttpStatus.GONE);
	}
	

	
	//medicalRecords
	@GetMapping(value = "/medicalRecords")
	public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() {
		
		List<MedicalRecord> medicalRecords = medicalRecordModel.getAllMedicalRecords();
		
		logger.info("Success : medicalRecords list found");
		
		return new ResponseEntity<>(medicalRecords, HttpStatus.FOUND);
	}
	
	@GetMapping(value = "/medicalRecords/{id}")
	public ResponseEntity<MedicalRecord> getMedicalRecordById(@PathVariable String id) {
		
		MedicalRecord medicalRecordToGet = medicalRecordModel.getMedicalRecordById(id);
		
		if (medicalRecordToGet == null) {
			logger.error("Error : medicalRecord not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		logger.info("Success : medicalRecord found");
		
		return new ResponseEntity<>(medicalRecordToGet, HttpStatus.FOUND);
	}
	
	@PostMapping(value = "/medicalRecords")
	public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
	
		if (medicalRecordModel.medicalRecordInList(medicalRecord)) {
			logger.error("Error : medicalRecord already exist");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		MedicalRecord medicalRecordToAdd = new MedicalRecord();
		medicalRecordToAdd.setIdMedicalRecord(medicalRecord.getFirstName() + medicalRecord.getLastName());
		medicalRecordToAdd.setFirstName(medicalRecord.getFirstName());
		medicalRecordToAdd.setLastName(medicalRecord.getLastName());
		medicalRecordToAdd.setBirthdate(medicalRecord.getBirthdate());
		medicalRecordToAdd.setMedications(medicalRecord.getMedications());
		medicalRecordToAdd.setAllergies(medicalRecord.getAllergies());
				
		medicalRecordModel.addMedicalRecord(medicalRecordToAdd);

		if (!medicalRecordModel.medicalRecordInList(medicalRecordToAdd)) {
			logger.error("Error : medicalRecord not added");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("Success : medicalRecord added");
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(medicalRecordToAdd.getIdMedicalRecord()).toUri();

		return ResponseEntity.created(location).build();
	}
	
	@PutMapping(value = "/medicalRecords/{id}")
	public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable String id, @RequestBody MedicalRecord medicalRecord) {

		MedicalRecord medicalRecordToUpdate =  medicalRecordModel.getMedicalRecordById(id);

		if (medicalRecordToUpdate == null) {
			logger.error("Error : medicalRecord does not exist");
			return new ResponseEntity<>(medicalRecord, HttpStatus.NOT_FOUND);
		}

		medicalRecordToUpdate.setBirthdate(medicalRecord.getBirthdate());
		medicalRecordToUpdate.setMedications(medicalRecord.getMedications());
		medicalRecordToUpdate.setAllergies(medicalRecord.getAllergies());
		
		MedicalRecord medicalRecordUpdated = medicalRecordModel.updateMedicalRecord(medicalRecordToUpdate);

		return new ResponseEntity<>(medicalRecordUpdated, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/medicalRecords/{id}")
	public ResponseEntity<Void> deleteMedicalRecord(@PathVariable(value = "id") String id) {
		
		MedicalRecord medicalRecordToDelete = medicalRecordModel.getMedicalRecordById(id);

		if (medicalRecordToDelete == null) {
			logger.error("Error : medicalRecord does not exist");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		MedicalRecord medicalRecordDeleted = medicalRecordModel.deleteMedicalRecord(id);

		if (medicalRecordModel.medicalRecordInList(medicalRecordDeleted)) {
			logger.error("Error : medicalRecord not deleted");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("Success : medicalRecord deleted");
		
		return new ResponseEntity<>(HttpStatus.GONE);
	}
	
}
