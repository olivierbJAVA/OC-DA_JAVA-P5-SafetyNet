package com.safetynet.util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.view.FirestationViewConsoleImpl;
import com.safety.view.MedicalRecordViewConsoleImpl;
import com.safety.view.PersonViewConsoleImpl;
import com.safetynet.dao.FirestationDaoTreemapImpl;
import com.safetynet.dao.MedicalRecordDaoHashmapImpl;
import com.safetynet.dao.PersonDaoHashmapImpl;
import com.safetynet.entities.Firestation;
import com.safetynet.entities.MedicalRecord;
import com.safetynet.entities.Person;
import com.safetynet.model.FirestationModelImpl;
import com.safetynet.model.MedicalRecordModelImpl;
import com.safetynet.model.PersonModelImpl;

public class JacksonReaderSN {

	public static void main(String[] args) {
			//InputStream input = new FileInputStream("data.json");

			try {
	        	/*
	        	ObjectMapper mapper = new ObjectMapper();
	            // JSON file to Java object
	            SafetyNetEntity sne = mapper.readValue(new File("data.json"), SafetyNetEntity.class);

	            // compact print
	            System.out.println(sne.toString());

	            // pretty print
	            String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(sne);

	            System.out.println(prettyStaff1);
				*/

	        	ObjectMapper mapper = new ObjectMapper();
	        	JsonNode rootNode = mapper.readTree(new File("data.json"));

	        	
	        	
	        	//persons
	        	JsonNode jsonNodePersons = rootNode.path("persons");
	        	//System.out.println(jsonNodePersons.toString());
	        	Iterator<JsonNode> iteratorPersons = jsonNodePersons.elements();
	    		
	        	//PERSONS
	        	//model
	        	PersonModelImpl personModel = new PersonModelImpl();
				PersonDaoHashmapImpl personDao = new PersonDaoHashmapImpl();
				personModel.setPersonDao(personDao);
				
				ObjectMapper mapperPersons = new ObjectMapper();
				Person person;
				int numPerson=0;
	        	while(iteratorPersons.hasNext()) {
	        			//System.out.println(jsonNodePersons.get(numPerson));
	        			person = mapperPersons.treeToValue(jsonNodePersons.get(numPerson),Person.class);
	        			person.setIdPerson(jsonNodePersons.get(numPerson).get("firstName").asText()+jsonNodePersons.get(numPerson).get("lastName").asText());
	        			personModel.addPerson(person);
	        			numPerson++;
	        			iteratorPersons.next();
	        	}
	        	
				//vue
				PersonViewConsoleImpl personView = new PersonViewConsoleImpl();
				List<Person> personList = personModel.listPerson();
				personView.setPersonListToPrint(personList);
				personView.printDetails();
	        	
	            // pretty print
	            String prettyPersons = mapperPersons.writerWithDefaultPrettyPrinter().writeValueAsString(personList);
	            System.out.println(prettyPersons);
				
	            
	            
	        	//firestations
	        	JsonNode jsonNodeFirestations = rootNode.path("firestations");
	        	//System.out.println(jsonNodeFirestations.toString());
	        	Iterator<JsonNode> iteratorFirestations = jsonNodeFirestations.elements();
	        	
				// FIRESTATIONS
				// model
				FirestationModelImpl firestationModel = new FirestationModelImpl();
				firestationModel.setFirestationDao(new FirestationDaoTreemapImpl());

				ObjectMapper mapperFirestations = new ObjectMapper();
	        	Firestation firestation;
				int numFirestation=0;
	        	while(iteratorFirestations.hasNext()) {
	        			//System.out.println(jsonNodePersons.get(numFirestation));
	        			firestation = mapperFirestations.treeToValue(jsonNodeFirestations.get(numFirestation),Firestation.class);
	        			firestation.setIdFirestation(jsonNodeFirestations.get(numFirestation).get("address").asText()+jsonNodeFirestations.get(numFirestation).get("station").asText());
	        			firestationModel.addFirestation(firestation);
	        			numFirestation++;
	        			iteratorFirestations.next();
	        	}
				 
				// vue
				List<Firestation> firestationList = firestationModel.listFirestation();
				FirestationViewConsoleImpl firestationViewList = new FirestationViewConsoleImpl();
				firestationViewList.printDetails(firestationList);
	        	
	            // pretty print
	            String prettyFirestations = mapperFirestations.writerWithDefaultPrettyPrinter().writeValueAsString(firestationList);
	            System.out.println(prettyFirestations);
	            
	            

	        	//medicalrecords
	        	JsonNode jsonNodeMedicalRecords = rootNode.path("medicalrecords");
	        	//System.out.println(jsonNodeMedicalRecords.toString());
	        	Iterator<JsonNode> iteratorMedicalRecords = jsonNodeMedicalRecords.elements();
	        	
	        	
	        	// MEDICALRECORDS
				// model
				MedicalRecordDaoHashmapImpl medicalRecordDao = new MedicalRecordDaoHashmapImpl();
				MedicalRecordModelImpl medicalRecordModel = new MedicalRecordModelImpl(medicalRecordDao);
				
				ObjectMapper mapperMedicalRecords = new ObjectMapper();
				MedicalRecord medicalRecord;
				int numMedicalRecord=0;
	        	while(iteratorMedicalRecords.hasNext()) {
	        			//System.out.println(jsonNodeMedicalRecords.get(numMedicalRecord));
	        			medicalRecord = mapperMedicalRecords.treeToValue(jsonNodeMedicalRecords.get(numMedicalRecord),MedicalRecord.class);
	        			medicalRecord.setIdMedicalRecord(jsonNodeMedicalRecords.get(numMedicalRecord).get("firstName").asText()+jsonNodeMedicalRecords.get(numMedicalRecord).get("lastName").asText());
	        			medicalRecordModel.addMedicalRecord(medicalRecord);
	        			numMedicalRecord++;
	        			iteratorMedicalRecords.next();
	        	}
				
				/*
				JsonArray jsonArrayMedicalRecords = rootJSON.getJsonArray("medicalrecords");

				for (int i = 0; i < jsonArrayMedicalRecords.size(); i++) {
					JsonObject obj = jsonArrayMedicalRecords.getJsonObject(i);
					
					MedicalRecord medicalRecord = new MedicalRecord();
					medicalRecord.setIdMedicalRecord(obj.getString("firstName") + obj.getString("lastName"));
					medicalRecord.setFirstName(obj.getString("firstName"));
					medicalRecord.setLastName(obj.getString("lastName"));
					medicalRecord.setBirthdate(obj.getString("birthdate"));

					JsonArray jsonArrayMedications = obj.getJsonArray("medications");
					String[] medications = new String[jsonArrayMedications.size()];
					int j = 0;
					for (JsonValue value : jsonArrayMedications) {
						medications[j++] = value.toString();
					}
					medicalRecord.setMedications(medications);

					JsonArray jsonArrayAllergies = obj.getJsonArray("allergies");
					String[] allergies = new String[jsonArrayAllergies.size()];
					j = 0;
					for (JsonValue value : jsonArrayAllergies) {
						allergies[j++] = value.toString();
					}
					medicalRecord.setAllergies(allergies);

					medicalRecordModel.addMedicalRecord(medicalRecord);
				}
				*/
				// vue
				List<MedicalRecord> medicalRecordList = medicalRecordModel.listMedicalRecords();
				MedicalRecordViewConsoleImpl medicalRecordViewList = new MedicalRecordViewConsoleImpl(medicalRecordList);
				medicalRecordViewList.printDetails();
				
	            // pretty print
	            String prettyMedicalRecords = mapperMedicalRecords.writerWithDefaultPrettyPrinter().writeValueAsString(medicalRecordList);
	            System.out.println(prettyMedicalRecords);
	        	
	        } catch (IOException e) {
	            
	        	e.printStackTrace();
	        }
	}
	
}
