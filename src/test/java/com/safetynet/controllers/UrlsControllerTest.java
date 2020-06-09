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

@WebMvcTest(UrlsController.class)
public class UrlsControllerTest {

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
	public void getUrlFirestation_whenStationExist() throws Exception {
		// ARRANGE
		Firestation firestationUrlResponse = urlsResponseConfig.getUrlFirestationResponse();
		
		FirestationMapping firestationMapping = new FirestationMapping();
		firestationMapping.setStation("2");
		firestationMapping.setAddress("29 15th St");

		when(mockFirestationMappingService.getFirestationMappingByIdStation("2")).thenReturn(firestationMapping);

		when(mockResponseUrlsService.responseFirestation("2")).thenReturn(firestationUrlResponse);
		
		// ACT & ASSERT
		MvcResult mvcResult = mockMvc
				.perform(get("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.param("stationNumber", "2"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();

		verify(mockResponseUrlsService, times(1)).responseFirestation("2");

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		assertEquals(objectMapper.writeValueAsString(firestationUrlResponse), actualResponseBody);
		// assertThat(objectMapper.writeValueAsString(firestationUrlResponse)).isEqualToIgnoringWhitespace(actualResponseBody);
		// assertThat(objectMapper.writeValueAsString(firestationUrlResponse)).isEqualTo(actualResponseBody);
	}

	// http://localhost:8080/firestation?stationNumber=<station_number>
	@Test
	public void getUrlFirestation_whenStationNotExist() throws Exception {
		// ARRANGE
		when(mockFirestationMappingService.getFirestationMappingByIdStation("2")).thenReturn(null);

		// ACT & ASSERT
		mockMvc.perform(get("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.param("stationNumber", "2"))
				.andExpect(status().isNotFound());

		verify(mockFirestationMappingService, times(1)).getFirestationMappingByIdStation("2");
		verify(mockResponseUrlsService, never()).responseFirestation("2");
	}

	// http://localhost:8080/childAlert?address=<address>
	@Test
	public void getUrlChildAlert_whenAddressExist() throws Exception {
		// ARRANGE
		ChildAlert childAlertUrlResponse = urlsResponseConfig.getUrlChildAlertResponse();

		when(mockPersonService.addressExist("1509 Culver St")).thenReturn(true);
				
		when(mockResponseUrlsService.responseChildAlert("1509 Culver St")).thenReturn(childAlertUrlResponse);
		
		// ACT & ASSERT
		MvcResult mvcResult = mockMvc
				.perform(get("/childAlert")
				.contentType(MediaType.APPLICATION_JSON)
				.param("address", "1509 Culver St"))
				.andExpect(status().isOk())
				.andExpect(content()
				.contentType("application/json"))
				.andReturn();

		verify(mockResponseUrlsService, times(1)).responseChildAlert("1509 Culver St");

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		assertEquals(objectMapper.writeValueAsString(childAlertUrlResponse), actualResponseBody);
	}	

	// http://localhost:8080/childAlert?address=<address>
	@Test
	public void getUrlChildAlert_whenAddressNotExist() throws Exception {
		// ARRANGE
		when(mockPersonService.addressExist("1509 Culver St")).thenReturn(false);
		
		// ACT & ASSERT
		mockMvc.perform(get("/childAlert")
				.contentType(MediaType.APPLICATION_JSON)
				.param("address", "1509 Culver St"))
				.andExpect(status().isNotFound());

		verify(mockPersonService, times(1)).addressExist("1509 Culver St");
		verify(mockResponseUrlsService, never()).responseChildAlert("1509 Culver St");
	}
	
	// http://localhost:8080/phoneAlert?firestation=<firestation_number>
	@Test
	public void getUrlPhoneAlert_whenFirestationExist() throws Exception {
		// ARRANGE
		Set<String> phoneAlertUrlResponse = urlsResponseConfig.getUrlPhoneAlertResponse();
		
		FirestationMapping firestationMapping = new FirestationMapping("644 Gershwin Cir", "1");
		when(mockFirestationMappingService.getFirestationMappingByIdStation("1")).thenReturn(firestationMapping);
		
		when(mockResponseUrlsService.responsePhoneAlert("1")).thenReturn(phoneAlertUrlResponse);
		
		// ACT & ASSERT
		MvcResult mvcResult = mockMvc
				.perform(get("/phoneAlert")
				.contentType(MediaType.APPLICATION_JSON)
				.param("firestation", "1"))
				.andExpect(status().isOk())
				.andExpect(content()
				.contentType("application/json"))
				.andReturn();

		verify(mockResponseUrlsService, times(1)).responsePhoneAlert("1");

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		assertEquals(objectMapper.writeValueAsString(phoneAlertUrlResponse), actualResponseBody);
	}	

	// http://localhost:8080/phoneAlert?firestation=<firestation_number>
	@Test
	public void getUrlPhoneAlert_whenFirestationNotExist() throws Exception {
		// ARRANGE
		when(mockFirestationMappingService.getFirestationMappingByIdStation("1")).thenReturn(null);
		
		// ACT & ASSERT
		mockMvc.perform(get("/phoneAlert")
				.contentType(MediaType.APPLICATION_JSON)
				.param("firestation", "1"))
				.andExpect(status().isNotFound());

		verify(mockFirestationMappingService, times(1)).getFirestationMappingByIdStation("1");
		verify(mockResponseUrlsService, never()).responsePhoneAlert("1");
	}
	
	// http://localhost:8080/fire?address=<address>
	@Test
	public void getUrlFire_whenAddressExist() throws Exception {
		// ARRANGE
		Fire fireUrlResponse = urlsResponseConfig.getUrlFireResponse();

		when(mockPersonService.addressExist("748 Townings Dr")).thenReturn(true);
						
		when(mockResponseUrlsService.responseFire("748 Townings Dr")).thenReturn(fireUrlResponse);
		
		// ACT & ASSERT
		MvcResult mvcResult = mockMvc
				.perform(get("/fire")
				.contentType(MediaType.APPLICATION_JSON)
				.param("address", "748 Townings Dr"))
				.andExpect(status().isOk())
				.andExpect(content()
				.contentType("application/json"))
				.andReturn();

		verify(mockResponseUrlsService, times(1)).responseFire("748 Townings Dr");

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		assertEquals(objectMapper.writeValueAsString(fireUrlResponse), actualResponseBody);
	}	
	
	// http://localhost:8080/fire?address=<address>
	@Test
	public void getUrlFire_whenAddressNotExist() throws Exception {
		// ARRANGE
		when(mockPersonService.addressExist("748 Townings Dr")).thenReturn(false);
		
		// ACT & ASSERT
		mockMvc.perform(get("/fire")
				.contentType(MediaType.APPLICATION_JSON)
				.param("address", "748 Townings Dr"))
				.andExpect(status().isNotFound());

		verify(mockPersonService, times(1)).addressExist("748 Townings Dr");
		verify(mockResponseUrlsService, never()).responseFire("748 Townings Dr");
	}
	
	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	@Test
	public void getUrlPersonInfo_whenPersonExist() throws Exception {
		// ARRANGE
		PersonInfo personInfoUrlResponse = urlsResponseConfig.getUrlPersonInfoResponse();
			
		when(mockPersonService.idPersonExist("LilyCooper")).thenReturn(true);

		when(mockResponseUrlsService.responsePersonInfo("Lily", "Cooper")).thenReturn(personInfoUrlResponse);
		
		// ACT & ASSERT
		MvcResult mvcResult = mockMvc
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
		assertEquals(objectMapper.writeValueAsString(personInfoUrlResponse), actualResponseBody);
	}	
		
	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	@Test
	public void getUrlPersonInfo_whenPersonNotExist() throws Exception {
		// ARRANGE
		when(mockPersonService.idPersonExist("LilyCooper")).thenReturn(false);
		
		// ACT & ASSERT
		mockMvc.perform(get("/personInfo")
				.contentType(MediaType.APPLICATION_JSON)
				.param("firstName", "Lily")
				.param("lastName", "Cooper"))
				.andExpect(status().isNotFound());

		verify(mockPersonService, times(1)).idPersonExist("LilyCooper");
		verify(mockResponseUrlsService, never()).responsePersonInfo("Lily", "Cooper");
	}
	
	// http://localhost:8080/communityEmail?city=<city>
	@Test
	public void getUrlCommunityEmail_whenCityExist() throws Exception {
		// ARRANGE
		Set<String> communityEmailUrlResponse = urlsResponseConfig.getUrlCommunityEmailResponse();
		
		when(mockPersonService.cityExist("Culver")).thenReturn(true);
		
		when(mockResponseUrlsService.responseCommunityEmail("Culver")).thenReturn(communityEmailUrlResponse);
		
		// ACT & ASSERT
		MvcResult mvcResult = mockMvc
				.perform(get("/communityEmail")
				.contentType(MediaType.APPLICATION_JSON)
				.param("city", "Culver"))
				.andExpect(status().isFound())
				.andExpect(content()
				.contentType("application/json"))
				.andReturn();

		verify(mockResponseUrlsService, times(1)).responseCommunityEmail("Culver");

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		assertEquals(objectMapper.writeValueAsString(communityEmailUrlResponse), actualResponseBody);
	}	

	// http://localhost:8080/communityEmail?city=<city>
	@Test
	public void getUrlCommunityEmail_whenCityNotExist() throws Exception {
		// ARRANGE
		when(mockPersonService.cityExist("Culver")).thenReturn(false);
		
		// ACT & ASSERT
		mockMvc.perform(get("/communityEmail")
				.contentType(MediaType.APPLICATION_JSON)
				.param("city", "Culver"))
				.andExpect(status().isNotFound());

		verify(mockPersonService, times(1)).cityExist("Culver");
		verify(mockResponseUrlsService, never()).responseCommunityEmail("Culver");
	}
	
	// http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	@Test
	public void getUrlFlood_whenStationExist() throws Exception {
		// ARRANGE
		Flood floodUrlReponse = urlsResponseConfig.getUrlFloodResponse();
		
		FirestationMapping firestationMapping = new FirestationMapping();
		firestationMapping.setStation("4");
		firestationMapping.setAddress("489 Manchester St");
		
		when(mockFirestationMappingService.getFirestationMappingByIdStation("4")).thenReturn(firestationMapping);

		when(mockResponseUrlsService.responseFlood(new String[] {"4"})).thenReturn(floodUrlReponse);
		
		// ACT & ASSERT
		MvcResult mvcResult = mockMvc
				.perform(get("/flood/stations")
				.contentType(MediaType.APPLICATION_JSON)
				.param("stations", "4"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();

		verify(mockResponseUrlsService, times(1)).responseFlood(new String[] {"4"});

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		assertEquals(objectMapper.writeValueAsString(floodUrlReponse), actualResponseBody);
	}

	// http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	@Test
	public void getUrlFlood_whenStationNotExist() throws Exception {
		// ARRANGE
		when(mockFirestationMappingService.getFirestationMappingByIdStation("4")).thenReturn(null);

		// ACT & ASSERT
		mockMvc.perform(get("/flood/stations")
				.contentType(MediaType.APPLICATION_JSON)
				.param("stations", "4"))
				.andExpect(status().isNotFound());

		verify(mockFirestationMappingService, times(1)).getFirestationMappingByIdStation("4");
		verify(mockResponseUrlsService, never()).responseFlood(new String[] {"4"});
	}
	
}