package com.safetynet.entities.urls;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fire {

	@JsonProperty("StationNumberForThisAddress")
	private int nbStation;
	
	@JsonProperty("PersonsLivingAtThisAddress")
	private List<FirePerson> firePersons;

	public Fire() {
		super();
	}

	public Fire(int nbStation, List<FirePerson> firePersons) {
		super();
		this.nbStation = nbStation;
		this.firePersons = firePersons;
	}

	public int getNbStation() {
		return nbStation;
	}

	public void setNbStation(int nbStation) {
		this.nbStation = nbStation;
	}

	public List<FirePerson> getFirePersons() {
		return firePersons;
	}

	public void setFirePersons(List<FirePerson> firePersons) {
		this.firePersons = firePersons;
	}
	
}
