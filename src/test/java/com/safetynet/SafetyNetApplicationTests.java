package com.safetynet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SafetyNetApplicationTests {

	private TestRestTemplate restTemplate;
	private static final String URL = "http://localhost:8080";
	
	private String getURLWithPort(String uri) {
		return URL + uri;
	}
	
	
	
	//@Test
	void contextLoads() {
	}

}
