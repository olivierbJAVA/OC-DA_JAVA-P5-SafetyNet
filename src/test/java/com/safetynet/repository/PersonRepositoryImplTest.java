package com.safetynet.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.entities.endpoints.Person;
import com.safetynet.repository.IPersonRepository;
import com.safetynet.repository.PersonRepositoryImpl;

@ExtendWith(SpringExtension.class)
public class PersonRepositoryImplTest {

	@TestConfiguration
	static class PersonRepositoryImplTestContextConfiguration {
		@Bean
		public IPersonRepository iPersonRepository() {
			return new PersonRepositoryImpl();
		}
	}

	@Autowired
	private IPersonRepository personRepositoryImplUnderTest;

	@BeforeEach
	private void setUpPerTest() {

		List<Person> persons = personRepositoryImplUnderTest.getAllPersons();
		for (Person person : persons) {
			personRepositoryImplUnderTest.deletePerson(person.getIdPerson());
		}

	}

	@Test
	public void addPerson() {
		// ARRANGE
		Person personToAdd = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		// ACT
		personRepositoryImplUnderTest.addPerson(personToAdd);

		// ASSERT
		assertNotNull(personRepositoryImplUnderTest.getPersonById(personToAdd.getIdPerson()));
		assertEquals("BertrandSimon", personRepositoryImplUnderTest.getPersonById(personToAdd.getIdPerson()).getIdPerson());
	}

	@Test
	public void deletePerson() {
		// ARRANGE
		Person personToDelete = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		personRepositoryImplUnderTest.addPerson(personToDelete);

		// ACT
		personRepositoryImplUnderTest.deletePerson(personToDelete.getIdPerson());

		// ASSERT
		assertNull(personRepositoryImplUnderTest.getPersonById(personToDelete.getIdPerson()));
	}

	@Test
	public void updatePerson() {
		// ARRANGE
		Person personToUpdate = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		personRepositoryImplUnderTest.addPerson(personToUpdate);
		
		Person personUpdated = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Marseille", "75000",
				"0696469887", "bs@email.com");

		// ACT
		personRepositoryImplUnderTest.updatePerson(personUpdated);

		// ASSERT
		assertEquals("Marseille", personRepositoryImplUnderTest.getPersonById(personToUpdate.getIdPerson()).getCity());
	}

	@Test
	public void getPersonById_whenPersonExist() {
		// ARRANGE
		Person personToGet = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		personRepositoryImplUnderTest.addPerson(personToGet);

		// ACT
		Person personGet = personRepositoryImplUnderTest.getPersonById(personToGet.getIdPerson());

		// ASSERT
		assertNotNull(personGet);
		assertEquals("BertrandSimon", personGet.getIdPerson());
	}

	@Test
	public void getPersonById_whenPersonNotExist() {
		// ASSERT
		assertNull(personRepositoryImplUnderTest.getPersonById("PersonNotExist"));
	}

	@Test
	public void getAllPersons() {
		// ARRANGE
		Person person1 = new Person("BertrandSimon1", "Bertrand", "Simon1", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		Person person2 = new Person("BertrandSimon2", "Bertrand", "Simon2", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		Person person3 = new Person("BertrandSimon3", "Bertrand", "Simon3", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		personRepositoryImplUnderTest.addPerson(person1);
		personRepositoryImplUnderTest.addPerson(person2);
		personRepositoryImplUnderTest.addPerson(person3);

		// ACT
		List<Person> listAllPersons = personRepositoryImplUnderTest.getAllPersons();

		// ASSERT
		assertEquals(3, listAllPersons.size());
		assertThat(listAllPersons).containsExactlyInAnyOrder(person1, person2, person3);
	}
}
