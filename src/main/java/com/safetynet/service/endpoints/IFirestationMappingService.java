package com.safetynet.service.endpoints;

import java.util.List;

import com.safetynet.entities.endpoints.FirestationMapping;

/**
 * Interface to implement for managing the services for the firestation mappings
 * endpoint.
 */
public interface IFirestationMappingService {

	/**
	 * Add a firestationMapping.
	 * 
	 * @param firestationMapping The firestationMapping to add
	 */
	public FirestationMapping addFirestationMapping(FirestationMapping firestationMapping);

	/**
	 * Delete a firestationMapping.
	 * 
	 * @param addressFirestation The address of the firestationMapping
	 */
	public FirestationMapping deleteFirestationMapping(String addressFirestation);

	/**
	 * Update a firestationMapping.
	 * 
	 * @param firestationMapping The firestationMapping to update
	 */
	public FirestationMapping updateFirestationMapping(FirestationMapping firestationMapping);

	/**
	 * Return all firestationMappings.
	 * 
	 * @return The list of all firestationMappings
	 */
	public List<FirestationMapping> getAllFirestationMappings();

	/**
	 * Return a firestationMapping given its address.
	 * 
	 * @param adressFirestation The address of the firestationMapping
	 * 
	 * @return The firestationMapping corresponding to the address or null if there
	 *         is no firestationMapping for the given address
	 */
	public FirestationMapping getFirestationMappingByAdress(String adressFirestation);

	/**
	 * Return a firestationMapping given its id.
	 * 
	 * @param idFirestation The id of the firestationMapping
	 * 
	 * @return The firestationMapping corresponding to the id or null if there is no
	 *         firestationMapping for the given id
	 */
	public FirestationMapping getFirestationMappingByIdStation(String idFirestation);

	/**
	 * Indicates if a firestationMapping exist or not.
	 * 
	 * @param firestationMapping The firestationMapping
	 * 
	 * @return true if the firestationMapping exist or false if this is not the case
	 */
	boolean firestationMappingExist(FirestationMapping firestationMapping);

}