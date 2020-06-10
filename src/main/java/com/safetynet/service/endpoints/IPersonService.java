package com.safetynet.service.endpoints;

import java.util.List;
import java.util.Set;

import com.safetynet.entities.endpoints.Person;

/**
 * Interface to implement for managing the services for the persons endpoint.
 */
public interface IPersonService {

	/**
	 * Add a person.
	 * 
	 * @param person The person to add
	 */
	public Person addPerson(Person person);

	/**
	 * Delete a person.
	 * 
	 * @param idPerson The id of the person
	 */
	public Person deletePerson(String idPerson);

	/**
	 * Update a person.
	 * 
	 * @param person The person to update
	 */
	public Person updatePerson(Person person);

	/**
	 * Return all persons.
	 * 
	 * @return The list of all persons
	 */
	public List<Person> getAllPersons();

	/**
	 * Return a person given its id.
	 * 
	 * @param idPerson The id of the person
	 * 
	 * @return The person corresponding to the id or null if there is no person for
	 *         the given id
	 */
	public Person getPersonById(String idPerson);

	/**
	 * Indicates if a person exist or not.
	 * 
	 * @param person The person
	 * 
	 * @return true if the person exist or false if this is not the case
	 */
	public boolean personExist(Person person);

	/**
	 * Indicates if a person exist or not, given its id.
	 * 
	 * @param idPerson The id of the person
	 * 
	 * @return true if the person exist or false if this is not the case
	 */
	public boolean idPersonExist(String idPerson);

	/**
	 * Indicates if there exist a person with the given address.
	 * 
	 * @param address The address
	 * 
	 * @return true if there exist a person with the given address or false if this
	 *         is not the case
	 */
	public boolean addressExist(String address);

	/**
	 * Indicates if there exist a person with the given city.
	 * 
	 * @param city The city
	 * 
	 * @return true if there exist a person with the given city or false if this is
	 *         not the case
	 */
	public boolean cityExist(String city);

	/**
	 * Return all the address of all persons.
	 * 
	 * @return The list the address of all persons
	 */
	public Set<String> getAllAddress();
}
