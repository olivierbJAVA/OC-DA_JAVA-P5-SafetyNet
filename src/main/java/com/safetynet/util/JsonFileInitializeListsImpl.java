package com.safetynet.util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.entities.FirestationMapping;
import com.safetynet.entities.MedicalRecord;
import com.safetynet.entities.Person;
import com.safetynet.model.IFirestationMappingModel;
import com.safetynet.model.IMedicalRecordModel;
import com.safetynet.model.IPersonModel;

@Component
public class JsonFileInitializeListsImpl implements IInitializeLists {

	private static final Logger logger = LoggerFactory.getLogger("JsonInputFileReader");

	// A passer en paramètre dans un constructeur pour les tests ?
	@Autowired
	IPersonModel personModel;

	@Autowired
	IFirestationMappingModel firestationMappingModel;

	@Autowired
	IMedicalRecordModel medicalRecordModel;

	// InputStream input = new FileInputStream("data.json");

	// OB : doit-on faire Autowired pour cet objet ?
	@Autowired
	ObjectMapper mapper;
	// ObjectMapper mapper = new ObjectMapper();

	// initialisation : possibilité 2 (appelé après la construction de cet objet)
	@PostConstruct
	public void initializeLists() {

		try {
			JsonNode rootNode = mapper.readTree(new File("data.json"));

			// initial persons list
			JsonNode jsonNodePersons = rootNode.path("persons");
			Iterator<JsonNode> iteratorPersons = jsonNodePersons.elements();

			// Pas possible ici ?
			/*
			 * @Autowired IPersonModel personModel;
			 */
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

			// initial firestation mappings list
			JsonNode jsonNodeFirestationMappings = rootNode.path("firestations");
			Iterator<JsonNode> iteratorFirestationMappings = jsonNodeFirestationMappings.elements();

			FirestationMapping firestationMapping;
			int numFirestationMapping = 0;
			while (iteratorFirestationMappings.hasNext()) {
				firestationMapping = mapper.treeToValue(jsonNodeFirestationMappings.get(numFirestationMapping),
						FirestationMapping.class);
				firestationMapping
						.setAddress(jsonNodeFirestationMappings.get(numFirestationMapping).get("address").asText());
				firestationMappingModel.addFirestationMapping(firestationMapping);
				numFirestationMapping++;
				iteratorFirestationMappings.next();
			}

			// pretty print
			String prettyFirestations = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(firestationMappingModel.getAllFirestationMappings());
			System.out.println(prettyFirestations);

			// initial medical records list
			JsonNode jsonNodeMedicalRecords = rootNode.path("medicalrecords");

			Iterator<JsonNode> iteratorMedicalRecords = jsonNodeMedicalRecords.elements();

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

		} catch (IOException e) {
			logger.error("Error in lists initialization " + e.toString());
			System.exit(0);
		}

		logger.info("Success lists initialization");
	}

}
