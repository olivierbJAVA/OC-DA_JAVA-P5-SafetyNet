package com.safetynet.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.entities.FirestationMapping;
import com.safetynet.entities.MedicalRecord;
import com.safetynet.entities.Person;
import com.safetynet.model.IFirestationMappingModel;
import com.safetynet.model.IMedicalRecordModel;
import com.safetynet.model.IPersonModel;

@RestController
public class SafetyNetUrlsController {

	private static final Logger logger = LoggerFactory.getLogger(SafetyNetUrlsController.class);

	@Autowired
	private IPersonModel personModel;

	@Autowired
	private IFirestationMappingModel firestationMappingModel;
	
	@Autowired
	private IMedicalRecordModel medicalRecordModel;

	
	// http://localhost:8080/communityEmail?city=<city>
	@GetMapping(value = "/communityEmail")
	public ResponseEntity<List<String>> getCommunityEmail(@RequestParam String city) {
			
		List<Person> persons = personModel.getAllPersons();
				
		//List<String> listEmailF = persons.stream().filter(xx->xx.getCity().equals(city)).map(Person::getEmail).distinct().collect(Collectors.toList());		
		List<String> listEmail = persons.stream()
				.filter(xx->xx.getCity().equals(city))
				.map(xx->xx.getEmail()).distinct()
				.collect(Collectors.toList());		
		
		for(String email : listEmail) {
			System.out.println(email);
		}
		return new ResponseEntity<>(listEmail, HttpStatus.FOUND);

		//List<String> result = persons.stream().map(xx->xx.getCity()).filter(xx->xx.equals(city)).collect(Collectors.toList(Person::getEmail));		
		//List<Person> result = persons.stream().filter(xx->xx.getCity().equals(city)).collect(Collectors.toList());		
	
	}

	//http://localhost:8080/phoneAlert?firestation=<firestation_number>
		@GetMapping(value = "/phoneAlert")
		public ResponseEntity<List<String>> getPhoneAlert(@RequestParam String firestation) {
				
			List<Person> persons = personModel.getAllPersons();
			List<FirestationMapping> firestationMappings = 
					firestationMappingModel.getAllFirestationMappings();
			
			//Stream<String> address = firestationMappings.stream().filter(xx->xx.getStation().equals(firestationNumber)).map(xx->xx.getAddress());
			
			List<String> address = firestationMappings.stream()
					.filter(xx->xx.getStation().equals(firestation))
					.map(xx->xx.getAddress())
					.collect(Collectors.toList());
			
			//address correspond à toutes les addresses de la firestation demandée
			/*
			for(String adress : address) {
				List<String> toto = persons.forEach(person -> person.getAddress().equals(adress));
			}
				
			

				List<String> phones = persons.stream()
						.filter(xx->xx.getAddress().equals(adress))
						.map(xx->xx.getPhone()).collect(Collectors.toList());
			}
			*/
			
			
			//List<String> result = persons.stream().filter(xx->xx.getAddress().matchAny(address)).map(xx->xx.getPhone()).collect(Collectors.toList());
			
			/*
			for(String adress : address) {
				System.out.println(adress);
			}
			
			for(String phone : phones) {
				System.out.println(phone);
			}
			*/
			return new ResponseEntity<>(address, HttpStatus.FOUND);

			//List<String> result = persons.stream().map(xx->xx.getCity()).filter(xx->xx.equals(city)).collect(Collectors.toList(Person::getEmail));		
			//List<Person> result = persons.stream().filter(xx->xx.getCity().equals(city)).collect(Collectors.toList());		
		
		}
	
	/*
	//http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	@GetMapping(value = "/personInfo")
	public ResponseEntity<List<String>> getPersonById(@RequestParam String firstName, String lastName) {
			
		List<Person> persons = personModel.getAllPersons();
		List<MedicalRecord> medicalRecord = medicalRecordModel.getAllMedicalRecords();
		
		medicalRecord.stream().map(xx->xx.getBirthdate().to)
		
		List<String> result = persons.stream().filter(xx->xx.getCity().equals(city)).map(Person::getEmail).distinct().collect(Collectors.toList());		
		List<String> result3 = persons.stream().filter(xx->xx.getCity().equals(city)).map(xx->xx.getEmail()).distinct().collect(Collectors.toList());		
		
		for(String email : result3) {
			System.out.println(email);
		}
		return new ResponseEntity<>(result3, HttpStatus.FOUND);

		//List<String> result = persons.stream().map(xx->xx.getCity()).filter(xx->xx.equals(city)).collect(Collectors.toList(Person::getEmail));		
		//List<Person> result = persons.stream().filter(xx->xx.getCity().equals(city)).collect(Collectors.toList());		
	
	}
	*/
}
