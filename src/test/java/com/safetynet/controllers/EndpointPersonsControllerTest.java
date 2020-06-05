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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.controllers.EndpointPersonsController;
import com.safetynet.entities.endpoints.Person;
import com.safetynet.service.endpoints.IPersonService;

@WebMvcTest(EndpointPersonsController.class)
public class EndpointPersonsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	//@Autowired
	//ObjectMapper objectMapper;
	
	@MockBean
	private IPersonService mockPersonModel;

	// @GetMapping(value = "/persons")
	@Test
	public void getAllPersons() throws Exception {

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

		when(mockPersonModel.getAllPersons()).thenReturn(allPersonsToGet);

		mockMvc.perform(get("/persons")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$[0].lastName", is(personToGet1.getLastName())))
				.andExpect(jsonPath("$[1].lastName", is(personToGet2.getLastName())))
				.andExpect(jsonPath("$[2].lastName", is(personToGet3.getLastName())));
		
		verify(mockPersonModel, times(1)).getAllPersons();
	}
		
	// @GetMapping(value = "/persons/{id}")
	@Test
	public void getPersonById_whenPersonExist() throws Exception {

		Person personToGet = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonModel.getPersonById(personToGet.getIdPerson())).thenReturn(personToGet);

		mockMvc.perform(get("/persons/{id}", personToGet.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound())
				.andExpect(jsonPath("$.firstName", is(personToGet.getFirstName())));
		
		verify(mockPersonModel, times(1)).getPersonById(anyString());
	}

	// @GetMapping(value = "/persons/{id}")
	@Test
	public void getPersonById_whenPersonNotExist() throws Exception {

		Person personToGet = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonModel.getPersonById(personToGet.getIdPerson())).thenReturn(null);
		
		mockMvc.perform(get("/persons/{id}", personToGet.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		
		verify(mockPersonModel, times(1)).getPersonById(anyString());
	}
	
	// @PostMapping(value = "/persons")
	@Test
	public void addPerson_whenPersonNotAlreadyExist() throws Exception {

		Person personToAdd = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonModel.personExist(any(Person.class))).thenReturn(false).thenReturn(true);
		when(mockPersonModel.addPerson(personToAdd)).thenReturn(null);

		ObjectMapper objectMapper = new ObjectMapper();
		
		MvcResult mvcResult = mockMvc.perform(post("/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personToAdd)))
				.andExpect(status().isCreated())
				.andReturn();
		
		verify(mockPersonModel, times(2)).personExist(any(Person.class));
		verify(mockPersonModel, times(1)).addPerson(any(Person.class));
	
		String actualResponseHeaderLocation = mvcResult.getResponse().getHeader("Location");
		assertEquals("http://localhost/persons/BertrandSimon", actualResponseHeaderLocation);
	}
	
	// @PostMapping(value = "/persons")
	@Test
	public void addPerson_whenPersonAlreadyExist() throws Exception {

		Person personToAdd = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonModel.personExist(any(Person.class))).thenReturn(true);

		ObjectMapper objectMapper = new ObjectMapper();
		
		mockMvc.perform(post("/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personToAdd)))
				.andExpect(status().isBadRequest());
		
		verify(mockPersonModel, times(1)).personExist(any(Person.class));
		verify(mockPersonModel, never()).addPerson(any(Person.class));
	}
	
	// @PostMapping(value = "/persons")
	@Test
	public void addPerson_whenPersonNotAlreadyExist_whenInternalServerError() throws Exception {

		Person personToAdd = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonModel.personExist(personToAdd)).thenReturn(false).thenReturn(false);
		when(mockPersonModel.addPerson(personToAdd)).thenReturn(null);

		ObjectMapper objectMapper = new ObjectMapper();
		
		mockMvc.perform(post("/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personToAdd)))
				.andExpect(status().isInternalServerError());

		verify(mockPersonModel, times(2)).personExist(any(Person.class));
		verify(mockPersonModel, times(1)).addPerson(any(Person.class));
	}
	
	// @PutMapping(value = "/persons/{id}")
	@Test
	public void updatePerson_whenPersonExist() throws Exception {

		Person personToUpdate = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		Person personUpdated = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Courcelles", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		when(mockPersonModel.getPersonById(personToUpdate.getIdPerson())).thenReturn(personToUpdate);
		when(mockPersonModel.updatePerson(personUpdated)).thenReturn(personToUpdate);

		ObjectMapper objectMapper = new ObjectMapper();
		
		mockMvc.perform(put("/persons/{id}", personToUpdate.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personUpdated)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.address", is(personUpdated.getAddress())));

		verify(mockPersonModel, times(1)).getPersonById(anyString());
		verify(mockPersonModel, times(1)).updatePerson(any(Person.class));
	}
	
	// @PutMapping(value = "/persons/{id}")
	@Test
	public void updatePerson_whenPersonNotExist() throws Exception {

		Person personToUpdate = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		Person personUpdated = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Courcelles", "Paris", "75000",
				"0696469887", "bs@email.com");
		
		when(mockPersonModel.getPersonById(personToUpdate.getIdPerson())).thenReturn(null);

		ObjectMapper objectMapper = new ObjectMapper();
		
		mockMvc.perform(put("/persons/{id}", personToUpdate.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personUpdated)))
				.andExpect(status().isNotFound());
		
		verify(mockPersonModel, times(1)).getPersonById(anyString());
		verify(mockPersonModel, never()).updatePerson(any(Person.class));
	}
	
	// @DeleteMapping(value = "/persons/{id}")
	@Test
	public void deletePerson_whenPersonExist() throws Exception {

		Person personToDelete = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonModel.getPersonById(personToDelete.getIdPerson())).thenReturn(personToDelete);
		when(mockPersonModel.deletePerson(personToDelete.getIdPerson())).thenReturn(personToDelete);
		when(mockPersonModel.personExist(personToDelete)).thenReturn(false);

		mockMvc.perform(delete("/persons/{id}", personToDelete.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isGone());
		
		verify(mockPersonModel, times(1)).getPersonById(anyString());
		verify(mockPersonModel, times(1)).deletePerson(anyString());
		verify(mockPersonModel, times(1)).personExist(any(Person.class));
	}
	
	// @DeleteMapping(value = "/persons/{id}")
	@Test
	public void deletePerson_whenPersonNotExist() throws Exception {

		Person personToDelete = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonModel.getPersonById(personToDelete.getIdPerson())).thenReturn(null);

		mockMvc.perform(delete("/persons/{id}", personToDelete.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		
		verify(mockPersonModel, times(1)).getPersonById(anyString());
		verify(mockPersonModel, never()).deletePerson(anyString());
		verify(mockPersonModel, never()).personExist(any(Person.class));
	}

	// @DeleteMapping(value = "/persons/{id}")
	@Test
	public void deletePerson_whenPersonExist_whenInternalServerError() throws Exception {

		Person personToDelete = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonModel.getPersonById(personToDelete.getIdPerson())).thenReturn(personToDelete);
		when(mockPersonModel.deletePerson(personToDelete.getIdPerson())).thenReturn(personToDelete);
		when(mockPersonModel.personExist(personToDelete)).thenReturn(true);
		
		mockMvc.perform(delete("/persons/{id}", personToDelete.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
		
		verify(mockPersonModel, times(1)).getPersonById(anyString());
		verify(mockPersonModel, times(1)).deletePerson(anyString());
		verify(mockPersonModel, times(1)).personExist(any(Person.class));
	}

}
