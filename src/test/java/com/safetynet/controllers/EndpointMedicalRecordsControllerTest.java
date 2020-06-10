package com.safetynet.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.entities.endpoints.MedicalRecord;
import com.safetynet.service.endpoints.IMedicalRecordService;

/**
 * Class including unit tests for the EndpointMedicalRecordsController Class.
 */
@WebMvcTest(EndpointMedicalRecordsController.class)
public class EndpointMedicalRecordsControllerTest {
	
	private static final Logger logger = LoggerFactory.getLogger(EndpointMedicalRecordsControllerTest.class);

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IMedicalRecordService mockMedicalRecordService;

	private ObjectMapper objectMapper;
		
	@BeforeEach
	private void setUpPerTest() {
		objectMapper = new ObjectMapper();
	}	
	
	// @GetMapping(value = "/medicalRecords")
	@Test
	public void getAllMedicalRecords() {
		//ARRANGE
		MedicalRecord medicalRecordToGet1 = new MedicalRecord("BertrandSimon1", "Bertrand", "Simon1", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		MedicalRecord medicalRecordToGet2 = new MedicalRecord("BertrandSimon2", "Bertrand", "Simon2", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		MedicalRecord medicalRecordToGet3 = new MedicalRecord("BertrandSimon3", "Bertrand", "Simon3", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		List<MedicalRecord> allMedicalRecordsToGet = new ArrayList<>();
		allMedicalRecordsToGet.add(medicalRecordToGet1);
		allMedicalRecordsToGet.add(medicalRecordToGet2);
		allMedicalRecordsToGet.add(medicalRecordToGet3);
		
		when(mockMedicalRecordService.getAllMedicalRecords()).thenReturn(allMedicalRecordsToGet);

		//ACT & ASSERT
		try {
			mockMvc.perform(get("/medicalRecords")
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isFound())
					.andExpect(jsonPath("$[0].lastName", is(medicalRecordToGet1.getLastName())))
					.andExpect(jsonPath("$[1].lastName", is(medicalRecordToGet2.getLastName())))
					.andExpect(jsonPath("$[2].lastName", is(medicalRecordToGet3.getLastName())));
		} catch (Exception e) {
			logger.error("Error in MockMvc", e);
		}
		
		verify(mockMedicalRecordService, times(1)).getAllMedicalRecords();
	}
		
	// @GetMapping(value = "/medicalRecords/{id}")
	@Test
	public void getMedicalRecordById_whenMedicalRecordExist() {
		//ARRANGE
		MedicalRecord medicalRecordToGet = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.getMedicalRecordById(medicalRecordToGet.getIdMedicalRecord())).thenReturn(medicalRecordToGet);

		//ACT & ASSERT
		try {
			mockMvc.perform(get("/medicalRecords/{id}", medicalRecordToGet.getIdMedicalRecord())
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isFound())
					.andExpect(jsonPath("$.firstName", is(medicalRecordToGet.getFirstName())));
		} catch (Exception e) {
			logger.error("Error in MockMvc", e);;
		}
		
		verify(mockMedicalRecordService, times(1)).getMedicalRecordById(medicalRecordToGet.getIdMedicalRecord());
	}

	// @GetMapping(value = "/medicalRecords/{id}")
	@Test
	public void getMedicalRecordById_whenMedicalRecordNotExist() {
		//ARRANGE
		MedicalRecord medicalRecordToGet = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.getMedicalRecordById(medicalRecordToGet.getIdMedicalRecord())).thenReturn(null);
		
		//ACT & ASSERT
		try {
			mockMvc.perform(get("/medicalRecords/{id}", medicalRecordToGet.getIdMedicalRecord())
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound());
		} catch (Exception e) {
			logger.error("Error in MockMvc", e);
		}
		
		verify(mockMedicalRecordService, times(1)).getMedicalRecordById(medicalRecordToGet.getIdMedicalRecord());
	}
	
	// @PostMapping(value = "/medicalRecords")
	@Test
	public void addMedicalRecord_whenMedicalRecordNotAlreadyExist() {
		//ARRANGE
		MedicalRecord medicalRecordToAdd = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.medicalRecordExist(any(MedicalRecord.class))).thenReturn(false).thenReturn(true);
		when(mockMedicalRecordService.addMedicalRecord(medicalRecordToAdd)).thenReturn(null);

		//ACT & ASSERT
		MvcResult mvcResult=null;
		try {
			mvcResult = mockMvc.perform(post("/medicalRecords")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(medicalRecordToAdd)))
					.andExpect(status().isCreated())
					.andReturn();
		} catch (Exception e) {
			logger.error("Error in MockMvc", e);
		}
	
		verify(mockMedicalRecordService, times(2)).medicalRecordExist(any(MedicalRecord.class));
		verify(mockMedicalRecordService, times(1)).addMedicalRecord(medicalRecordToAdd);
	
