package com.safetynet;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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

	@BeforeAll
	private static void setUp() throws Exception {

	}

	@BeforeEach
	private void setUpPerTest() throws Exception {

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

		Assertions.assertEquals("BertrandSimon", medicalRecordDaoImplUnderTest
				.getMedicalRecordById(medicalRecordToAdd.getIdMedicalRecord()).getIdMedicalRecord());

	}

	@Test
	public void deleteMedicalRecord() {
		MedicalRecord medicalRecordToDelete = new MedicalRecord("BertrandSimon", "Bertrand", "Simon", "10/05/1979",
				new String[] { "abc:500mg", "def:1000mg", "ghi:500mg" },
				new String[] { "pollen", "acariens", "chats" });

		medicalRecordDaoImplUnderTest.addMedicalRecord(medicalRecordToDelete);

		medicalRecordDaoImplUnderTest.deleteMedicalRecord(medicalRecordToDelete.getIdMedicalRecord());

		Assertions.assertNull(
				medicalRecordDaoImplUnderTest.getMedicalRecordById(medicalRecordToDelete.getIdMedicalRecord()));

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

		Assertions.assertEquals("10/05/1980", medicalRecordDaoImplUnderTest
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

		Assertions.assertEquals("BertrandSimon", medicalRecordGet.getIdMedicalRecord());

	}

	@Test
	public void getMedicalRecordById_whenMedicalRecordNotExist() {

		Assertions.assertNull(medicalRecordDaoImplUnderTest.getMedicalRecordById("BertrandSimon"));

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

		Assertions.assertEquals(3, listAllMedicalRecords.size());

	}

}
