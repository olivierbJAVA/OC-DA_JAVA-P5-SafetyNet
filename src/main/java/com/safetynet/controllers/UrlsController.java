package com.safetynet.controllers;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.entities.urls.ChildAlert;
import com.safetynet.entities.urls.Fire;
import com.safetynet.entities.urls.Firestation;
import com.safetynet.entities.urls.Flood;
import com.safetynet.entities.urls.PersonInfo;
import com.safetynet.exception.RessourceNotFoundException;
import com.safetynet.service.endpoints.IFirestationMappingService;
import com.safetynet.service.endpoints.IPersonService;
import com.safetynet.service.urls.IResponseUrlsService;

/**
 * Controller in charge of managing the urls requests.
 */
@RestController
public class UrlsController {

	private static final Logger logger = LoggerFactory.getLogger(UrlsController.class);

	@Autowired
	private IResponseUrlsService responseService;

	@Autowired
	private IFirestationMappingService firestationMappingService;

	@Autowired
	private IPersonService personService;

	/**
	 * Method managing the GET "/firestation?stationNumber=<station_number>" url
	 * HTTP request.
	 * 
	 * @param stationNumber The stationNumber for which to get the response
	 */
	// http://localhost:8080/firestation?stationNumber=<station_number>
	@GetMapping(value = "/firestation")
	public ResponseEntity<Firestation> responseFirestation(@RequestParam String stationNumber) {

		logger.info("Request : GET /firestation");

		// We check that the station requested exist
		if (firestationMappingService.getFirestationMappingByIdStation(stationNumber) == null) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", stationNumber);
		}

		Firestation responseFirestation = responseService.responseFirestation(stationNumber);

		logger.info("Success : firestation response found");

		return new ResponseEntity<>(responseFirestation, HttpStatus.OK);
	}

	/**
	 * Method managing the GET "/childAlert?address=<address>" url HTTP request.
	 * 
	 * @param address The address for which to get the response
	 */
	// http://localhost:8080/childAlert?address=<address>
	@GetMapping(value = "/childAlert")
	public ResponseEntity<ChildAlert> responseChildAlert(@RequestParam String address) {

		logger.info("Request : GET /childAlert");

		// We check that the address requested exist
		if (!personService.addressExist(address)) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", address);
		}

		ChildAlert responseChildAlert = responseService.responseChildAlert(address);

		logger.info("Success : childAlert response found");

		return new ResponseEntity<>(responseChildAlert, HttpStatus.OK);

	}

	/**
	 * Method managing the GET "/phoneAlert?firestation=<firestation_number>" url
	 * HTTP request.
	 * 
	 * @param firestation The firestation for which to get the response
	 */
	// http://localhost:8080/phoneAlert?firestation=<firestation_number>
	@GetMapping(value = "/phoneAlert")
	public ResponseEntity<Set<String>> responsePhoneAlert(@RequestParam String firestation) {

		logger.info("Request : GET /phoneAlert");

		// We check that the station requested exist
		if (firestationMappingService.getFirestationMappingByIdStation(firestation) == null) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", firestation);
		}

		Set<String> responsePhoneAlert = responseService.responsePhoneAlert(firestation);

		logger.info("Success : phoneAlert response found");

		return new ResponseEntity<>(responsePhoneAlert, HttpStatus.OK);
	}

	/**
	 * Method managing the GET "/fire?address=<address>" url HTTP request.
	 * 
	 * @param address The address for which to get the response
	 */
	// http://localhost:8080/fire?address=<address>
	@GetMapping(value = "/fire")
	public ResponseEntity<Fire> responseFire(@RequestParam String address) {

		logger.info("Request : GET /fire");

		// We check that the address requested exist
		if (!personService.addressExist(address)) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", address);
		}

		Fire responseFire = responseService.responseFire(address);

		logger.info("Success : fire response found");

		return new ResponseEntity<>(responseFire, HttpStatus.OK);

	}

	/**
	 * Method managing the GET "/flood/stations?stations=<a list of
	 * station_numbers>" url HTTP request.
	 * 
	 * @param list of stations The list of stations for which to get the response
	 */
	// http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	@GetMapping(value = "/flood/stations")
	public ResponseEntity<Flood> responseFlood(@RequestParam String[] stations) {

		logger.info("Request : GET /flood/stations");

		// We check that stations requested exist
		for (int i = 0; i < stations.length; i++) {

			if (firestationMappingService.getFirestationMappingByIdStation(stations[i]) == null) {
				throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", stations[i]);
			}
		}

		Flood responseFlood = responseService.responseFlood(stations);

		logger.info("Success : flood response found");

		return new ResponseEntity<>(responseFlood, HttpStatus.OK);
	}

	/**
	 * Method managing the GET
	 * "/personInfo?firstName=<firstName>&lastName=<lastName>" url HTTP request.
	 * 
	 * @param firstName The firstName of the person for which to get the response
	 * 
	 * @param lastName  The lastName of the person for which to get the response
	 */
	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	@GetMapping(value = "/personInfo")
	public ResponseEntity<PersonInfo> responsePersonInfo(String firstName, String lastName) {

		logger.info("Request : GET /personInfo");

		// We check that the person requested exist
		if (!personService.idPersonExist(firstName + lastName)) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ",
					firstName + lastName);
		}

		PersonInfo responsePersonInfo = responseService.responsePersonInfo(firstName, lastName);

		logger.info("Success : personInfo response found");

		return new ResponseEntity<>(responsePersonInfo, HttpStatus.OK);
	}

	/**
	 * Method managing the GET "/communityEmail?city=<city>" url HTTP request.
	 * 
	 * @param city The city for which to get the response
	 */
	// http://localhost:8080/communityEmail?city=<city>
	@GetMapping(value = "/communityEmail")
	public ResponseEntity<Set<String>> responseCommunityEmail(@RequestParam String city) {

		logger.info("Request : GET /communityEmail");

		// We check that the city requested exist
		if (!personService.cityExist(city)) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", city);
		}

		Set<String> responseCommunityEmail = responseService.responseCommunityEmail(city);

		logger.info("Success : communityEmail response found");

		return new ResponseEntity<>(responseCommunityEmail, HttpStatus.FOUND);
	}

}
