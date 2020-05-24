package com.safetynet.util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.dao.FirestationDaoTreemapImpl;
import com.safetynet.dao.MedicalRecordDaoHashmapImpl;
import com.safetynet.dao.PersonDaoHashmapImpl;
import com.safetynet.entities.Firestation;
import com.safetynet.entities.MedicalRecord;
import com.safetynet.entities.Person;
import com.safetynet.model.FirestationModelImpl;
import com.safetynet.model.MedicalRecordModelImpl;
import com.safetynet.model.PersonModelImpl;

@Component
public class JsonFileInputReaderImpl implements IInputReader {

	private static final Logger logger = LogManager.getLogger("JsonInputFileReader");

	// InputStream input = new FileInputStream("data.json");

	public List<Person> readIntitialListPersons() {
		List<Person> personList;
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(new File("data.json"));

			JsonNode jsonNodePersons = rootNode.path("persons");
			Iterator<JsonNode> iteratorPersons = jsonNodePersons.elements();
			PersonModelImpl personModel = new PersonModelImpl();
			PersonDaoHashmapImpl personDao = new PersonDaoHashmapImpl();
			personModel.setPersonDao(personDao);

			ObjectMapper mapperPersons = new ObjectMapper();
			Person person;
			int numPerson = 0;
			while (iteratorPersons.hasNext()) {
				person = mapperPersons.treeToValue(jsonNodePersons.get(numPerson), Person.class);
				person.setIdPerson(jsonNodePersons.get(numPerson).get("firstName").asText()
						+ jsonNodePersons.get(numPerson).get("lastName").asText());
				personModel.addPerson(person);
				numPerson++;
				iteratorPersons.next();
			}

			// pretty print
			personList = personModel.getAllPersons();
			String prettyPersons = mapperPersons.writerWithDefaultPrettyPrinter().writeValueAsString(personList);
			System.out.println(prettyPersons);

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return personList;
	}

	public List<Firestation> readIntitialListFirestations() {
		List<Firestation> firestationList;
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(new File("data.json"));

			JsonNode jsonNodeFirestations = rootNode.path("firestations");
			Iterator<JsonNode> iteratorFirestations = jsonNodeFirestations.elements();

			FirestationModelImpl firestationModel = new FirestationModelImpl();
			firestationModel.setFirestationDao(new FirestationDaoTreemapImpl());

			ObjectMapper mapperFirestations = new ObjectMapper();
			Firestation firestation;
			int numFirestation = 0;
			while (iteratorFirestations.hasNext()) {
				firestation = mapperFirestations.treeToValue(jsonNodeFirestations.get(numFirestation),
						Firestation.class);
				firestation.setIdFirestation(jsonNodeFirestations.get(numFirestation).get("address").asText()
						+ jsonNodeFirestations.get(numFirestation).get("station").asText());
				firestationModel.addFirestation(firestation);
				numFirestation++;
				iteratorFirestations.next();
			}

			// pretty print
			firestationList = firestationModel.listFirestation();
			String prettyFirestations = mapperFirestations.writerWithDefaultPrettyPrinter()
					.writeValueAsString(firestationList);
			System.out.println(prettyFirestations);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return firestationList;
	}

	public List<MedicalRecord> readIntitialListMedicalRecords() {
		List<MedicalRecord> medicalRecordList;
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(new File("data.json"));

			JsonNode jsonNodeMedicalRecords = rootNode.path("medicalrecords");

			Iterator<JsonNode> iteratorMedicalRecords = jsonNodeMedicalRecords.elements();

			MedicalRecordDaoHashmapImpl medicalRecordDao = new MedicalRecordDaoHashmapImpl();
			MedicalRecordModelImpl medicalRecordModel = new MedicalRecordModelImpl(medicalRecordDao);

			ObjectMapper mapperMedicalRecords = new ObjectMapper();
			MedicalRecord medicalRecord;
			int numMedicalRecord = 0;
			while (iteratorMedicalRecords.hasNext()) {
				medicalRecord = mapperMedicalRecords.treeToValue(jsonNodeMedicalRecords.get(numMedicalRecord),
						MedicalRecord.class);
				medicalRecord.setIdMedicalRecord(jsonNodeMedicalRecords.get(numMedicalRecord).get("firstName").asText()
						+ jsonNodeMedicalRecords.get(numMedicalRecord).get("lastName").asText());
				medicalRecordModel.addMedicalRecord(medicalRecord);
				numMedicalRecord++;
				iteratorMedicalRecords.next();
			}

			// pretty print
			medicalRecordList = medicalRecordModel.listMedicalRecords();
			String prettyMedicalRecords = mapperMedicalRecords.writerWithDefaultPrettyPrinter()
					.writeValueAsString(medicalRecordList);
			System.out.println(prettyMedicalRecords);

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return medicalRecordList;
	}

}
