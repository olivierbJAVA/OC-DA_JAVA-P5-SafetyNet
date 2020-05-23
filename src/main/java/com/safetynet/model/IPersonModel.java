package com.safetynet.model;

import java.util.List;

import com.safetynet.entities.Person;

public interface IPersonModel {
	public void addPerson(Person firestation);

	public void deletePerson(String idPerson);

	public void updatePerson(Person person);

	public List<Person> listPerson();
}
