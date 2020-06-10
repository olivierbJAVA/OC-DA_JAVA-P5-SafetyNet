package com.safetynet.service.endpoints;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.entities.endpoints.Person;
import com.safetynet.repository.IPersonRepository;

/**
 * Class in charge of managing the services for the persons endpoint.
 */
@Service
public class PersonServiceImpl implements IPersonService {

	@Autowired
	private IPersonRepository personRepository;

	public PersonServiceImpl() {
		super();
	}

	public PersonServiceImpl(IPersonRepository personRepository) {
		super();
		this.personRepository = personRepository;
	}

	public IPersonRepository getPersonRepository() {
		return personRepository;
	}

	public void setPersonRepository(IPersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	/**
	 * Add a person.
	 * 
	 * @param person The person to add
	 */
	@Override
	public Person addPerson(Person person) {
		return personRepository.addPerson(person);
	}

	/**
	 * Delete a person.
	 * 
	 * @param idPerson The id of the person
	 */
	@Override
	public Person deletePerson(String idPerson) {
		return personRepository.deletePerson(idPerson);
	}

	/**
	 * Update a person.
	 * 
	 * @param person The person to update
	 */
	@Override
	public Person updatePerson(Person person) {
		return personRepository.updatePerson(person);
	}

	/**
	 * Return all persons.
	 * 
	 * @return The list of all persons
	 */
	@Override
	public List<Person> getAllPersons() {
		return personRepository.getAllPersons();
	}

	/**
	 * Return a person given its id.
	 * 
	 * @param idPerson The id of the person
	 * 
	 * @return The person corresponding to the id or null if there is no person for
	 *         the given id
	 */
	@Override
	public Person getPersonById(String idPerson) {
		return personRepository.getPersonById(idPerson);
	}

	/**
	 * Indicates if a person exist or not.
	 * 
	 * @param person The person
	 * 
	 * @return true if the person exist or false if this is not the case
	 */
	@Override
	public boolean personExist(Person person) {
		if (personRepository.getPersonById(person.getFirstName() + person.getLastName()) == null) {
			return false;
		}
		return true;
	}

	/**
	 * Indicates if a person exist or not, given its id.
	 * 
	 * @param idPerson The id of the person
	 * 
	 * @return true if the person exist or false if this is not the case
	 */
	@Override
	public boolean idPersonExist(String idPerson) {
		List<Person> persons = personRepository.getAllPersons();
		for (Person person : persons) {
			if (person.getIdPerson().equals(idPerson)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Indicates if there exist a person with the given address.
	 * 
	 * @param address The address
	 * 
	 * @return true if there exist a person with the given address or false if this
	 *         is not the case
	 */
	@Override
	public boolean addressExist(String address) {
		List<Person> persons = personRepository.getAllPersons();
		for (Person person : persons) {
			if (person.getAddress().equals(address)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Indicates if there exist a person with the given city.
	 * 
	 * @param city The city
	 * 
	 * @return true if there exist a person with the given city or false if this is
	 *         not the case
	 */
	@Override
	public boolean cityExist(String city) {
		List<Person> persons = personRepository.getAllPersons();
		for (Person person : persons) {
			if (person.getCity().equals(city)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return all the address of all persons.
	 * 
	 * @return The list the address of all persons
	 */
	@Override
	public Set<String> getAllAddress() {
		Set<String> address = new HashSet<>();
		List<Person> persons = personRepository.getAllPersons();
		for (Person person : persons) {
			address.add(person.getAddress());
		}
		return address;
	}

}
