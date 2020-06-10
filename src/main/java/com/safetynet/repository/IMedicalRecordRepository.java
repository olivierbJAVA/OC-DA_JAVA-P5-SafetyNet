package com.safetynet.repository;

import java.util.List;

import com.safetynet.entities.endpoints.MedicalRecord;

/**
 * Interface to implement for managing the data persistence for the medical
 * records.
 */
public interface IMedicalRecordRepository {

	/**
	 * Method adding a medicalRecord in the repository.
	 * 
	 * @param medicalRecord The medicalRecord to add
	 */
	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord);

	/**
	 * Method deleting a medicalRecord from the repository.
	 * 
	 * @param medicalRecord The medicalRecord to delete
	 */
	public MedicalRecord deleteMedicalRecord(String idmedicalRecord);

	/**
	 * Method updating a medicalRecord in the repository.
	 * 
	 * @param medicalRecord The medicalRecord to update
	 */
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord);

	/**
	 * Method returning all medicalRecords from the repository.
	 * 
	 * @return The list of all medicalRecords
	 */
	public List<MedicalRecord> getAllMedicalRecords();

	/**
	 * Method returning a medicalRecord from the repository given its id.
	 * 
	 * @param idMedicalRecord The id of the medicalRecord
	 * 
	 * @return The medicalRecord corresponding to the idMedicalRecord or null if
	 *         there is no medicalRecord for the given idMedicalRecord
	 */
	public MedicalRecord getMedicalRecordById(String idMedicalRecord);

}