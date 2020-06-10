package com.safetynet.service.endpoints;

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

import com.safetynet.entities.endpoints.FirestationMapping;
import com.safetynet.repository.IFirestationMappingRepository;
import com.safetynet.service.endpoints.FirestationMappingServiceImpl;
import com.safetynet.service.endpoints.IFirestationMappingService;

/**
 * Class including unit tests for the FirestationMappingServiceImpl Class.
 */
@ExtendWith(SpringExtension.class)
public class FirestationMappingServiceImplTest {

	@TestConfiguration
	static class FirestationMappingServiceImplTestContextConfiguration {
		@Bean
		public IFirestationMappingService iFirestationMappingService() {
			return new FirestationMappingServiceImpl();
		}
	}

	@Autowired
	private IFirestationMappingService firestationMappingServiceImplUnderTest;

	@MockBean
	private IFirestationMappingRepository mockFirestationMappingRepository;

	@Test
	public void addFirestationMapping() {
		// ARRANGE
		FirestationMapping firestationMappingToAdd = new FirestationMapping("2 rue de Paris", "5");

		when(mockFirestationMappingRepository.addFirestationMapping(firestationMappingToAdd)).thenReturn(null);

		// ACT
		firestationMappingServiceImplUnderTest.addFirestationMapping(firestationMappingToAdd);

		// ASSERT
		// We check that the correct method has been called with correct argument
		ArgumentCaptor<FirestationMapping> argumentCaptorFirestationMapping = ArgumentCaptor
				.forClass(FirestationMapping.class);
		verify(mockFirestationMappingRepository, times(1))
				.addFirestationMapping(argumentCaptorFirestationMapping.capture());

		FirestationMapping agrumentFirestationMappingCaptured = argumentCaptorFirestationMapping.getValue();
		assertEquals(firestationMappingToAdd, agrumentFirestationMappingCaptured);

	}

	@Test
	public void deleteFirestationMapping() {
		// ARRANGE
		FirestationMapping firestationMappingToDelete = new FirestationMapping("2 rue de Paris", "5");

		when(mockFirestationMappingRepository.deleteFirestationMapping(firestationMappingToDelete.getAddress()))
				.thenReturn(firestationMappingToDelete);

		// ACT
		firestationMappingServiceImplUnderTest.deleteFirestationMapping(firestationMappingToDelete.getAddress());

		// ASSERT
		// We check that the correct method has been called with correct argument
		ArgumentCaptor<String> argumentCaptorAddress = ArgumentCaptor.forClass(String.class);
		Mockito.verify(mockFirestationMappingRepository, times(1))
				.deleteFirestationMapping(argumentCaptorAddress.capture());

		String argumentAddressCaptured = argumentCaptorAddress.getValue();
		assertEquals(firestationMappingToDelete.getAddress(), argumentAddressCaptured);

	}

	@Test
	public void updateFirestationMapping() {
		// ARRANGE
		FirestationMapping firestationMappingToUpdate = new FirestationMapping("2 rue de Paris", "5");

		FirestationMapping firestationMappingUpdated = new FirestationMapping("2 rue de Paris", "6");

		when(mockFirestationMappingRepository.updateFirestationMapping(firestationMappingUpdated))
				.thenReturn(firestationMappingToUpdate);

		// ACT
		firestationMappingServiceImplUnderTest.updateFirestationMapping(firestationMappingUpdated);

		// ASSERT
		// We check that the correct method has been called with correct argument
		ArgumentCaptor<FirestationMapping> argumentCaptorFirestationMapping = ArgumentCaptor
				.forClass(FirestationMapping.class);
		verify(mockFirestationMappingRepository, times(1))
				.updateFirestationMapping(argumentCaptorFirestationMapping.capture());

		FirestationMapping agrumentFirestationMappingCaptured = argumentCaptorFirestationMapping.getValue();
		assertEquals(firestationMappingUpdated, agrumentFirestationMappingCaptured);
	}

