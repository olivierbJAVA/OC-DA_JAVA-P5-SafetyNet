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
import com.safetynet.exception.RessourceAlreadyExistException;
import com.safetynet.exception.RessourceNotFoundException;
import com.safetynet.service.endpoints.IMedicalRecordService;
import com.safetynet.exception.InternalServerErrorException;

@RestController
public class EndpointMedicalRecordsController {

	private static final Logger logger = LoggerFactory.getLogger(EndpointMedicalRecordsController.class);

	@Autowired
	private IMedicalRecordService medicalRecordModel;

	@GetMapping(value = "/medicalRecords")
	public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() {

		List<MedicalRecord> medicalRecords = medicalRecordModel.getAllMedicalRecords();

		logger.info("Success : medicalRecords list found");

		return new ResponseEntity<>(medicalRecords, HttpStatus.FOUND);
	}

	@GetMapping(value = "/medicalRecords/{id}")
	public ResponseEntity<MedicalRecord> getMedicalRecordById(@PathVariable String id) {

		MedicalRecord medicalRecordToGet = medicalRecordModel.getMedicalRecordById(id);

		if (medicalRecordModel.getMedicalRecordById(id) == null) {
			logger.error("Error : medicalRecord not found");
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", id);
		}

		logger.info("Success : medicalRecord found");

		return new ResponseEntity<>(medicalRecordToGet, HttpStatus.FOUND);
	}

	@PostMapping(value = "/medicalRecords")
	public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {

		if (medicalRecordModel.medicalRecordExist(medicalRecord)) {
			logger.error("Error : medicalRecord already exist");
			throw new RessourceAlreadyExistException(HttpStatus.BAD_REQUEST, "Error ressource already exist : ",
					medicalRecord.getFirstName() + medicalRecord.getLastName());
		}

		medicalRecord.setIdMedicalRecord(medicalRecord.getFirstName() + medicalRecord.getLastName());

		medicalRecordModel.addMedicalRecord(medicalRecord);

		if (!medicalRecordModel.medicalRecordExist(medicalRecord)) {
			logger.error("Error : medicalRecord not added");
			throw new InternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the operation");
		}

		logger.info("Success : medicalRecord added");
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(medicalRecord.getIdMedicalRecord()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping(value = "/medicalRecords/{id}")
	public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable String id,
			@RequestBody MedicalRecord medicalRecord) {

		if (medicalRecordModel.getMedicalRecordById(id) == null) {
			logger.error("Error : medicalRecord does not exist");
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", id);
		}

		medicalRecord.setIdMedicalRecord(id);

		medicalRecordModel.updateMedicalRecord(medicalRecord);

		return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
	}

	@DeleteMapping(value = "/medicalRecords/{id}")
	public ResponseEntity<Void> deleteMedicalRecord(@PathVariable(value = "id") String id) {

		if (medicalRecordModel.getMedicalRecordById(id) == null) {
			logger.error("Error : medicalRecord does not exist");
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", id);
		}

		MedicalRecord medicalRecordDeleted = medicalRecordModel.deleteMedicalRecord(id);

		if (medicalRecordModel.medicalRecordExist(medicalRecordDeleted)) {
			logger.error("Error : medicalRecord not deleted");
			throw new InternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the operation");
		}

		logger.info("Success : medicalRecord deleted");

		return new ResponseEntity<>(HttpStatus.GONE);
	}

}
