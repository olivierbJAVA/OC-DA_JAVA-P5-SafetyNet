package com.safetynet.service.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.entities.endpoints.FirestationMapping;
import com.safetynet.repository.IFirestationMappingRepository;

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

	@Override
	public FirestationMapping addFirestationMapping(FirestationMapping firestationMapping) {
		return firestationMappingRepository.addFirestationMapping(firestationMapping);
	}

	@Override
	public FirestationMapping deleteFirestationMapping(String firestationAddress) {
		return firestationMappingRepository.deleteFirestationMapping(firestationAddress);
	}

	@Override
	public FirestationMapping updateFirestationMapping(FirestationMapping firestationMapping) {
		return firestationMappingRepository.updateFirestationMapping(firestationMapping);
	}

	@Override
	public List<FirestationMapping> getAllFirestationMappings() {
		return firestationMappingRepository.getAllFirestationMappings();
	}

	@Override
	public FirestationMapping getFirestationMappingByAdress(String firestationAddress) {
		return firestationMappingRepository.getFirestationMappingByAdress(firestationAddress);
	}

	@Override
	public FirestationMapping getFirestationMappingByIdStation(String idFirestation) {
		return firestationMappingRepository.getFirestationMappingByIdStation(idFirestation);
	}

	@Override
	public boolean firestationMappingExist(FirestationMapping firestationMapping) {
		if (firestationMappingRepository.getFirestationMappingByAdress(firestationMapping.getAddress()) == null) {
			return false;
		}
		return true;
	}

}
