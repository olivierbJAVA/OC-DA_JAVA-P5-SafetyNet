package com.safetynet.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.entities.Person;
import com.safetynet.util.IInputReader;
import com.safetynet.util.JsonFileInputReaderImpl;

@Component
public class PersonDaoHashmapImpl implements IPersonDao {

	private static Map<String, Person> persons = new HashMap<>();
	
	/*
	//initialisation : possibilité 2 (méthode init appelée après la construction du bean)
	@PostConstruct
	public void init() {
		//OB : @Autowired ne fonctionne pas ici
		//@Autowired
		//IInputReader inputReader;
		
		IInputReader jsonFileInputReaderImpl = new JsonFileInputReaderImpl();
		jsonFileInputReaderImpl.readIntitialListPersons();
	}
	*/
	/*
	//initialisation : possibilité 3 (static)
	static {
			//OB : @Autowired ne fonctionne pas ici
			//@Autowired
			//IInputReader inputReader;
			
			IInputReader jsonFileInputReaderImpl = new JsonFileInputReaderImpl();
			jsonFileInputReaderImpl.readIntitialListPersons();
	}
	*/
	
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
