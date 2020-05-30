package com.safetynet.model.endpoints;

import java.util.List;

import com.safetynet.entities.endpoints.MedicalRecord;

public interface IMedicalRecordModel {
	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord);

	public MedicalRecord deleteMedicalRecord(String idmedicalRecord);

	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord);

	public List<MedicalRecord> getAllMedicalRecords();

	public MedicalRecord getMedicalRecordById(String idMedicalRecord);

	public boolean medicalRecordExist(MedicalRecord medicalRecord);
}