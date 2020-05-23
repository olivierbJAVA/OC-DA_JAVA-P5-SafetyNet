package com.safetynet.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.dao.IPersonDao;
import com.safetynet.entities.Person;

@Component
public class PersonModelImpl implements IPersonModel {

	@Autowired
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
