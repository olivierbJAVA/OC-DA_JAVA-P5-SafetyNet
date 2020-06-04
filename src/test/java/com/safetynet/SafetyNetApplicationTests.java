package com.safetynet;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.safetynet.entities.endpoints.Person;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@SpringBootTest
class SafetyNetApplicationTests {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	//private static final String URL = "http://localhost:8080";
	private String URL = "http://localhost:"+String.valueOf(port);
	
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
		
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",String.class)).contains("Server response : OK");
		
		/*
		ResponseEntity<Object> responseEntity = restTemplate.getForEntity(getURLWithPort("/persons"), Object.class);
		assertNotNull(responseEntity);
		
		ResponseEntity<Object> responseEntity = restTemplate.getForEntity("http://localhost:" + String.valueOf(port) + "/persons"), Object.class);
		assertNotNull(responseEntity);
		*/
	}
	
}
