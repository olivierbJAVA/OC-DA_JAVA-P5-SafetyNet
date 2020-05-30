package com.safetynet.entities.endpoints;

public class FirestationMapping {

	private String address;
	private int nbStation;

	public FirestationMapping() {
		super();
	}

	public FirestationMapping(String address, int nbStation) {
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

	public int getStation() {
		return nbStation;
	}

	public void setStation(int nbStation) {
		this.nbStation = nbStation;
	}

}
