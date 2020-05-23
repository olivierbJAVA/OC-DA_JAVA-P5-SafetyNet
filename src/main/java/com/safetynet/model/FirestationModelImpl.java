package com.safetynet.model;

import java.util.List;

import com.safetynet.dao.IFirestationDao;
import com.safetynet.entities.Firestation;

public class FirestationModelImpl implements IFirestationModel {

	private IFirestationDao firestationDao;

	public FirestationModelImpl() {
		super();
	}

	public FirestationModelImpl(IFirestationDao firestationDao) {
		super();
		this.firestationDao = firestationDao;
	}

	public IFirestationDao getFirestationDao() {
		return firestationDao;
	}

	public void setFirestationDao(IFirestationDao firestationDao) {
		this.firestationDao = firestationDao;
	}

	@Override
	public void addFirestation(Firestation firestation) {
		firestationDao.addFirestation(firestation);
	}

	@Override
	public void deleteFirestation(String idFirestation) {
		firestationDao.deleteFirestation(idFirestation);
	}

	@Override
	public void updateFirestation(Firestation firestation) {
		firestationDao.updateFirestation(firestation);
	}

	@Override
	public List<Firestation> listFirestation() {
		return firestationDao.listFirestations();
	}
}
