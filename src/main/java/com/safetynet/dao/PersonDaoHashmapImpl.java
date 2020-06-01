package com.safetynet.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.safetynet.entities.endpoints.Person;

@Repository
public class PersonDaoHashmapImpl implements IPersonDao {

	private static Map<String, Person> persons = new HashMap<>();

	@Override
	public Person addPerson(Person person) {
		return persons.put(person.getIdPerson(), person);
	}

	@Override
	public Person deletePerson(String idPerson) {
		return persons.remove(idPerson);
	}

	@Override
	public Person updatePerson(Person person) {
		return persons.put(person.getIdPerson(), person);
	}

	@Override
	public List<Person> getAllPersons() {
		Collection<Person> listAllPersons = persons.values();
		return new ArrayList<>(listAllPersons);
	}

	@Override
	public Person getPersonById(String idPerson) {
		for (Map.Entry<String, Person> mapentry : persons.entrySet()) {
			if (mapentry.getKey().equals(idPerson)) {
				return mapentry.getValue();
			}
		}
		return null;
	}

}
