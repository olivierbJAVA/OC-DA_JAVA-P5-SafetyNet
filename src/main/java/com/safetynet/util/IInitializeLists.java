package com.safetynet.util;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.safetynet.entities.endpoints.FirestationMapping;
import com.safetynet.entities.endpoints.MedicalRecord;
import com.safetynet.entities.endpoints.Person;

public interface IInitializeLists {

	public void getInitialData();

	public void initializeDataPersons();

	public void initializeDataFirestationMappings();
	
	public void initializeDataMedicalRecords();
	
	/*
	public void initializeDataPersons(JsonNode rootNode);

	public void initializeDataFirestationMappings(JsonNode rootNode);
	
	public void initializeDataMedicalRecords(JsonNode rootNode);
	*/
	/*
	public List<Person> initializeListPersons();

	public List<FirestationMapping> initializeListFirestationMappings();
	
	public List<MedicalRecord> initializeListMedicalRecords();
	*/
}
