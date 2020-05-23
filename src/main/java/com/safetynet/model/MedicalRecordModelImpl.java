package com.safetynet.model;

import java.util.List;

import com.safetynet.dao.IMedicalRecordDao;
import com.safetynet.entities.MedicalRecord;

public class MedicalRecordModelImpl implements IMedicalRecordModel {

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
	public void addMedicalRecord(MedicalRecord medicalRecord) {
		medicalRecordDao.addMedicalRecord(medicalRecord);
	}

	@Override
	public void deleteMedicalRecord(String idMedicalRecord) {
		medicalRecordDao.deleteMedicalRecord(idMedicalRecord);
	}

	@Override
	public void updateMedicalRecord(MedicalRecord medicalRecord) {
		medicalRecordDao.updateMedicalRecord(medicalRecord);
	}

	@Override
	public List<MedicalRecord> listMedicalRecords() {
		return medicalRecordDao.listMedicalRecords();
	}
}
