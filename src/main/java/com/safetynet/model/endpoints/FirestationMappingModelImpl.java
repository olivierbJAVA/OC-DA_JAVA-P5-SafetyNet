package com.safetynet.model.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.dao.IFirestationMappingDao;
import com.safetynet.entities.endpoints.FirestationMapping;

@Service
public class FirestationMappingModelImpl implements IFirestationMappingModel {

	@Autowired
	private IFirestationMappingDao firestationMappingDao;

	public FirestationMappingModelImpl() {
		super();
	}

	public FirestationMappingModelImpl(IFirestationMappingDao firestationMappingDao) {
		super();
		this.firestationMappingDao = firestationMappingDao;
	}

	public IFirestationMappingDao getFirestationMappingDao() {
		return firestationMappingDao;
	}

	public void setFirestationMappingDao(IFirestationMappingDao firestationMappingDao) {
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
	public FirestationMapping getFirestationMappingByFirestationNumber(int firestationNumber) {
		return firestationMappingDao.getFirestationMappingByStationNumber(firestationNumber);
	}

	@Override
	public boolean firestationMappingExist(FirestationMapping firestationMapping) {
		if (firestationMappingDao.getFirestationMappingByAdress(firestationMapping.getAddress()) == null) {
			return false;
		}
		return true;
	}

}
