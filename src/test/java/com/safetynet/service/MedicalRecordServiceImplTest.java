package com.safetynet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.entities.endpoints.MedicalRecord;
import com.safetynet.repository.IMedicalRecordRepository;
import com.safetynet.service.endpoints.IMedicalRecordService;
import com.safetynet.service.endpoints.MedicalRecordServiceImpl;

@ExtendWith(SpringExtension.class)
public class MedicalRecordServiceImplTest {

	@TestConfiguration
	static class MedicalRecordServiceImplTestContextConfiguration {
		@Bean
		public IMedicalRecordService iMedicalRecordService() {
			return new MedicalRecordServiceImpl();
		}
	}

	@Autowired
	private IMedicalRecordService medicalRecordServiceImplUnderTest;

	@MockBean
	private IMedicalRecordRepository mockMedicalRecordRepository;

	@Test
	public void addMedicalRecord() {
		// ARRANGE
		MedicalRecord medicalRecordToAdd = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });

		when(mockMedicalRecordRepository.addMedicalRecord(medicalRecordToAdd)).thenReturn(null);

		// ACT
		medicalRecordServiceImplUnderTest.addMedicalRecord(medicalRecordToAdd);

		// ASSERT
		ArgumentCaptor<MedicalRecord> argumentCaptorMedicalRecord = ArgumentCaptor.forClass(MedicalRecord.class);
		verify(mockMedicalRecordRepository, times(1)).addMedicalRecord(argumentCaptorMedicalRecord.capture());

		MedicalRecord agrumentMedicalRecordCaptured = argumentCaptorMedicalRecord.getValue();
		assertEquals(medicalRecordToAdd, agrumentMedicalRecordCaptured);

	}

	@Test
	public void deleteMedicalRecord() {
		// ARRANGE
		MedicalRecord medicalRecordToDelete = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });

		when(mockMedicalRecordRepository.deleteMedicalRecord(medicalRecordToDelete.getIdMedicalRecord()))
				.thenReturn(medicalRecordToDelete);

		// ACT
		medicalRecordServiceImplUnderTest.deleteMedicalRecord(medicalRecordToDelete.getIdMedicalRecord());

		// ASSERT
		ArgumentCaptor<String> argumentCaptorMedicalRecord = ArgumentCaptor.forClass(String.class);
		Mockito.verify(mockMedicalRecordRepository, times(1))
				.deleteMedicalRecord(argumentCaptorMedicalRecord.capture());

		String argumentMedicalRecordCaptured = argumentCaptorMedicalRecord.getValue();
		assertEquals(medicalRecordToDelete.getIdMedicalRecord(), argumentMedicalRecordCaptured);

	}

	@Test
	public void updateMedicalRecord() {
		// ARRANGE
		MedicalRecord medicalRecordToUpdate = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });

		MedicalRecord medicalRecordUpdated = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "act:500mg", "vit:1000mg", "cal:500mg" },
				new String[] { "lactose", "arachides", "chiens" });

		when(mockMedicalRecordRepository.updateMedicalRecord(medicalRecordUpdated)).thenReturn(medicalRecordToUpdate);

		// ACT
		medicalRecordServiceImplUnderTest.updateMedicalRecord(medicalRecordUpdated);

		// ASSERT
		ArgumentCaptor<MedicalRecord> argumentCaptorMedicalRecord = ArgumentCaptor.forClass(MedicalRecord.class);
		verify(mockMedicalRecordRepository, times(1)).updateMedicalRecord(argumentCaptorMedicalRecord.capture());

		MedicalRecord agrumentMedicalRecordCaptured = argumentCaptorMedicalRecord.getValue();
		assertEquals(medicalRecordUpdated, agrumentMedicalRecordCaptured);
	}

	@Test
	public void getMedicalRecordById() {
		// ARRANGE
		MedicalRecord medicalRecordToGet = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });

		when(mockMedicalRecordRepository.getMedicalRecordById(medicalRecordToGet.getIdMedicalRecord()))
				.thenReturn(medicalRecordToGet);

		// ACT
		medicalRecordServiceImplUnderTest.getMedicalRecordById(medicalRecordToGet.getIdMedicalRecord());

		// ASSERT
		ArgumentCaptor<String> argumentCaptorMedicalRecord = ArgumentCaptor.forClass(String.class);
		verify(mockMedicalRecordRepository, times(1)).getMedicalRecordById(argumentCaptorMedicalRecord.capture());

		String argumentIdMedicalRecordCaptured = argumentCaptorMedicalRecord.getValue();
		assertEquals(medicalRecordToGet.getIdMedicalRecord(), argumentIdMedicalRecordCaptured);
	}

	@Test
	public void getAllMedicalRecrods() {
		// ARRANGE
		MedicalRecord medicalRecord1 = new MedicalRecord("BertrandSimon1", "Bertrand", "Simon1", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		MedicalRecord medicalRecord2 = new MedicalRecord("BertrandSimon2", "Bertrand", "Simon2", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		MedicalRecord medicalRecord3 = new MedicalRecord("BertrandSimon3", "Bertrand", "Simon3", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });

		List<MedicalRecord> allMedicalRecords = new ArrayList<>();
		allMedicalRecords.add(medicalRecord1);
		allMedicalRecords.add(medicalRecord2);
		allMedicalRecords.add(medicalRecord3);

		when(mockMedicalRecordRepository.getAllMedicalRecords()).thenReturn(allMedicalRecords);

		// ACT
		medicalRecordServiceImplUnderTest.getAllMedicalRecords();

		// ASSERT
		verify(mockMedicalRecordRepository, times(1)).getAllMedicalRecords();
	}

	@Test
	public void medicalRecordExist_whenMedicalRecordExist() {
		// ARRANGE
		MedicalRecord medicalRecordTestExist = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });

		when(mockMedicalRecordRepository.getMedicalRecordById(medicalRecordTestExist.getIdMedicalRecord()))
				.thenReturn(medicalRecordTestExist);

		// ACT & ASSERT
		assertTrue(medicalRecordServiceImplUnderTest.medicalRecordExist(medicalRecordTestExist));
	}

	@Test
	public void medicalRecordExist_whenMedicalRecordNotExist() {
		// ARRANGE
		MedicalRecord medicalRecordTestNotExist = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });

		when(mockMedicalRecordRepository.getMedicalRecordById(medicalRecordTestNotExist.getIdMedicalRecord()))
				.thenReturn(null);

		// ACT & ASSERT
		assertFalse(medicalRecordServiceImplUnderTest.medicalRecordExist(medicalRecordTestNotExist));
	}

}
