package com.safetynet.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.config.UrlsResponseConfig;
import com.safetynet.entities.endpoints.FirestationMapping;
import com.safetynet.entities.urls.ChildAlert;
import com.safetynet.entities.urls.Fire;
import com.safetynet.entities.urls.Firestation;
import com.safetynet.entities.urls.Flood;

import com.safetynet.entities.urls.PersonInfo;
import com.safetynet.repository.FirestationMappingRepositoryImpl;
import com.safetynet.repository.IFirestationMappingRepository;
import com.safetynet.repository.IPersonRepository;
import com.safetynet.repository.PersonRepositoryImpl;
import com.safetynet.service.endpoints.FirestationMappingServiceImpl;
import com.safetynet.service.endpoints.IFirestationMappingService;
import com.safetynet.service.endpoints.IPersonService;
import com.safetynet.service.endpoints.PersonServiceImpl;
import com.safetynet.service.urls.IResponseUrlsService;

/**
 * Class including unit tests for the UrlsController Class.
 */
@WebMvcTest(UrlsController.class)
public class UrlsControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(UrlsControllerTest.class);
	
	private static UrlsResponseConfig urlsResponseConfig = new UrlsResponseConfig();
	
	@TestConfiguration
	static class UrlsControllerTestContextConfiguration {
		@Bean
		public IFirestationMappingService iFirestationMappingService() {
			return new FirestationMappingServiceImpl();
		}

		@Bean
		public IFirestationMappingRepository iFirestationMappingRepository() {
			return new FirestationMappingRepositoryImpl();
		}

		@Bean
		public IPersonService iPersonService() {
			return new PersonServiceImpl();
		}

		@Bean
		public IPersonRepository iPersonRepository() {
			return new PersonRepositoryImpl();
		}
	}

	private ObjectMapper objectMapper;
	
	@BeforeEach
	private void setUpPerTest() {
		objectMapper = new ObjectMapper();
	}
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IResponseUrlsService mockResponseUrlsService;

	@MockBean
	private IFirestationMappingService mockFirestationMappingService;

	@MockBean
	private IPersonService mockPersonService;
	
	// http://localhost:8080/firestation?stationNumber=<station_number>
	@Test
	public void getUrlFirestation_whenStationExist() {
		// ARRANGE
		Firestation expectedFirestationUrlResponse = urlsResponseConfig.getUrlFirestationResponse();
		
		FirestationMapping firestationMapping = new FirestationMapping();
		firestationMapping.setStation("2");
		firestationMapping.setAddress("29 15th St");

		when(mockFirestationMappingService.getFirestationMappingByIdStation("2")).thenReturn(firestationMapping);

		when(mockResponseUrlsService.responseFirestation("2")).thenReturn(expectedFirestationUrlResponse);
		
		// ACT & ASSERT
		MvcResult mvcResult;
		try {
			mvcResult = mockMvc
					.perform(get("/firestation")
					.contentType(MediaType.APPLICATION_JSON)
					.param("stationNumber", "2"))
					.andExpect(status().isOk())
					.andExpect(content().contentType("application/json"))
					.andReturn();
		
			verify(mockResponseUrlsService, times(1)).responseFirestation("2");

			String actualResponseBody = mvcResult.getResponse().getContentAsString();
			assertEquals(objectMapper.writeValueAsString(expectedFirestationUrlResponse), actualResponseBody);
		} catch (Exception e) {
			logger.error("Exception in unit test", e);
		}
	}

	// http://localhost:8080/firestation?stationNumber=<station_number>
	@Test
	public void getUrlFirestation_whenStationNotExist() {
		// ARRANGE
		when(mockFirestationMappingService.getFirestationMappingByIdStation("2")).thenReturn(null);

		// ACT & ASSERT
		try {
			mockMvc.perform(get("/firestation")
					.contentType(MediaType.APPLICATION_JSON)
					.param("stationNumber", "2"))
					.andExpect(status().isNotFound());
		} catch (Exception e) {
			logger.error("Exception in MockMvc", e);
		}

		verify(mockFirestationMappingService, times(1)).getFirestationMappingByIdStation("2");
		verify(mockResponseUrlsService, never()).responseFirestation("2");
	}

	// http://localhost:8080/childAlert?address=<address>
	@Test
	public void getUrlChildAlert_whenAddressExist() {
		// ARRANGE
		ChildAlert expectedChildAlertUrlResponse = urlsResponseConfig.getUrlChildAlertResponse();

		when(mockPersonService.addressExist("1509 Culver St")).thenReturn(true);
				
		when(mockResponseUrlsService.responseChildAlert("1509 Culver St")).thenReturn(expectedChildAlertUrlResponse);
		
		// ACT & ASSERT
		MvcResult mvcResult;
		try {
			mvcResult = mockMvc
					.perform(get("/childAlert")
					.contentType(MediaType.APPLICATION_JSON)
					.param("address", "1509 Culver St"))
					.andExpect(status().isOk())
					.andExpect(content()
					.contentType("application/json"))
					.andReturn();

			verify(mockResponseUrlsService, times(1)).responseChildAlert("1509 Culver St");
			
			String actualResponseBody = mvcResult.getResponse().getContentAsString();
			assertEquals(objectMapper.writeValueAsString(expectedChildAlertUrlResponse), actualResponseBody);
		} catch (Exception e) {
			logger.error("Exception in unit test", e);
		}	
	}	

	// http://localhost:8080/childAlert?address=<address>
	@Test
	public void getUrlChildAlert_whenAddressNotExist() {
		// ARRANGE
		when(mockPersonService.addressExist("1509 Culver St")).thenReturn(false);
		
		// ACT & ASSERT
		try {
			mockMvc.perform(get("/childAlert")
					.contentType(MediaType.APPLICATION_JSON)
					.param("address", "1509 Culver St"))
					.andExpect(status().isNotFound());
		} catch (Exception e) {
			logger.error("Exception in MockMvc", e);
		}

		verify(mockPersonService, times(1)).addressExist("1509 Culver St");
		verify(mockResponseUrlsService, never()).responseChildAlert("1509 Culver St");
	}
	
	// http://localhost:8080/phoneAlert?firestation=<firestation_number>
	@Test
	public void getUrlPhoneAlert_whenFirestationExist() {
		// ARRANGE
		Set<String> expectedPhoneAlertUrlResponse = urlsResponseConfig.getUrlPhoneAlertResponse();
		
		FirestationMapping firestationMapping = new FirestationMapping("644 Gershwin Cir", "1");
		when(mockFirestationMappingService.getFirestationMappingByIdStation("1")).thenReturn(firestationMapping);
		
		when(mockResponseUrlsService.responsePhoneAlert("1")).thenReturn(expectedPhoneAlertUrlResponse);
		
		// ACT & ASSERT
		MvcResult mvcResult;
		try {
			mvcResult = mockMvc
					.perform(get("/phoneAlert")
					.contentType(MediaType.APPLICATION_JSON)
					.param("firestation", "1"))
					.andExpect(status().isOk())
					.andExpect(content()
					.contentType("application/json"))
					.andReturn();

			verify(mockResponseUrlsService, times(1)).responsePhoneAlert("1");
			
			String actualResponseBody = mvcResult.getResponse().getContentAsString();
			assertEquals(objectMapper.writeValueAsString(expectedPhoneAlertUrlResponse), actualResponseBody);
		} catch (Exception e) {
			logger.error("Exception in unit test", e);
		}
	}	

	// http://localhost:8080/phoneAlert?firestation=<firestation_number>
	@Test
	public void getUrlPhoneAlert_whenFirestationNotExist() {
		// ARRANGE
		when(mockFirestationMappingService.getFirestationMappingByIdStation("1")).thenReturn(null);
		
		// ACT & ASSERT
		try {
			mockMvc.perform(get("/phoneAlert")
					.contentType(MediaType.APPLICATION_JSON)
					.param("firestation", "1"))
					.andExpect(status().isNotFound());
		} catch (Exception e) {
			logger.error("Exception in MockMvc", e);
		}

		verify(mockFirestationMappingService, times(1)).getFirestationMappingByIdStation("1");
		verify(mockResponseUrlsService, never()).responsePhoneAlert("1");
	}
	
	// http://localhost:8080/fire?address=<address>
	@Test
	public void getUrlFire_whenAddressExist() {
		// ARRANGE
		Fire expectedFireUrlResponse = urlsResponseConfig.getUrlFireResponse();

		when(mockPersonService.addressExist("748 Townings Dr")).thenReturn(true);
						
		when(mockResponseUrlsService.responseFire("748 Townings Dr")).thenReturn(expectedFireUrlResponse);
		
		// ACT & ASSERT
		MvcResult mvcResult;
		try {
			mvcResult = mockMvc
					.perform(get("/fire")
					.contentType(MediaType.APPLICATION_JSON)
					.param("address", "748 Townings Dr"))
					.andExpect(status().isOk())
					.andExpect(content()
					.contentType("application/json"))
					.andReturn();

			verify(mockResponseUrlsService, times(1)).responseFire("748 Townings Dr");
			
			String actualResponseBody = mvcResult.getResponse().getContentAsString();
			assertEquals(objectMapper.writeValueAsString(expectedFireUrlResponse), actualResponseBody);
		} catch (Exception e) {
			logger.error("Exception in unit test", e);
		}
	}	
	
	// http://localhost:8080/fire?address=<address>
	@Test
	public void getUrlFire_whenAddressNotExist() {
		// ARRANGE
		when(mockPersonService.addressExist("748 Townings Dr")).thenReturn(false);
		
		// ACT & ASSERT
		try {
			mockMvc.perform(get("/fire")
					.contentType(MediaType.APPLICATION_JSON)
					.param("address", "748 Townings Dr"))
					.andExpect(status().isNotFound());
		} catch (Exception e) {
			logger.error("Exception in MockMvc", e);
		}

		verify(mockPersonService, times(1)).addressExist("748 Townings Dr");
		verify(mockResponseUrlsService, never()).responseFire("748 Townings Dr");
	}
	
	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	@Test
	public void getUrlPersonInfo_whenPersonExist() {
		// ARRANGE
		PersonInfo expectedPersonInfoUrlResponse = urlsResponseConfig.getUrlPersonInfoResponse();
			
		when(mockPersonService.idPersonExist("LilyCooper")).thenReturn(true);

		when(mockResponseUrlsService.responsePersonInfo("Lily", "Cooper")).thenReturn(expectedPersonInfoUrlResponse);
		
		// ACT & ASSERT
		MvcResult mvcResult;
		try {
			mvcResult = mockMvc
					.perform(get("/personInfo")
					.contentType(MediaType.APPLICATION_JSON)
					.param("firstName", "Lily")
					.param("lastName", "Cooper"))
					.andExpect(status().isOk())
					.andExpect(content()
					.contentType("application/json"))
					.andReturn();

			verify(mockResponseUrlsService, times(1)).responsePersonInfo("Lily", "Cooper");
			
			String actualResponseBody = mvcResult.getResponse().getContentAsString();
			assertEquals(objectMapper.writeValueAsString(expectedPersonInfoUrlResponse), actualResponseBody);
		} catch (Exception e) {
			logger.error("Exception in unit test", e);
		}
	}	
		
	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	@Test
	public void getUrlPersonInfo_whenPersonNotExist() {
		// ARRANGE
		when(mockPersonService.idPersonExist("LilyCooper")).thenReturn(false);
		
		// ACT & ASSERT
		try {
			mockMvc.perform(get("/personInfo")
					.contentType(MediaType.APPLICATION_JSON)
					.param("firstName", "Lily")
					.param("lastName", "Cooper"))
					.andExpect(status().isNotFound());
		} catch (Exception e) {
			logger.error("Exception in MockMvc", e);
		}

		verify(mockPersonService, times(1)).idPersonExist("LilyCooper");
		verify(mockResponseUrlsService, never()).responsePersonInfo("Lily", "Cooper");
	}
	
	// http://localhost:8080/communityEmail?city=<city>
	@Test
	public void getUrlCommunityEmail_whenCityExist() {
		// ARRANGE
		Set<String> expectedCommunityEmailUrlResponse = urlsResponseConfig.getUrlCommunityEmailResponse();
		
		when(mockPersonService.cityExist("Culver")).thenReturn(true);
		
		when(mockResponseUrlsService.responseCommunityEmail("Culver")).thenReturn(expectedCommunityEmailUrlResponse);
		
		// ACT & ASSERT
		MvcResult mvcResult;
		try {
			mvcResult = mockMvc
					.perform(get("/communityEmail")
					.contentType(MediaType.APPLICATION_JSON)
					.param("city", "Culver"))
					.andExpect(status().isFound())
					.andExpect(content()
					.contentType("application/json"))
					.andReturn();

			verify(mockResponseUrlsService, times(1)).responseCommunityEmail("Culver");
			
			String actualResponseBody = mvcResult.getResponse().getContentAsString();
			assertEquals(objectMapper.writeValueAsString(expectedCommunityEmailUrlResponse), actualResponseBody);
		} catch (Exception e) {
			logger.error("Exception in unit test", e);
		}
	}	

	// http://localhost:8080/communityEmail?city=<city>
	@Test
	public void getUrlCommunityEmail_whenCityNotExist() {
		// ARRANGE
		when(mockPersonService.cityExist("Culver")).thenReturn(false);
		
		// ACT & ASSERT
		try {
			mockMvc.perform(get("/communityEmail")
					.contentType(MediaType.APPLICATION_JSON)
					.param("city", "Culver"))
					.andExpect(status().isNotFound());
		} catch (Exception e) {
			logger.error("Exception in MockMvc", e);
		}

		verify(mockPersonService, times(1)).cityExist("Culver");
		verify(mockResponseUrlsService, never()).responseCommunityEmail("Culver");
	}
	
	// http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	@Test
	public void getUrlFlood_whenStationExist() {
		// ARRANGE
		Flood expectedFloodUrlReponse = urlsResponseConfig.getUrlFloodResponse();
		
		FirestationMapping firestationMapping = new FirestationMapping();
		firestationMapping.setStation("4");
		firestationMapping.setAddress("489 Manchester St");
		
		when(mockFirestationMappingService.getFirestationMappingByIdStation("4")).thenReturn(firestationMapping);

		when(mockResponseUrlsService.responseFlood(new String[] {"4"})).thenReturn(expectedFloodUrlReponse);
		
		// ACT & ASSERT
		MvcResult mvcResult;
		try {
			mvcResult = mockMvc
					.perform(get("/flood/stations")
					.contentType(MediaType.APPLICATION_JSON)
					.param("stations", "4"))
					.andExpect(status().isOk())
					.andExpect(content().contentType("application/json"))
					.andReturn();
		
			verify(mockResponseUrlsService, times(1)).responseFlood(new String[] {"4"});
			
			String actualResponseBody = mvcResult.getResponse().getContentAsString();
			assertEquals(objectMapper.writeValueAsString(expectedFloodUrlReponse), actualResponseBody);
		} catch (Exception e) {
			logger.error("Exception in unit test", e);
		}
	}

	// http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	@Test
	public void getUrlFlood_whenStationNotExist() {
		// ARRANGE
		when(mockFirestationMappingService.getFirestationMappingByIdStation("4")).thenReturn(null);

		// ACT & ASSERT
		try {
			mockMvc.perform(get("/flood/stations")
					.contentType(MediaType.APPLICATION_JSON)
					.param("stations", "4"))
					.andExpect(status().isNotFound());
		} catch (Exception e) {
			logger.error("Exception in MockMvc", e);
		}

		verify(mockFirestationMappingService, times(1)).getFirestationMappingByIdStation("4");
		verify(mockResponseUrlsService, never()).responseFlood(new String[] {"4"});
	}
	
}