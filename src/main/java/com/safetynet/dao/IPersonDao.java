package com.safetynet.dao;

import java.util.List;

import com.safetynet.entities.Person;

public interface IPersonDao {
	public Person addPerson(Person person);

	public void deletePerson(String idPerson);

	public void updatePerson(Person person);

	public List<Person> getAllPersons();

	public Person getPersonById(String idPerson);

}
