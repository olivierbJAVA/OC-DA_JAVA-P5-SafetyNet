package com.safetynet.service.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.entities.endpoints.MedicalRecord;
import com.safetynet.repository.IMedicalRecordRepository;

@Service
public class MedicalRecordServiceImpl implements IMedicalRecordService {

	@Autowired
	private IMedicalRecordRepository medicalRecordDao;

	public MedicalRecordServiceImpl() {
		super();
	}

	public MedicalRecordServiceImpl(IMedicalRecordRepository medicalRecordDao) {
		super();
		this.medicalRecordDao = medicalRecordDao;
	}

	public IMedicalRecordRepository getMedicalRecordDao() {
		return medicalRecordDao;
	}

	public void setMedicalRecordDao(IMedicalRecordRepository medicalRecordDao) {
		this.medicalRecordDao = medicalRecordDao;
	}

	@Override
	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordDao.addMedicalRecord(medicalRecord);
	}

	@Override
	public MedicalRecord deleteMedicalRecord(String idMedicalRecord) {
		return medicalRecordDao.deleteMedicalRecord(idMedicalRecord);
	}

	@Override
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordDao.updateMedicalRecord(medicalRecord);
	}

	@Override
	public List<MedicalRecord> getAllMedicalRecords() {
		return medicalRecordDao.getAllMedicalRecords();
	}

	@Override
	public MedicalRecord getMedicalRecordById(String idMedicalRecord) {
		return medicalRecordDao.getMedicalRecordById(idMedicalRecord);
	}

	@Override
	public boolean medicalRecordExist(MedicalRecord medicalRecord) {
		if (medicalRecordDao.getMedicalRecordById(medicalRecord.getFirstName() + medicalRecord.getLastName()) == null) {
			return false;
		}
		return true;
	}
}
