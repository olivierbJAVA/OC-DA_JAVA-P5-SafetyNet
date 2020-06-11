package com.safetynet.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Class including unit tests for the DefaultController Class.
 */
@WebMvcTest(DefaultController.class)
public class DefaultControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(DefaultControllerTest.class);

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void defautController() {
		// ARRANGE

		// ACT & ASSERT
		try {
			mockMvc.perform(get("/")
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(content().string(containsString("Welcome to the SafetyNet API !")));

		} catch (Exception e) {
			logger.error("Error in MockMvc", e);
		}
	}
}
