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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.entities.endpoints.FirestationMapping;
import com.safetynet.service.endpoints.IFirestationMappingService;

@WebMvcTest(EndpointFirestationMappingsController.class)
public class EndpointFirestationMappingsControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IFirestationMappingService mockFirestationMappingService;

	private ObjectMapper objectMapper;
	
	@BeforeEach
	private void setUpPerTest() {
		objectMapper = new ObjectMapper();
	}	
	
	// @GetMapping(value = "/firestations")
	@Test
	public void getAllFirestationMappings() throws Exception {
		//ARRANGE
		FirestationMapping firestationMappingToGet1 = new FirestationMapping("3 rue de Paris", "1");
		FirestationMapping firestationMappingToGet2 = new FirestationMapping("3 rue de Nantes", "2");
		FirestationMapping firestationMappingToGet3 = new FirestationMapping("3 rue de Marseille", "3");
		
		List<FirestationMapping> allFirestationMappingsToGet = new ArrayList<>();
		allFirestationMappingsToGet.add(firestationMappingToGet1);
		allFirestationMappingsToGet.add(firestationMappingToGet2);
		allFirestationMappingsToGet.add(firestationMappingToGet3);
		
		when(mockFirestationMappingService.getAllFirestationMappings()).thenReturn(allFirestationMappingsToGet);

		//ACT & ASSERT
		mockMvc.perform(get("/firestations")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound())
				.andExpect(jsonPath("$[0].address", is(firestationMappingToGet1.getAddress())))
				.andExpect(jsonPath("$[1].address", is(firestationMappingToGet2.getAddress())))
				.andExpect(jsonPath("$[2].address", is(firestationMappingToGet3.getAddress())));
		
		verify(mockFirestationMappingService, times(1)).getAllFirestationMappings();
	}
	
	// @PostMapping(value = "/firestations")
	@Test
	public void addFirestationMapping_whenFirestationMappingNotAlreadyExist() throws Exception {
		//ARRANGE
		FirestationMapping firestationMappingToAdd = new FirestationMapping("2 rue de Paris", "5");
		
		when(mockFirestationMappingService.firestationMappingExist(firestationMappingToAdd)).thenReturn(false).thenReturn(true);
		when(mockFirestationMappingService.addFirestationMapping(firestationMappingToAdd)).thenReturn(null);

		//ACT & ASSERT
		MvcResult mvcResult = mockMvc.perform(post("/firestations")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(firestationMappingToAdd)))
				.andExpect(status().isCreated())
				.andReturn();
	
		verify(mockFirestationMappingService, times(2)).firestationMappingExist(firestationMappingToAdd);
		verify(mockFirestationMappingService, times(1)).addFirestationMapping(firestationMappingToAdd);
	
		String actualResponseHeaderLocation = mvcResult.getResponse().getHeader("Location");
		assertEquals("http://localhost/firestations/2%20rue%20de%20Paris", actualResponseHeaderLocation);
	}
	
	// @PostMapping(value = "/firestations")
	@Test
	public void addFirestationMapping_whenFirestationMappingAlreadyExist() throws Exception {
		//ARRANGE
		FirestationMapping firestationMappingToAdd = new FirestationMapping("2 rue de Paris", "5");
		
		when(mockFirestationMappingService.firestationMappingExist(firestationMappingToAdd)).thenReturn(true);

		//ACT & ASSERT
		mockMvc.perform(post("/firestations")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(firestationMappingToAdd)))
				.andExpect(status().isBadRequest());
		
		verify(mockFirestationMappingService, times(1)).firestationMappingExist(firestationMappingToAdd);
		verify(mockFirestationMappingService, never()).addFirestationMapping(firestationMappingToAdd);
	}
	
	// @PostMapping(value = "/firestations")
	@Test
	public void addFirestationMapping_whenFirestationMappingNotAlreadyExist_whenInternalServerError() throws Exception {
		//ARRANGE
		FirestationMapping firestationMappingToAdd = new FirestationMapping("2 rue de Paris", "5");
		
		when(mockFirestationMappingService.firestationMappingExist(firestationMappingToAdd)).thenReturn(false).thenReturn(false);
		when(mockFirestationMappingService.addFirestationMapping(firestationMappingToAdd)).thenReturn(null);

		//ACT & ASSERT
		mockMvc.perform(post("/firestations")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(firestationMappingToAdd)))
				.andExpect(status().isInternalServerError());

		verify(mockFirestationMappingService, times(2)).firestationMappingExist(firestationMappingToAdd);
		verify(mockFirestationMappingService, times(1)).addFirestationMapping(firestationMappingToAdd);
	}
	
	// @PutMapping(value = "/firestations/{id}")
	@Test
	public void updateFirestationMapping_whenFirestationMappingExist() throws Exception {
		//ARRANGE
		FirestationMapping firestationMappingToUpdate = new FirestationMapping("2 rue de Paris", "5");
		FirestationMapping firestationMappingUpdated = new FirestationMapping("2 rue de Paris", "6");
		
		when(mockFirestationMappingService.getFirestationMappingByAdress(firestationMappingToUpdate.getAddress())).thenReturn(firestationMappingToUpdate).thenReturn(firestationMappingToUpdate).thenReturn(firestationMappingUpdated);
		when(mockFirestationMappingService.updateFirestationMapping(firestationMappingUpdated)).thenReturn(firestationMappingToUpdate);

		//ACT & ASSERT
		mockMvc.perform(put("/firestations/{id}", firestationMappingToUpdate.getAddress())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(firestationMappingUpdated)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.address", is(firestationMappingUpdated.getAddress())));

		verify(mockFirestationMappingService, times(3)).getFirestationMappingByAdress(firestationMappingToUpdate.getAddress());
		verify(mockFirestationMappingService, times(1)).updateFirestationMapping(firestationMappingUpdated);
	}
	
	// @PutMapping(value = "/firestations/{id}")
	@Test
	public void updateFirestationMapping_whenFirestationMappingInPathRequestNotExist() throws Exception {
		//ARRANGE
		FirestationMapping firestationMappingToUpdate = new FirestationMapping("2 rue de Paris", "5");
		FirestationMapping firestationMappingUpdated = new FirestationMapping("2 rue de Paris", "6");
		
		when(mockFirestationMappingService.getFirestationMappingByAdress(firestationMappingToUpdate.getAddress())).thenReturn(null);

		//ACT & ASSERT
		mockMvc.perform(put("/firestations/{id}", firestationMappingToUpdate.getAddress())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(firestationMappingUpdated)))
				.andExpect(status().isNotFound());
		
		verify(mockFirestationMappingService, times(1)).getFirestationMappingByAdress(firestationMappingToUpdate.getAddress());
		verify(mockFirestationMappingService, never()).updateFirestationMapping(any(FirestationMapping.class));
	}
	
	// @PutMapping(value = "/firestations/{id}")
	@Test
	public void updateFirestationMapping_whenFirestationMappingInRequestBodyNotExist() throws Exception {
		//ARRANGE
		FirestationMapping firestationMappingToUpdate = new FirestationMapping("2 rue de Paris", "5");
		FirestationMapping firestationMappingUpdated = new FirestationMapping("2 rue de Paris", "6");
		
		when(mockFirestationMappingService.getFirestationMappingByAdress(firestationMappingToUpdate.getAddress())).thenReturn(firestationMappingToUpdate).thenReturn(null);

		//ACT & ASSERT
		mockMvc.perform(put("/firestations/{id}", firestationMappingToUpdate.getAddress())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(firestationMappingUpdated)))
				.andExpect(status().isNotFound());
		
		verify(mockFirestationMappingService, times(2)).getFirestationMappingByAdress(firestationMappingToUpdate.getAddress());
		verify(mockFirestationMappingService, never()).updateFirestationMapping(any(FirestationMapping.class));
	}
	
	// @PutMapping(value = "/firestations/{id}")
	@Test
	public void updateFirestationMapping_whenFirestationMappingExist_whenInternalServerError() throws Exception {
		//ARRANGE
		FirestationMapping firestationMappingToUpdate = new FirestationMapping("2 rue de Paris", "5");
		FirestationMapping firestationMappingUpdated = new FirestationMapping("2 rue de Paris", "6");
		
		when(mockFirestationMappingService.getFirestationMappingByAdress(firestationMappingToUpdate.getAddress())).thenReturn(firestationMappingToUpdate).thenReturn(firestationMappingToUpdate).thenReturn(firestationMappingToUpdate);
		when(mockFirestationMappingService.updateFirestationMapping(firestationMappingUpdated)).thenReturn(firestationMappingToUpdate);

		//ACT & ASSERT
		mockMvc.perform(put("/firestations/{id}", firestationMappingToUpdate.getAddress())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(firestationMappingUpdated)))
				.andExpect(status().isInternalServerError());
		
		verify(mockFirestationMappingService, times(3)).getFirestationMappingByAdress(firestationMappingToUpdate.getAddress());
		verify(mockFirestationMappingService, times(1)).updateFirestationMapping(firestationMappingUpdated);
	}
	
	// @DeleteMapping(value = "/firestations/{id}")
	@Test
	public void deleteFirestationMapping_whenFirestationMappingExist() throws Exception {
		//ARRANGE
		FirestationMapping firestationMappingToDelete = new FirestationMapping("2 rue de Paris", "5");
		
		when(mockFirestationMappingService.getFirestationMappingByAdress(firestationMappingToDelete.getAddress())).thenReturn(firestationMappingToDelete);
		when(mockFirestationMappingService.deleteFirestationMapping(firestationMappingToDelete.getAddress())).thenReturn(firestationMappingToDelete);
		when(mockFirestationMappingService.firestationMappingExist(firestationMappingToDelete)).thenReturn(false);

		//ACT & ASSERT
		mockMvc.perform(delete("/firestations/{id}", firestationMappingToDelete.getAddress())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isGone());
		
		verify(mockFirestationMappingService, times(1)).getFirestationMappingByAdress(firestationMappingToDelete.getAddress());
		verify(mockFirestationMappingService, times(1)).deleteFirestationMapping(firestationMappingToDelete.getAddress());
		verify(mockFirestationMappingService, times(1)).firestationMappingExist(firestationMappingToDelete);
	}
	
	// @DeleteMapping(value = "/firestations/{id}")
	@Test
	public void deleteFirestationMapping_whenFirestationMappingNotExist() throws Exception {
		//ARRANGE
		FirestationMapping firestationMappingToDelete = new FirestationMapping("2 rue de Paris", "5");
		
		when(mockFirestationMappingService.getFirestationMappingByAdress(firestationMappingToDelete.getAddress())).thenReturn(null);

		//ACT & ASSERT
		mockMvc.perform(delete("/firestations/{id}", firestationMappingToDelete.getAddress())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		
		verify(mockFirestationMappingService, times(1)).getFirestationMappingByAdress(firestationMappingToDelete.getAddress());
		verify(mockFirestationMappingService, never()).deleteFirestationMapping(anyString());
		verify(mockFirestationMappingService, never()).firestationMappingExist(any(FirestationMapping.class));
	}

	// @DeleteMapping(value = "/firestations/{id}")
	@Test
	public void deleteFirestationMapping_whenFirestationMappingExist_whenInternalServerError() throws Exception {
		//ARRANGE
		FirestationMapping firestationMappingToDelete = new FirestationMapping("2 rue de Paris", "5");
		
		when(mockFirestationMappingService.getFirestationMappingByAdress(firestationMappingToDelete.getAddress())).thenReturn(firestationMappingToDelete);
		when(mockFirestationMappingService.deleteFirestationMapping(firestationMappingToDelete.getAddress())).thenReturn(firestationMappingToDelete);
		when(mockFirestationMappingService.firestationMappingExist(firestationMappingToDelete)).thenReturn(true);
	
		//ACT & ASSERT
		mockMvc.perform(delete("/firestations/{id}", firestationMappingToDelete.getAddress())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
		
		verify(mockFirestationMappingService, times(1)).getFirestationMappingByAdress(firestationMappingToDelete.getAddress());
		verify(mockFirestationMappingService, times(1)).deleteFirestationMapping(firestationMappingToDelete.getAddress());
		verify(mockFirestationMappingService, times(1)).firestationMappingExist(firestationMappingToDelete);
	}

}
