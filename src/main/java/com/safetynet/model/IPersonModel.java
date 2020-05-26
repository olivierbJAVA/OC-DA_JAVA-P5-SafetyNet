package com.safetynet.model;

import java.util.List;

import com.safetynet.entities.Person;

public interface IPersonModel {
	public Person addPerson(Person person);

	public Person deletePerson(String idPerson);

	public Person updatePerson(Person person);

	public List<Person> getAllPersons();

	public Person getPersonById(String idPerson);

	public boolean personInList(Person person);
}
