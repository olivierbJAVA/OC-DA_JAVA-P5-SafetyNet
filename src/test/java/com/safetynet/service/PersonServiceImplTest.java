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
		// ARRANGE
		Person personToAdd = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonDao.addPerson(personToAdd)).thenReturn(null);

		// ACT
		personModelImplUnderTest.addPerson(personToAdd);

		// ASSERT
		ArgumentCaptor<Person> argumentCaptorPerson = ArgumentCaptor.forClass(Person.class);
		verify(mockPersonDao, times(1)).addPerson(argumentCaptorPerson.capture());

		Person agrumentPersonCaptured = argumentCaptorPerson.getValue();
		assertEquals(personToAdd, agrumentPersonCaptured);

	}

	@Test
	public void deletePerson() {
		// ARRANGE
		Person personToDelete = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonDao.deletePerson(personToDelete.getIdPerson())).thenReturn(personToDelete);

		// ACT
		personModelImplUnderTest.deletePerson(personToDelete.getIdPerson());

		// ASSERT
		ArgumentCaptor<String> argumentCaptorIdPerson = ArgumentCaptor.forClass(String.class);
		Mockito.verify(mockPersonDao, times(1)).deletePerson(argumentCaptorIdPerson.capture());

		String argumentIdPersonCaptured = argumentCaptorIdPerson.getValue();
		assertEquals(personToDelete.getIdPerson(), argumentIdPersonCaptured);

	}

	@Test
	public void updatePerson() {
		// ARRANGE
		Person personToUpdate = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		Person personUpdated = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Marseille", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonDao.updatePerson(personUpdated)).thenReturn(personToUpdate);

		// ACT
		personModelImplUnderTest.updatePerson(personUpdated);

		// ASSERT
		ArgumentCaptor<Person> argumentCaptorPerson = ArgumentCaptor.forClass(Person.class);
		verify(mockPersonDao, times(1)).updatePerson(argumentCaptorPerson.capture());

		Person agrumentPersonCaptured = argumentCaptorPerson.getValue();
		assertEquals(personUpdated, agrumentPersonCaptured);
	}

	@Test
	public void getPersonById() {
		// ARRANGE
		Person personToGet = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonDao.getPersonById(personToGet.getIdPerson())).thenReturn(personToGet);

		// ACT
		personModelImplUnderTest.getPersonById(personToGet.getIdPerson());

		// ASSERT
		ArgumentCaptor<String> argumentCaptorIdPerson = ArgumentCaptor.forClass(String.class);
		verify(mockPersonDao, times(1)).getPersonById(argumentCaptorIdPerson.capture());

		String argumentIdPersonCaptured = argumentCaptorIdPerson.getValue();
		assertEquals(personToGet.getIdPerson(), argumentIdPersonCaptured);
	}

	@Test
	public void getAllPersons() {
		// ARRANGE
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

		// ACT
		personModelImplUnderTest.getAllPersons();

		// ASSERT
		verify(mockPersonDao, times(1)).getAllPersons();
	}

	@Test
	public void personExist_whenPersonExist() {
		// ARRANGE
		Person personTestExist = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonDao.getPersonById(personTestExist.getIdPerson())).thenReturn(personTestExist);

		// ACT & ASSERT
		assertTrue(personModelImplUnderTest.personExist(personTestExist));
	}

	@Test
	public void personExist_whenPersonNotExist() {
		// ARRANGE
		Person personTestNotExist = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonDao.getPersonById(personTestNotExist.getIdPerson())).thenReturn(null);

		// ACT & ASSERT
		assertFalse(personModelImplUnderTest.personExist(personTestNotExist));
	}

	@Test
	public void idPersonExist_whenPersonExist() {
		// ARRANGE
		Person personTestExist = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTestExist);

		when(mockPersonDao.getAllPersons()).thenReturn(allPersons);

		// ACT & ASSERT
		assertTrue(personModelImplUnderTest.idPersonExist(personTestExist.getIdPerson()));
	}

	@Test
	public void idPersonExist_whenPersonNotExist() {
		// ARRANGE
		Person personTestExist = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTestExist);

		when(mockPersonDao.getAllPersons()).thenReturn(allPersons);

		// ACT & ASSERT
		assertFalse(personModelImplUnderTest.idPersonExist("PersonNotExist"));
	}

	@Test
	public void addressExist_whenAddressExist() {
		// ARRANGE
		Person personTestExist = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTestExist);

		when(mockPersonDao.getAllPersons()).thenReturn(allPersons);

		// ACT & ASSERT
		assertTrue(personModelImplUnderTest.addressExist(personTestExist.getAddress()));
	}

	@Test
	public void addressExist_whenAddressNotExist() {
		// ARRANGE
		Person personTest = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTest);

		when(mockPersonDao.getAllPersons()).thenReturn(allPersons);

		// ACT & ASSERT
		assertFalse(personModelImplUnderTest.addressExist("AddressNotExist"));
	}

	@Test
	public void cityExist_whenCityExist() {
		// ARRANGE
		Person personTestExist = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTestExist);

		when(mockPersonDao.getAllPersons()).thenReturn(allPersons);

		// ACT & ASSERT
		assertTrue(personModelImplUnderTest.cityExist(personTestExist.getCity()));
	}

	@Test
	public void cityExist_whenCityNotExist() {
		// ARRANGE
		Person personTest = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTest);

		when(mockPersonDao.getAllPersons()).thenReturn(allPersons);

		// ACT & ASSERT
		assertFalse(personModelImplUnderTest.cityExist("CityNotExist"));
	}

	@Test
	public void getAllAddress() {
		// ARRANGE
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

		// ACT & ASSERT
		assertThat(personModelImplUnderTest.getAllAddress()).containsExactlyInAnyOrder("address1", "address2",
				"address3");
	}

}
