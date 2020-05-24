package com.safetynet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Firestation {

	@JsonIgnore
	private String idFirestation;
	private String address;
	private String nbStation;
	
	public Firestation() {
		super();
	}
	public Firestation(String idFirestation, String address, String nbStation) {
		super();
		this.idFirestation = idFirestation;
		this.address = address;
		this.nbStation = nbStation;
	}
	public String getIdFirestation() {
		return idFirestation;
	}
	public void setIdFirestation(String idFirestation) {
		this.idFirestation = idFirestation;
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
	public void setStation(String station) {
		this.nbStation = station;
	}

}
