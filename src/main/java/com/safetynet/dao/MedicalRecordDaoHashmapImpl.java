package com.safetynet.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.safetynet.entities.endpoints.MedicalRecord;

@Component
public class MedicalRecordDaoHashmapImpl implements IMedicalRecordDao {

	private static Map<String, MedicalRecord> medicalRecords = new HashMap<>();

	@Override
	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecords.put(medicalRecord.getIdMedicalRecord(), medicalRecord);
	}

	@Override
	public MedicalRecord deleteMedicalRecord(String idMedicalRecord) {
		return medicalRecords.remove(idMedicalRecord);
	}

	@Override
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecords.put(medicalRecord.getIdMedicalRecord(), medicalRecord);
	}

	@Override
	public List<MedicalRecord> getAllMedicalRecords() {
		Collection<MedicalRecord> listAllMedicalRecords = medicalRecords.values();
		return new ArrayList<>(listAllMedicalRecords);
	}

	@Override
	public MedicalRecord getMedicalRecordById(String idMedicalRecord) {
		for (Map.Entry<String, MedicalRecord> mapentry : medicalRecords.entrySet()) {
			if (mapentry.getKey().equals(idMedicalRecord)) {
				return mapentry.getValue();
			}
		}
		return null;
	}
}
