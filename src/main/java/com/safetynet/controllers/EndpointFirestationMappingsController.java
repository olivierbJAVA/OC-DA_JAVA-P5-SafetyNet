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
import com.safetynet.service.endpoints.IFirestationMappingService;

/**
 * Controller in charge of managing the endpoint for the firestation mappings.
 */
@RestController
public class EndpointFirestationMappingsController {

	private static final Logger logger = LoggerFactory.getLogger(EndpointFirestationMappingsController.class);

	@Autowired
	private IFirestationMappingService firestationMappingService;

	/**
	 * Method managing the GET "/firestations" endpoint HTTP request.
	 */
	@GetMapping(value = "/firestations")
	public ResponseEntity<List<FirestationMapping>> getAllFirestationMappings() {

		logger.info("Request : GET /firestations");

		List<FirestationMapping> firestationMappings = firestationMappingService.getAllFirestationMappings();

		logger.info("Success : firestationMappings found");

		return new ResponseEntity<>(firestationMappings, HttpStatus.FOUND);
	}

	/**
	 * Method managing the POST "/firestations" endpoint HTTP request.
	 * 
	 * @param firestationMapping The firestationMapping to add
	 */
	@PostMapping(value = "/firestations")
	public ResponseEntity<FirestationMapping> addFirestationMapping(
			@RequestBody FirestationMapping firestationMapping) {

		logger.info("Request : POST /firestations");

		// We check that the resource to add don't already exist
		if (firestationMappingService.firestationMappingExist(firestationMapping)) {
			throw new RessourceAlreadyExistException(HttpStatus.BAD_REQUEST, "Error ressource already exist : ",
					firestationMapping.getAddress());
		}

		firestationMappingService.addFirestationMapping(firestationMapping);

		// We check that the resource has been added
		if (!firestationMappingService.firestationMappingExist(firestationMapping)) {
			throw new InternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the operation");
		}

		logger.info("Success : firestationMapping for address {} added", firestationMapping.getAddress());

		// We return the new resource location in the header
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(firestationMapping.getAddress()).toUri();

		return ResponseEntity.created(location).build();
	}

	/**
	 * Method managing the PUT "/firestations/{address}" endpoint HTTP request.
	 * 
	 * @param address            The address of the firestationMapping to update
	 * 
	 * @param firestationMapping The firestationMapping to update
	 */
	@PutMapping(value = "/firestations/{address}")
	public ResponseEntity<FirestationMapping> updateFirestationMapping(@PathVariable String address,
			@RequestBody FirestationMapping firestationMapping) {

		logger.info("Request : PUT /firestations/{}", address);

		// We check that the resource to update exist
		if (firestationMappingService.getFirestationMappingByAdress(address) == null) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", address);
		}

		if (firestationMappingService.getFirestationMappingByAdress(firestationMapping.getAddress()) == null) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ",
					firestationMapping.getAddress());
		}

		firestationMappingService.updateFirestationMapping(firestationMapping);

		// We check that the resource has been updated
		if (!firestationMappingService.getFirestationMappingByAdress(address).equals(firestationMapping)) {
			throw new InternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the operation");
		}

		logger.info("Success : firestationMapping for address {} updated", firestationMapping.getAddress());

		return new ResponseEntity<>(firestationMapping, HttpStatus.OK);
	}

	/**
	 * Method managing the DELETE "/firestations/{address}" endpoint HTTP request.
	 * 
	 * @param address The address of the firestationMapping to delete
	 */
	@DeleteMapping(value = "/firestations/{address}")
	public ResponseEntity<Void> deleteFirestationMapping(@PathVariable(value = "address") String address) {

		logger.info("Request : DELETE /firestations/{}", address);

		// We check that the resource to delete exist
		if (firestationMappingService.getFirestationMappingByAdress(address) == null) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", address);
		}

		FirestationMapping firestationMappingDeleted = firestationMappingService.deleteFirestationMapping(address);

		// We check that the resource has been deleted
		if (firestationMappingService.firestationMappingExist(firestationMappingDeleted)) {
			throw new InternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the operation");
		}

		logger.info("Success : firestationMapping for address {} deleted", address);

		return new ResponseEntity<>(HttpStatus.GONE);
	}

}
