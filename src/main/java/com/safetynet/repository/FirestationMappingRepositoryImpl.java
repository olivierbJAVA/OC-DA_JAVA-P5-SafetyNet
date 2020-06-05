package com.safetynet.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.safetynet.entities.endpoints.FirestationMapping;

@Repository
public class FirestationMappingRepositoryImpl implements IFirestationMappingRepository {

	private static Map<String, FirestationMapping> firestationMappings = new HashMap<>();

	@Override
	public FirestationMapping addFirestationMapping(FirestationMapping firestationMapping) {
		return firestationMappings.put(firestationMapping.getAddress(), firestationMapping);
	}

	@Override
	public FirestationMapping deleteFirestationMapping(String addressFirestationMapping) {
		return firestationMappings.remove(addressFirestationMapping);
	}

	@Override
	public FirestationMapping updateFirestationMapping(FirestationMapping firestationMapping) {
		return firestationMappings.put(firestationMapping.getAddress(), firestationMapping);
	}

	@Override
	public List<FirestationMapping> getAllFirestationMappings() {
		Collection<FirestationMapping> listAllFirestationMappings = firestationMappings.values();
		return new ArrayList<>(listAllFirestationMappings);
	}

	@Override
	public FirestationMapping getFirestationMappingByAdress(String adressFirestationMapping) {
		for (Map.Entry<String, FirestationMapping> mapentry : firestationMappings.entrySet()) {
			if (mapentry.getValue().getAddress().equals(adressFirestationMapping)) {
				return mapentry.getValue();
			}
		}
		return null;
	}

	@Override
	public FirestationMapping getFirestationMappingByIdStation(String idStation) {
		for (Map.Entry<String, FirestationMapping> mapentry : firestationMappings.entrySet()) {
			if ((mapentry.getValue().getStation()).equals(idStation)) {
				return mapentry.getValue();
			}
		}
		return null;
	}
}
