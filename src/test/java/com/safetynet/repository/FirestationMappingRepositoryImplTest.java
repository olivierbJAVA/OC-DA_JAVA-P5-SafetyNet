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

import com.safetynet.entities.endpoints.FirestationMapping;

/**
 * Class including unit tests for the FirestationMappingRepositoryImpl Class.
 */
@ExtendWith(SpringExtension.class)
public class FirestationMappingRepositoryImplTest {

	@TestConfiguration
	static class FirestationMappingRepositoryImplTestContextConfiguration {
		@Bean
		public IFirestationMappingRepository iFirestationMappingRepository() {
			return new FirestationMappingRepositoryImpl();
		}
	}

	@Autowired
	private IFirestationMappingRepository firestationMappingRepositoryImplUnderTest;

	@BeforeEach
	private void setUpPerTest() {
		// We clear the firestationMappings list
		List<FirestationMapping> firestationMappings = firestationMappingRepositoryImplUnderTest
				.getAllFirestationMappings();
		for (FirestationMapping firestationMapping : firestationMappings) {
			firestationMappingRepositoryImplUnderTest.deleteFirestationMapping(firestationMapping.getAddress());
		}
	}

	@Test
	public void addFirestationMapping() {
		// ARRANGE
		FirestationMapping firestationMappingToAdd = new FirestationMapping("2 rue de Paris", "5");

		// ACT
		firestationMappingRepositoryImplUnderTest.addFirestationMapping(firestationMappingToAdd);

		// ASSERT
		assertNotNull(firestationMappingRepositoryImplUnderTest
				.getFirestationMappingByAdress(firestationMappingToAdd.getAddress()));
		assertEquals("2 rue de Paris", firestationMappingRepositoryImplUnderTest
				.getFirestationMappingByAdress(firestationMappingToAdd.getAddress()).getAddress());
	}

	@Test
	public void deleteFirestationMapping() {
		// ARRANGE
		FirestationMapping firestationMappingToDelete = new FirestationMapping("2 rue de Paris", "5");

		firestationMappingRepositoryImplUnderTest.addFirestationMapping(firestationMappingToDelete);

		// ACT
		firestationMappingRepositoryImplUnderTest.deleteFirestationMapping(firestationMappingToDelete.getAddress());

		// ASSERT
		assertNull(firestationMappingRepositoryImplUnderTest
				.getFirestationMappingByAdress(firestationMappingToDelete.getAddress()));
	}

	@Test
	public void updateFirestationMapping() {
		// ARRANGE
		FirestationMapping firestationMappingToUpdate = new FirestationMapping("2 rue de Paris", "5");

		firestationMappingRepositoryImplUnderTest.addFirestationMapping(firestationMappingToUpdate);

		FirestationMapping firestationMappingUpdated = new FirestationMapping("2 rue de Paris", "6");

		// ACT
		firestationMappingRepositoryImplUnderTest.updateFirestationMapping(firestationMappingUpdated);

		// ASSERT
		assertEquals("6", firestationMappingRepositoryImplUnderTest
				.getFirestationMappingByAdress(firestationMappingToUpdate.getAddress()).getStation());
	}

	@Test
	public void getFirestationMappingByIdStation_whenIdStationExist() {
		// ARRANGE
		FirestationMapping firestationMappingToGet = new FirestationMapping("2 rue de Paris", "5");

		firestationMappingRepositoryImplUnderTest.addFirestationMapping(firestationMappingToGet);

		// ACT
		FirestationMapping firestationMappingGet = firestationMappingRepositoryImplUnderTest
				.getFirestationMappingByIdStation(firestationMappingToGet.getStation());

		// ASSERT
		assertNotNull(firestationMappingGet);
		assertEquals("5", firestationMappingGet.getStation());
	}

	@Test
	public void getFirestationMappingByIdStation_whenIdStationNotExist() {
		// ACT & ASSERT
		assertNull(firestationMappingRepositoryImplUnderTest.getFirestationMappingByIdStation("IdStationNotExist"));
	}

	@Test
	public void getFirestationMappingByAddress_whenAddressExist() {
		// ARRANGE
		FirestationMapping firestationMappingToGet = new FirestationMapping("2 rue de Paris", "5");

		firestationMappingRepositoryImplUnderTest.addFirestationMapping(firestationMappingToGet);

		// ACT
		FirestationMapping firestationMappingGet = firestationMappingRepositoryImplUnderTest
				.getFirestationMappingByAdress(firestationMappingToGet.getAddress());

		// ASSERT
		assertNotNull(firestationMappingGet);
		assertEquals("2 rue de Paris", firestationMappingGet.getAddress());
	}

	@Test
	public void getFirestationMappingByAddress_whenAddressNotExist() {
		// ACT & ASSERT
		assertNull(firestationMappingRepositoryImplUnderTest.getFirestationMappingByAdress("AddressNotExist"));
	}

	@Test
	public void getAllFirestationMappings() {
		// ARRANGE
		FirestationMapping firestationMapping1 = new FirestationMapping("3 rue de Paris", "1");
		FirestationMapping firestationMapping2 = new FirestationMapping("3 rue de Nantes", "2");
		FirestationMapping firestationMapping3 = new FirestationMapping("3 rue de Marseille", "3");

		firestationMappingRepositoryImplUnderTest.addFirestationMapping(firestationMapping1);
		firestationMappingRepositoryImplUnderTest.addFirestationMapping(firestationMapping2);
		firestationMappingRepositoryImplUnderTest.addFirestationMapping(firestationMapping3);

		// ACT
		List<FirestationMapping> listAllfirestationMappings = firestationMappingRepositoryImplUnderTest
				.getAllFirestationMappings();

		// ASSERT
		assertEquals(3, listAllfirestationMappings.size());
		assertThat(listAllfirestationMappings).containsExactlyInAnyOrder(firestationMapping1, firestationMapping2,
				firestationMapping3);
	}
}
