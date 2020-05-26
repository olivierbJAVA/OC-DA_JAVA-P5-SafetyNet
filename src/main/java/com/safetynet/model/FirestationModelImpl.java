package com.safetynet.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.dao.IFirestationDao;
import com.safetynet.entities.Firestation;

@Component
public class FirestationModelImpl implements IFirestationModel {

	@Autowired
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
	public Firestation addFirestation(Firestation firestation) {
		return firestationDao.addFirestation(firestation);
	}

	@Override
	public Firestation deleteFirestation(String idFirestation) {
		return firestationDao.deleteFirestation(idFirestation);
	}

	@Override
	public Firestation updateFirestation(Firestation firestation) {
		return firestationDao.updateFirestation(firestation);
	}

	@Override
	public List<Firestation> getAllFirestations() {
		return firestationDao.getAllFirestations();
	}

	@Override
	public Firestation getFirestationById(String idFirestation) {
		return firestationDao.getFirestationById(idFirestation);
	}

	@Override
	public Firestation getFirestationByAdress(String adressFirestation) {
		return firestationDao.getFirestationByAdress(adressFirestation);
	}

}
