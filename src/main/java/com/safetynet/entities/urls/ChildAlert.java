package com.safetynet.entities.urls;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.safetynet.entities.endpoints.Person;

public class ChildAlert {

	@JsonProperty("ChildsLivingAtThisAddress")
	private List<ChildAlertChild> childAlertChilds;

	@JsonProperty("OtherHouseholdMembers")
	private List<Person> childAlertHouseholdMembers;

	public ChildAlert() {
		super();
	}

	public ChildAlert(List<ChildAlertChild> childAlertChilds, List<Person> childAlertHouseholdMembers) {
		super();
		this.childAlertChilds = childAlertChilds;
		this.childAlertHouseholdMembers = childAlertHouseholdMembers;
	}

	public List<ChildAlertChild> getChildAlertChilds() {
		return childAlertChilds;
	}

	public void setChildAlertChilds(List<ChildAlertChild> childAlertChilds) {
		this.childAlertChilds = childAlertChilds;
	}

	public List<Person> getChildAlertHouseholdMembers() {
		return childAlertHouseholdMembers;
	}

	public void setChildAlertHouseholdMembers(List<Person> childAlertHouseholdMembers) {
		this.childAlertHouseholdMembers = childAlertHouseholdMembers;
	}

}
