package com.safetynet.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.entities.endpoints.FirestationMapping;
import com.safetynet.entities.endpoints.Person;
import com.safetynet.entities.urls.ChildAlert;
import com.safetynet.entities.urls.ChildAlertChild;
import com.safetynet.entities.urls.Fire;
import com.safetynet.entities.urls.FirePerson;
import com.safetynet.entities.urls.Firestation;
import com.safetynet.entities.urls.FirestationPerson;
import com.safetynet.entities.urls.Flood;
import com.safetynet.entities.urls.FloodPerson;
import com.safetynet.entities.urls.FloodStation;
import com.safetynet.entities.urls.PersonInfo;
import com.safetynet.entities.urls.PersonInfoSameName;
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
		FirestationPerson firestationPerson1 = new FirestationPerson("Jonanathan", "Marrack", "29 15th St",
				"841-874-6513");
		FirestationPerson firestationPerson2 = new FirestationPerson("Warren", "Zemicks", "892 Downing Ct",
				"841-874-7512");
		FirestationPerson firestationPerson3 = new FirestationPerson("Eric", "Cadigan", "951 LoneTree Rd",
				"841-874-7458");
		FirestationPerson firestationPerson4 = new FirestationPerson("Sophia", "Zemicks", "892 Downing Ct",
				"841-874-7878");
		FirestationPerson firestationPerson5 = new FirestationPerson("Zach", "Zemicks", "892 Downing Ct",
				"841-874-7512");

		List<FirestationPerson> firestationPersons = new ArrayList<>();
		firestationPersons.add(firestationPerson1);
		firestationPersons.add(firestationPerson2);
		firestationPersons.add(firestationPerson3);
		firestationPersons.add(firestationPerson4);
		firestationPersons.add(firestationPerson5);

		Firestation firestationUrlResponse = new Firestation();
		firestationUrlResponse.setFirestationPersons(firestationPersons);
		firestationUrlResponse.setNbAdults(4);
		firestationUrlResponse.setNbChilds(1);

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

		ObjectMapper objectMapper = new ObjectMapper();

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		assertEquals(objectMapper.writeValueAsString(firestationUrlResponse), actualResponseBody);
		// assertThat(objectMapper.writeValueAsString(firestationUrlResponse)).isEqualToIgnoringWhitespace(actualResponseBody);
		// assertThat(objectMapper.writeValueAsString(firestationUrlResponse)).isEqualTo(actualResponseBody);
	}

	@Test
	public void getUrlFirestation_whenStationNotExist() throws Exception {
		// ARRANGE
		when(mockFirestationMappingService.getFirestationMappingByIdStation("2")).thenReturn(null);

		// ACT & ASSERT
		mockMvc.perform(get("/firestation").contentType(MediaType.APPLICATION_JSON).param("stationNumber", "2"))
				.andExpect(status().isNotFound());

		verify(mockFirestationMappingService, times(1)).getFirestationMappingByIdStation("2");
		verify(mockResponseUrlsService, never()).responseFirestation("2");
	}

	// http://localhost:8080/childAlert?address=<address>
	@Test
	public void getUrlChildAlert_whenAddressExist() throws Exception {
		// ARRANGE
		ChildAlert childAlertUrlResponse = new ChildAlert();

		List<ChildAlertChild> childAlertChilds = new ArrayList<>();
		ChildAlertChild childAlertChild1 = new ChildAlertChild("Roger","Boyd",2);
		ChildAlertChild childAlertChild2 = new ChildAlertChild("Tenley","Boyd",8);
		childAlertChilds.add(childAlertChild1);
		childAlertChilds.add(childAlertChild2);
		
		List<Person> childAlertHouseholdMembers = new ArrayList<>();
		Person childAlertHouseholdMember1 = new Person("JacobBoyd","Jacob","Boyd","1509 Culver St","Culver","97451","841-874-6513","drk@email.com");
		Person childAlertHouseholdMember2 = new Person("JohnBoyd","John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
		Person childAlertHouseholdMember3 = new Person("FeliciaBoyd","Felicia","Boyd","1509 Culver St","Culver","97451","841-874-6544","jaboyd@email.com");
		childAlertHouseholdMembers.add(childAlertHouseholdMember1);
		childAlertHouseholdMembers.add(childAlertHouseholdMember2);
		childAlertHouseholdMembers.add(childAlertHouseholdMember3);
		
		childAlertUrlResponse.setChildAlertChilds(childAlertChilds);
		childAlertUrlResponse.setChildAlertHouseholdMembers(childAlertHouseholdMembers);
		
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

		ObjectMapper objectMapper = new ObjectMapper();

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		assertEquals(objectMapper.writeValueAsString(childAlertUrlResponse), actualResponseBody);
	}	

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
		Set<String> phoneAlertUrlResponse = new HashSet<>();
		phoneAlertUrlResponse.add("841-874-7784");
		phoneAlertUrlResponse.add("841-874-7462");
		phoneAlertUrlResponse.add("841-874-6512");
		phoneAlertUrlResponse.add("841-874-8547");
		
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

		ObjectMapper objectMapper = new ObjectMapper();

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		assertEquals(objectMapper.writeValueAsString(phoneAlertUrlResponse), actualResponseBody);
	}	

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
		Fire fireUrlResponse = new Fire();
		
		List<FirePerson> firePersons = new ArrayList<>();
		FirePerson firePerson1 = new FirePerson("Clive", "Ferguson", "841-874-6741", 26, new String[0], new String[0]);
		FirePerson firePerson2 = new FirePerson("Foster", "Shepard", "841-874-6544", 40, new String[0], new String[0]);
		firePersons.add(firePerson1);
		firePersons.add(firePerson2);
		
		fireUrlResponse.setIdStation("3");
		fireUrlResponse.setFirePersons(firePersons);

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

		ObjectMapper objectMapper = new ObjectMapper();

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
		List<PersonInfoSameName> otherPersonsWithSameName = new ArrayList<PersonInfoSameName>();
		PersonInfoSameName otherPersonWithSameName = new PersonInfoSameName("Tony", "Cooper", "112 Steppes Pl", 26, "tcoop@ymail.com", new String[]{"hydrapermazol:300mg", "dodoxadin:30mg"}, new String[]{"shellfish"});
		otherPersonsWithSameName.add(otherPersonWithSameName);
	
		PersonInfo personInfoUrlResponse = new PersonInfo("Lily", "Cooper", "489 Manchester St", 26, "lily@email.com",  new String[]{}, new String[]{}, otherPersonsWithSameName);
			
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

		ObjectMapper objectMapper = new ObjectMapper();

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
		List<String> communityEmailUrlResponse = new ArrayList<>();
		communityEmailUrlResponse.add("jaboyd@email.com");
		communityEmailUrlResponse.add("ssanw@email.com");
		communityEmailUrlResponse.add("drk@email.com");
		communityEmailUrlResponse.add("tenz@email.com");
		communityEmailUrlResponse.add("bstel@email.com");
		communityEmailUrlResponse.add("aly@imail.com");
		communityEmailUrlResponse.add("ward@email.com");
		communityEmailUrlResponse.add("reg@email.com");
		communityEmailUrlResponse.add("lily@email.com");
		communityEmailUrlResponse.add("gramps@email.com");
		communityEmailUrlResponse.add("jpeter@email.com");
		communityEmailUrlResponse.add("clivfd@ymail.com");
		communityEmailUrlResponse.add("tcoop@ymail.com");
		communityEmailUrlResponse.add("soph@email.com");
		communityEmailUrlResponse.add("zarc@email.com");
		
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
		
		ObjectMapper objectMapper = new ObjectMapper();

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
		Flood floodUrlReponse = new Flood();
		Map<String, FloodStation> mapFloodStations = new HashMap<>();
				
		FloodStation floodStation = new FloodStation();
		Map<String, List<FloodPerson>> mapFloodPersons = new HashMap<>();
		
		String address1 = "489 Manchester St";
		FloodPerson floodPerson1Address1 = new FloodPerson("Lily", "Cooper", "841-874-9845", 26, new String[] {}, new String[] {});
		List<FloodPerson> floodPersonsAddress1 = new ArrayList<>();
		floodPersonsAddress1.add(floodPerson1Address1);
		
		String address2 = "112 Steppes Pl";
		FloodPerson floodPerson1Address2 = new FloodPerson("Allison", "Boyd", "841-874-9888", 55, new String[] {"aznol:200mg"}, new String[] {"nillacilan"});
		FloodPerson floodPerson2Address2 = new FloodPerson("Tony", "Cooper", "841-874-6874", 26, new String[] {"hydrapermazol:300mg", "dodoxadin:30mg"}, new String[] {"shellfish"});
		List<FloodPerson> floodPersonsAddress2 = new ArrayList<>();
		floodPersonsAddress2.add(floodPerson1Address2);
		floodPersonsAddress2.add(floodPerson2Address2);

		mapFloodPersons.put(address1, floodPersonsAddress1);
		mapFloodPersons.put(address2, floodPersonsAddress2);
	
		floodStation.setMapFloodPersons(mapFloodPersons);
		
		String station1 = "4";
		mapFloodStations.put(station1, floodStation);

		floodUrlReponse.setMapFloodStations(mapFloodStations);
		
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

		ObjectMapper objectMapper = new ObjectMapper();

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