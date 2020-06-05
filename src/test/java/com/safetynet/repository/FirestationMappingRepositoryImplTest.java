package com.safetynet.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.entities.endpoints.Person;
import com.safetynet.repository.FirestationMappingRepositoryImpl;
import com.safetynet.repository.IFirestationMappingRepository;
import com.safetynet.repository.IPersonRepository;
import com.safetynet.repository.PersonRepositoryImpl;

@ExtendWith(SpringExtension.class)
public class FirestationMappingRepositoryImplTest {

	@TestConfiguration
	static class FirestationMappingDaoImplTestContextConfiguration {
		@Bean
		public IFirestationMappingRepository iFirestationsMappingDao() {
			return new FirestationMappingRepositoryImpl();
		}
	}

	@Autowired
	private FirestationMappingRepositoryImpl firestationMappingDaoImplUnderTest;

	@BeforeAll
	private static void setUp() throws Exception {

	}
/*
	@BeforeEach
	private void setUpPerTest() throws Exception {

		List<Person> persons = personDaoImplUnderTest.getAllPersons();
		for (Person person : persons) {
			personDaoImplUnderTest.deletePerson(person.getIdPerson());
		}

	}

	@Test
	public void addPerson() {
		Person personToAdd = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		personDaoImplUnderTest.addPerson(personToAdd);

		Assertions.assertNotNull(personToAdd.getIdPerson());
		Assertions.assertEquals("BertrandSimon",
				personDaoImplUnderTest.getPersonById(personToAdd.getIdPerson()).getIdPerson());

	}

	@Test
	public void deletePerson() {
		Person personToDelete = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		personDaoImplUnderTest.addPerson(personToDelete);

		personDaoImplUnderTest.deletePerson(personToDelete.getIdPerson());

		Assertions.assertNull(personDaoImplUnderTest.getPersonById(personToDelete.getIdPerson()));

	}

	@Test
	public void updatePerson() {
		Person personToUpdate = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		personDaoImplUnderTest.addPerson(personToUpdate);

		Person personUpdated = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Marseille", "75000",
				"0696469887", "bs@email.com");

		personDaoImplUnderTest.updatePerson(personUpdated);

		Assertions.assertNotNull(personUpdated.getIdPerson());
		Assertions.assertEquals("Marseille",
				personDaoImplUnderTest.getPersonById(personToUpdate.getIdPerson()).getCity());

	}

	@Test
	public void getPersonById_whenPersonExist() {

		Person personToGet = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		personDaoImplUnderTest.addPerson(personToGet);

		Person personGet = personDaoImplUnderTest.getPersonById(personToGet.getIdPerson());

		Assertions.assertEquals("BertrandSimon", personGet.getIdPerson());

	}

	@Test
	public void getPersonById_whenPersonNotExist() {

		Assertions.assertNull(personDaoImplUnderTest.getPersonById("BertrandSimon"));

	}

	@Test
	public void getAllPersons() {
		Person person1 = new Person("BertrandSimon1", "Bertrand", "Simon1", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		Person person2 = new Person("BertrandSimon2", "Bertrand", "Simon2", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		Person person3 = new Person("BertrandSimon3", "Bertrand", "Simon3", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		personDaoImplUnderTest.addPerson(person1);
		personDaoImplUnderTest.addPerson(person2);
		personDaoImplUnderTest.addPerson(person3);

		List<Person> listAllPersons = personDaoImplUnderTest.getAllPersons();

		Assertions.assertEquals(3, listAllPersons.size());

	}
*/
}
