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

import com.safetynet.entities.endpoints.FirestationMapping;
import com.safetynet.exception.InternalServerErrorException;
import com.safetynet.exception.RessourceAlreadyExistException;
import com.safetynet.exception.RessourceNotFoundException;
import com.safetynet.model.endpoints.IFirestationMappingModel;

@RestController
public class EndpointFirestationMappingsController {

	private static final Logger logger = LoggerFactory.getLogger(EndpointFirestationMappingsController.class);

	@Autowired
	private IFirestationMappingModel firestationMappingModel;

	@GetMapping(value = "/firestations")
	public ResponseEntity<List<FirestationMapping>> getAllFirestationMappings() {

		List<FirestationMapping> firestationMappings = firestationMappingModel.getAllFirestationMappings();

		logger.info("Success : firestation mappings list found");

		return new ResponseEntity<>(firestationMappings, HttpStatus.FOUND);
	}

	@PostMapping(value = "/firestations")
	public ResponseEntity<FirestationMapping> addFirestationMapping(
			@RequestBody FirestationMapping firestationMapping) {

		if (firestationMappingModel.firestationMappingExist(firestationMapping)) {
			logger.error("Error : firestation mapping already exist");
			throw new RessourceAlreadyExistException(HttpStatus.BAD_REQUEST, "Error ressource already exist : ",
					firestationMapping.getAddress());
		}

		firestationMappingModel.addFirestationMapping(firestationMapping);

		if (!firestationMappingModel.firestationMappingExist(firestationMapping)) {
			logger.error("Error : firestation mapping not added");
			throw new InternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the operation");
		}

		logger.info("Success : firestation mapping added");
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(firestationMapping.getAddress()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping(value = "/firestations/{address}")
	public ResponseEntity<FirestationMapping> updateFirestationMapping(@PathVariable String address,
			@RequestBody FirestationMapping firestationMapping) {

		if (firestationMappingModel.getFirestationMappingByAdress(address) == null) {
			logger.error("Error : firestation mapping adress does not exist");
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", address);
		}

		firestationMappingModel.updateFirestationMapping(firestationMapping);

		return new ResponseEntity<>(firestationMapping, HttpStatus.OK);
	}

	@DeleteMapping(value = "/firestations/{address}")
	public ResponseEntity<Void> deleteFirestationMapping(@PathVariable(value = "address") String address) {

		if (firestationMappingModel.getFirestationMappingByAdress(address) == null) {
			logger.error("Error : firestation mapping address does not exist");
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", address);
		}

		FirestationMapping firestationMappingDeleted = firestationMappingModel.deleteFirestationMapping(address);

		if (firestationMappingModel.firestationMappingExist(firestationMappingDeleted)) {
			logger.error("Error : firestation mapping not deleted");
			throw new InternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the operation");
		}

		logger.info("Success : firestation mapping deleted");
		return new ResponseEntity<>(HttpStatus.GONE);
	}

}
