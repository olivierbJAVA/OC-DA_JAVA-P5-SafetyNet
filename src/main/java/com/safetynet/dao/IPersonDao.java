package com.safetynet.dao;

import java.util.List;

import com.safetynet.entities.endpoints.Person;

public interface IPersonDao {
	public Person addPerson(Person person);

	public Person deletePerson(String idPerson);

	public Person updatePerson(Person person);

	public List<Person> getAllPersons();

	public Person getPersonById(String idPerson);

	//boolean addressExist(String address);

}
