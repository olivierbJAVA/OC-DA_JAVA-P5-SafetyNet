package com.safetynet.repository;

import java.util.List;

import com.safetynet.entities.endpoints.FirestationMapping;

/**
 * Interface to implement for managing the data persistence for the firestation
 * mappings.
 */
public interface IFirestationMappingRepository {

	/**
	 * Method adding a firestationMapping in the repository.
	 * 
	 * @param firestationMapping The firestationMapping to add
	 */
	public FirestationMapping addFirestationMapping(FirestationMapping firestation);

	/**
	 * Method deleting a firestationMapping from the repository.
	 * 
	 * @param addressFirestationMapping The address of the firestationMapping
	 */
	public FirestationMapping deleteFirestationMapping(String addressFirestation);

	/**
	 * Method updating a firestationMapping in the repository.
	 * 
	 * @param firestationMapping The firestationMapping to update
	 */
	public FirestationMapping updateFirestationMapping(FirestationMapping firestation);

	/**
	 * Method returning all firestationMappings from the repository.
	 * 
	 * @return The list of all firestationMappings
	 */
	public List<FirestationMapping> getAllFirestationMappings();

	/**
	 * Method returning a firestationMapping from the repository given its address.
	 * 
	 * @param adressFirestationMapping The address of the firestationMapping
	 * 
	 * @return The firestationMapping corresponding to the address or null if there
	 *         is no firestationMapping for the given address
	 */
	public FirestationMapping getFirestationMappingByAdress(String adressFirestation);

	/**
	 * Method returning a firestationMapping from the repository given its id.
	 * 
	 * @param idStation The id of the firestationMapping
	 * 
	 * @return The firestationMapping corresponding to the idStation or null if
	 *         there is no firestationMapping for the given idStation
	 */
	public FirestationMapping getFirestationMappingByIdStation(String idFirestation);
}