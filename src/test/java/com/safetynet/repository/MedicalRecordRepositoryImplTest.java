package com.safetynet.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.entities.endpoints.MedicalRecord;
import com.safetynet.repository.MedicalRecordRepositoryImpl;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = com.safetynet.repository.MedicalRecordRepositoryImpl.class)
public class MedicalRecordRepositoryImplTest {

	@Autowired
	private MedicalRecordRepositoryImpl medicalRecordRepositoryImplUnderTest;

	@BeforeEach
	private void setUpPerTest() {

		List<MedicalRecord> medicalRecords = medicalRecordRepositoryImplUnderTest.getAllMedicalRecords();
		for (MedicalRecord medicalRecord : medicalRecords) {
			medicalRecordRepositoryImplUnderTest.deleteMedicalRecord(medicalRecord.getIdMedicalRecord());
		}

	}

	@Test
	public void addMedicalRecord() {
		// ARRANGE
		MedicalRecord medicalRecordToAdd = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });

		// ACT
		medicalRecordRepositoryImplUnderTest.addMedicalRecord(medicalRecordToAdd);

		// ASSERT
		assertNotNull(
				medicalRecordRepositoryImplUnderTest.getMedicalRecordById(medicalRecordToAdd.getIdMedicalRecord()));
		assertEquals("BertrandSimon", medicalRecordRepositoryImplUnderTest
				.getMedicalRecordById(medicalRecordToAdd.getIdMedicalRecord()).getIdMedicalRecord());
	}

	@Test
	public void deleteMedicalRecord() {
		// ARRANGE
		MedicalRecord medicalRecordToDelete = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });

		medicalRecordRepositoryImplUnderTest.addMedicalRecord(medicalRecordToDelete);

		// ACT
		medicalRecordRepositoryImplUnderTest.deleteMedicalRecord(medicalRecordToDelete.getIdMedicalRecord());

		// ASSERT
		assertNull(
				medicalRecordRepositoryImplUnderTest.getMedicalRecordById(medicalRecordToDelete.getIdMedicalRecord()));
	}

	@Test
	public void updateMedicalRecord() {
		// ARRANGE
		MedicalRecord medicalRecordToUpdate = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });

		medicalRecordRepositoryImplUnderTest.addMedicalRecord(medicalRecordToUpdate);

		MedicalRecord medicalRecordUpdated = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1983",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });

		// ACT
		medicalRecordRepositoryImplUnderTest.updateMedicalRecord(medicalRecordUpdated);

		// ASSERT
		assertEquals("10/05/1983", medicalRecordRepositoryImplUnderTest
				.getMedicalRecordById(medicalRecordToUpdate.getIdMedicalRecord()).getBirthdate());
	}

	@Test
	public void getMedicalRecordById_whenMedicalRecordExist() {
		// ARRANGE
		MedicalRecord medicalRecordToGet = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });

		medicalRecordRepositoryImplUnderTest.addMedicalRecord(medicalRecordToGet);

		// ACT
		MedicalRecord medicalRecordGet = medicalRecordRepositoryImplUnderTest
				.getMedicalRecordById(medicalRecordToGet.getIdMedicalRecord());

		// ASSERT
		assertNotNull(medicalRecordGet);
		assertEquals("BertrandSimon", medicalRecordGet.getIdMedicalRecord());
	}

	@Test
	public void getMedicalRecordById_whenMedicalRecordNotExist() {
		// ACT & ASSERT
		assertNull(medicalRecordRepositoryImplUnderTest.getMedicalRecordById("MedicalRecordNotExist"));
	}

	@Test
	public void getAllMedicalRecords() {
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

		medicalRecordRepositoryImplUnderTest.addMedicalRecord(medicalRecord1);
		medicalRecordRepositoryImplUnderTest.addMedicalRecord(medicalRecord2);
		medicalRecordRepositoryImplUnderTest.addMedicalRecord(medicalRecord3);

		// ACT
		List<MedicalRecord> listAllMedicalRecords = medicalRecordRepositoryImplUnderTest.getAllMedicalRecords();

		// ASSERT
		assertEquals(3, listAllMedicalRecords.size());
		assertThat(listAllMedicalRecords).containsExactlyInAnyOrder(medicalRecord1, medicalRecord2, medicalRecord3);
	}

}
