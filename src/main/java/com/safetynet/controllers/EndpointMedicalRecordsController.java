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
	private IMedicalRecordService medicalRecordService;

	@GetMapping(value = "/medicalRecords")
	public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() {

		logger.info("Request : GET /medicalRecords");

		List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();

		logger.info("Success : medicalRecords found");

		return new ResponseEntity<>(medicalRecords, HttpStatus.FOUND);
	}

	@GetMapping(value = "/medicalRecords/{id}")
	public ResponseEntity<MedicalRecord> getMedicalRecordById(@PathVariable String id) {

		logger.info("Request : GET /medicalRecords{}", id);

		MedicalRecord medicalRecordToGet = medicalRecordService.getMedicalRecordById(id);

		if (medicalRecordToGet == null) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", id);
		}

		logger.info("Success : medicalRecord {} found", id);

		return new ResponseEntity<>(medicalRecordToGet, HttpStatus.FOUND);
	}

	@PostMapping(value = "/medicalRecords")
	public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {

		logger.info("Request : POST /medicalRecords");

		// We check that the resource to add don't already exist
		if (medicalRecordService.medicalRecordExist(medicalRecord)) {
			throw new RessourceAlreadyExistException(HttpStatus.BAD_REQUEST, "Error ressource already exist : ",
					medicalRecord.getFirstName() + medicalRecord.getLastName());
		}

		medicalRecord.setIdMedicalRecord(medicalRecord.getFirstName() + medicalRecord.getLastName());

		medicalRecordService.addMedicalRecord(medicalRecord);

		// We check that the resource has been added
		if (!medicalRecordService.medicalRecordExist(medicalRecord)) {
			throw new InternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the operation");
		}

		logger.info("Success : medicalRecord {} added", medicalRecord.getIdMedicalRecord());

		// We return the new resource location in the header
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(medicalRecord.getIdMedicalRecord()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping(value = "/medicalRecords/{id}")
	public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable String id,
			@RequestBody MedicalRecord medicalRecord) {

		logger.info("Request : PUT /medicalRecords{}", id);

		// We check that the resource to update exist
		if (medicalRecordService.getMedicalRecordById(id) == null) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", id);
		}

		if (medicalRecordService
				.getMedicalRecordById(medicalRecord.getFirstName() + medicalRecord.getLastName()) == null) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ",
					medicalRecord.getFirstName() + medicalRecord.getLastName());
		}

		medicalRecord.setIdMedicalRecord(id);

		medicalRecordService.updateMedicalRecord(medicalRecord);

		// We check that the resource has been updated
		if (!medicalRecordService.getMedicalRecordById(id).equals(medicalRecord)) {
			throw new InternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the operation");
		}

		logger.info("Success : medicalRecord {} updated", medicalRecord.getIdMedicalRecord());

		return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
	}

	@DeleteMapping(value = "/medicalRecords/{id}")
	public ResponseEntity<Void> deleteMedicalRecord(@PathVariable(value = "id") String id) {

		logger.info("Request : DELETE /medicalRecords{}", id);

		// We check that the resource to delete exist
		if (medicalRecordService.getMedicalRecordById(id) == null) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", id);
		}

		MedicalRecord medicalRecordDeleted = medicalRecordService.deleteMedicalRecord(id);

		// We check that the resource has been deleted
		if (medicalRecordService.medicalRecordExist(medicalRecordDeleted)) {
			throw new InternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the operation");
		}

		logger.info("Success : medicalRecord {} deleted", id);

		return new ResponseEntity<>(HttpStatus.GONE);
	}

}
