package com.safetynet.repository;

import java.util.List;

import com.safetynet.entities.endpoints.FirestationMapping;

public interface IFirestationMappingRepository {
	public FirestationMapping addFirestationMapping(FirestationMapping firestation);

	public FirestationMapping deleteFirestationMapping(String addressFirestation);

	public FirestationMapping updateFirestationMapping(FirestationMapping firestation);

	public List<FirestationMapping> getAllFirestationMappings();

	public FirestationMapping getFirestationMappingByAdress(String adressFirestation);

	public FirestationMapping getFirestationMappingByIdStation(String idFirestation);
}