	@Test
	public void getFirestationMappingById() {
		// ARRANGE
		FirestationMapping firestationMappingToGet = new FirestationMapping("2 rue de Paris", "5");

		when(mockFirestationMappingRepository.getFirestationMappingByIdStation(firestationMappingToGet.getStation()))
				.thenReturn(firestationMappingToGet);

		// ACT
		firestationMappingServiceImplUnderTest.getFirestationMappingByIdStation(firestationMappingToGet.getStation());

		// ASSERT
		// We check that the correct method has been called with correct argument
		ArgumentCaptor<String> argumentCaptorIdStation = ArgumentCaptor.forClass(String.class);
		verify(mockFirestationMappingRepository, times(1))
				.getFirestationMappingByIdStation(argumentCaptorIdStation.capture());

		String argumentIdStationCaptured = argumentCaptorIdStation.getValue();
		assertEquals(firestationMappingToGet.getStation(), argumentIdStationCaptured);
	}

	@Test
	public void getFirestationMappingByAddress() {
		// ARRANGE
		FirestationMapping firestationMappingToGet = new FirestationMapping("2 rue de Paris", "5");

		when(mockFirestationMappingRepository.getFirestationMappingByAdress((firestationMappingToGet.getAddress())))
				.thenReturn(firestationMappingToGet);

		// ACT
		firestationMappingServiceImplUnderTest.getFirestationMappingByAdress(firestationMappingToGet.getAddress());

		// ASSERT
		// We check that the correct method has been called with correct argument
		ArgumentCaptor<String> argumentCaptorAddressStation = ArgumentCaptor.forClass(String.class);
		verify(mockFirestationMappingRepository, times(1))
				.getFirestationMappingByAdress(argumentCaptorAddressStation.capture());

		String argumentAddressCaptured = argumentCaptorAddressStation.getValue();
		assertEquals(firestationMappingToGet.getAddress(), argumentAddressCaptured);
	}

	@Test
	public void getAllFirestationMappings() {
		// ARRANGE
		FirestationMapping firestationMapping1 = new FirestationMapping("3 rue de Paris", "1");
		FirestationMapping firestationMapping2 = new FirestationMapping("3 rue de Nantes", "2");
		FirestationMapping firestationMapping3 = new FirestationMapping("3 rue de Marseille", "3");

		List<FirestationMapping> allFirestationMappings = new ArrayList<>();
		allFirestationMappings.add(firestationMapping1);
		allFirestationMappings.add(firestationMapping2);
		allFirestationMappings.add(firestationMapping3);

		when(mockFirestationMappingRepository.getAllFirestationMappings()).thenReturn(allFirestationMappings);

		// ACT
		firestationMappingServiceImplUnderTest.getAllFirestationMappings();

		// ASSERT
		verify(mockFirestationMappingRepository, times(1)).getAllFirestationMappings();
	}

	@Test
	public void firestationMappingExist_whenFirestationMappingExist() {
		// ARRANGE
		FirestationMapping firestationMappingTestExist = new FirestationMapping("2 rue de Paris", "5");

		when(mockFirestationMappingRepository.getFirestationMappingByAdress(firestationMappingTestExist.getAddress()))
				.thenReturn(firestationMappingTestExist);

		// ACT & ASSERT
		assertTrue(firestationMappingServiceImplUnderTest.firestationMappingExist(firestationMappingTestExist));
	}

	@Test
	public void firestationMappingExist_whenFirestationMappingNotExist() {
		// ARRANGE
		FirestationMapping firestationMappingTestNotExist = new FirestationMapping("2 rue de Paris", "5");

		when(mockFirestationMappingRepository
				.getFirestationMappingByAdress(firestationMappingTestNotExist.getAddress())).thenReturn(null);

		// ACT & ASSERT
		assertFalse(firestationMappingServiceImplUnderTest.firestationMappingExist(firestationMappingTestNotExist));
	}

}
