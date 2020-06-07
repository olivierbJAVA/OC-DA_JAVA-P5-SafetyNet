package com.safetynet.service.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.entities.endpoints.MedicalRecord;
import com.safetynet.repository.IMedicalRecordRepository;

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

	@Override
	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordRepository.addMedicalRecord(medicalRecord);
	}

	@Override
	public MedicalRecord deleteMedicalRecord(String idMedicalRecord) {
		return medicalRecordRepository.deleteMedicalRecord(idMedicalRecord);
	}

	@Override
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordRepository.updateMedicalRecord(medicalRecord);
	}

	@Override
	public List<MedicalRecord> getAllMedicalRecords() {
		return medicalRecordRepository.getAllMedicalRecords();
	}

	@Override
	public MedicalRecord getMedicalRecordById(String idMedicalRecord) {
		return medicalRecordRepository.getMedicalRecordById(idMedicalRecord);
	}

	@Override
	public boolean medicalRecordExist(MedicalRecord medicalRecord) {
		if (medicalRecordRepository.getMedicalRecordById(medicalRecord.getFirstName() + medicalRecord.getLastName()) == null) {
			return false;
		}
		return true;
	}
}
