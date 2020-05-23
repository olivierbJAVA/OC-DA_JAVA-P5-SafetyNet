package com.safetynet.model;

import java.util.List;

import com.safetynet.entities.MedicalRecord;

public interface IMedicalRecordModel {
	public void addMedicalRecord(MedicalRecord medicalRecord);

	public void deleteMedicalRecord(String idmedicalRecord);

	public void updateMedicalRecord(MedicalRecord medicalRecord);

	public List<MedicalRecord> listMedicalRecords();
}
