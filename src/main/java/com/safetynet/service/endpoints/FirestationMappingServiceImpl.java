package com.safetynet.service.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.entities.endpoints.FirestationMapping;
import com.safetynet.repository.IFirestationMappingRepository;

/**
 * Class in charge of managing the services for the firestation mappings
 * endpoint.
 */
@Service
public class FirestationMappingServiceImpl implements IFirestationMappingService {

	@Autowired
	private IFirestationMappingRepository firestationMappingRepository;

	public FirestationMappingServiceImpl() {
		super();
	}

	public FirestationMappingServiceImpl(IFirestationMappingRepository firestationMappingRepository) {
		super();
		this.firestationMappingRepository = firestationMappingRepository;
	}

	public IFirestationMappingRepository getFirestationMappingRepository() {
		return firestationMappingRepository;
	}

	public void setFirestationMappingRepository(IFirestationMappingRepository firestationMappingRepository) {
		this.firestationMappingRepository = firestationMappingRepository;
	}

	/**
	 * Add a firestationMapping.
	 * 
	 * @param firestationMapping The firestationMapping to add
	 */
	@Override
	public FirestationMapping addFirestationMapping(FirestationMapping firestationMapping) {
		return firestationMappingRepository.addFirestationMapping(firestationMapping);
	}

	/**
	 * Delete a firestationMapping.
	 * 
	 * @param firestationAddress The address of the firestationMapping
	 */
	@Override
	public FirestationMapping deleteFirestationMapping(String firestationAddress) {
		return firestationMappingRepository.deleteFirestationMapping(firestationAddress);
	}

	/**
	 * Update a firestationMapping.
	 * 
	 * @param firestationMapping The firestationMapping to update
	 */
	@Override
	public FirestationMapping updateFirestationMapping(FirestationMapping firestationMapping) {
		return firestationMappingRepository.updateFirestationMapping(firestationMapping);
	}

	/**
	 * Return all firestationMappings.
	 * 
	 * @return The list of all firestationMappings
	 */
	@Override
	public List<FirestationMapping> getAllFirestationMappings() {
		return firestationMappingRepository.getAllFirestationMappings();
	}

	/**
	 * Return a firestationMapping given its address.
	 * 
	 * @param firestationAddress The address of the firestationMapping
	 * 
	 * @return The firestationMapping corresponding to the address or null if there
	 *         is no firestationMapping for the given address
	 */
	@Override
	public FirestationMapping getFirestationMappingByAdress(String firestationAddress) {
		return firestationMappingRepository.getFirestationMappingByAdress(firestationAddress);
	}

	/**
	 * Return a firestationMapping given its id.
	 * 
	 * @param idFirestation The id of the firestationMapping
	 * 
	 * @return The firestationMapping corresponding to the id or null if there is no
	 *         firestationMapping for the given id
	 */
	@Override
	public FirestationMapping getFirestationMappingByIdStation(String idFirestation) {
		return firestationMappingRepository.getFirestationMappingByIdStation(idFirestation);
	}

	/**
	 * Indicates if a firestationMapping exist or not.
	 * 
	 * @param firestationMapping The firestationMapping
	 * 
	 * @return true if the firestationMapping exist or false if this is not the case
	 */
	@Override
	public boolean firestationMappingExist(FirestationMapping firestationMapping) {
		if (firestationMappingRepository.getFirestationMappingByAdress(firestationMapping.getAddress()) == null) {
			return false;
		}
		return true;
	}

}
