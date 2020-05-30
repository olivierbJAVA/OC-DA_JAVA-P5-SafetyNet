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

import com.safetynet.entities.endpoints.MedicalRecord;
import com.safetynet.model.endpoints.IMedicalRecordModel;

@RestController
public class SafetyNetEndpointsMedicalRecordsController {

	private static final Logger logger = LoggerFactory.getLogger(SafetyNetEndpointsMedicalRecordsController.class);

	@Autowired
	private IMedicalRecordModel medicalRecordModel;

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

		if (medicalRecordModel.medicalRecordExist(medicalRecord)) {
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

		if (!medicalRecordModel.medicalRecordExist(medicalRecordToAdd)) {
			logger.error("Error : medicalRecord not added");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("Success : medicalRecord added");
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(medicalRecordToAdd.getIdMedicalRecord()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping(value = "/medicalRecords/{id}")
	public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable String id,
			@RequestBody MedicalRecord medicalRecord) {

		MedicalRecord medicalRecordToUpdate = medicalRecordModel.getMedicalRecordById(id);

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

		if (medicalRecordModel.medicalRecordExist(medicalRecordDeleted)) {
			logger.error("Error : medicalRecord not deleted");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("Success : medicalRecord deleted");

		return new ResponseEntity<>(HttpStatus.GONE);
	}

}
