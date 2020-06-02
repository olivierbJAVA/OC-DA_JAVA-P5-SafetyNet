package com.safetynet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.controllers.EndpointPersonsController;
import com.safetynet.entities.endpoints.Person;
import com.safetynet.exception.InternalServerErrorException;
import com.safetynet.exception.RessourceAlreadyExistException;
import com.safetynet.exception.RessourceNotFoundException;
import com.safetynet.model.endpoints.IPersonModel;

//@ExtendWith(SpringExtension.class)
@WebMvcTest(EndpointPersonsController.class)
public class EndpointPersonsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IPersonModel personModelImpl;

	@BeforeAll
	private static void setUp() throws Exception {

	}

	@BeforeEach
	private void setUpPerTest() throws Exception {

	}

	// @GetMapping(value = "/persons/{id}")
	@Test
	public void getPersonById_whenPersonExist() throws Exception {

		Person personToGet = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(personModelImpl.getPersonById(personToGet.getIdPerson())).thenReturn(personToGet);

		/*
		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders.get("/persons/{id}", "BertrandSimon").contentType(MediaType.APPLICATION_JSON));

		resultActions.andExpect(MockMvcResultMatchers.status().isFound())
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(personTest.getFirstName())));
		*/
		
		mockMvc.perform(get("/persons/{id}", "BertrandSimon")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound())
				.andExpect(jsonPath("$.firstName", is(personToGet.getFirstName())));
		
		verify(personModelImpl, times(1)).getPersonById(anyString());
	}

	// @GetMapping(value = "/persons/{id}")
	@Test
	public void getPersonById_whenPersonNotExist() throws Exception {

		Person personToGet = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		//when(personModelImpl.getPersonById(personToGet.getIdPerson())).thenThrow(new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", personToGet.getIdPerson()));
		when(personModelImpl.getPersonById(personToGet.getIdPerson())).thenReturn(null);
		
		mockMvc.perform(get("/persons/{id}", "BertrandSimon")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		
		verify(personModelImpl, times(1)).getPersonById(anyString());
		
		//assertThrows(RessourceNotFoundException.class, () -> personModelImpl.getPersonById(personToGet.getIdPerson()));
		
	}
	
	// @PostMapping(value = "/persons")
	@Test
	public void addPerson_whenPersonNotAlreadyExist() throws Exception {

		Person personToAdd = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(personModelImpl.personExist(any(Person.class))).thenReturn(false).thenReturn(true);
		// when(personModelImpl.personExist(personTest)).thenReturn(false).thenReturn(true);
		when(personModelImpl.addPerson(personToAdd)).thenReturn(null);

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(personToAdd);

		mockMvc.perform(post("/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isCreated());
		
		verify(personModelImpl, times(1)).addPerson(any(Person.class));
		verify(personModelImpl, times(2)).personExist(any(Person.class));
	}
	
	// @PostMapping(value = "/persons")
	@Test
	public void addPerson_whenPersonAlreadyExist() throws Exception {

		Person personToAdd = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");
		/*
		when(personModelImpl.personExist(any(Person.class))).thenThrow(new RessourceAlreadyExistException(HttpStatus.BAD_REQUEST, "Error ressource already exist : ",
				personToAdd.getFirstName() + personToAdd.getLastName()));
		*/		
		when(personModelImpl.personExist(any(Person.class))).thenReturn(true);
		// when(personModelImpl.personExist(personTest)).thenReturn(false).thenReturn(true);
		
		//when(personModelImpl.addPerson(personToAdd)).thenReturn(null);

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(personToAdd);
		
		mockMvc.perform(post("/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isBadRequest());
		
		verify(personModelImpl, times(1)).personExist(any(Person.class));
		verify(personModelImpl, never()).addPerson(any(Person.class));
		
		//assertThrows(RessourceAlreadyExistException.class, () -> personModelImpl.personExist(personToAdd));
		
	}
	
	// @PostMapping(value = "/persons")
	@Test
	public void addPerson_whenPersonNotAlreadyExist_whenInternalServerError() throws Exception {

		Person personToAdd = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		//when(personModelImpl.personExist(any(Person.class))).thenReturn(false).thenThrow(new InternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the operation"));
		when(personModelImpl.personExist(personToAdd)).thenReturn(false).thenReturn(false);
		when(personModelImpl.addPerson(personToAdd)).thenReturn(null);

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(personToAdd);

		mockMvc.perform(post("/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isInternalServerError());
		
		verify(personModelImpl, times(1)).addPerson(any(Person.class));
		verify(personModelImpl, times(2)).personExist(any(Person.class));
		
		//assertThrows(InternalServerErrorException.class, () -> personModelImpl.personExist(personToAdd));
	}
	
	// @PutMapping(value = "/persons/{id}")
	@Test
	public void updatePerson_whenPersonExist() throws Exception {

		Person personToUpdate = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		Person personUpdated = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Marseille", "75000",
				"0696469887", "bs@email.com");
		
		when(personModelImpl.getPersonById(personToUpdate.getIdPerson())).thenReturn(personToUpdate);
		when(personModelImpl.updatePerson(personUpdated)).thenReturn(personToUpdate);

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(personUpdated);

		mockMvc.perform(put("/persons/{id}", personToUpdate.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.city", is(personUpdated.getCity())));
		
		verify(personModelImpl, times(1)).updatePerson(any(Person.class));

	}
	
	// @DeleteMapping(value = "/persons/{id}")
	@Test
	public void deletePerson_whenPersonExist() throws Exception {

		Person personToDelete = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		
		when(personModelImpl.getPersonById(personToDelete.getIdPerson())).thenReturn(personToDelete);
		when(personModelImpl.personExist(personToDelete)).thenReturn(false);
		
		when(personModelImpl.deletePerson(personToDelete.getIdPerson())).thenReturn(personToDelete);

		mockMvc.perform(delete("/persons/{id}", personToDelete.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isGone());
		
		verify(personModelImpl, times(1)).deletePerson(anyString());

	}
	
}
