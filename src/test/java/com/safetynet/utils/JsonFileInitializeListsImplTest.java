package com.safetynet.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.config.InputDataConfig;
import com.safetynet.entities.endpoints.Person;
import com.safetynet.repository.FirestationMappingRepositoryImpl;
import com.safetynet.repository.IFirestationMappingRepository;
import com.safetynet.repository.IMedicalRecordRepository;
import com.safetynet.repository.IPersonRepository;
import com.safetynet.repository.MedicalRecordRepositoryImpl;
import com.safetynet.repository.PersonRepositoryImpl;
import com.safetynet.service.endpoints.FirestationMappingServiceImpl;
import com.safetynet.service.endpoints.IFirestationMappingService;
import com.safetynet.service.endpoints.IMedicalRecordService;
import com.safetynet.service.endpoints.IPersonService;
import com.safetynet.service.endpoints.MedicalRecordServiceImpl;
import com.safetynet.service.endpoints.PersonServiceImpl;
import com.safetynet.util.IInitializeLists;
import com.safetynet.util.JsonFileInitializeListsImpl;

import junit.framework.Assert;

@ExtendWith(SpringExtension.class)
public class JsonFileInitializeListsImplTest {

	@TestConfiguration
	static class JsonFileInitializeListsImplTestContextConfiguration {
		@Bean
		public IInitializeLists initializeLists() {
			return new JsonFileInitializeListsImpl();
		}

		@Bean
		public IFirestationMappingService iFirestationMappingService() {
			return new FirestationMappingServiceImpl();
		}

		@Bean
		public IFirestationMappingRepository iFirestationMappingRepository() {
			return new FirestationMappingRepositoryImpl();
		}

		@Bean
		public IMedicalRecordService iMedicalRecordService() {
			return new MedicalRecordServiceImpl();
		}

		@Bean
		public IMedicalRecordRepository iMedicalRecordRepository() {
			return new MedicalRecordRepositoryImpl();
		}

		@Bean
		public IPersonService iPersonService() {
			return new PersonServiceImpl();
		}

		@Bean
		public IPersonRepository iPersonRepository() {
			return new PersonRepositoryImpl();
		}
	}

	@Autowired
	private IInitializeLists jsonFileInitializeListsImpl;

	@Autowired
	private IPersonService personService;

	@Test
	public void getInitialData() throws JsonProcessingException {

		InputDataConfig inputDataConfig = new InputDataConfig();

		List<Person> expectedListPersons = inputDataConfig.inputDataPerson();

		jsonFileInitializeListsImpl.getInitialData();

		List<Person> actualListPersons = personService.getAllPersons();

	//	assertEquals(expectedListPersons, actualListPersons);
		
		//assertThat(actualListPersons, is(expectedListPersons));
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		//assertThat(actualListPersons,  IsIterableContainingInOrder.contains(expectedListPersons.toArray()));
		
		assertEquals(objectMapper.writeValueAsString(expectedListPersons),objectMapper.writeValueAsString(actualListPersons));
	}
}
