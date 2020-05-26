package com.safetynet.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.dao.IFirestationMappingDao;
import com.safetynet.entities.FirestationMapping;

@Component
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
	public FirestationMapping deleteFirestationMapping(String addressFirestation) {
		return firestationMappingDao.deleteFirestationMapping(addressFirestation);
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
	public FirestationMapping getFirestationMappingByAdress(String adressFirestation) {
		return firestationMappingDao.getFirestationMappingByAdress(adressFirestation);
	}

	@Override
	public boolean firestationMappingInList(FirestationMapping firestationMapping) {
		if (firestationMappingDao.getFirestationMappingByAdress(firestationMapping.getAddress()) == null) {
			return false;
		}
		return true;
	}

}
