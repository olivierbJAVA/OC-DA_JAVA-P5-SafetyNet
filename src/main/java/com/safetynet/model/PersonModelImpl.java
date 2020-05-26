package com.safetynet.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.dao.IPersonDao;
import com.safetynet.entities.Person;

@Component
public class PersonModelImpl implements IPersonModel {

	private static final Logger logger = LoggerFactory.getLogger(PersonModelImpl.class);

	@Autowired
	private IPersonDao personDao;

	public PersonModelImpl() {
		super();
		logger.info("Constructeur PersonModelImpl sans arg");
	}

	public PersonModelImpl(IPersonDao personDao) {
		super();
		logger.info("Constructeur PersonModelImpl avec arg");
		this.personDao = personDao;
	}

	public IPersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(IPersonDao personDao) {
		this.personDao = personDao;
	}

	@Override
	public Person addPerson(Person person) {
		return personDao.addPerson(person);
	}

	@Override
	public Person deletePerson(String idPerson) {
		return personDao.deletePerson(idPerson);
	}

	@Override
	public Person updatePerson(Person person) {
		return personDao.updatePerson(person);
	}

	@Override
	public List<Person> getAllPersons() {
		return personDao.getAllPersons();
	}

	@Override
	public Person getPersonById(String idPerson) {
		return personDao.getPersonById(idPerson);
	}

	@Override
	public boolean personInList(Person person) {
		if (personDao.getPersonById(person.getFirstName() + person.getLastName()) == null) {
			return false;
		}
		return true;
	}
}
