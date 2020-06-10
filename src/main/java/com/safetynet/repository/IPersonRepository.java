package com.safetynet.repository;

import java.util.List;

import com.safetynet.entities.endpoints.Person;

/**
 * Interface to implement for managing the data persistence for the persons.
 */
public interface IPersonRepository {

	/**
	 * Method adding a person in the repository.
	 * 
	 * @param person The person to add
	 */
	public Person addPerson(Person person);

	/**
	 * Method deleting a person from the repository.
	 * 
	 * @param person The person to delete
	 */
	public Person deletePerson(String idPerson);

	/**
	 * Method updating a person in the repository.
	 * 
	 * @param person The person to update
	 */
	public Person updatePerson(Person person);

	/**
	 * Method returning all persons from the repository.
	 * 
	 * @return The list of all persons
	 */
	public List<Person> getAllPersons();

	/**
	 * Method returning a person from the repository given its id.
	 * 
	 * @param idPerson The id of the person
	 * 
	 * @return The person corresponding to the idPerson or null if there is no
	 *         person for the given idPerson
	 */
	public Person getPersonById(String idPerson);
}
