package com.safetynet.service.endpoints;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.entities.endpoints.Person;
import com.safetynet.repository.IPersonRepository;

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

	@Override
	public Person addPerson(Person person) {
		return personRepository.addPerson(person);
	}

	@Override
	public Person deletePerson(String idPerson) {
		return personRepository.deletePerson(idPerson);
	}

	@Override
	public Person updatePerson(Person person) {
		return personRepository.updatePerson(person);
	}

	@Override
	public List<Person> getAllPersons() {
		return personRepository.getAllPersons();
	}

	@Override
	public Person getPersonById(String idPerson) {
		return personRepository.getPersonById(idPerson);
	}

	@Override
	public boolean personExist(Person person) {
		if (personRepository.getPersonById(person.getFirstName() + person.getLastName()) == null) {
			return false;
		}
		return true;
	}

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
