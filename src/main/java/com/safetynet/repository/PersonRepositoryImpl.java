package com.safetynet.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.safetynet.entities.endpoints.Person;

/**
 * Class in charge of managing the data persistence for the persons. Data are
 * stored in a HashMap.
 */
@Repository
public class PersonRepositoryImpl implements IPersonRepository {

	private static Map<String, Person> persons = new HashMap<>();

	/**
	 * Method adding a person in the repository.
	 * 
	 * @param person The person to add
	 */
	@Override
	public Person addPerson(Person person) {
		return persons.put(person.getIdPerson(), person);
	}

	/**
	 * Method deleting a person from the repository.
	 * 
	 * @param person The person to delete
	 */
	@Override
	public Person deletePerson(String idPerson) {
		return persons.remove(idPerson);
	}

	/**
	 * Method updating a person in the repository.
	 * 
	 * @param person The person to update
	 */
	@Override
	public Person updatePerson(Person person) {
		return persons.put(person.getIdPerson(), person);
	}

	/**
	 * Method returning all persons from the repository.
	 * 
	 * @return The list of all persons
	 */
	@Override
	public List<Person> getAllPersons() {
		Collection<Person> listAllPersons = persons.values();
		return new ArrayList<>(listAllPersons);
	}

	/**
	 * Method returning a person from the repository given its id.
	 * 
	 * @param idPerson The id of the person
	 * 
	 * @return The person corresponding to the idPerson or null if there is no
	 *         person for the given idPerson
	 */
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
