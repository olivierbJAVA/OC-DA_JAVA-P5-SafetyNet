package com.safetynet;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.dao.IPersonDao;
import com.safetynet.entities.endpoints.Person;
import com.safetynet.entities.urls.Firestation;
import com.safetynet.model.endpoints.IFirestationMappingModel;
import com.safetynet.model.endpoints.IMedicalRecordModel;
import com.safetynet.model.endpoints.IPersonModel;
import com.safetynet.model.urls.IResponseUrlsModel;
import com.safetynet.model.urls.ResponseUrlsModelImpl;
import com.safetynet.util.IInitializeLists;
import com.safetynet.util.JsonFileInitializeListsImpl;

@ExtendWith(SpringExtension.class)
public class ResponseUrlsModelImplTest {

	@TestConfiguration
	static class ResponseUrlsModelImplTestContextConfiguration {
		
		@Bean
		public IResponseUrlsModel responseUrlsModelImpl() {
			return new ResponseUrlsModelImpl();
		}
	
		@Bean
		public IInitializeLists jsonFileInitializeListsImpl() {
			return new JsonFileInitializeListsImpl();
		}
		
	}
	
	@Autowired
	private IResponseUrlsModel responseUrlsModelUnderTest;

	@MockBean
	private IPersonModel mockPersonModel;

	@MockBean
	private IFirestationMappingModel mockFirestationMappingModel;

	@MockBean
	private IMedicalRecordModel mockMedicalRecordModel;
	
	@Autowired
	private IInitializeLists jsonFileInitializeListsImpl;
	
	@BeforeAll
	public void beforeAll() {
		
		jsonFileInitializeListsImpl.initializeLists();
	}
	
	@Test
	public void responseFirestation() {
		//List<Person> listAllPersons = personModel.getAllPersons();

		//when(mockPersonModel.getAllPersons()).thenReturn(value)
		
	}
	
}
