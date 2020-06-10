package com.safetynet.service.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.entities.endpoints.MedicalRecord;
import com.safetynet.repository.IMedicalRecordRepository;

/**
 * Class in charge of managing the services for the medical records endpoint.
 */
@Service
public class MedicalRecordServiceImpl implements IMedicalRecordService {

	@Autowired
	private IMedicalRecordRepository medicalRecordRepository;

	public MedicalRecordServiceImpl() {
		super();
	}

	public MedicalRecordServiceImpl(IMedicalRecordRepository medicalRecordRepository) {
		super();
		this.medicalRecordRepository = medicalRecordRepository;
	}

	public IMedicalRecordRepository getMedicalRecordRepository() {
		return medicalRecordRepository;
	}

	public void setMedicalRecordRepository(IMedicalRecordRepository medicalRecordRepository) {
		this.medicalRecordRepository = medicalRecordRepository;
	}

	/**
	 * Add a medicalRecord.
	 * 
	 * @param medicalRecord The medicalRecord to add
	 */
	@Override
	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordRepository.addMedicalRecord(medicalRecord);
	}

	/**
	 * Delete a medicalRecord.
	 * 
	 * @param idmedicalRecord The id of the medicalRecord
	 */
	@Override
	public MedicalRecord deleteMedicalRecord(String idMedicalRecord) {
		return medicalRecordRepository.deleteMedicalRecord(idMedicalRecord);
	}

	/**
	 * Update a medicalRecord.
	 * 
	 * @param medicalRecord The medicalRecord to update
	 */
	@Override
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordRepository.updateMedicalRecord(medicalRecord);
	}

	/**
	 * Return all medicalRecords.
	 * 
	 * @return The list of all medicalRecords
	 */
	@Override
	public List<MedicalRecord> getAllMedicalRecords() {
		return medicalRecordRepository.getAllMedicalRecords();
	}

	/**
	 * Return a medicalRecord given its id.
	 * 
	 * @param idMedicalRecord The id of the medicalRecord
	 * 
	 * @return The medicalRecord corresponding to the id or null if there is no
	 *         medicalRecord for the given id
	 */
	@Override
	public MedicalRecord getMedicalRecordById(String idMedicalRecord) {
		return medicalRecordRepository.getMedicalRecordById(idMedicalRecord);
	}

	/**
	 * Indicates if a medicalRecord exist or not.
	 * 
	 * @param medicalRecord The medicalRecord
	 * 
	 * @return true if the medicalRecord exist or false if this is not the case
	 */
	@Override
	public boolean medicalRecordExist(MedicalRecord medicalRecord) {
		if (medicalRecordRepository
				.getMedicalRecordById(medicalRecord.getFirstName() + medicalRecord.getLastName()) == null) {
			return false;
		}
		return true;
	}
}
