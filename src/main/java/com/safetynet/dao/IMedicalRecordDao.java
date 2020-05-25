package com.safetynet.dao;

import java.util.List;

import com.safetynet.entities.MedicalRecord;

public interface IMedicalRecordDao {
	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord);

	public MedicalRecord deleteMedicalRecord(String idmedicalRecord);

	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord);

	public List<MedicalRecord> getAllMedicalRecords();

	public MedicalRecord getMedicalRecordById(String idMedicalRecord);

}