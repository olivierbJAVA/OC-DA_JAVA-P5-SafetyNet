package com.safetynet.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.safetynet.entities.Firestation;
import com.safetynet.entities.Person;

@Component
public class FirestationDaoHashmapImpl implements IFirestationDao {

	private static Map<String, Firestation> firestations = new HashMap<>();

	@Override
	public void addFirestation(Firestation firestation) {
		firestations.put(firestation.getIdFirestation(), firestation);
	}

	@Override
	public void deleteFirestation(String idFirestation) {
		firestations.remove(idFirestation);
	}

	@Override
	public void updateFirestation(Firestation firestation) {
		firestations.put(firestation.getIdFirestation(), firestation);
	}

	@Override
	public List<Firestation> listFirestations() {
		Collection<Firestation> listAllFirestations = firestations.values();
		return new ArrayList<>(listAllFirestations);
	}

	@Override
	public Firestation getFirestationById(String idFirestation) {
		for (Map.Entry<String,Firestation> mapentry : firestations.entrySet()) {
			if(mapentry.getKey().equals(idFirestation)) {
				return mapentry.getValue();
			}	
		}
		return null;
	}
}
