package com.safetynet.entities.urls;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class materializing a fire response for url response.
 */
public class Fire {

	@JsonProperty("StationIdForThisAddress")
	private String idStation;

	@JsonProperty("PersonsLivingAtThisAddress")
	private List<FirePerson> firePersons;

	public Fire() {
		super();
	}

	public Fire(String idStation, List<FirePerson> firePersons) {
		super();
		this.idStation = idStation;
		this.firePersons = firePersons;
	}

	public String getIdStation() {
		return idStation;
	}

	public void setIdStation(String idStation) {
		this.idStation = idStation;
	}

	public List<FirePerson> getFirePersons() {
		return firePersons;
	}

	public void setFirePersons(List<FirePerson> firePersons) {
		this.firePersons = firePersons;
	}
}
