package com.safetynet.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.dao.IMedicalRecordDao;
import com.safetynet.entities.MedicalRecord;
import com.safetynet.entities.Person;

@Component
public class MedicalRecordModelImpl implements IMedicalRecordModel {

	@Autowired
	private IMedicalRecordDao medicalRecordDao;
	
	public MedicalRecordModelImpl() {
		super();
	}
	
	public MedicalRecordModelImpl(IMedicalRecordDao medicalRecordDao) {
		super();
		this.medicalRecordDao = medicalRecordDao;
	}

	public IMedicalRecordDao getMedicalRecordDao() {
		return medicalRecordDao;
	}

	public void setMedicalRecordDao(IMedicalRecordDao medicalRecordDao) {
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
}
