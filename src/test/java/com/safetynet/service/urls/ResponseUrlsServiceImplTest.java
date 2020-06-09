package com.safetynet.service.urls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

@ExtendWith(SpringExtension.class)
public class ResponseUrlsServiceImplTest {

	// private static InputDataConfig inputDataConfig = new InputDataConfig();

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

	private UrlsResponseConfig urlsResponseConfig;

	private ObjectMapper objectMapper;

	@BeforeEach
	private void setUpPerTest() {
		urlsResponseConfig = new UrlsResponseConfig();
		objectMapper = new ObjectMapper();
	}

	@Test
	public void responseFirestation() throws JsonProcessingException {
		// ARRANGE
		// when(mockPersonService.getAllPersons()).thenReturn(mockListAllPersons);
		Firestation expectedFirestationResponse = urlsResponseConfig.getUrlFirestationResponse();

		// ACT
		Firestation actualFirestationResponse = responseUrlsServiceImplUnderTest.responseFirestation("2");

		// ASSERT
		assertEquals(objectMapper.writeValueAsString(expectedFirestationResponse),
				objectMapper.writeValueAsString(actualFirestationResponse));

		// assertEquals(expectedFirestationResponse, actualFirestationResponse);
	}

	@Test
	public void responseChildAlert_whenChildAlertNotEmpty() throws JsonProcessingException {
		// ARRANGE
		ChildAlert expectedChildAlertResponse = urlsResponseConfig.getUrlChildAlertResponse();

		// ACT
		ChildAlert actualChildAlertResponse = responseUrlsServiceImplUnderTest.responseChildAlert("1509 Culver St");

		// ASSERT
		assertEquals(objectMapper.writeValueAsString(expectedChildAlertResponse),
				objectMapper.writeValueAsString(actualChildAlertResponse));
	}

	@Test
	public void responseChildAlert_whenChildAlertEmpty() throws JsonProcessingException {
		// ACT
		ChildAlert actualChildAlertResponse = responseUrlsServiceImplUnderTest.responseChildAlert("29 15th St");

		// ASSERT
		assertNull(actualChildAlertResponse);
	}

	@Test
	public void responsePhoneAlert() throws JsonProcessingException {
		// ARRANGE
		Set<String> expectedPhoneAlertResponse = urlsResponseConfig.getUrlPhoneAlertResponse();

		// ACT
		Set<String> actualPhoneAlertResponse = responseUrlsServiceImplUnderTest.responsePhoneAlert("1");

		// ASSERT
		assertEquals(objectMapper.writeValueAsString(expectedPhoneAlertResponse),
				objectMapper.writeValueAsString(actualPhoneAlertResponse));
	}

	@Test
	public void responseFire() throws JsonProcessingException {
		// ARRANGE
		Fire expectedFireResponse = urlsResponseConfig.getUrlFireResponse();

		// ACT
		Fire actualFireResponse = responseUrlsServiceImplUnderTest.responseFire("748 Townings Dr");

		// ASSERT
		assertEquals(objectMapper.writeValueAsString(expectedFireResponse),
				objectMapper.writeValueAsString(actualFireResponse));
	}

	@Test
	public void responseFlood() throws JsonProcessingException {
		// ARRANGE
		Flood expectedFloodResponse = urlsResponseConfig.getUrlFloodResponse();

		// ACT
		Flood actualFloodResponse = responseUrlsServiceImplUnderTest.responseFlood(new String[] { "4" });

		// ASSERT
		assertEquals(objectMapper.writeValueAsString(expectedFloodResponse),
				objectMapper.writeValueAsString(actualFloodResponse));
	}

	@Test
	public void responsePersonInfo() throws JsonProcessingException {
		// ARRANGE
		PersonInfo expectedPersonInfoResponse = urlsResponseConfig.getUrlPersonInfoResponse();

		// ACT
		PersonInfo actualPersonInfoResponse = responseUrlsServiceImplUnderTest.responsePersonInfo("Lily", "Cooper");

		// ASSERT
		assertEquals(objectMapper.writeValueAsString(expectedPersonInfoResponse),
				objectMapper.writeValueAsString(actualPersonInfoResponse));
	}

	@Test
	public void responseCommunityEmail() throws JsonProcessingException {
		// ARRANGE
		Set<String> expectedCommunityEmailResponse = urlsResponseConfig.getUrlCommunityEmailResponse();

		// ACT
		Set<String> actualCommunityEmailtResponse = responseUrlsServiceImplUnderTest.responseCommunityEmail("Culver");

		// ASSERT
		assertEquals(objectMapper.writeValueAsString(expectedCommunityEmailResponse),
				objectMapper.writeValueAsString(actualCommunityEmailtResponse));
	}
}