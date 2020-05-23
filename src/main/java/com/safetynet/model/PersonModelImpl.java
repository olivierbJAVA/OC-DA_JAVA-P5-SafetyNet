package com.safetynet.model;

import java.util.List;

import com.safetynet.dao.IPersonDao;
import com.safetynet.entities.Person;

public class PersonModelImpl implements IPersonModel {

	private IPersonDao personDao;
	
	public PersonModelImpl() {
		super();
	}
	
	public PersonModelImpl(IPersonDao personDao) {
		super();
		this.personDao = personDao;
	}

	public IPersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(IPersonDao personDao) {
		this.personDao = personDao;
	}

	@Override
	public void addPerson(Person person) {
		personDao.addPerson(person);
	}

	@Override
	public void deletePerson(String idPerson) {
		personDao.deletePerson(idPerson);
	}

	@Override
	public void updatePerson(Person person) {
		personDao.updatePerson(person);
	}

	@Override
	public List<Person> listPerson() {
		return personDao.listPersons();
	}
}
