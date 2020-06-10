package com.safetynet.entities.urls;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class materializing a flood response for url response.
 */
public class Flood {

	@JsonProperty("Firestation")
	private Map<String, FloodStation> mapFloodStations = new HashMap<>();

	public Flood() {
		super();
	}

	public Flood(Map<String, FloodStation> mapFloodStations) {
		super();
		this.mapFloodStations = mapFloodStations;
	}

	public Map<String, FloodStation> getMapFloodStations() {
		return mapFloodStations;
	}

	public void setMapFloodStations(Map<String, FloodStation> mapFloodStations) {
		this.mapFloodStations = mapFloodStations;
	}

}
