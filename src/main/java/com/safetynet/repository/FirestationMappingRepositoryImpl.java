package com.safetynet.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.safetynet.entities.endpoints.FirestationMapping;

/**
 * Class in charge of managing the data persistence for the firestation
 * mappings. Data are stored in a HashMap.
 */
@Repository
public class FirestationMappingRepositoryImpl implements IFirestationMappingRepository {

	private static Map<String, FirestationMapping> firestationMappings = new HashMap<>();

	/**
	 * Method adding a firestationMapping in the repository.
	 * 
	 * @param firestationMapping The firestationMapping to add
	 */
	@Override
	public FirestationMapping addFirestationMapping(FirestationMapping firestationMapping) {
		return firestationMappings.put(firestationMapping.getAddress(), firestationMapping);
	}

	/**
	 * Method deleting a firestationMapping from the repository.
	 * 
	 * @param addressFirestationMapping The address of the firestationMapping
	 */
	@Override
	public FirestationMapping deleteFirestationMapping(String addressFirestationMapping) {
		return firestationMappings.remove(addressFirestationMapping);
	}

	/**
	 * Method updating a firestationMapping in the repository.
	 * 
	 * @param firestationMapping The firestationMapping to update
	 */
	@Override
	public FirestationMapping updateFirestationMapping(FirestationMapping firestationMapping) {
		return firestationMappings.put(firestationMapping.getAddress(), firestationMapping);
	}

	/**
	 * Method returning all firestationMappings from the repository.
	 * 
	 * @return The list of all firestationMappings
	 */
	@Override
	public List<FirestationMapping> getAllFirestationMappings() {
		Collection<FirestationMapping> listAllFirestationMappings = firestationMappings.values();
		return new ArrayList<>(listAllFirestationMappings);
	}

	/**
	 * Method returning a firestationMapping from the repository given its address.
	 * 
	 * @param adressFirestationMapping The address of the firestationMapping
	 * 
	 * @return The firestationMapping corresponding to the address or null if there
	 *         is no firestationMapping for the given address
	 */
	@Override
	public FirestationMapping getFirestationMappingByAdress(String adressFirestationMapping) {
		for (Map.Entry<String, FirestationMapping> mapentry : firestationMappings.entrySet()) {
			if (mapentry.getValue().getAddress().equals(adressFirestationMapping)) {
				return mapentry.getValue();
			}
		}
		return null;
	}

	/**
	 * Method returning a firestationMapping the repository given its id.
	 * 
	 * @param idStation The id of the firestationMapping
	 * 
	 * @return The firestationMapping corresponding to the idStation or null if
	 *         there is no firestationMapping for the given idStation
	 */
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
