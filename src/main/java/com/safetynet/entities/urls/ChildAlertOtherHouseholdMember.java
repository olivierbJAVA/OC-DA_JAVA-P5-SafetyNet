package com.safetynet.entities.urls;

public class ChildAlertOtherHouseholdMember {

	private String householdMemberFirstName;

	private String householdMemberLastName;

	public ChildAlertOtherHouseholdMember() {
		super();
	}

	public ChildAlertOtherHouseholdMember(String householdMemberFirstName, String householdMemberLastName) {
		super();
		this.householdMemberFirstName = householdMemberFirstName;
		this.householdMemberLastName = householdMemberLastName;
	}

	public String getHouseholdMemberFirstName() {
		return householdMemberFirstName;
	}

	public void setHouseholdMemberFirstName(String householdMemberFirstName) {
		this.householdMemberFirstName = householdMemberFirstName;
	}

	public String getHouseholdMemberLastName() {
		return householdMemberLastName;
	}

	public void setHouseholdMemberLastName(String householdMemberLastName) {
		this.householdMemberLastName = householdMemberLastName;
	}

}
