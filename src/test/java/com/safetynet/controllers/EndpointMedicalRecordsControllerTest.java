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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.entities.endpoints.MedicalRecord;
import com.safetynet.service.endpoints.IMedicalRecordService;

@WebMvcTest(EndpointMedicalRecordsController.class)
public class EndpointMedicalRecordsControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IMedicalRecordService mockMedicalRecordService;

	// @GetMapping(value = "/medicalRecords")
	@Test
	public void getAllMedicalRecords() throws Exception {
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
		mockMvc.perform(get("/medicalRecords")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound())
				.andExpect(jsonPath("$[0].lastName", is(medicalRecordToGet1.getLastName())))
				.andExpect(jsonPath("$[1].lastName", is(medicalRecordToGet2.getLastName())))
				.andExpect(jsonPath("$[2].lastName", is(medicalRecordToGet3.getLastName())));
		
		verify(mockMedicalRecordService, times(1)).getAllMedicalRecords();
	}
		
	// @GetMapping(value = "/medicalRecords/{id}")
	@Test
	public void getMedicalRecordById_whenMedicalRecordExist() throws Exception {
		//ARRANGE
		MedicalRecord medicalRecordToGet = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.getMedicalRecordById(medicalRecordToGet.getIdMedicalRecord())).thenReturn(medicalRecordToGet);

		//ACT & ASSERT
		mockMvc.perform(get("/medicalRecords/{id}", medicalRecordToGet.getIdMedicalRecord())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound())
				.andExpect(jsonPath("$.firstName", is(medicalRecordToGet.getFirstName())));
		
		verify(mockMedicalRecordService, times(1)).getMedicalRecordById(medicalRecordToGet.getIdMedicalRecord());
	}

	// @GetMapping(value = "/medicalRecords/{id}")
	@Test
	public void getMedicalRecordById_whenMedicalRecordNotExist() throws Exception {
		//ARRANGE
		MedicalRecord medicalRecordToGet = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.getMedicalRecordById(medicalRecordToGet.getIdMedicalRecord())).thenReturn(null);
		
		//ACT & ASSERT
		mockMvc.perform(get("/medicalRecords/{id}", medicalRecordToGet.getIdMedicalRecord())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		
		verify(mockMedicalRecordService, times(1)).getMedicalRecordById(medicalRecordToGet.getIdMedicalRecord());
	}
	
	// @PostMapping(value = "/medicalRecords")
	@Test
	public void addMedicalRecord_whenMedicalRecordNotAlreadyExist() throws Exception {
		//ARRANGE
		MedicalRecord medicalRecordToAdd = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.medicalRecordExist(any(MedicalRecord.class))).thenReturn(false).thenReturn(true);
		when(mockMedicalRecordService.addMedicalRecord(medicalRecordToAdd)).thenReturn(null);
		
		ObjectMapper objectMapper = new ObjectMapper();
	
		//ACT & ASSERT
		MvcResult mvcResult = mockMvc.perform(post("/medicalRecords")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(medicalRecordToAdd)))
				.andExpect(status().isCreated())
				.andReturn();
	
		verify(mockMedicalRecordService, times(2)).medicalRecordExist(any(MedicalRecord.class));
		verify(mockMedicalRecordService, times(1)).addMedicalRecord(medicalRecordToAdd);
	
		String actualResponseHeaderLocation = mvcResult.getResponse().getHeader("Location");
		assertEquals("http://localhost/medicalRecords/BertrandSimon", actualResponseHeaderLocation);
	}
	
	// @PostMapping(value = "/medicalRecords")
	@Test
	public void addMedicalRecord_whenMedicalRecordAlreadyExist() throws Exception {
		//ARRANGE
		MedicalRecord medicalRecordToAdd = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.medicalRecordExist(any(MedicalRecord.class))).thenReturn(true);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		//ACT & ASSERT
		mockMvc.perform(post("/medicalRecords")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(medicalRecordToAdd)))
				.andExpect(status().isBadRequest());
		
		verify(mockMedicalRecordService, times(1)).medicalRecordExist(any(MedicalRecord.class));
		verify(mockMedicalRecordService, never()).addMedicalRecord(medicalRecordToAdd);
	}
	
	// @PostMapping(value = "/medicalRecords")
	@Test
	public void addMedicalRecord_whenMedicalRecordNotAlreadyExist_whenInternalServerError() throws Exception {
		//ARRANGE
		MedicalRecord medicalRecordToAdd = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.medicalRecordExist(any(MedicalRecord.class))).thenReturn(false).thenReturn(false);
		when(mockMedicalRecordService.addMedicalRecord(medicalRecordToAdd)).thenReturn(null);
		
		ObjectMapper objectMapper = new ObjectMapper();
	
		//ACT & ASSERT
		mockMvc.perform(post("/medicalRecords")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(medicalRecordToAdd)))
				.andExpect(status().isInternalServerError());

		verify(mockMedicalRecordService, times(2)).medicalRecordExist(any(MedicalRecord.class));
		verify(mockMedicalRecordService, times(1)).addMedicalRecord(medicalRecordToAdd);
	}
	
	// @PutMapping(value = "/medicalRecords/{id}")
	@Test
	public void updateMedicalRecord_whenMedicalRecordExist() throws Exception {
		//ARRANGE
		MedicalRecord medicalRecordToUpdate = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		MedicalRecord medicalRecordUpdated = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1983",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.getMedicalRecordById(medicalRecordToUpdate.getIdMedicalRecord())).thenReturn(medicalRecordToUpdate).thenReturn(medicalRecordToUpdate).thenReturn(medicalRecordUpdated);
		when(mockMedicalRecordService.updateMedicalRecord(medicalRecordUpdated)).thenReturn(medicalRecordToUpdate);
		
		ObjectMapper objectMapper = new ObjectMapper();
	
		//ACT & ASSERT
		mockMvc.perform(put("/medicalRecords/{id}", medicalRecordToUpdate.getIdMedicalRecord())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(medicalRecordUpdated)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.birthdate", is(medicalRecordUpdated.getBirthdate())));

		verify(mockMedicalRecordService, times(3)).getMedicalRecordById(medicalRecordToUpdate.getIdMedicalRecord());
		verify(mockMedicalRecordService, times(1)).updateMedicalRecord(medicalRecordUpdated);
	}
	
	// @PutMapping(value = "/medicalRecords/{id}")
	@Test
	public void updateMedicalRecord_whenMedicalRecordInPathRequestNotExist() throws Exception {
		//ARRANGE
		MedicalRecord medicalRecordToUpdate = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		MedicalRecord medicalRecordUpdated = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1983",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.getMedicalRecordById(medicalRecordToUpdate.getIdMedicalRecord())).thenReturn(null);
		
		ObjectMapper objectMapper = new ObjectMapper();

		//ACT & ASSERT
		mockMvc.perform(put("/medicalRecords/{id}", medicalRecordToUpdate.getIdMedicalRecord())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(medicalRecordUpdated)))
				.andExpect(status().isNotFound());
		
		verify(mockMedicalRecordService, times(1)).getMedicalRecordById(medicalRecordToUpdate.getIdMedicalRecord());
		verify(mockMedicalRecordService, never()).updateMedicalRecord(any(MedicalRecord.class));
	}
	
	// @PutMapping(value = "/medicalRecords/{id}")
	@Test
	public void updateMedicalRecord_whenMedicalRecordInRequestBodyNotExist() throws Exception {
		//ARRANGE
		MedicalRecord medicalRecordToUpdate = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		MedicalRecord medicalRecordUpdated = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1983",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.getMedicalRecordById(medicalRecordToUpdate.getIdMedicalRecord())).thenReturn(medicalRecordToUpdate).thenReturn(null);
		
		ObjectMapper objectMapper = new ObjectMapper();

		//ACT & ASSERT
		mockMvc.perform(put("/medicalRecords/{id}", medicalRecordToUpdate.getIdMedicalRecord())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(medicalRecordUpdated)))
				.andExpect(status().isNotFound());
		
		verify(mockMedicalRecordService, times(2)).getMedicalRecordById(medicalRecordToUpdate.getIdMedicalRecord());
		verify(mockMedicalRecordService, never()).updateMedicalRecord(any(MedicalRecord.class));
	}
	
	// @PutMapping(value = "/medicalRecords/{id}")
	@Test
	public void updateMedicalRecord_whenMedicalRecordExist_whenInternalServerError() throws Exception {
		//ARRANGE
		MedicalRecord medicalRecordToUpdate = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		MedicalRecord medicalRecordUpdated = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1983",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.getMedicalRecordById(medicalRecordToUpdate.getIdMedicalRecord())).thenReturn(medicalRecordToUpdate).thenReturn(medicalRecordToUpdate).thenReturn(medicalRecordToUpdate);
		when(mockMedicalRecordService.updateMedicalRecord(medicalRecordUpdated)).thenReturn(medicalRecordToUpdate);
		
		ObjectMapper objectMapper = new ObjectMapper();

		//ACT & ASSERT
		mockMvc.perform(put("/medicalRecords/{id}", medicalRecordToUpdate.getIdMedicalRecord())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(medicalRecordUpdated)))
				.andExpect(status().isInternalServerError());
		
		verify(mockMedicalRecordService, times(3)).getMedicalRecordById(medicalRecordToUpdate.getIdMedicalRecord());
		verify(mockMedicalRecordService, times(1)).updateMedicalRecord(medicalRecordUpdated);
	}
	
	// @DeleteMapping(value = "/medicalRecords/{id}")
	@Test
	public void deleteMedicalRecord_whenMedicalRecordExist() throws Exception {
		//ARRANGE
		MedicalRecord medicalRecordToDelete = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.getMedicalRecordById(medicalRecordToDelete.getIdMedicalRecord())).thenReturn(medicalRecordToDelete);
		when(mockMedicalRecordService.deleteMedicalRecord(medicalRecordToDelete.getIdMedicalRecord())).thenReturn(medicalRecordToDelete);
		when(mockMedicalRecordService.medicalRecordExist(medicalRecordToDelete)).thenReturn(false);

		//ACT & ASSERT
		mockMvc.perform(delete("/medicalRecords/{id}", medicalRecordToDelete.getIdMedicalRecord())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isGone());
		
		verify(mockMedicalRecordService, times(1)).getMedicalRecordById(medicalRecordToDelete.getIdMedicalRecord());
		verify(mockMedicalRecordService, times(1)).deleteMedicalRecord(medicalRecordToDelete.getIdMedicalRecord());
		verify(mockMedicalRecordService, times(1)).medicalRecordExist(medicalRecordToDelete);
	}
	
	// @DeleteMapping(value = "/medicalRecords/{id}")
	@Test
	public void deleteMedicalRecord_whenMedicalRecordNotExist() throws Exception {
		//ARRANGE
		MedicalRecord medicalRecordToDelete = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.getMedicalRecordById(medicalRecordToDelete.getIdMedicalRecord())).thenReturn(null);

		//ACT & ASSERT
		mockMvc.perform(delete("/medicalRecords/{id}", medicalRecordToDelete.getIdMedicalRecord())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		
		verify(mockMedicalRecordService, times(1)).getMedicalRecordById(medicalRecordToDelete.getIdMedicalRecord());
		verify(mockMedicalRecordService, never()).deleteMedicalRecord(anyString());
		verify(mockMedicalRecordService, never()).medicalRecordExist(any(MedicalRecord.class));
	}

	// @DeleteMapping(value = "/medicalRecords/{id}")
	@Test
	public void deleteMedicalRecord_whenMedicalRecordExist_whenInternalServerError() throws Exception {
		//ARRANGE
		MedicalRecord medicalRecordToDelete = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		
		when(mockMedicalRecordService.getMedicalRecordById(medicalRecordToDelete.getIdMedicalRecord())).thenReturn(medicalRecordToDelete);
		when(mockMedicalRecordService.deleteMedicalRecord(medicalRecordToDelete.getIdMedicalRecord())).thenReturn(medicalRecordToDelete);
		when(mockMedicalRecordService.medicalRecordExist(medicalRecordToDelete)).thenReturn(true);
	
		//ACT & ASSERT
		mockMvc.perform(delete("/medicalRecords/{id}", medicalRecordToDelete.getIdMedicalRecord())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
		
		verify(mockMedicalRecordService, times(1)).getMedicalRecordById(medicalRecordToDelete.getIdMedicalRecord());
		verify(mockMedicalRecordService, times(1)).deleteMedicalRecord(medicalRecordToDelete.getIdMedicalRecord());
		verify(mockMedicalRecordService, times(1)).medicalRecordExist(medicalRecordToDelete);
	}

}
