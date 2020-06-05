package com.safetynet.service.endpoints;

import java.util.List;

import com.safetynet.entities.endpoints.FirestationMapping;

public interface IFirestationMappingService {
	public FirestationMapping addFirestationMapping(FirestationMapping firestationMapping);

	public FirestationMapping deleteFirestationMapping(String addressFirestation);

	public FirestationMapping updateFirestationMapping(FirestationMapping firestationMapping);

	public List<FirestationMapping> getAllFirestationMappings();

	public FirestationMapping getFirestationMappingByAdress(String adressFirestation);

	public FirestationMapping getFirestationMappingByIdStation(String idFirestation);
	
	boolean firestationMappingExist(FirestationMapping firestationMapping);

}