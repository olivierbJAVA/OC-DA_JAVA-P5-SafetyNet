package com.safetynet.entities;

public class FirestationMapping {

	private String address;
	private String nbStation;

	public FirestationMapping() {
		super();
	}

	public FirestationMapping(String address, String nbStation) {
		super();
		this.address = address;
		this.nbStation = nbStation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStation() {
		return nbStation;
	}

	public void setStation(String nbStation) {
		this.nbStation = nbStation;
	}

}
