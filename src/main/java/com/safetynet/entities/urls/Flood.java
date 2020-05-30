package com.safetynet.entities.urls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Flood {

	@JsonProperty("PersonsCoverdedByThisFirestationOrderedByAddress")
	private Map<String, List<FloodPerson>> mapFloodPersons = new HashMap<>();

	public Flood() {
		super();
	}

	public Flood(Map<String, List<FloodPerson>> mapFloodPersons) {
		super();
		this.mapFloodPersons = mapFloodPersons;
	}

	public Map<String, List<FloodPerson>> getMapFloodPersons() {
		return mapFloodPersons;
	}

	public void setMapFloodPersons(Map<String, List<FloodPerson>> mapFloodPersons) {
		this.mapFloodPersons = mapFloodPersons;
	}
			
}
