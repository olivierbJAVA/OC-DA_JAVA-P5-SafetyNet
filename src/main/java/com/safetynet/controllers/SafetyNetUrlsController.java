package com.safetynet.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.entities.endpoints.FirestationMapping;
import com.safetynet.entities.endpoints.Person;
import com.safetynet.entities.urls.ChildAlert;
import com.safetynet.entities.urls.Fire;
import com.safetynet.entities.urls.Firestation;
import com.safetynet.entities.urls.Flood;
import com.safetynet.entities.urls.PersonInfo;
import com.safetynet.model.endpoints.IFirestationMappingModel;
import com.safetynet.model.endpoints.IPersonModel;
import com.safetynet.model.urls.IResponseUrlsModel;

@RestController
public class SafetyNetUrlsController {

	private static final Logger logger = LoggerFactory.getLogger(SafetyNetUrlsController.class);

	@Autowired
	private IResponseUrlsModel responseModel;

	@Autowired
	private IFirestationMappingModel firestationMappingModel;

	@Autowired
	private IPersonModel personModel;

	// http://localhost:8080/firestation?stationNumber=<station_number>
	@GetMapping(value = "/firestation")
	public ResponseEntity<Firestation> responseFirestation(@RequestParam int stationNumber) {

		if (firestationMappingModel.getFirestationMappingByFirestationNumber(stationNumber) == null) {
			logger.error("Error : no mapping exist for this firestation");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Firestation responseFirestation = responseModel.responseFirestation(stationNumber);

		return new ResponseEntity<>(responseFirestation, HttpStatus.OK);
	}

	// http://localhost:8080/childAlert?address=<address>
	@GetMapping(value = "/childAlert")
	public ResponseEntity<ChildAlert> responseChildAlert(@RequestParam String address) {

		if (!personModel.addressExist(address)) {
			logger.error("Error : address does not exist");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		ChildAlert responseChildAlert = responseModel.responseChildAlert(address);

		return new ResponseEntity<>(responseChildAlert, HttpStatus.OK);

	}

	// http://localhost:8080/fire?address=<address>
	@GetMapping(value = "/fire")
	public ResponseEntity<Fire> responseFire(@RequestParam String address) {

		if (!personModel.addressExist(address)) {
			logger.error("Error : address does not exist");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Fire responseFire = responseModel.responseFire(address);

		return new ResponseEntity<>(responseFire, HttpStatus.OK);

	}

	// http://localhost:8080/flood/station?station=<a list of station_numbers>
	@GetMapping(value = "/flood/station")
	public ResponseEntity<Flood> responseFlood(@RequestParam int station) {

		if (firestationMappingModel.getFirestationMappingByFirestationNumber(station) == null) {
			logger.error("Error : no mapping exist for this firestation");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Flood responseFlood = responseModel.responseFlood(station);

		return new ResponseEntity<>(responseFlood, HttpStatus.OK);
	}

	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	@GetMapping(value = "/personInfo")
	public ResponseEntity<PersonInfo> responsePersonInfo(String firstName, String lastName) {

		if (!personModel.idPersonExist(firstName+lastName)) {
			logger.error("Error : person does not exist");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		PersonInfo responsePersonInfo = responseModel.responsePersonInfo(firstName, lastName);

		return new ResponseEntity<>(responsePersonInfo, HttpStatus.OK);
	}

	// http://localhost:8080/communityEmail?city=<city>
	@GetMapping(value = "/communityEmail")
	public ResponseEntity<List<String>> getCommunityEmail(@RequestParam String city) {

		if (!personModel.cityExist(city)) {
			logger.error("Error : city does not exist");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<Person> listPersons = personModel.getAllPersons();

		List<String> listEmails = listPersons.stream().filter(x -> x.getCity().equals(city)).map(Person::getEmail)
				.distinct().collect(Collectors.toList());

		return new ResponseEntity<>(listEmails, HttpStatus.FOUND);
	}
	/*
	// http://localhost:8080/phoneAlert?firestation=<firestation_number>
	@GetMapping(value = "/phoneAlert")
	public void getPhoneAlert(@RequestParam int firestation) {
			
		List<Person> persons = personModel.getAllPersons();
		List<FirestationMapping> firestationMappings = firestationMappingModel.getAllFirestationMappings();
		
		List<String> address = firestationMappings.stream()
				.filter(xx->xx.getStation().equals(firestation))
				.map(xx->xx.getAddress())
				.collect(Collectors.toList());
		
	}
	*/
}
