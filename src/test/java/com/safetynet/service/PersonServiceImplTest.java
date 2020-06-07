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
	static class PersonServiceImplTestContextConfiguration {
		@Bean
		public IPersonService iPersonModel() {
			return new PersonServiceImpl();
		}
	}

	@Autowired
	private IPersonService personServiceImplUnderTest;

	@MockBean
	private IPersonRepository mockPersonRepository;

	@Test
	public void addPerson() {
		// ARRANGE
		Person personToAdd = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonRepository.addPerson(personToAdd)).thenReturn(null);

		// ACT
		personServiceImplUnderTest.addPerson(personToAdd);

		// ASSERT
		ArgumentCaptor<Person> argumentCaptorPerson = ArgumentCaptor.forClass(Person.class);
		verify(mockPersonRepository, times(1)).addPerson(argumentCaptorPerson.capture());

		Person agrumentPersonCaptured = argumentCaptorPerson.getValue();
		assertEquals(personToAdd, agrumentPersonCaptured);

	}

	@Test
	public void deletePerson() {
		// ARRANGE
		Person personToDelete = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonRepository.deletePerson(personToDelete.getIdPerson())).thenReturn(personToDelete);

		// ACT
		personServiceImplUnderTest.deletePerson(personToDelete.getIdPerson());

		// ASSERT
		ArgumentCaptor<String> argumentCaptorIdPerson = ArgumentCaptor.forClass(String.class);
		Mockito.verify(mockPersonRepository, times(1)).deletePerson(argumentCaptorIdPerson.capture());

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

		when(mockPersonRepository.updatePerson(personUpdated)).thenReturn(personToUpdate);

		// ACT
		personServiceImplUnderTest.updatePerson(personUpdated);

		// ASSERT
		ArgumentCaptor<Person> argumentCaptorPerson = ArgumentCaptor.forClass(Person.class);
		verify(mockPersonRepository, times(1)).updatePerson(argumentCaptorPerson.capture());

		Person agrumentPersonCaptured = argumentCaptorPerson.getValue();
		assertEquals(personUpdated, agrumentPersonCaptured);
	}

	@Test
	public void getPersonById() {
		// ARRANGE
		Person personToGet = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonRepository.getPersonById(personToGet.getIdPerson())).thenReturn(personToGet);

		// ACT
		personServiceImplUnderTest.getPersonById(personToGet.getIdPerson());

		// ASSERT
		ArgumentCaptor<String> argumentCaptorIdPerson = ArgumentCaptor.forClass(String.class);
		verify(mockPersonRepository, times(1)).getPersonById(argumentCaptorIdPerson.capture());

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

		when(mockPersonRepository.getAllPersons()).thenReturn(allPersons);

		// ACT
		personServiceImplUnderTest.getAllPersons();

		// ASSERT
		verify(mockPersonRepository, times(1)).getAllPersons();
	}

	@Test
	public void personExist_whenPersonExist() {
		// ARRANGE
		Person personTestExist = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonRepository.getPersonById(personTestExist.getIdPerson())).thenReturn(personTestExist);

		// ACT & ASSERT
		assertTrue(personServiceImplUnderTest.personExist(personTestExist));
	}

	@Test
	public void personExist_whenPersonNotExist() {
		// ARRANGE
		Person personTestNotExist = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonRepository.getPersonById(personTestNotExist.getIdPerson())).thenReturn(null);

		// ACT & ASSERT
		assertFalse(personServiceImplUnderTest.personExist(personTestNotExist));
	}

	@Test
	public void idPersonExist_whenPersonExist() {
		// ARRANGE
		Person personTestExist = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTestExist);

		when(mockPersonRepository.getAllPersons()).thenReturn(allPersons);

		// ACT & ASSERT
		assertTrue(personServiceImplUnderTest.idPersonExist(personTestExist.getIdPerson()));
	}

	@Test
	public void idPersonExist_whenPersonNotExist() {
		// ARRANGE
		Person personTestExist = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTestExist);

		when(mockPersonRepository.getAllPersons()).thenReturn(allPersons);

		// ACT & ASSERT
		assertFalse(personServiceImplUnderTest.idPersonExist("PersonNotExist"));
	}

	@Test
	public void addressExist_whenAddressExist() {
		// ARRANGE
		Person personTestExist = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTestExist);

		when(mockPersonRepository.getAllPersons()).thenReturn(allPersons);

		// ACT & ASSERT
		assertTrue(personServiceImplUnderTest.addressExist(personTestExist.getAddress()));
	}

	@Test
	public void addressExist_whenAddressNotExist() {
		// ARRANGE
		Person personTest = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTest);

		when(mockPersonRepository.getAllPersons()).thenReturn(allPersons);

		// ACT & ASSERT
		assertFalse(personServiceImplUnderTest.addressExist("AddressNotExist"));
	}

	@Test
	public void cityExist_whenCityExist() {
		// ARRANGE
		Person personTestExist = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTestExist);

		when(mockPersonRepository.getAllPersons()).thenReturn(allPersons);

		// ACT & ASSERT
		assertTrue(personServiceImplUnderTest.cityExist(personTestExist.getCity()));
	}

	@Test
	public void cityExist_whenCityNotExist() {
		// ARRANGE
		Person personTest = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTest);

		when(mockPersonRepository.getAllPersons()).thenReturn(allPersons);

		// ACT & ASSERT
		assertFalse(personServiceImplUnderTest.cityExist("CityNotExist"));
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

		when(mockPersonRepository.getAllPersons()).thenReturn(allPersons);

		// ACT & ASSERT
		assertThat(personServiceImplUnderTest.getAllAddress()).containsExactlyInAnyOrder("address1", "address2",
				"address3");
	}

}
