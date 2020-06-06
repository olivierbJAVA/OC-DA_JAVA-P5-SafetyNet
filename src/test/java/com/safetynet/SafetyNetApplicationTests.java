package com.safetynet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.entities.endpoints.Person;
import com.safetynet.entities.urls.Firestation;
import com.safetynet.entities.urls.FirestationPerson;
import com.safetynet.repository.IPersonRepository;
import com.safetynet.util.IInitializeLists;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@SpringBootTest
class SafetyNetApplicationTests {
		
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private IInitializeLists initializeList;
	
	@Autowired
	private IPersonRepository personDaoImpl;
	
	@BeforeEach
	public void setupPerTest() {
		List<Person> persons = personDaoImpl.getAllPersons();
		for (Person person : persons) {
			personDaoImpl.deletePerson(person.getIdPerson());
		}
		initializeList.getInitialData();	
	}
	
	/*
	@Test
	void contextLoads() {
	}
	*/
	
	// @GetMapping("/")
	@Test
	public void testDefaultController() throws Exception {
/*
		assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/",String.class)).contains("Server response : OK");
		
		
		//ResponseEntity<Object> responseEntity = restTemplate.getForEntity("http://localhost:" + String.valueOf(port) + "/persons"), Object.class);
		//assertNotNull(responseEntity);
		
	}
	
	// http://localhost:8080/firestation?stationNumber=<station_number>
	@Test
	public void getUrlFirestation() throws Exception {

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

		ObjectMapper objectMapper = new ObjectMapper();
		
		//String actualResponseBody = mvcResult.getResponse().getContentAsString();
		assertEquals(objectMapper.writeValueAsString(firestationUrlResponse),
				this.testRestTemplate.getForObject("http://localhost:" + port + "/firestation?stationNumber=2",String.class) );
*/
	}

}
