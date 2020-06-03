package com.safetynet.entities.endpoints;

public class FirestationMapping {

	private String address;
	private String station;

	public FirestationMapping() {
		super();
	}

	public FirestationMapping(String address, String station) {
		super();
		this.address = address;
		this.station = station;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

}

