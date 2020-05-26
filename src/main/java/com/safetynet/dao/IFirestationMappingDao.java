package com.safetynet.dao;

import java.util.List;

import com.safetynet.entities.FirestationMapping;

public interface IFirestationMappingDao {
	public FirestationMapping addFirestationMapping(FirestationMapping firestation);

	public FirestationMapping deleteFirestationMapping(String addressFirestation);

	public FirestationMapping updateFirestationMapping(FirestationMapping firestation);

	public List<FirestationMapping> getAllFirestationMappings();

	public FirestationMapping getFirestationMappingByAdress(String adressFirestation);
}