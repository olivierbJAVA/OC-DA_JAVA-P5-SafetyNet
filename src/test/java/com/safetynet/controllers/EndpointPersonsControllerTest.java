package com.safetynet.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.entities.endpoints.Person;
import com.safetynet.service.endpoints.IPersonService;

/**
 * Class including unit tests for the EndpointPersonsController Class.
 */
@WebMvcTest(EndpointPersonsController.class)
public class EndpointPersonsControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IPersonService mockPersonService;

	private ObjectMapper objectMapper;
	
	@BeforeEach
	private void setUpPerTest() {
		objectMapper = new ObjectMapper();
	}	
	
	// @GetMapping(value = "/persons")
	@Test
	public void getAllPersons() throws Exception {
		//ARRANGE
		Person personToGet1 = new Person("BertrandSimon1", "Bertrand", "Simon1", "address1", "Paris", "75000",
				"0696469887", "bs@email.com");
		Person personToGet2 = new Person("BertrandSimon2", "Bertrand", "Simon2", "address2", "Paris", "75000",
				"0696469887", "bs@email.com");
		Person personToGet3 = new Person("BertrandSimon3", "Bertrand", "Simon3", "address3", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		List<Person> allPersonsToGet = new ArrayList<>();
		allPersonsToGet.add(personToGet1);
		allPersonsToGet.add(personToGet2);
		allPersonsToGet.add(personToGet3);
		
		when(mockPersonService.getAllPersons()).thenReturn(allPersonsToGet);

		//ACT & ASSERT
		mockMvc.perform(get("/persons")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound())
				.andExpect(jsonPath("$[0].lastName", is(personToGet1.getLastName())))
				.andExpect(jsonPath("$[1].lastName", is(personToGet2.getLastName())))
				.andExpect(jsonPath("$[2].lastName", is(personToGet3.getLastName())));
		
		verify(mockPersonService, times(1)).getAllPersons();
	}
		
	// @GetMapping(value = "/persons/{id}")
	@Test
	public void getPersonById_whenPersonExist() throws Exception {
		//ARRANGE
		Person personToGet = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		when(mockPersonService.getPersonById(personToGet.getIdPerson())).thenReturn(personToGet);

		//ACT & ASSERT
		mockMvc.perform(get("/persons/{id}", personToGet.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound())
				.andExpect(jsonPath("$.firstName", is(personToGet.getFirstName())));
		
		verify(mockPersonService, times(1)).getPersonById(personToGet.getIdPerson());
	}

	// @GetMapping(value = "/persons/{id}")
	@Test
	public void getPersonById_whenPersonNotExist() throws Exception {
		//ARRANGE
		Person personToGet = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		when(mockPersonService.getPersonById(personToGet.getIdPerson())).thenReturn(null);
		
		//ACT & ASSERT
		mockMvc.perform(get("/persons/{id}", personToGet.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		
		verify(mockPersonService, times(1)).getPersonById(personToGet.getIdPerson());
	}
	
	// @PostMapping(value = "/persons")
	@Test
	public void addPerson_whenPersonNotAlreadyExist() throws Exception {
		//ARRANGE
		Person personToAdd = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		when(mockPersonService.personExist(any(Person.class))).thenReturn(false).thenReturn(true);
		when(mockPersonService.addPerson(personToAdd)).thenReturn(null);
	
		//ACT & ASSERT
		MvcResult mvcResult = mockMvc.perform(post("/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personToAdd)))
				.andExpect(status().isCreated())
				.andReturn();
	
		verify(mockPersonService, times(2)).personExist(any(Person.class));
		verify(mockPersonService, times(1)).addPerson(personToAdd);
	
		String actualResponseHeaderLocation = mvcResult.getResponse().getHeader("Location");
		assertEquals("http://localhost/persons/BertrandSimon", actualResponseHeaderLocation);
	}
	
	// @PostMapping(value = "/persons")
	@Test
	public void addPerson_whenPersonAlreadyExist() throws Exception {
		//ARRANGE
		Person personToAdd = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		when(mockPersonService.personExist(any(Person.class))).thenReturn(true);
		
		//ACT & ASSERT
		mockMvc.perform(post("/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personToAdd)))
				.andExpect(status().isBadRequest());
		
		verify(mockPersonService, times(1)).personExist(any(Person.class));
		verify(mockPersonService, never()).addPerson(personToAdd);
	}
	
	// @PostMapping(value = "/persons")
	@Test
	public void addPerson_whenPersonNotAlreadyExist_whenInternalServerError() throws Exception {
		//ARRANGE
		Person personToAdd = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		when(mockPersonService.personExist(any(Person.class))).thenReturn(false).thenReturn(false);
		when(mockPersonService.addPerson(personToAdd)).thenReturn(null);
	
		//ACT & ASSERT
		mockMvc.perform(post("/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personToAdd)))
				.andExpect(status().isInternalServerError());

		verify(mockPersonService, times(2)).personExist(any(Person.class));
		verify(mockPersonService, times(1)).addPerson(personToAdd);
	}
	
	// @PutMapping(value = "/persons/{id}")
	@Test
	public void updatePerson_whenPersonExist() throws Exception {
		//ARRANGE
		Person personToUpdate = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		Person personUpdated = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Courcelles", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		when(mockPersonService.getPersonById(personToUpdate.getIdPerson())).thenReturn(personToUpdate).thenReturn(personToUpdate).thenReturn(personUpdated);
		when(mockPersonService.updatePerson(personUpdated)).thenReturn(personToUpdate);
	
		//ACT & ASSERT
		mockMvc.perform(put("/persons/{id}", personToUpdate.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personUpdated)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.address", is(personUpdated.getAddress())));

		verify(mockPersonService, times(3)).getPersonById(personToUpdate.getIdPerson());
		verify(mockPersonService, times(1)).updatePerson(personUpdated);
	}
	
	// @PutMapping(value = "/persons/{id}")
	@Test
	public void updatePerson_whenPersonInPathRequestNotExist() throws Exception {
		//ARRANGE
		Person personToUpdate = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		Person personUpdated = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Courcelles", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		when(mockPersonService.getPersonById(personToUpdate.getIdPerson())).thenReturn(null);

		//ACT & ASSERT
		mockMvc.perform(put("/persons/{id}", personToUpdate.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personUpdated)))
				.andExpect(status().isNotFound());
		
		verify(mockPersonService, times(1)).getPersonById(personToUpdate.getIdPerson());
		verify(mockPersonService, never()).updatePerson(any(Person.class));
	}
	
	// @PutMapping(value = "/persons/{id}")
	@Test
	public void updatePerson_whenPersonInRequestBodyNotExist() throws Exception {
		//ARRANGE
		Person personToUpdate = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		Person personUpdated = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Courcelles", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		when(mockPersonService.getPersonById(personToUpdate.getIdPerson())).thenReturn(personToUpdate).thenReturn(null);

		//ACT & ASSERT
		mockMvc.perform(put("/persons/{id}", personToUpdate.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personUpdated)))
				.andExpect(status().isNotFound());
		
		verify(mockPersonService, times(2)).getPersonById(personToUpdate.getIdPerson());
		verify(mockPersonService, never()).updatePerson(any(Person.class));
	}
	
	// @PutMapping(value = "/persons/{id}")
	@Test
	public void updatePerson_whenPersonExist_whenInternalServerError() throws Exception {
		//ARRANGE
		Person personToUpdate = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		Person personUpdated = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Courcelles", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		when(mockPersonService.getPersonById(personToUpdate.getIdPerson())).thenReturn(personToUpdate).thenReturn(personToUpdate).thenReturn(personToUpdate);
		when(mockPersonService.updatePerson(personUpdated)).thenReturn(personToUpdate);

		//ACT & ASSERT
		mockMvc.perform(put("/persons/{id}", personToUpdate.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personUpdated)))
				.andExpect(status().isInternalServerError());
		
		verify(mockPersonService, times(3)).getPersonById(personToUpdate.getIdPerson());
		verify(mockPersonService, times(1)).updatePerson(personUpdated);
	}
	
	// @DeleteMapping(value = "/persons/{id}")
	@Test
	public void deletePerson_whenPersonExist() throws Exception {
		//ARRANGE
		Person personToDelete = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		when(mockPersonService.getPersonById(personToDelete.getIdPerson())).thenReturn(personToDelete);
		when(mockPersonService.deletePerson(personToDelete.getIdPerson())).thenReturn(personToDelete);
		when(mockPersonService.personExist(personToDelete)).thenReturn(false);

		//ACT & ASSERT
		mockMvc.perform(delete("/persons/{id}", personToDelete.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isGone());
		
		verify(mockPersonService, times(1)).getPersonById(personToDelete.getIdPerson());
		verify(mockPersonService, times(1)).deletePerson(personToDelete.getIdPerson());
		verify(mockPersonService, times(1)).personExist(personToDelete);
	}
	
	// @DeleteMapping(value = "/persons/{id}")
	@Test
	public void deletePerson_whenPersonNotExist() throws Exception {
		//ARRANGE
		Person personToDelete = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		when(mockPersonService.getPersonById(personToDelete.getIdPerson())).thenReturn(null);

		//ACT & ASSERT
		mockMvc.perform(delete("/persons/{id}", personToDelete.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		
		verify(mockPersonService, times(1)).getPersonById(personToDelete.getIdPerson());
		verify(mockPersonService, never()).deletePerson(anyString());
		verify(mockPersonService, never()).personExist(any(Person.class));
	}

	// @DeleteMapping(value = "/persons/{id}")
	@Test
	public void deletePerson_whenPersonExist_whenInternalServerError() throws Exception {
		//ARRANGE
		Person personToDelete = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		when(mockPersonService.getPersonById(personToDelete.getIdPerson())).thenReturn(personToDelete);
		when(mockPersonService.deletePerson(personToDelete.getIdPerson())).thenReturn(personToDelete);
		when(mockPersonService.personExist(personToDelete)).thenReturn(true);
	
		//ACT & ASSERT
		mockMvc.perform(delete("/persons/{id}", personToDelete.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
		
		verify(mockPersonService, times(1)).getPersonById(personToDelete.getIdPerson());
		verify(mockPersonService, times(1)).deletePerson(personToDelete.getIdPerson());
		verify(mockPersonService, times(1)).personExist(personToDelete);
	}

}
