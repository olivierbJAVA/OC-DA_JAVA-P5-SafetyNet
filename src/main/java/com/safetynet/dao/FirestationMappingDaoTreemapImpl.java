package com.safetynet.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.safetynet.entities.FirestationMapping;

public class FirestationMappingDaoTreemapImpl implements IFirestationMappingDao {

	private static Map<String, FirestationMapping> firestationMappings = new TreeMap<>();

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
}
