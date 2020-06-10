package com.safetynet.entities.urls;

/**
 * Class materializing a child in child alert response.
 */
public class ChildAlertChild {

	private String childFirstName;
	
	private String childLastName;
	
	private long childAge;

	public ChildAlertChild() {
		super();
	}

	public ChildAlertChild(String childFirstName, String childLastName, long childAge) {
		super();
		this.childFirstName = childFirstName;
		this.childLastName = childLastName;
		this.childAge = childAge;
	}

	public String getChildFirstName() {
		return childFirstName;
	}

	public void setChildFirstName(String childFirstName) {
		this.childFirstName = childFirstName;
	}

	public String getChildLastName() {
		return childLastName;
	}

	public void setChildLastName(String childLastName) {
		this.childLastName = childLastName;
	}

	public long getChildAge() {
		return childAge;
	}

	public void setChildAge(long childAge) {
		this.childAge = childAge;
	}

}
