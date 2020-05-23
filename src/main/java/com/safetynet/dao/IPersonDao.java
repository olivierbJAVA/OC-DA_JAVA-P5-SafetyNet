package com.safetynet.dao;

import java.util.List;

import com.safetynet.entities.Person;

public interface IPersonDao {
	public void addPerson(Person person);

	public void deletePerson(String idPerson);

	public void updatePerson(Person person);

	public List<Person> listPersons();

}
