package com.safetynet.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.safetynet.entities.Firestation;

public class FirestationDaoTreemapImpl implements IFirestationDao {

	private static Map<String, Firestation> firestations = new TreeMap<>();

	@Override
	public Firestation addFirestation(Firestation firestation) {
		return firestations.put(firestation.getIdFirestation(), firestation);
	}

	@Override
	public Firestation deleteFirestation(String idFirestation) {
		return firestations.remove(idFirestation);
	}

	@Override
	public Firestation updateFirestation(Firestation firestation) {
		return firestations.put(firestation.getIdFirestation(), firestation);
	}

	@Override
	public List<Firestation> getAllFirestations() {
		Collection<Firestation> listAllFirestations = firestations.values();
		return new ArrayList<>(listAllFirestations);
	}

	@Override
	public Firestation getFirestationById(String idFirestation) {
		for (Map.Entry<String, Firestation> mapentry : firestations.entrySet()) {
			if (mapentry.getKey().equals(idFirestation)) {
				return mapentry.getValue();
			}
		}
		return null;
	}
}
