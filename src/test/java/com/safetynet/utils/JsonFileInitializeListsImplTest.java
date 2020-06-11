package com.safetynet.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.entities.endpoints.FirestationMapping;
import com.safetynet.entities.endpoints.MedicalRecord;
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

/**
 * Class including unit tests for the JsonFileInitializeListsImpl Class.
 */
@ExtendWith(SpringExtension.class)
public class JsonFileInitializeListsImplTest {

	// We use a dedicated input data file for tests purposes
	private static String filePathInputDataForTests = "./data-test.json";

	@TestConfiguration
	static class JsonFileInitializeListsImplTestContextConfiguration {

		@Bean
		public IInitializeLists initializeLists() {
			return new JsonFileInitializeListsImpl(filePathInputDataForTests);
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

	@Autowired
	private IFirestationMappingService firestationMappingService;

	@Autowired
	private IMedicalRecordService medicalRecordService;

	@Test
	public void getInitialData_whenInputFileIsFound() {
		// ARRANGE
		// Expected persons to be in the list of persons following data initialization
		Person person1 = new Person("RogerBoyd", "Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		Person person2 = new Person("PeterDuncan", "Peter", "Duncan", "644 Gershwin Cir", "Culver", "97451",
				"841-874-6512", "jaboyd@email.com");
		Person person3 = new Person("ShawnaStelzer", "Shawna", "Stelzer", "947 E. Rose Dr", "Culver", "97451",
				"841-874-7784", "ssanw@email.com");
		Person person4 = new Person("JacobBoyd", "Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513",
				"drk@email.com");
		Person person5 = new Person("TenleyBoyd", "Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"tenz@email.com");
		Person person6 = new Person("JonanathanMarrack", "Jonanathan", "Marrack", "29 15th St", "Culver", "97451",
				"841-874-6513", "drk@email.com");
		Person person7 = new Person("KendrikStelzer", "Kendrik", "Stelzer", "947 E. Rose Dr", "Culver", "97451",
				"841-874-7784", "bstel@email.com");
		Person person8 = new Person("AllisonBoyd", "Allison", "Boyd", "112 Steppes Pl", "Culver", "97451",
				"841-874-9888", "aly@imail.com");
		Person person9 = new Person("WarrenZemicks", "Warren", "Zemicks", "892 Downing Ct", "Culver", "97451",
				"841-874-7512", "ward@email.com");
		Person person10 = new Person("ReginoldWalker", "Reginold", "Walker", "908 73rd St", "Culver", "97451",
				"841-874-8547", "reg@email.com");
		Person person11 = new Person("TessaCarman", "Tessa", "Carman", "834 Binoc Ave", "Culver", "97451",
				"841-874-6512", "tenz@email.com");
		Person person12 = new Person("LilyCooper", "Lily", "Cooper", "489 Manchester St", "Culver", "97451",
				"841-874-9845", "lily@email.com");
		Person person13 = new Person("EricCadigan", "Eric", "Cadigan", "951 LoneTree Rd", "Culver", "97451",
				"841-874-7458", "gramps@email.com");
		Person person14 = new Person("JohnBoyd", "John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		Person person15 = new Person("JamiePeters", "Jamie", "Peters", "908 73rd St", "Culver", "97451", "841-874-7462",
				"jpeter@email.com");
		Person person16 = new Person("BrianStelzer", "Brian", "Stelzer", "947 E. Rose Dr", "Culver", "97451",
				"841-874-7784", "bstel@email.com");
		Person person17 = new Person("CliveFerguson", "Clive", "Ferguson", "748 Townings Dr", "Culver", "97451",
				"841-874-6741", "clivfd@ymail.com");
		Person person18 = new Person("FosterShepard", "Foster", "Shepard", "748 Townings Dr", "Culver", "97451",
				"841-874-6544", "jaboyd@email.com");
		Person person19 = new Person("RonPeters", "Ron", "Peters", "112 Steppes Pl", "Culver", "97451", "841-874-8888",
				"jpeter@email.com");
		Person person20 = new Person("TonyCooper", "Tony", "Cooper", "112 Steppes Pl", "Culver", "97451",
				"841-874-6874", "tcoop@ymail.com");
		Person person21 = new Person("SophiaZemicks", "Sophia", "Zemicks", "892 Downing Ct", "Culver", "97451",
				"841-874-7878", "soph@email.com");
		Person person22 = new Person("FeliciaBoyd", "Felicia", "Boyd", "1509 Culver St", "Culver", "97451",
				"841-874-6544", "jaboyd@email.com");
		Person person23 = new Person("ZachZemicks", "Zach", "Zemicks", "892 Downing Ct", "Culver", "97451",
				"841-874-7512", "zarc@email.com");

		// Expected firestationMappings to be in the list of firestationMappings
		// following data initialization
		FirestationMapping firestationMapping1 = new FirestationMapping("748 Townings Dr", "3");
		FirestationMapping firestationMapping2 = new FirestationMapping("951 LoneTree Rd", "2");
		FirestationMapping firestationMapping3 = new FirestationMapping("489 Manchester St", "4");
		FirestationMapping firestationMapping4 = new FirestationMapping("908 73rd St", "1");
		FirestationMapping firestationMapping5 = new FirestationMapping("947 E. Rose Dr", "1");
		FirestationMapping firestationMapping6 = new FirestationMapping("644 Gershwin Cir", "1");
		FirestationMapping firestationMapping7 = new FirestationMapping("1509 Culver St", "3");
		FirestationMapping firestationMapping8 = new FirestationMapping("892 Downing Ct", "2");
		FirestationMapping firestationMapping9 = new FirestationMapping("834 Binoc Ave", "3");
		FirestationMapping firestationMapping10 = new FirestationMapping("112 Steppes Pl", "4");
		FirestationMapping firestationMapping11 = new FirestationMapping("29 15th St", "2");

		// Expected medicalRecords to be in the list of medicalRecords following data
		// initialization
		MedicalRecord medicalRecord1 = new MedicalRecord("JohnBoyd", "John", "Boyd", "03/06/1984",
				new String[] { "aznol:350mg", "hydrapermazol:100mg" }, new String[] { "nillacilan" });
		MedicalRecord medicalRecord2 = new MedicalRecord("JacobBoyd", "Jacob", "Boyd", "03/06/1989",
				new String[] { "pharmacol:5000mg", "terazine:10mg", "noznazol:250mg" }, new String[] {});
		MedicalRecord medicalRecord3 = new MedicalRecord("TenleyBoyd", "Tenley", "Boyd", "02/18/2012", new String[] {},
				new String[] { "peanut" });
		MedicalRecord medicalRecord4 = new MedicalRecord("RogerBoyd", "Roger", "Boyd", "09/06/2017", new String[] {},
				new String[] {});
		MedicalRecord medicalRecord5 = new MedicalRecord("FeliciaBoyd", "Felicia", "Boyd", "01/08/1986",
				new String[] { "tetracyclaz:650mg" }, new String[] { "xilliathal" });
		MedicalRecord medicalRecord6 = new MedicalRecord("JonanathanMarrack", "Jonanathan", "Marrack", "01/03/1989",
				new String[] {}, new String[] {});
		MedicalRecord medicalRecord7 = new MedicalRecord("TessaCarman", "Tessa", "Carman", "02/18/2012",
				new String[] {}, new String[] {});
		MedicalRecord medicalRecord8 = new MedicalRecord("PeterDuncan", "Peter", "Duncan", "09/06/2000",
				new String[] {}, new String[] { "shellfish" });
		MedicalRecord medicalRecord9 = new MedicalRecord("FosterShepard", "Foster", "Shepard", "01/08/1980",
				new String[] {}, new String[] {});
		MedicalRecord medicalRecord10 = new MedicalRecord("TonyCooper", "Tony", "Cooper", "03/06/1994",
				new String[] { "hydrapermazol:300mg", "dodoxadin:30mg" }, new String[] { "shellfish" });
		MedicalRecord medicalRecord11 = new MedicalRecord("LilyCooper", "Lily", "Cooper", "03/06/1994", new String[] {},
				new String[] {});
		MedicalRecord medicalRecord12 = new MedicalRecord("SophiaZemicks", "Sophia", "Zemicks", "03/06/1988",
				new String[] { "aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg", "terazine:500mg" },
				new String[] { "peanut", "shellfish", "aznol" });
		MedicalRecord medicalRecord13 = new MedicalRecord("WarrenZemicks", "Warren", "Zemicks", "03/06/1985",
				new String[] {}, new String[] {});
		MedicalRecord medicalRecord14 = new MedicalRecord("ZachZemicks", "Zach", "Zemicks", "03/06/2017",
				new String[] {}, new String[] {});
		MedicalRecord medicalRecord15 = new MedicalRecord("ReginoldWalker", "Reginold", "Walker", "08/30/1979",
				new String[] { "thradox:700mg" }, new String[] { "illisoxian" });
		MedicalRecord medicalRecord16 = new MedicalRecord("JamiePeters", "Jamie", "Peters", "03/06/1982",
				new String[] {}, new String[] {});
		MedicalRecord medicalRecord17 = new MedicalRecord("RonPeters", "Ron", "Peters", "04/06/1965", new String[] {},
				new String[] {});
		MedicalRecord medicalRecord18 = new MedicalRecord("AllisonBoyd", "Allison", "Boyd", "03/15/1965",
				new String[] { "aznol:200mg" }, new String[] { "nillacilan" });
		MedicalRecord medicalRecord19 = new MedicalRecord("BrianStelzer", "Brian", "Stelzer", "12/06/1975",
				new String[] { "ibupurin:200mg", "hydrapermazol:400mg" }, new String[] { "nillacilan" });
		MedicalRecord medicalRecord20 = new MedicalRecord("ShawnaStelzer", "Shawna", "Stelzer", "07/08/1980",
				new String[] {}, new String[] {});
		MedicalRecord medicalRecord21 = new MedicalRecord("KendrikStelzer", "Kendrik", "Stelzer", "03/06/2014",
				new String[] { "noxidian:100mg", "pharmacol:2500mg" }, new String[] {});
		MedicalRecord medicalRecord22 = new MedicalRecord("CliveFerguson", "Clive", "Ferguson", "03/06/1994",
				new String[] {}, new String[] {});
		MedicalRecord medicalRecord23 = new MedicalRecord("EricCadigan", "Eric", "Cadigan", "08/06/1945",
				new String[] { "tradoxidine:400mg" }, new String[] {});

		// ACT
		jsonFileInitializeListsImpl.getInitialData();

		// ASSERT
		// We check that the list of persons generated contains the expected persons
		List<Person> actualListPersons = personService.getAllPersons();
		assertThat(actualListPersons).containsExactlyInAnyOrder(person1, person2, person3, person4, person5, person6,
				person7, person8, person9, person10, person11, person12, person13, person14, person15, person16,
				person17, person18, person19, person20, person21, person22, person23);

		// We check that the list of firestationMappings generated contains the expected
		// firestationMappings
		List<FirestationMapping> actualListFirestationMappings = firestationMappingService.getAllFirestationMappings();
		assertThat(actualListFirestationMappings).containsExactlyInAnyOrder(firestationMapping1, firestationMapping2,
				firestationMapping3, firestationMapping4, firestationMapping5, firestationMapping6, firestationMapping7,
				firestationMapping8, firestationMapping9, firestationMapping10, firestationMapping11);

		// We check that the list of medicalRecords generated contains the expected
		// medicalRecords
		List<MedicalRecord> actualListMedicalRecords = medicalRecordService.getAllMedicalRecords();
		assertThat(actualListMedicalRecords).containsExactlyInAnyOrder(medicalRecord1, medicalRecord2, medicalRecord3,
				medicalRecord4, medicalRecord5, medicalRecord6, medicalRecord7, medicalRecord8, medicalRecord9,
				medicalRecord10, medicalRecord11, medicalRecord12, medicalRecord13, medicalRecord14, medicalRecord15,
				medicalRecord16, medicalRecord17, medicalRecord18, medicalRecord19, medicalRecord20, medicalRecord21,
				medicalRecord22, medicalRecord23);
	}

	/*
	@Test
	public void getInitialData_whenInputFileNotFound() {
		jsonFileInitializeListsImpl = new JsonFileInitializeListsImpl("FilePathNotFound");

		assertThrows(FileNotFoundException.class, () -> {
			jsonFileInitializeListsImpl.getInitialData();
		});
	}
	*/
}
