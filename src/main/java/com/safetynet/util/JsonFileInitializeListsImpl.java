package com.safetynet.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.entities.endpoints.FirestationMapping;
import com.safetynet.entities.endpoints.MedicalRecord;
import com.safetynet.entities.endpoints.Person;
import com.safetynet.service.endpoints.IFirestationMappingService;
import com.safetynet.service.endpoints.IMedicalRecordService;
import com.safetynet.service.endpoints.IPersonService;

@Service
public class JsonFileInitializeListsImpl implements IInitializeLists {

	private static final Logger logger = LoggerFactory.getLogger("JsonInputFileReader");

	@Autowired
	private IPersonService personModel;

	@Autowired
	private IFirestationMappingService firestationMappingModel;

	@Autowired
	private IMedicalRecordService medicalRecordModel;

	private JsonNode rootNode;

	@PostConstruct
	public void getInitialData() {

		logger.info("Start data initilization");
		
		// JsonNode rootNode = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			// JsonNode rootNode = mapper.readTree(jsonInputDataFile);
			this.rootNode = mapper.readTree(new File("./data.json"));

		} catch (FileNotFoundException e) {
			logger.error("Error : JSON initialization file not found" + e.toString());
			System.out.println(
					"Error : JSON initialization file not found. To solve the issue please name the Json initialization file 'data.json' and put it in the same directory that the SafetyNet jar file");
			System.exit(0);
		}

		catch (IOException e) {
			logger.error("Error : data initialization " + e.toString());
			System.exit(0);
		}

		initializeDataPersons();
		initializeDataFirestationMappings();
		initializeDataMedicalRecords();

		logger.info("Success : data initialization");

		// this.rootNode=rootNode;
		// return rootNode;
	}

	public void initializeDataPersons() {
		JsonNode jsonNodePersons = this.rootNode.path("persons");
		Iterator<JsonNode> iteratorPersons = jsonNodePersons.elements();

		try {
			ObjectMapper mapper = new ObjectMapper();
			Person person;
			int numPerson = 0;
			while (iteratorPersons.hasNext()) {
				person = mapper.treeToValue(jsonNodePersons.get(numPerson), Person.class);
				person.setIdPerson(jsonNodePersons.get(numPerson).get("firstName").asText()
						+ jsonNodePersons.get(numPerson).get("lastName").asText());
				personModel.addPerson(person);
				numPerson++;
				iteratorPersons.next();
			}

			// pretty print
			String prettyPersons = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(personModel.getAllPersons());
			System.out.println(prettyPersons);

		} catch (Exception e) {
			logger.error("Error : persons data initialization " + e.toString());
		}

		logger.info("Success : persons data initialization");
	}

	public void initializeDataFirestationMappings() {
		JsonNode jsonNodeFirestationMappings = this.rootNode.path("firestations");
		Iterator<JsonNode> iteratorFirestationMappings = jsonNodeFirestationMappings.elements();

		try {
			ObjectMapper mapper = new ObjectMapper();
			FirestationMapping firestationMapping;
			int numFirestationMapping = 0;
			while (iteratorFirestationMappings.hasNext()) {
				firestationMapping = mapper.treeToValue(jsonNodeFirestationMappings.get(numFirestationMapping),
						FirestationMapping.class);
				firestationMappingModel.addFirestationMapping(firestationMapping);
				numFirestationMapping++;
				iteratorFirestationMappings.next();
			}

			// pretty print
			String prettyFirestations = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(firestationMappingModel.getAllFirestationMappings());
			System.out.println(prettyFirestations);
		} catch (Exception e) {
			logger.error("Error : firestationMappings data initialization " + e.toString());
		}

		logger.info("Success : firestationMappings data initialization");
	}

	// public void initializeDataMedicalRecords(JsonNode rootNode) {
	public void initializeDataMedicalRecords() {
		JsonNode jsonNodeMedicalRecords = this.rootNode.path("medicalrecords");

		Iterator<JsonNode> iteratorMedicalRecords = jsonNodeMedicalRecords.elements();

		try {
			ObjectMapper mapper = new ObjectMapper();
			MedicalRecord medicalRecord;
			int numMedicalRecord = 0;
			while (iteratorMedicalRecords.hasNext()) {
				medicalRecord = mapper.treeToValue(jsonNodeMedicalRecords.get(numMedicalRecord), MedicalRecord.class);
				medicalRecord.setIdMedicalRecord(jsonNodeMedicalRecords.get(numMedicalRecord).get("firstName").asText()
						+ jsonNodeMedicalRecords.get(numMedicalRecord).get("lastName").asText());
				medicalRecordModel.addMedicalRecord(medicalRecord);
				numMedicalRecord++;
				iteratorMedicalRecords.next();
			}

			// pretty print
			String prettyMedicalRecords = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(medicalRecordModel.getAllMedicalRecords());
			System.out.println(prettyMedicalRecords);
		} catch (Exception e) {
			logger.error("Error : medicalRecords data initialization " + e.toString());
		}

		logger.info("Success : medicalRecords data initialization");
	}
}
