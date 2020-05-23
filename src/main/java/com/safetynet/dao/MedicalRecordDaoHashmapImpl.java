package com.safetynet.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.safetynet.entities.MedicalRecord;

@Component
public class MedicalRecordDaoHashmapImpl implements IMedicalRecordDao {

	private static Map<String, MedicalRecord> medicalRecords = new HashMap<>();

	@Override
	public void addMedicalRecord(MedicalRecord medicalRecord) {
		medicalRecords.put(medicalRecord.getIdMedicalRecord(), medicalRecord);
	}

	@Override
	public void deleteMedicalRecord(String idMedicalRecord) {
		medicalRecords.remove(idMedicalRecord);
	}

	@Override
	public void updateMedicalRecord(MedicalRecord medicalRecord) {
		medicalRecords.put(medicalRecord.getIdMedicalRecord(), medicalRecord);
	}

	@Override
	public List<MedicalRecord> listMedicalRecords() {
		Collection<MedicalRecord> listAllMedicalRecords = medicalRecords.values();
		return new ArrayList<>(listAllMedicalRecords);
	}

}