		String actualResponseHeaderLocation = mvcResult.getResponse().getHeader("Location");
		assertEquals("http://localhost/medicalRecords/BertrandSimon", actualResponseHeaderLocation);
	}
	
	// @PostMapping(value = "/medicalRecords")
	@Test
	public void addMedicalRecord_whenMedicalRecordAlreadyExist() {
		//ARRANGE
		MedicalRecord medicalRecordToAdd = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.medicalRecordExist(any(MedicalRecord.class))).thenReturn(true);

		//ACT & ASSERT
		try {
			mockMvc.perform(post("/medicalRecords")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(medicalRecordToAdd)))
					.andExpect(status().isBadRequest());
		} catch (Exception e) {
			logger.error("Error in MockMvc", e);
		}
		
		verify(mockMedicalRecordService, times(1)).medicalRecordExist(any(MedicalRecord.class));
		verify(mockMedicalRecordService, never()).addMedicalRecord(medicalRecordToAdd);
	}
	
	// @PostMapping(value = "/medicalRecords")
	@Test
	public void addMedicalRecord_whenMedicalRecordNotAlreadyExist_whenInternalServerError() {
		//ARRANGE
		MedicalRecord medicalRecordToAdd = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.medicalRecordExist(any(MedicalRecord.class))).thenReturn(false).thenReturn(false);
		when(mockMedicalRecordService.addMedicalRecord(medicalRecordToAdd)).thenReturn(null);

		//ACT & ASSERT
		try {
			mockMvc.perform(post("/medicalRecords")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(medicalRecordToAdd)))
					.andExpect(status().isInternalServerError());
		} catch (Exception e) {
			logger.error("Error in MockMvc", e);
		}

		verify(mockMedicalRecordService, times(2)).medicalRecordExist(any(MedicalRecord.class));
		verify(mockMedicalRecordService, times(1)).addMedicalRecord(medicalRecordToAdd);
	}
	
	// @PutMapping(value = "/medicalRecords/{id}")
	@Test
	public void updateMedicalRecord_whenMedicalRecordExist() {
		//ARRANGE
		MedicalRecord medicalRecordToUpdate = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		MedicalRecord medicalRecordUpdated = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1983",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.getMedicalRecordById(medicalRecordToUpdate.getIdMedicalRecord())).thenReturn(medicalRecordToUpdate).thenReturn(medicalRecordToUpdate).thenReturn(medicalRecordUpdated);
		when(mockMedicalRecordService.updateMedicalRecord(medicalRecordUpdated)).thenReturn(medicalRecordToUpdate);

		//ACT & ASSERT
		try {
			mockMvc.perform(put("/medicalRecords/{id}", medicalRecordToUpdate.getIdMedicalRecord())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(medicalRecordUpdated)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.birthdate", is(medicalRecordUpdated.getBirthdate())));
		} catch (Exception e) {
			logger.error("Error in MockMvc", e);
		}

		verify(mockMedicalRecordService, times(3)).getMedicalRecordById(medicalRecordToUpdate.getIdMedicalRecord());
		verify(mockMedicalRecordService, times(1)).updateMedicalRecord(medicalRecordUpdated);
	}
	
	// @PutMapping(value = "/medicalRecords/{id}")
	@Test
	public void updateMedicalRecord_whenMedicalRecordInPathRequestNotExist() {
		//ARRANGE
		MedicalRecord medicalRecordToUpdate = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		MedicalRecord medicalRecordUpdated = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1983",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.getMedicalRecordById(medicalRecordToUpdate.getIdMedicalRecord())).thenReturn(null);

		//ACT & ASSERT
		try {
			mockMvc.perform(put("/medicalRecords/{id}", medicalRecordToUpdate.getIdMedicalRecord())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(medicalRecordUpdated)))
					.andExpect(status().isNotFound());
		} catch (Exception e) {
			logger.error("Error in MockMvc", e);
		}
		
		verify(mockMedicalRecordService, times(1)).getMedicalRecordById(medicalRecordToUpdate.getIdMedicalRecord());
		verify(mockMedicalRecordService, never()).updateMedicalRecord(any(MedicalRecord.class));
	}
	
	// @PutMapping(value = "/medicalRecords/{id}")
	@Test
	public void updateMedicalRecord_whenMedicalRecordInRequestBodyNotExist() {
		//ARRANGE
		MedicalRecord medicalRecordToUpdate = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		MedicalRecord medicalRecordUpdated = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1983",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.getMedicalRecordById(medicalRecordToUpdate.getIdMedicalRecord())).thenReturn(medicalRecordToUpdate).thenReturn(null);

		//ACT & ASSERT
		try {
			mockMvc.perform(put("/medicalRecords/{id}", medicalRecordToUpdate.getIdMedicalRecord())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(medicalRecordUpdated)))
					.andExpect(status().isNotFound());
		} catch (Exception e) {
			logger.error("Error in MockMvc", e);;
		}
		
		verify(mockMedicalRecordService, times(2)).getMedicalRecordById(medicalRecordToUpdate.getIdMedicalRecord());
		verify(mockMedicalRecordService, never()).updateMedicalRecord(any(MedicalRecord.class));
	}
	
	// @PutMapping(value = "/medicalRecords/{id}")
	@Test
	public void updateMedicalRecord_whenMedicalRecordExist_whenInternalServerError() {
		//ARRANGE
		MedicalRecord medicalRecordToUpdate = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		MedicalRecord medicalRecordUpdated = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1983",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.getMedicalRecordById(medicalRecordToUpdate.getIdMedicalRecord())).thenReturn(medicalRecordToUpdate).thenReturn(medicalRecordToUpdate).thenReturn(medicalRecordToUpdate);
		when(mockMedicalRecordService.updateMedicalRecord(medicalRecordUpdated)).thenReturn(medicalRecordToUpdate);

		//ACT & ASSERT
		try {
			mockMvc.perform(put("/medicalRecords/{id}", medicalRecordToUpdate.getIdMedicalRecord())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(medicalRecordUpdated)))
					.andExpect(status().isInternalServerError());
		} catch (Exception e) {
			logger.error("Error in MockMvc", e);
		}
		
		verify(mockMedicalRecordService, times(3)).getMedicalRecordById(medicalRecordToUpdate.getIdMedicalRecord());
		verify(mockMedicalRecordService, times(1)).updateMedicalRecord(medicalRecordUpdated);
	}
	
	// @DeleteMapping(value = "/medicalRecords/{id}")
	@Test
	public void deleteMedicalRecord_whenMedicalRecordExist() {
		//ARRANGE
		MedicalRecord medicalRecordToDelete = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.getMedicalRecordById(medicalRecordToDelete.getIdMedicalRecord())).thenReturn(medicalRecordToDelete);
		when(mockMedicalRecordService.deleteMedicalRecord(medicalRecordToDelete.getIdMedicalRecord())).thenReturn(medicalRecordToDelete);
		when(mockMedicalRecordService.medicalRecordExist(medicalRecordToDelete)).thenReturn(false);

		//ACT & ASSERT
		try {
			mockMvc.perform(delete("/medicalRecords/{id}", medicalRecordToDelete.getIdMedicalRecord())
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isGone());
		} catch (Exception e) {
			logger.error("Error in MockMvc", e);
		}
		
		verify(mockMedicalRecordService, times(1)).getMedicalRecordById(medicalRecordToDelete.getIdMedicalRecord());
		verify(mockMedicalRecordService, times(1)).deleteMedicalRecord(medicalRecordToDelete.getIdMedicalRecord());
		verify(mockMedicalRecordService, times(1)).medicalRecordExist(medicalRecordToDelete);
	}
	
	// @DeleteMapping(value = "/medicalRecords/{id}")
	@Test
	public void deleteMedicalRecord_whenMedicalRecordNotExist() {
		//ARRANGE
		MedicalRecord medicalRecordToDelete = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.getMedicalRecordById(medicalRecordToDelete.getIdMedicalRecord())).thenReturn(null);

		//ACT & ASSERT
		try {
			mockMvc.perform(delete("/medicalRecords/{id}", medicalRecordToDelete.getIdMedicalRecord())
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound());
		} catch (Exception e) {
			logger.error("Error in MockMvc", e);
		}
		
		verify(mockMedicalRecordService, times(1)).getMedicalRecordById(medicalRecordToDelete.getIdMedicalRecord());
		verify(mockMedicalRecordService, never()).deleteMedicalRecord(anyString());
		verify(mockMedicalRecordService, never()).medicalRecordExist(any(MedicalRecord.class));
	}

	// @DeleteMapping(value = "/medicalRecords/{id}")
	@Test
	public void deleteMedicalRecord_whenMedicalRecordExist_whenInternalServerError() {
		//ARRANGE
		MedicalRecord medicalRecordToDelete = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.getMedicalRecordById(medicalRecordToDelete.getIdMedicalRecord())).thenReturn(medicalRecordToDelete);
		when(mockMedicalRecordService.deleteMedicalRecord(medicalRecordToDelete.getIdMedicalRecord())).thenReturn(medicalRecordToDelete);
		when(mockMedicalRecordService.medicalRecordExist(medicalRecordToDelete)).thenReturn(true);
	
		//ACT & ASSERT
		try {
			mockMvc.perform(delete("/medicalRecords/{id}", medicalRecordToDelete.getIdMedicalRecord())
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isInternalServerError());
		} catch (Exception e) {
			logger.error("Error in MockMvc", e);
		}
		
		verify(mockMedicalRecordService, times(1)).getMedicalRecordById(medicalRecordToDelete.getIdMedicalRecord());
		verify(mockMedicalRecordService, times(1)).deleteMedicalRecord(medicalRecordToDelete.getIdMedicalRecord());
		verify(mockMedicalRecordService, times(1)).medicalRecordExist(medicalRecordToDelete);
	}

}
