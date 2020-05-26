package com.safetynet.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.safetynet.entities.FirestationMapping;

@Component
public class FirestationMappingDaoHashmapImpl implements IFirestationMappingDao {

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
}
