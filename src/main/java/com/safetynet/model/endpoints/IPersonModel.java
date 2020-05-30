package com.safetynet.model.endpoints;

import java.util.List;

import com.safetynet.entities.endpoints.Person;

public interface IPersonModel {
	public Person addPerson(Person person);

	public Person deletePerson(String idPerson);

	public Person updatePerson(Person person);

	public List<Person> getAllPersons();

	public Person getPersonById(String idPerson);

	public boolean personExist(Person person);

	public boolean addressExist(String address);

	public boolean cityExist(String city);
}
