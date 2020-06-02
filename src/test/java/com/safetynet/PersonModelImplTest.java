package com.safetynet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.dao.IPersonDao;
import com.safetynet.entities.endpoints.Person;
import com.safetynet.model.endpoints.IPersonModel;
import com.safetynet.model.endpoints.PersonModelImpl;

@ExtendWith(SpringExtension.class)
public class PersonModelImplTest {
	
	@TestConfiguration
	static class PersonModelImplTestContextConfiguration {
		@Bean
		public IPersonModel iPersonModel() {
			return new PersonModelImpl();
		}
	}

	@Autowired
	private IPersonModel personModelImplUnderTest;

	@MockBean
	private IPersonDao personDao;
	
	@BeforeAll
	private static void setUp() throws Exception {

	}

	@BeforeEach
	private void setUpPerTest() throws Exception {
/*
		List<Person> persons = personDaoImplUnderTest.getAllPersons();
		for (Person person : persons) {
			personDaoImplUnderTest.deletePerson(person.getIdPerson());
		}
*/
	}

	@Test
	public void addPerson() {
		Person personToAdd = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		//Mockito.when(personDao.addPerson(personToAdd)).thenReturn(null);
		
		personModelImplUnderTest.addPerson(personToAdd);
	
		ArgumentCaptor<Person> argumentCaptorPerson = ArgumentCaptor.forClass(Person.class);
		Mockito.verify(personDao,times(1)).addPerson(argumentCaptorPerson.capture());
		
		Person agrumentPersonCaptured = argumentCaptorPerson.getValue();
		assertEquals(personToAdd, agrumentPersonCaptured);	
	
	}

	@Test
	public void deletePerson() {
		Person personToDelete = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		//personModelImplUnderTest.addPerson(personToDelete);

		personModelImplUnderTest.deletePerson(personToDelete.getIdPerson());

		ArgumentCaptor<String> argumentCaptorIdPerson = ArgumentCaptor.forClass(String.class);
		Mockito.verify(personDao,times(1)).deletePerson(argumentCaptorIdPerson.capture());
		
		String argumentIdPersonCaptured = argumentCaptorIdPerson.getValue();
		assertEquals(personToDelete.getIdPerson(), argumentIdPersonCaptured);	
		
	}
	
	@Test
	public void updatePerson() {
		/*
		Person personToUpdate = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		personModelImplUnderTest.addPerson(personToUpdate);
		*/
		
		Person personToUpdate = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Marseille", "75000",
				"0696469887", "bs@email.com");

		personModelImplUnderTest.updatePerson(personToUpdate);

		ArgumentCaptor<Person> argumentCaptorPerson = ArgumentCaptor.forClass(Person.class);
		Mockito.verify(personDao,times(1)).updatePerson(argumentCaptorPerson.capture());
		
		Person agrumentPersonCaptured = argumentCaptorPerson.getValue();
		assertEquals(personToUpdate, agrumentPersonCaptured);	

	}
	
	@Test
	public void getPersonById() {

		Person personToGet = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		//personModelImplUnderTest.addPerson(personToGet);

		personModelImplUnderTest.getPersonById(personToGet.getIdPerson());

		ArgumentCaptor<String> argumentCaptorIdPerson = ArgumentCaptor.forClass(String.class);
		Mockito.verify(personDao,times(1)).getPersonById(argumentCaptorIdPerson.capture());
		
		String argumentIdPersonCaptured = argumentCaptorIdPerson.getValue();
		assertEquals(personToGet.getIdPerson(), argumentIdPersonCaptured);
	}
	
	@Test
	public void getAllPersons() {

		personModelImplUnderTest.getAllPersons();

		Mockito.verify(personDao,times(1)).getAllPersons();
	}
	
	@Test
	public void personExist_whenPersonExist(){
		Person personTest = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		Mockito.when(personDao.getPersonById(personTest.getIdPerson())).thenReturn(personTest);

		assertTrue(personModelImplUnderTest.personExist(personTest));
	}	
	
	@Test
	public void personExist_whenPersonNotExist(){
		Person personTest = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		Mockito.when(personDao.getPersonById(personTest.getIdPerson())).thenReturn(null);

		assertFalse(personModelImplUnderTest.personExist(personTest));
	}
	
	@Test
	public void idPersonExist_whenPersonExist(){
		Person personTest = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTest);
		
		Mockito.when(personDao.getAllPersons()).thenReturn(allPersons);

		assertTrue(personModelImplUnderTest.idPersonExist(personTest.getIdPerson()));
	}	
	
	@Test
	public void idPersonExist_whenPersonNotExist(){
		Person personTest = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTest);
		
		Mockito.when(personDao.getAllPersons()).thenReturn(allPersons);

		assertFalse(personModelImplUnderTest.idPersonExist("PersonNotExist"));
	}	
	
	@Test
	public void addressExist_whenAddressExist(){
		Person personTest = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTest);
		
		Mockito.when(personDao.getAllPersons()).thenReturn(allPersons);

		assertTrue(personModelImplUnderTest.addressExist(personTest.getAddress()));
	}

	@Test
	public void addressExist_whenAddressNotExist(){
		Person personTest = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTest);
		
		Mockito.when(personDao.getAllPersons()).thenReturn(allPersons);

		assertFalse(personModelImplUnderTest.addressExist("AddressNotExist"));
	}
	
	@Test
	public void cityExist_whenCityExist(){
		Person personTest = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTest);
		
		Mockito.when(personDao.getAllPersons()).thenReturn(allPersons);

		assertTrue(personModelImplUnderTest.cityExist(personTest.getCity()));
	}

	@Test
	public void cityExist_whenCityNotExist(){
		Person personTest = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTest);
		
		Mockito.when(personDao.getAllPersons()).thenReturn(allPersons);

		assertFalse(personModelImplUnderTest.cityExist("CityNotExist"));
	}
	
	@Test
	public void getAllAddress(){
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
		
		Mockito.when(personDao.getAllPersons()).thenReturn(allPersons);

		List<String> listAddressExpected = new ArrayList<>();
		listAddressExpected.add("address1");
		listAddressExpected.add("address2");
		listAddressExpected.add("address3");
		
		assertThat(personModelImplUnderTest.getAllAddress()).contains("address1", "address2", "address3");
	}
	
}
