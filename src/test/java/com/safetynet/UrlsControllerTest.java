package com.safetynet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import com.safetynet.controllers.UrlsController;
import com.safetynet.dao.FirestationMappingDaoImpl;
import com.safetynet.dao.IFirestationMappingDao;
import com.safetynet.dao.IPersonDao;
import com.safetynet.dao.PersonDaoImpl;
import com.safetynet.entities.endpoints.FirestationMapping;
import com.safetynet.entities.urls.Firestation;
import com.safetynet.entities.urls.FirestationPerson;
import com.safetynet.model.endpoints.FirestationMappingModelImpl;
import com.safetynet.model.endpoints.IFirestationMappingModel;
import com.safetynet.model.endpoints.IPersonModel;
import com.safetynet.model.endpoints.PersonModelImpl;
import com.safetynet.model.urls.IResponseUrlsModel;

@WebMvcTest(UrlsController.class)
public class UrlsControllerTest {

	@TestConfiguration
	static class UrlsControllerTestContextConfiguration {
		@Bean
		public IFirestationMappingModel iFirestationMappingModel() {
			return new FirestationMappingModelImpl();
		}

		@Bean
		public IFirestationMappingDao iFirestationMappingDao() {
			return new FirestationMappingDaoImpl();
		}

		@Bean
		public IPersonModel iPersonModel() {
			return new PersonModelImpl();
		}

		@Bean
		public IPersonDao iPersonDao() {
			return new PersonDaoImpl();
		}
	}

	@Autowired
	private MockMvc mockMvc;

	// @Autowired
	// ObjectMapper objectMapper;

	@MockBean
	private IResponseUrlsModel mockResponseUrlsModel;

	@MockBean
	private IFirestationMappingModel mockFirestationMappingModel;

	// http://localhost:8080/firestation?stationNumber=<station_number>
	@Test
	public void getUrlFirestation_whenStationExist() throws Exception {

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

		when(mockFirestationMappingModel.getFirestationMappingByIdStation("2")).thenReturn(firestationMapping);

		when(mockResponseUrlsModel.responseFirestation("2")).thenReturn(firestationUrlResponse);

		MvcResult mvcResult = mockMvc
				.perform(get("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.param("stationNumber", "2"))
				.andExpect(status().isOk())
				.andExpect(content()
				.contentType("application/json"))
				.andReturn();

		verify(mockResponseUrlsModel, times(1)).responseFirestation("2");

		ObjectMapper objectMapper = new ObjectMapper();

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		assertEquals(objectMapper.writeValueAsString(firestationUrlResponse), actualResponseBody);
		// assertThat(objectMapper.writeValueAsString(firestationUrlResponse)).isEqualToIgnoringWhitespace(actualResponseBody);
		// assertThat(objectMapper.writeValueAsString(firestationUrlResponse)).isEqualTo(actualResponseBody);
	}

	@Test
	public void getUrlFirestation_whenStationNotExist() throws Exception {

		when(mockFirestationMappingModel.getFirestationMappingByIdStation("2")).thenReturn(null);

		mockMvc.perform(get("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.param("stationNumber", "2"))
				.andExpect(status().isNotFound());

		verify(mockFirestationMappingModel, times(1)).getFirestationMappingByIdStation("2");
		verify(mockResponseUrlsModel, never()).responseFirestation("2");
	}
}
