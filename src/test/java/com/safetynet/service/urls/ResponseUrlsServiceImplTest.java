package com.safetynet.service.urls;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.config.InputDataConfig;
import com.safetynet.config.UrlsResponseConfig;
import com.safetynet.entities.urls.Firestation;
import com.safetynet.repository.FirestationMappingRepositoryImpl;
import com.safetynet.repository.IFirestationMappingRepository;
import com.safetynet.repository.IMedicalRecordRepository;
import com.safetynet.repository.IPersonRepository;
import com.safetynet.repository.MedicalRecordRepositoryImpl;
import com.safetynet.repository.PersonRepositoryImpl;
import com.safetynet.service.endpoints.FirestationMappingServiceImpl;
import com.safetynet.service.endpoints.IFirestationMappingService;
import com.safetynet.service.endpoints.IMedicalRecordService;
import com.safetynet.service.endpoints.IPersonService;
import com.safetynet.service.endpoints.MedicalRecordServiceImpl;
import com.safetynet.service.endpoints.PersonServiceImpl;
import com.safetynet.util.IInitializeLists;
import com.safetynet.util.JsonFileInitializeListsImpl;

@ExtendWith(SpringExtension.class)
public class ResponseUrlsServiceImplTest {

	private static UrlsResponseConfig urlsResponseConfig = new UrlsResponseConfig();

	private static InputDataConfig inputDataConfig = new InputDataConfig();

	@TestConfiguration
	static class ResponseUrlsServiceImplTestContextConfiguration {
		@Bean
		public IResponseUrlsService iResponseUrlsService() {
			return new ResponseUrlsServiceImpl();
		}

		@Bean
		public IFirestationMappingService iFirestationMappingService() {
			return new FirestationMappingServiceImpl();
		}

		@Bean
		public IFirestationMappingRepository iFirestationMappingRepository() {
			return new FirestationMappingRepositoryImpl();
		}

		@Bean
		public IMedicalRecordService iMedicalRecordService() {
			return new MedicalRecordServiceImpl();
		}

		@Bean
		public IMedicalRecordRepository iMedicalRecordRepository() {
			return new MedicalRecordRepositoryImpl();
		}

		@Bean
		public IPersonService iPersonService() {
			return new PersonServiceImpl();
		}

		@Bean
		public IPersonRepository iPersonRepository() {
			return new PersonRepositoryImpl();
		}

		@Bean
		public IInitializeLists iInitializeLists() {
			return new JsonFileInitializeListsImpl();
		}

	}

	@Autowired
	private IResponseUrlsService responseUrlsServiceImplUnderTest;

	// @MockBean
	// private IPersonService mockPersonService;

	@Test
	public void responseFirestation() throws JsonProcessingException {

		// when(mockPersonService.getAllPersons()).thenReturn(mockListAllPersons);

		Firestation expectedFirestationResponse = urlsResponseConfig.getUrlFirestationResponse();

		Firestation actualFirestationResponse = responseUrlsServiceImplUnderTest.responseFirestation("2");

		// assertEquals(firestationUrlResponse, responseFirestation);

		ObjectMapper objectMapper = new ObjectMapper();

		assertEquals(objectMapper.writeValueAsString(expectedFirestationResponse),
				objectMapper.writeValueAsString(actualFirestationResponse));

	}

}
