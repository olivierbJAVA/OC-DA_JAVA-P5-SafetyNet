package com.safetynet.repository;

import java.util.List;

import com.safetynet.entities.endpoints.Person;

public interface IPersonRepository {
	public Person addPerson(Person person);

	public Person deletePerson(String idPerson);

	public Person updatePerson(Person person);

	public List<Person> getAllPersons();

	public Person getPersonById(String idPerson);
}
