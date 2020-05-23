package com.safetynet.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.SafetyNetApplication;
import com.safetynet.dao.IPersonDao;
import com.safetynet.entities.Firestation;
import com.safetynet.entities.MedicalRecord;
import com.safetynet.entities.Person;
import com.safetynet.model.IFirestationModel;
import com.safetynet.model.IMedicalRecordModel;
import com.safetynet.model.IPersonModel;
import com.safetynet.util.JsonInputFileReader;
import com.safetynet.util.JsonInputFileReader;


@RestController
public class SafetyNetController {

	private static final Logger logger = LoggerFactory.getLogger(SafetyNetController.class);
	
	@Autowired
	private IPersonModel personModel;

	private static List<Person> listPersons;
	private static List<Firestation> listFirestations;
	private static List<MedicalRecord> listMedicalRecords;
	
	public SafetyNetController() {
		listPersons = JsonInputFileReader.readIntitialListPersons();
		listFirestations = JsonInputFileReader.readIntitialListFirestations();
		listMedicalRecords = JsonInputFileReader.readIntitialListMedicalRecords();
	
	}
	
	@GetMapping(value="/Persons")
	public List<Person> listPersons(){
		return listPersons;
	}
	
	@GetMapping(value="/Firestations")
	public List<Firestation> listFirestations(){
		return listFirestations;
	}
	
	@GetMapping(value="/MedicalRecords")
	public List<MedicalRecord> listMedicalRecords(){
		return listMedicalRecords;
	}
	
	 @PostMapping(value = "/Persons")
	    public void addPerson(@RequestBody Person person) {
		 Person personToAdd = new Person();
		 personToAdd.setIdPerson(person.getFirstName()+person.getLastName());
		 personToAdd.setFirstName(person.getFirstName());
		 personToAdd.setLastName(person.getLastName());
		 personToAdd.setAddress(person.getAddress());
		 personToAdd.setCity(person.getCity());
		 personToAdd.setZip(person.getZip());
		 personToAdd.setPhone(person.getPhone());
		 personToAdd.setEmail(person.getEmail());
		 
		 personModel.addPerson(personToAdd);
		 listPersons = personModel.listPerson();
	    }
}
