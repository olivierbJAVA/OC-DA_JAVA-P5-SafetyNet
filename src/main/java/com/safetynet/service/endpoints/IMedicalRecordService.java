package com.safetynet.service.endpoints;

import java.util.List;

import com.safetynet.entities.endpoints.MedicalRecord;

/**
 * Interface to implement for managing the services for the medical records
 * endpoint.
 */
public interface IMedicalRecordService {

	/**
	 * Add a medicalRecord.
	 * 
	 * @param medicalRecord The medicalRecord to add
	 */
	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord);

	/**
	 * Delete a medicalRecord.
	 * 
	 * @param idmedicalRecord The id of the medicalRecord
	 */
	public MedicalRecord deleteMedicalRecord(String idmedicalRecord);

	/**
	 * Update a medicalRecord.
	 * 
	 * @param medicalRecord The medicalRecord to update
	 */
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord);

	/**
	 * Return all medicalRecords.
	 * 
	 * @return The list of all medicalRecords
	 */
	public List<MedicalRecord> getAllMedicalRecords();

	/**
	 * Return a medicalRecord given its id.
	 * 
	 * @param idMedicalRecord The id of the medicalRecord
	 * 
	 * @return The medicalRecord corresponding to the id or null if there is no
	 *         medicalRecord for the given id
	 */
	public MedicalRecord getMedicalRecordById(String idMedicalRecord);

	/**
	 * Indicates if a medicalRecord exist or not.
	 * 
	 * @param medicalRecord The medicalRecord
	 * 
	 * @return true if the medicalRecord exist or false if this is not the case
	 */
	public boolean medicalRecordExist(MedicalRecord medicalRecord);
}