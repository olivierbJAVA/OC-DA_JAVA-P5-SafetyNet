package com.safetynet.service.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.entities.endpoints.FirestationMapping;
import com.safetynet.repository.IFirestationMappingRepository;

@Service
public class FirestationMappingServiceImpl implements IFirestationMappingService {

	@Autowired
	private IFirestationMappingRepository firestationMappingDao;

	public FirestationMappingServiceImpl() {
		super();
	}

	public FirestationMappingServiceImpl(IFirestationMappingRepository firestationMappingDao) {
		super();
		this.firestationMappingDao = firestationMappingDao;
	}

	public IFirestationMappingRepository getFirestationMappingDao() {
		return firestationMappingDao;
	}

	public void setFirestationMappingDao(IFirestationMappingRepository firestationMappingDao) {
		this.firestationMappingDao = firestationMappingDao;
	}

	@Override
	public FirestationMapping addFirestationMapping(FirestationMapping firestationMapping) {
		return firestationMappingDao.addFirestationMapping(firestationMapping);
	}

	@Override
	public FirestationMapping deleteFirestationMapping(String firestationAddress) {
		return firestationMappingDao.deleteFirestationMapping(firestationAddress);
	}

	@Override
	public FirestationMapping updateFirestationMapping(FirestationMapping firestationMapping) {
		return firestationMappingDao.updateFirestationMapping(firestationMapping);
	}

	@Override
	public List<FirestationMapping> getAllFirestationMappings() {
		return firestationMappingDao.getAllFirestationMappings();
	}

	@Override
	public FirestationMapping getFirestationMappingByAdress(String firestationAddress) {
		return firestationMappingDao.getFirestationMappingByAdress(firestationAddress);
	}

	@Override
	public FirestationMapping getFirestationMappingByIdStation(String idFirestation) {
		return firestationMappingDao.getFirestationMappingByIdStation(idFirestation);
	}

	@Override
	public boolean firestationMappingExist(FirestationMapping firestationMapping) {
		if (firestationMappingDao.getFirestationMappingByAdress(firestationMapping.getAddress()) == null) {
			return false;
		}
		return true;
	}

}
