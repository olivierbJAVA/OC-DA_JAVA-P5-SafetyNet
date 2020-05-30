package com.safetynet.entities.urls;

public class FirePerson {

	private String firstName;
	private String lastName;
	private String phone;
	private long age;
	private String[] medications;
	private String[] allergies;
	
	public FirePerson() {
		super();
	}
	
	public FirePerson(String firstName, String lastName, String phone, long age, String[] medications,
			String[] allergies) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.age = age;
		this.medications = medications;
		this.allergies = allergies;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public String[] getMedications() {
		return medications;
	}

	public void setMedications(String[] medications) {
		this.medications = medications;
	}

	public String[] getAllergies() {
		return allergies;
	}

	public void setAllergies(String[] allergies) {
		this.allergies = allergies;
	}
	
}
