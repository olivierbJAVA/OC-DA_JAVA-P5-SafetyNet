package com.safetynet.dao;

import java.util.List;

import com.safetynet.entities.Firestation;

public interface IFirestationDao {
	public void addFirestation(Firestation firestation);

	public void deleteFirestation(String idFirestation);

	public void updateFirestation(Firestation firestation);

	public List<Firestation> listFirestations();

	public Firestation getFirestationById(String idFirestation);
}
