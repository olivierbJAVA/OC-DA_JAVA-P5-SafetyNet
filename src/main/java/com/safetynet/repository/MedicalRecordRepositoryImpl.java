package com.safetynet.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.safetynet.entities.endpoints.MedicalRecord;

/**
 * Class in charge of managing the data persistence for the medical records.
 * Data are stored in a HashMap.
 */
@Repository
public class MedicalRecordRepositoryImpl implements IMedicalRecordRepository {

	private static Map<String, MedicalRecord> medicalRecords = new HashMap<>();

	/**
	 * Method adding a medicalRecord in the repository.
	 * 
	 * @param medicalRecord The medicalRecord to add
	 */
	@Override
	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecords.put(medicalRecord.getIdMedicalRecord(), medicalRecord);
	}

	/**
	 * Method deleting a medicalRecord from the repository.
	 * 
	 * @param medicalRecord The medicalRecord to delete
	 */
	@Override
	public MedicalRecord deleteMedicalRecord(String idMedicalRecord) {
		return medicalRecords.remove(idMedicalRecord);
	}

	/**
	 * Method updating a medicalRecord in the repository.
	 * 
	 * @param medicalRecord The medicalRecord to update
	 */
	@Override
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecords.put(medicalRecord.getIdMedicalRecord(), medicalRecord);
	}

	/**
	 * Method returning all medicalRecords from the repository.
	 * 
	 * @return The list of all medicalRecords
	 */
	@Override
	public List<MedicalRecord> getAllMedicalRecords() {
		Collection<MedicalRecord> listAllMedicalRecords = medicalRecords.values();
		return new ArrayList<>(listAllMedicalRecords);
	}

	/**
	 * Method returning a medicalRecord from the repository given its id.
	 * 
	 * @param idMedicalRecord The id of the medicalRecord
	 * 
	 * @return The medicalRecord corresponding to the idMedicalRecord or null if
	 *         there is no medicalRecord for the given idMedicalRecord
	 */
	@Override
	public MedicalRecord getMedicalRecordById(String idMedicalRecord) {
		for (Map.Entry<String, MedicalRecord> mapentry : medicalRecords.entrySet()) {
			if (mapentry.getKey().equals(idMedicalRecord)) {
				return mapentry.getValue();
			}
		}
		return null;
	}
}
