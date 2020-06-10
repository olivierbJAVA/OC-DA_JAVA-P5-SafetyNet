package com.safetynet.entities.endpoints;

/**
 * Class materializing a medical record.
 */
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MedicalRecord {

	@JsonIgnore
	private String idMedicalRecord;
	private String firstName;
	private String lastName;
	private String birthdate;
	private String[] medications;
	private String[] allergies;

	public MedicalRecord() {
		super();
	}

	public MedicalRecord(String idMedicalRecord, String firstName, String lastName, String birthdate,
			String[] medications, String[] allergies) {
		super();
		this.idMedicalRecord = idMedicalRecord;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.medications = medications;
		this.allergies = allergies;
	}

	public String getIdMedicalRecord() {
		return idMedicalRecord;
	}

	public void setIdMedicalRecord(String idMedicalRecord) {
		this.idMedicalRecord = idMedicalRecord;
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

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(allergies);
		result = prime * result + ((birthdate == null) ? 0 : birthdate.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((idMedicalRecord == null) ? 0 : idMedicalRecord.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + Arrays.hashCode(medications);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicalRecord other = (MedicalRecord) obj;
		if (!Arrays.equals(allergies, other.allergies))
			return false;
		if (birthdate == null) {
			if (other.birthdate != null)
				return false;
		} else if (!birthdate.equals(other.birthdate))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (idMedicalRecord == null) {
			if (other.idMedicalRecord != null)
				return false;
		} else if (!idMedicalRecord.equals(other.idMedicalRecord))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (!Arrays.equals(medications, other.medications))
			return false;
		return true;
	}

}
