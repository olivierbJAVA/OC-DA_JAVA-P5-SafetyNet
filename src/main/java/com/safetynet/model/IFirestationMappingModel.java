package com.safetynet.model;

import java.util.List;

import com.safetynet.entities.FirestationMapping;

public interface IFirestationMappingModel {
	public FirestationMapping addFirestationMapping(FirestationMapping firestationMapping);

	public FirestationMapping deleteFirestationMapping(String addressFirestation);

	public FirestationMapping updateFirestationMapping(FirestationMapping firestationMapping);

	public List<FirestationMapping> getAllFirestationMappings();

	public FirestationMapping getFirestationMappingByAdress(String adressFirestation);

	boolean firestationMappingInList(FirestationMapping firestationMapping);
}