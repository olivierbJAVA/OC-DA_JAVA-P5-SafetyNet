package com.safetynet;

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

import com.safetynet.dao.MedicalRecordDaoImpl;
import com.safetynet.entities.endpoints.MedicalRecord;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = com.safetynet.dao.MedicalRecordDaoImpl.class)
public class MedicalRecordDaoImplTest {

	@Autowired
	private MedicalRecordDaoImpl medicalRecordDaoImplUnderTest;

	@BeforeEach
	private void setUpPerTest() {

		List<MedicalRecord> medicalRecords = medicalRecordDaoImplUnderTest.getAllMedicalRecords();
		for (MedicalRecord medicalRecord : medicalRecords) {
			medicalRecordDaoImplUnderTest.deleteMedicalRecord(medicalRecord.getIdMedicalRecord());
		}

	}

	@Test
	public void addMedicalRecord() {
		MedicalRecord medicalRecordToAdd = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1979",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });

		medicalRecordDaoImplUnderTest.addMedicalRecord(medicalRecordToAdd);

		assertNotNull(medicalRecordDaoImplUnderTest.getMedicalRecordById(medicalRecordToAdd.getIdMedicalRecord()));
		assertEquals("BertrandSimon", medicalRecordDaoImplUnderTest
				.getMedicalRecordById(medicalRecordToAdd.getIdMedicalRecord()).getIdMedicalRecord());

	}

	@Test
	public void deleteMedicalRecord() {
		MedicalRecord medicalRecordToDelete = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1979",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });

		medicalRecordDaoImplUnderTest.addMedicalRecord(medicalRecordToDelete);

		medicalRecordDaoImplUnderTest.deleteMedicalRecord(medicalRecordToDelete.getIdMedicalRecord());

		assertNull(medicalRecordDaoImplUnderTest.getMedicalRecordById(medicalRecordToDelete.getIdMedicalRecord()));

	}

	@Test
	public void updateMedicalRecord() {
		MedicalRecord medicalRecordToUpdate = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1979",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });

		medicalRecordDaoImplUnderTest.addMedicalRecord(medicalRecordToUpdate);

		MedicalRecord medicalRecordUpdated = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1980",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });

		medicalRecordDaoImplUnderTest.updateMedicalRecord(medicalRecordUpdated);

		assertEquals("10/05/1980", medicalRecordDaoImplUnderTest
				.getMedicalRecordById(medicalRecordToUpdate.getIdMedicalRecord()).getBirthdate());

	}

	@Test
	public void getMedicalRecordById_whenMedicalRecordExist() {

		MedicalRecord medicalRecordToGet = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1979",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });

		medicalRecordDaoImplUnderTest.addMedicalRecord(medicalRecordToGet);

		MedicalRecord medicalRecordGet = medicalRecordDaoImplUnderTest
				.getMedicalRecordById(medicalRecordToGet.getIdMedicalRecord());

		assertNotNull(medicalRecordDaoImplUnderTest.getMedicalRecordById(medicalRecordToGet.getIdMedicalRecord()));
		assertEquals("BertrandSimon", medicalRecordGet.getIdMedicalRecord());

	}

	@Test
	public void getMedicalRecordById_whenMedicalRecordNotExist() {

		assertNull(medicalRecordDaoImplUnderTest.getMedicalRecordById("BertrandSimon"));

	}

	@Test
	public void getAllMedicalRecords() {
		MedicalRecord medicalRecord1 = new MedicalRecord("BertrandSimon1", "Bertrand", "Simon1", "10/05/1979",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		MedicalRecord medicalRecord2 = new MedicalRecord("BertrandSimon2", "Bertrand", "Simon2", "10/05/1979",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });
		MedicalRecord medicalRecord3 = new MedicalRecord("BertrandSimon3", "Bertrand", "Simon3", "10/05/1979",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });

		medicalRecordDaoImplUnderTest.addMedicalRecord(medicalRecord1);
		medicalRecordDaoImplUnderTest.addMedicalRecord(medicalRecord2);
		medicalRecordDaoImplUnderTest.addMedicalRecord(medicalRecord3);

		List<MedicalRecord> listAllMedicalRecords = medicalRecordDaoImplUnderTest.getAllMedicalRecords();

		assertEquals(3, listAllMedicalRecords.size());
		assertThat(listAllMedicalRecords).containsExactlyInAnyOrder(medicalRecord1, medicalRecord2, medicalRecord3);

	}

}
