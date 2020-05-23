package com.safetynet.dao;

import java.util.List;

import com.safetynet.entities.MedicalRecord;

public interface IMedicalRecordDao {
	public void addMedicalRecord(MedicalRecord medicalRecord);

	public void deleteMedicalRecord(String idmedicalRecord);

	public void updateMedicalRecord(MedicalRecord medicalRecord);

	public List<MedicalRecord> listMedicalRecords();

}
