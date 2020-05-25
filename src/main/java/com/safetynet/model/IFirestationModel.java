package com.safetynet.model;

import java.util.List;

import com.safetynet.entities.Firestation;

public interface IFirestationModel {
	public Firestation addFirestation(Firestation firestation);

	public Firestation deleteFirestation(String idFirestation);

	public Firestation updateFirestation(Firestation firestation);

	public List<Firestation> getAllFirestations();

	public Firestation getFirestationById(String idFirestation);
}