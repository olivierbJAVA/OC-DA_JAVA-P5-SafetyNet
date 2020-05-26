package com.safetynet.model;

import java.util.List;

import com.safetynet.entities.MedicalRecord;

public interface IMedicalRecordModel {
	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord);

	public MedicalRecord deleteMedicalRecord(String idmedicalRecord);

	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord);

	public List<MedicalRecord> getAllMedicalRecords();

	public MedicalRecord getMedicalRecordById(String idMedicalRecord);

	public boolean medicalRecordInList(MedicalRecord medicalRecord);
}