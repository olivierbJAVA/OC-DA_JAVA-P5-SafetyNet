package com.safetynet.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.entities.endpoints.Person;
import com.safetynet.repository.IPersonRepository;
import com.safetynet.service.endpoints.IPersonService;
import com.safetynet.service.endpoints.PersonServiceImpl;

@ExtendWith(SpringExtension.class)
public class PersonServiceImplTest {

	@TestConfiguration
	static class PersonModelImplTestContextConfiguration {
		@Bean
		public IPersonService iPersonModel() {
			return new PersonServiceImpl();
		}
	}

	@Autowired
	private IPersonService personModelImplUnderTest;

	@MockBean
	private IPersonRepository mockPersonDao;

	@Test
	public void addPerson() {
		Person personToAdd = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonDao.addPerson(personToAdd)).thenReturn(null);

		personModelImplUnderTest.addPerson(personToAdd);

		ArgumentCaptor<Person> argumentCaptorPerson = ArgumentCaptor.forClass(Person.class);
		verify(mockPersonDao, times(1)).addPerson(argumentCaptorPerson.capture());

		Person agrumentPersonCaptured = argumentCaptorPerson.getValue();
		assertEquals(personToAdd, agrumentPersonCaptured);

	}

	@Test
	public void deletePerson() {
		Person personToDelete = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		// personModelImplUnderTest.addPerson(personToDelete);
		when(mockPersonDao.deletePerson(personToDelete.getIdPerson())).thenReturn(personToDelete);

		personModelImplUnderTest.deletePerson(personToDelete.getIdPerson());

		ArgumentCaptor<String> argumentCaptorIdPerson = ArgumentCaptor.forClass(String.class);
		Mockito.verify(mockPersonDao, times(1)).deletePerson(argumentCaptorIdPerson.capture());

		String argumentIdPersonCaptured = argumentCaptorIdPerson.getValue();
		assertEquals(personToDelete.getIdPerson(), argumentIdPersonCaptured);

	}

	@Test
	public void updatePerson() {

		Person personBeforeUpdate = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		Person personToUpdate = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Marseille", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonDao.updatePerson(personToUpdate)).thenReturn(personBeforeUpdate);

		// personModelImplUnderTest.addPerson(personToUpdate);

		personModelImplUnderTest.updatePerson(personToUpdate);

		ArgumentCaptor<Person> argumentCaptorPerson = ArgumentCaptor.forClass(Person.class);
		verify(mockPersonDao, times(1)).updatePerson(argumentCaptorPerson.capture());

		Person agrumentPersonCaptured = argumentCaptorPerson.getValue();
		assertEquals(personToUpdate, agrumentPersonCaptured);

	}

	@Test
	public void getPersonById() {

		Person personToGet = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		// personModelImplUnderTest.addPerson(personToGet);

		when(mockPersonDao.getPersonById(personToGet.getIdPerson())).thenReturn(personToGet);

		personModelImplUnderTest.getPersonById(personToGet.getIdPerson());

		ArgumentCaptor<String> argumentCaptorIdPerson = ArgumentCaptor.forClass(String.class);
		verify(mockPersonDao, times(1)).getPersonById(argumentCaptorIdPerson.capture());

		String argumentIdPersonCaptured = argumentCaptorIdPerson.getValue();
		assertEquals(personToGet.getIdPerson(), argumentIdPersonCaptured);
	}

	@Test
	public void getAllPersons() {

		Person personTest1 = new Person("BertrandSimon", "Bertrand", "Simon", "address1", "Paris", "75000",
				"0696469887", "bs@email.com");
		Person personTest2 = new Person("BertrandSimon", "Bertrand", "Simon", "address2", "Paris", "75000",
				"0696469887", "bs@email.com");
		Person personTest3 = new Person("BertrandSimon", "Bertrand", "Simon", "address3", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTest1);
		allPersons.add(personTest2);
		allPersons.add(personTest3);

		when(mockPersonDao.getAllPersons()).thenReturn(allPersons);

		personModelImplUnderTest.getAllPersons();

		verify(mockPersonDao, times(1)).getAllPersons();
	}

	@Test
	public void personExist_whenPersonExist() {
		Person personTest = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonDao.getPersonById(personTest.getIdPerson())).thenReturn(personTest);

		assertTrue(personModelImplUnderTest.personExist(personTest));
	}

	@Test
	public void personExist_whenPersonNotExist() {
		Person personTest = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonDao.getPersonById(personTest.getIdPerson())).thenReturn(null);

		assertFalse(personModelImplUnderTest.personExist(personTest));
	}

	@Test
	public void idPersonExist_whenPersonExist() {
		Person personTest = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTest);

		when(mockPersonDao.getAllPersons()).thenReturn(allPersons);

		assertTrue(personModelImplUnderTest.idPersonExist(personTest.getIdPerson()));
	}

	@Test
	public void idPersonExist_whenPersonNotExist() {
		Person personTest = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTest);

		when(mockPersonDao.getAllPersons()).thenReturn(allPersons);

		assertFalse(personModelImplUnderTest.idPersonExist("PersonNotExist"));
	}

	@Test
	public void addressExist_whenAddressExist() {
		Person personTest = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTest);

		when(mockPersonDao.getAllPersons()).thenReturn(allPersons);

		assertTrue(personModelImplUnderTest.addressExist(personTest.getAddress()));
	}

	@Test
	public void addressExist_whenAddressNotExist() {
		Person personTest = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTest);

		when(mockPersonDao.getAllPersons()).thenReturn(allPersons);

		assertFalse(personModelImplUnderTest.addressExist("AddressNotExist"));
	}

	@Test
	public void cityExist_whenCityExist() {
		Person personTest = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTest);

		when(mockPersonDao.getAllPersons()).thenReturn(allPersons);

		assertTrue(personModelImplUnderTest.cityExist(personTest.getCity()));
	}

	@Test
	public void cityExist_whenCityNotExist() {

		Person personTest = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTest);

		when(mockPersonDao.getAllPersons()).thenReturn(allPersons);

		assertFalse(personModelImplUnderTest.cityExist("CityNotExist"));
	}

	@Test
	public void getAllAddress() {
		Person personTest1 = new Person("BertrandSimon", "Bertrand", "Simon", "address1", "Paris", "75000",
				"0696469887", "bs@email.com");
		Person personTest2 = new Person("BertrandSimon", "Bertrand", "Simon", "address2", "Paris", "75000",
				"0696469887", "bs@email.com");
		Person personTest3 = new Person("BertrandSimon", "Bertrand", "Simon", "address3", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTest1);
		allPersons.add(personTest2);
		allPersons.add(personTest3);

		when(mockPersonDao.getAllPersons()).thenReturn(allPersons);

		assertThat(personModelImplUnderTest.getAllAddress()).containsExactlyInAnyOrder("address1", "address2",
				"address3");
	}

}
