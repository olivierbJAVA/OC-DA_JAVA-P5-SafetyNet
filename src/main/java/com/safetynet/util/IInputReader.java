package com.safetynet.util;

import java.util.List;

import com.safetynet.entities.FirestationMapping;
import com.safetynet.entities.MedicalRecord;
import com.safetynet.entities.Person;

public interface IInputReader {
	public List<Person> readIntitialListPersons();

	public List<FirestationMapping> readIntitialListFirestationMappings();

	public List<MedicalRecord> readIntitialListMedicalRecords();

}
