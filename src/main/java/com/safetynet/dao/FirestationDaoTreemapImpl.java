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

}
