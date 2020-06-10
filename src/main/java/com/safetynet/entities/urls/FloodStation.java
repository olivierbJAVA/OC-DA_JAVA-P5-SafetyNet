package com.safetynet.entities.urls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class materializing a flood station in flood response.
 */
public class FloodStation {

	@JsonProperty("PersonsCoverdedByTheFirestationOrderedByAddress")
	private Map<String, List<FloodPerson>> mapFloodPersons = new HashMap<>();

	public FloodStation() {
		super();
	}

	public FloodStation(Map<String, List<FloodPerson>> mapFloodPersons) {
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
