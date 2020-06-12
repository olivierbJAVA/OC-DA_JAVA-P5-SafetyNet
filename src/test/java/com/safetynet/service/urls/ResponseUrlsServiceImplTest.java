package com.safetynet.service.urls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.config.UrlsResponseConfig;
import com.safetynet.entities.urls.ChildAlert;
import com.safetynet.entities.urls.Fire;
import com.safetynet.entities.urls.Firestation;
import com.safetynet.entities.urls.Flood;
import com.safetynet.entities.urls.PersonInfo;
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

/**
 * Class including unit tests for the ResponseUrlsServiceImpl Class.
 */
@ExtendWith(SpringExtension.class)
public class ResponseUrlsServiceImplTest {

	private static final Logger logger = LoggerFactory.getLogger(ResponseUrlsServiceImplTest.class);

	// We use a dedicated input data file for tests purposes
	private static String filePathInputDataForTests = "./data-test.json";

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
			return new JsonFileInitializeListsImpl(filePathInputDataForTests);
		}

	}

	@Autowired
	private IInitializeLists iInitializeLists;
	
	@Autowired
	private IResponseUrlsService responseUrlsServiceImplUnderTest;

	private UrlsResponseConfig urlsResponseConfig;

	private ObjectMapper objectMapper;
/*
	@BeforeAll
	private static void setUp() {
	
	}
*/	
	@BeforeEach
	private void setUpPerTest() {
		urlsResponseConfig = new UrlsResponseConfig();
		objectMapper = new ObjectMapper();
		//iInitializeLists.getInitialData();
	}

	@Test
	public void responseFirestation() {
		// ARRANGE
		Firestation expectedFirestationResponse = urlsResponseConfig.getUrlFirestationResponse();

		//ResponseUrlsServiceImpl responseUrlsServiceImplUnderTes = new ResponseUrlsServiceImpl();
		
		// ACT
				
		Firestation actualFirestationResponse = responseUrlsServiceImplUnderTest.responseFirestation("2");

		// ASSERT
		try {
			assertEquals(objectMapper.writeValueAsString(expectedFirestationResponse),
					objectMapper.writeValueAsString(actualFirestationResponse));
		} catch (JsonProcessingException e) {
			logger.error("Exception in JsonProcessing", e);
		}
	}

	@Test
	public void responseChildAlert_whenChildAlertNotEmpty() {
		// ARRANGE
		ChildAlert expectedChildAlertResponse = urlsResponseConfig.getUrlChildAlertResponse();

		// ACT
		ChildAlert actualChildAlertResponse = responseUrlsServiceImplUnderTest.responseChildAlert("1509 Culver St");

		// ASSERT
		try {
			assertEquals(objectMapper.writeValueAsString(expectedChildAlertResponse),
					objectMapper.writeValueAsString(actualChildAlertResponse));
		} catch (JsonProcessingException e) {
			logger.error("Exception in JsonProcessing", e);
		}
	}

	@Test
	public void responseChildAlert_whenChildAlertEmpty() {
		// ACT
		ChildAlert actualChildAlertResponse = responseUrlsServiceImplUnderTest.responseChildAlert("29 15th St");

		// ASSERT
		assertNull(actualChildAlertResponse);
	}

	@Test
	public void responsePhoneAlert() {
		// ARRANGE
		Set<String> expectedPhoneAlertResponse = urlsResponseConfig.getUrlPhoneAlertResponse();

		// ACT
		Set<String> actualPhoneAlertResponse = responseUrlsServiceImplUnderTest.responsePhoneAlert("1");

		// ASSERT
		try {
			assertEquals(objectMapper.writeValueAsString(expectedPhoneAlertResponse),
					objectMapper.writeValueAsString(actualPhoneAlertResponse));
		} catch (JsonProcessingException e) {
			logger.error("Exception in JsonProcessing", e);
		}
	}

	@Test
	public void responseFire() {
		// ARRANGE
		Fire expectedFireResponse = urlsResponseConfig.getUrlFireResponse();

		// ACT
		Fire actualFireResponse = responseUrlsServiceImplUnderTest.responseFire("29 15th St");

		// ASSERT
		try {
			assertEquals(objectMapper.writeValueAsString(expectedFireResponse),
					objectMapper.writeValueAsString(actualFireResponse));
		} catch (JsonProcessingException e) {
			logger.error("Exception in JsonProcessing", e);
		}
	}

	@Test
	public void responseFlood() {
		// ARRANGE
		Flood expectedFloodResponse = urlsResponseConfig.getUrlFloodResponse();

		// ACT
		Flood actualFloodResponse = responseUrlsServiceImplUnderTest.responseFlood(new String[] { "4" });

		// ASSERT
		try {
			assertEquals(objectMapper.writeValueAsString(expectedFloodResponse),
					objectMapper.writeValueAsString(actualFloodResponse));
		} catch (JsonProcessingException e) {
			logger.error("Exception in JsonProcessing", e);
		}
	}

	@Test
	public void responsePersonInfo() {
		// ARRANGE
		PersonInfo expectedPersonInfoResponse = urlsResponseConfig.getUrlPersonInfoResponse();

		// ACT
		PersonInfo actualPersonInfoResponse = responseUrlsServiceImplUnderTest.responsePersonInfo("Lily", "Cooper");

		// ASSERT
		try {
			assertEquals(objectMapper.writeValueAsString(expectedPersonInfoResponse),
					objectMapper.writeValueAsString(actualPersonInfoResponse));
		} catch (JsonProcessingException e) {
			logger.error("Exception in JsonProcessing", e);
		}
	}

	@Test
	public void responseCommunityEmail() {
		// ARRANGE
		Set<String> expectedCommunityEmailResponse = urlsResponseConfig.getUrlCommunityEmailResponse();

		// ACT
		Set<String> actualCommunityEmailtResponse = responseUrlsServiceImplUnderTest.responseCommunityEmail("Culver");

		// ASSERT
		try {
			assertEquals(objectMapper.writeValueAsString(expectedCommunityEmailResponse),
					objectMapper.writeValueAsString(actualCommunityEmailtResponse));
		} catch (JsonProcessingException e) {
			logger.error("Exception in JsonProcessing", e);
		}
	}
}