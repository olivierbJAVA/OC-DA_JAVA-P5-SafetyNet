package com.safetynet.model;

import java.util.List;

import com.safetynet.entities.Firestation;

public interface IFirestationModel {
	public void addFirestation(Firestation firestation);

	public void deleteFirestation(String idFirestation);

	public void updateFirestation(Firestation firestation);

	public List<Firestation> listFirestation();
}
