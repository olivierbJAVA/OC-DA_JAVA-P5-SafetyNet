package com.safetynet.util;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.safetynet.entities.endpoints.FirestationMapping;
import com.safetynet.entities.endpoints.MedicalRecord;
import com.safetynet.entities.endpoints.Person;

public interface IInitializeLists {

	public void getData();
	

	public void initializeListPersons(JsonNode rootNode);

	public void initializeListFirestationMappings(JsonNode rootNode);
	
	public void initializeListMedicalRecords(JsonNode rootNode);

	
	
	/*
	public List<Person> initializeListPersons();

	public List<FirestationMapping> initializeListFirestationMappings();
	
	public List<MedicalRecord> initializeListMedicalRecords();
	*/
}
