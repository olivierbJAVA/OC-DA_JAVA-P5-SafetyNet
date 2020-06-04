package com.safetynet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.safetynet.entities.endpoints.Person;

//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@SpringBootTest
class SafetyNetApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;
	
	private static final String URL = "http://localhost:8080";
	
	private String getURLWithPort(String uri) {
		return URL + uri;
	}
	
	
	/*
	@Test
	void contextLoads() {
		
	}
	*/
	
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

		ResponseEntity<Object> responseEntity = restTemplate.getForEntity(getURLWithPort("/persons"), Object.class);
		assertNotNull(responseEntity);
		
		/*
		when(mockPersonModel.getAllPersons()).thenReturn(allPersonsToGet);

		mockMvc.perform(get("/persons")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$[0].lastName", is(personToGet1.getLastName())))
				.andExpect(jsonPath("$[1].lastName", is(personToGet2.getLastName())))
				.andExpect(jsonPath("$[2].lastName", is(personToGet3.getLastName())));
				
		verify(mockPersonModel, times(1)).getAllPersons();
		*/
	}
	
}
