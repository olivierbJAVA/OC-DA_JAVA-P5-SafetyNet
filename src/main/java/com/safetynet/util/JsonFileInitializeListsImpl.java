package com.safetynet.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.entities.endpoints.FirestationMapping;
import com.safetynet.entities.endpoints.MedicalRecord;
import com.safetynet.entities.endpoints.Person;
import com.safetynet.service.endpoints.IFirestationMappingService;
import com.safetynet.service.endpoints.IMedicalRecordService;
import com.safetynet.service.endpoints.IPersonService;

/**
 * Class in charge of managing the data initialization of the lists. The data
 * come from a file which path is parameter of the object constructor.
 */
@Service
public class JsonFileInitializeListsImpl implements IInitializeLists {

	private static final Logger logger = LoggerFactory.getLogger("JsonInputFileReader");

	@Autowired
	private IPersonService personService;

	@Autowired
	private IFirestationMappingService firestationMappingService;

	@Autowired
	private IMedicalRecordService medicalRecordService;

	private String filePathInputData;

	private JsonNode rootNode;

	// The value of the file path containing input data comes from the SpringBoot
	// application.properties file
	/**
	 * Constructor of the JsonFileInitializeListsImpl object.
	 * 
	 * @param filePathInputData The path of the file containing input data
	 */
	public JsonFileInitializeListsImpl(@Value("${filePathInputData}") String filePathInputData) {
		this.filePathInputData = filePathInputData;
	}

	/**
	 * Get the initial data.
	 */
	@PostConstruct
	public void getInitialData() {

		logger.info("Start data initilization");

		try {
			ObjectMapper mapper = new ObjectMapper();

			logger.info("File Input for Data Initialization : {}", filePathInputData);

			this.rootNode = mapper.readTree(new File(filePathInputData));

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
	}

	/**
	 * Initialize the data list of persons.
	 */
	public void initializeDataPersons() {
		JsonNode jsonNodePersons = this.rootNode.path("persons");
		Iterator<JsonNode> iteratorPersons = jsonNodePersons.elements();

		try {
			ObjectMapper mapper = new ObjectMapper();
			Person person;
			int numPerson = 0;
			// We add each person
			while (iteratorPersons.hasNext()) {
				person = mapper.treeToValue(jsonNodePersons.get(numPerson), Person.class);
				person.setIdPerson(jsonNodePersons.get(numPerson).get("firstName").asText()
						+ jsonNodePersons.get(numPerson).get("lastName").asText());
				personService.addPerson(person);
				numPerson++;
				iteratorPersons.next();
			}

			// pretty print
			String prettyPrintPersons = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(personService.getAllPersons());
			logger.debug("Initial list of persons : {}", prettyPrintPersons);

		} catch (Exception e) {
			logger.error("Error : persons data initialization " + e.toString());
		}

		logger.info("Success : persons data initialization");
	}

	/**
	 * Initialize the data list of firestation mappings.
	 */
	public void initializeDataFirestationMappings() {
		JsonNode jsonNodeFirestationMappings = this.rootNode.path("firestations");
		Iterator<JsonNode> iteratorFirestationMappings = jsonNodeFirestationMappings.elements();

		try {
			ObjectMapper mapper = new ObjectMapper();
			FirestationMapping firestationMapping;
			int numFirestationMapping = 0;
			// We add each firestationMapping
			while (iteratorFirestationMappings.hasNext()) {
				firestationMapping = mapper.treeToValue(jsonNodeFirestationMappings.get(numFirestationMapping),
						FirestationMapping.class);
				firestationMappingService.addFirestationMapping(firestationMapping);
				numFirestationMapping++;
				iteratorFirestationMappings.next();
			}

			// pretty print
			String prettyPrintFirestations = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(firestationMappingService.getAllFirestationMappings());
			logger.debug("Initial list of firestation mappings : {}", prettyPrintFirestations);

		} catch (Exception e) {
			logger.error("Error : firestationMappings data initialization " + e.toString());
		}

		logger.info("Success : firestationMappings data initialization");
	}

	/**
	 * Initialize the data list of medical records.
	 */
	public void initializeDataMedicalRecords() {
		JsonNode jsonNodeMedicalRecords = this.rootNode.path("medicalrecords");

		Iterator<JsonNode> iteratorMedicalRecords = jsonNodeMedicalRecords.elements();

		try {
			ObjectMapper mapper = new ObjectMapper();
			MedicalRecord medicalRecord;
			int numMedicalRecord = 0;
			// We add each medicalRecord
			while (iteratorMedicalRecords.hasNext()) {
				medicalRecord = mapper.treeToValue(jsonNodeMedicalRecords.get(numMedicalRecord), MedicalRecord.class);
				medicalRecord.setIdMedicalRecord(jsonNodeMedicalRecords.get(numMedicalRecord).get("firstName").asText()
						+ jsonNodeMedicalRecords.get(numMedicalRecord).get("lastName").asText());
				medicalRecordService.addMedicalRecord(medicalRecord);
				numMedicalRecord++;
				iteratorMedicalRecords.next();
			}

			// pretty print
			String prettyPrintMedicalRecords = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(medicalRecordService.getAllMedicalRecords());
			logger.debug("Initial list of firestation mappings : {}", prettyPrintMedicalRecords);

		} catch (Exception e) {
			logger.error("Error : medicalRecords data initialization " + e.toString());
		}

		logger.info("Success : medicalRecords data initialization");
	}
}
