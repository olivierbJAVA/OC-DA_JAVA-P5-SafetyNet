package com.safetynet;

import static org.hamcrest.CoreMatchers.is;
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
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.controllers.EndpointPersonsController;
import com.safetynet.entities.endpoints.Person;
import com.safetynet.model.endpoints.IPersonModel;

//@ExtendWith(SpringExtension.class)
@WebMvcTest(EndpointPersonsController.class)
public class EndpointPersonsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IPersonModel mockPersonModel;

	// @GetMapping(value = "/persons")
	@Test
	public void getAllPersons() throws Exception {

		Person personTest1 = new Person("BertrandSimon", "Bertrand", "Simon", "address1", "Paris", "75000",
				"0696469887", "bs@email.com");
		Person personTest2 = new Person("BertrandSimon", "Bertrand", "Simon", "address2", "Paris", "75000",
				"0696469887", "bs@email.com");
		Person personTest3 = new Person("BertrandSimon", "Bertrand", "Simon", "address3", "Paris", "75000",
				"0696469887", "bs@email.com");

		List<Person> allPersons = new ArrayList<>();
		allPersons.add(personTest1);
		allPersons.add(personTest2);
		allPersons.add(personTest3);

		when(mockPersonModel.getAllPersons()).thenReturn(allPersons);

		mockMvc.perform(get("/persons")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound())
				.andExpect(content().contentType("application/json"));
				//.andExpect(jsonPath("$.firstName", is(personToGet.getFirstName())));
		
		verify(mockPersonModel, times(1)).getAllPersons();
	}
		
	// @GetMapping(value = "/persons/{id}")
	@Test
	public void getPersonById_whenPersonExist() throws Exception {

		Person personToGet = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonModel.getPersonById(personToGet.getIdPerson())).thenReturn(personToGet);

		mockMvc.perform(get("/persons/{id}", "BertrandSimon")
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
		
		mockMvc.perform(get("/persons/{id}", "BertrandSimon")
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
		String json = objectMapper.writeValueAsString(personToAdd);

		mockMvc.perform(post("/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isCreated());
		
		verify(mockPersonModel, times(2)).personExist(any(Person.class));
		verify(mockPersonModel, times(1)).addPerson(any(Person.class));
	}
	
	// @PostMapping(value = "/persons")
	@Test
	public void addPerson_whenPersonAlreadyExist() throws Exception {

		Person personToAdd = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		when(mockPersonModel.personExist(any(Person.class))).thenReturn(true);

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(personToAdd);
		
		mockMvc.perform(post("/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
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
		String json = objectMapper.writeValueAsString(personToAdd);

		mockMvc.perform(post("/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isInternalServerError());

		verify(mockPersonModel, times(2)).personExist(any(Person.class));
		verify(mockPersonModel, times(1)).addPerson(any(Person.class));
	}
	
	// @PutMapping(value = "/persons/{id}")
	@Test
	public void updatePerson_whenPersonExist() throws Exception {

		Person personToUpdate = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		Person personUpdated = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Marseille", "75000",
				"0696469887", "bs@email.com");
		
		when(mockPersonModel.getPersonById(personToUpdate.getIdPerson())).thenReturn(personToUpdate);
		when(mockPersonModel.updatePerson(personUpdated)).thenReturn(personToUpdate);

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(personUpdated);

		mockMvc.perform(put("/persons/{id}", personToUpdate.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.city", is(personUpdated.getCity())));
		
		verify(mockPersonModel, times(1)).getPersonById(anyString());
		verify(mockPersonModel, times(1)).updatePerson(any(Person.class));
	}
	
	// @PutMapping(value = "/persons/{id}")
	@Test
	public void updatePerson_whenPersonNotExist() throws Exception {

		Person personToUpdate = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Paris", "75000",
				"0696469887", "bs@email.com");

		Person personUpdated = new Person("BertrandSimon", "Bertrand", "Simon", "2 rue de Paris", "Marseille", "75000",
				"0696469887", "bs@email.com");
		
		when(mockPersonModel.getPersonById(personToUpdate.getIdPerson())).thenReturn(null);
	
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(personUpdated);

		mockMvc.perform(put("/persons/{id}", personToUpdate.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
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
		when(mockPersonModel.personExist(personToDelete)).thenReturn(false);
		
		when(mockPersonModel.deletePerson(personToDelete.getIdPerson())).thenReturn(personToDelete);

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
		when(mockPersonModel.personExist(personToDelete)).thenReturn(false);
		
		when(mockPersonModel.deletePerson(personToDelete.getIdPerson())).thenReturn(personToDelete);

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
		when(mockPersonModel.personExist(personToDelete)).thenReturn(true);
		
		when(mockPersonModel.deletePerson(personToDelete.getIdPerson())).thenReturn(personToDelete);

		mockMvc.perform(delete("/persons/{id}", personToDelete.getIdPerson())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
		
		verify(mockPersonModel, times(1)).getPersonById(anyString());
		verify(mockPersonModel, times(1)).deletePerson(anyString());
		verify(mockPersonModel, times(1)).personExist(any(Person.class));
	}

}
