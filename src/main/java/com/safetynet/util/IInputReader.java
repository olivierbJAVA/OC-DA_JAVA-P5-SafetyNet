package com.safetynet.util;

import java.util.List;

import com.safetynet.entities.Firestation;
import com.safetynet.entities.MedicalRecord;
import com.safetynet.entities.Person;

public interface IInputReader {
	public List<Person> readIntitialListPersons();

	public List<Firestation> readIntitialListFirestations();

	public List<MedicalRecord> readIntitialListMedicalRecords();

}
