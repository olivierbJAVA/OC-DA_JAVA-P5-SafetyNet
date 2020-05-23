package com.safetynet.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.safetynet.entities.Person;

@Component
public class PersonDaoHashmapImpl implements IPersonDao {

	private Map<String, Person> persons = new HashMap<>();

	@Override
	public void addPerson(Person person) {
		persons.put(person.getIdPerson(), person);
	}

	@Override
	public void deletePerson(String idPerson) {
		persons.remove(idPerson);
	}

	@Override
	public void updatePerson(Person person) {
		persons.put(person.getIdPerson(), person);
	}

	@Override
	public List<Person> listPersons() {
		Collection<Person> listAllPersons = persons.values();
		return new ArrayList<>(listAllPersons);
	}

}
