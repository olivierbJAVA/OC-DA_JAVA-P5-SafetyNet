package com.safetynet.model.urls;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.entities.endpoints.Person;
import com.safetynet.entities.urls.ChildAlert;
import com.safetynet.entities.urls.ChildAlertChild;
import com.safetynet.entities.urls.Fire;
import com.safetynet.entities.urls.FirePerson;
import com.safetynet.entities.urls.Firestation;
import com.safetynet.entities.urls.FirestationPerson;
import com.safetynet.entities.urls.Flood;
import com.safetynet.entities.urls.FloodPerson;
import com.safetynet.entities.urls.PersonInfo;
import com.safetynet.entities.urls.PersonInfoSameName;
import com.safetynet.service.endpoints.IFirestationMappingService;
import com.safetynet.service.endpoints.IMedicalRecordService;
import com.safetynet.service.endpoints.IPersonService;

@Service
public class ResponseUrlsModelImpl implements IResponseUrlsModel {

	@Autowired
	private IPersonService personModel;

	@Autowired
	private IFirestationMappingService firestationMappingModel;

	@Autowired
	private IMedicalRecordService medicalRecordModel;

	@Override
	public Firestation responseFirestation(String idStation) {

		Firestation responseFirestation = new Firestation();

		List<FirestationPerson> responseFirestationListPersons = new ArrayList<>();

		List<Person> listAllPersons = personModel.getAllPersons();

		int nbAdults = 0;
		int nbChilds = 0;

		for (Person personInList : listAllPersons) {

			if ((firestationMappingModel.getFirestationMappingByAdress(personInList.getAddress())
					.getStation()).equals(idStation)) {

				FirestationPerson responseFirestationPerson = new FirestationPerson();
				responseFirestationPerson.setFirstName(personInList.getFirstName());
				responseFirestationPerson.setLastName(personInList.getLastName());
				responseFirestationPerson.setAddress(personInList.getAddress());
				responseFirestationPerson.setPhone(personInList.getPhone());

				responseFirestationListPersons.add(responseFirestationPerson);

				long personAge = getPersonAge(personInList);

				if (personAge <= 18) {
					nbChilds++;
				} else {
					nbAdults++;
				}
			}
		}
		responseFirestation.setFirestationPersons(responseFirestationListPersons);
		responseFirestation.setNbAdults(nbAdults);
		responseFirestation.setNbChilds(nbChilds);

		return responseFirestation;
	}

	@Override
	public ChildAlert responseChildAlert(String address) {

		ChildAlert responseChildAlert = new ChildAlert();

		List<ChildAlertChild> responseChildAlertListChilds = new ArrayList<>();

		List<Person> responseChildAlertListHouseholdMembers = new ArrayList<>();

		List<Person> listAllPersons = personModel.getAllPersons();

		for (Person personInList : listAllPersons) {

			if (personInList.getAddress().equals(address)) {

				long personAge = getPersonAge(personInList);

				if (personAge <= 18) {
					ChildAlertChild child = new ChildAlertChild();
					child.setChildFirstName(personInList.getFirstName());
					child.setChildLastName(personInList.getLastName());
					child.setChildAge(personAge);
					responseChildAlertListChilds.add(child);
				} else {
					Person otherHouseholdMember = new Person();
					otherHouseholdMember.setFirstName(personInList.getFirstName());
					otherHouseholdMember.setLastName(personInList.getLastName());
					otherHouseholdMember.setAddress(personInList.getAddress());
					otherHouseholdMember.setCity(personInList.getCity());
					otherHouseholdMember.setZip(personInList.getZip());
					otherHouseholdMember.setPhone(personInList.getPhone());
					otherHouseholdMember.setEmail(personInList.getEmail());
					responseChildAlertListHouseholdMembers.add(otherHouseholdMember);
				}
			}
		}
		responseChildAlert.setChildAlertChilds(responseChildAlertListChilds);
		responseChildAlert.setChildAlertHouseholdMembers(responseChildAlertListHouseholdMembers);

		if (responseChildAlertListChilds.isEmpty()) {
			return null;
		}

		return responseChildAlert;
	}

	@Override
	public Set<String> responsePhoneAlert(String idStation) {

		Set<String> responsePhoneAlert = new HashSet<>();

		List<Person> listAllPersons = personModel.getAllPersons();

		for (Person personInList : listAllPersons) {

			if ((firestationMappingModel.getFirestationMappingByAdress(personInList.getAddress())
					.getStation()).equals(idStation)) {

				responsePhoneAlert.add(personInList.getPhone());

			}
		}
		return responsePhoneAlert;
	}

	@Override
	public Fire responseFire(String address) {

		Fire responseFire = new Fire();

		List<FirePerson> responseFirePerson = new ArrayList<>();

		List<Person> listAllPersons = personModel.getAllPersons();

		String idStation = firestationMappingModel.getFirestationMappingByAdress(address).getStation();

		responseFire.setIdStation(idStation);

		for (Person personInList : listAllPersons) {

			if (personInList.getAddress().equals(address)) {

				FirePerson firePerson = new FirePerson();

				firePerson.setFirstName(personInList.getFirstName());
				firePerson.setLastName(personInList.getLastName());
				firePerson.setPhone(personInList.getPhone());
				firePerson.setAge(getPersonAge(personInList));

				firePerson.setMedications(medicalRecordModel
						.getMedicalRecordById(firePerson.getFirstName() + firePerson.getLastName()).getMedications());
				firePerson.setAllergies(medicalRecordModel
						.getMedicalRecordById(firePerson.getFirstName() + firePerson.getLastName()).getAllergies());

				responseFirePerson.add(firePerson);
			}
		}
		responseFire.setFirePersons(responseFirePerson);

		return responseFire;
	}

	@Override
	public Flood responseFlood(String idStation) {

		Flood responseFlood = new Flood();

		Map<String, List<FloodPerson>> responseMapFloodPersons = new HashMap<>();

		List<Person> listAllPersons = personModel.getAllPersons();

		Set<String> allAddress = personModel.getAllAddress();

		for (String address : allAddress) {

			List<FloodPerson> responseListFloodPersons = new ArrayList<>();

			if (firestationMappingModel.getFirestationMappingByAdress(address).getStation().equals(idStation)) {

				for (Person personInList : listAllPersons) {

					if (personInList.getAddress().equals(address)) {

						FloodPerson responseFloodPerson = new FloodPerson();

						responseFloodPerson.setFirstName(personInList.getFirstName());
						responseFloodPerson.setLastName(personInList.getLastName());
						responseFloodPerson.setPhone(personInList.getPhone());
						responseFloodPerson.setAge(getPersonAge(personInList));

						responseFloodPerson.setMedications(medicalRecordModel
								.getMedicalRecordById(
										responseFloodPerson.getFirstName() + responseFloodPerson.getLastName())
								.getMedications());
						responseFloodPerson.setAllergies(medicalRecordModel
								.getMedicalRecordById(
										responseFloodPerson.getFirstName() + responseFloodPerson.getLastName())
								.getAllergies());

						responseListFloodPersons.add(responseFloodPerson);
					}
				}
				responseMapFloodPersons.put(address, responseListFloodPersons);
			}
		}
		responseFlood.setMapFloodPersons(responseMapFloodPersons);

		return responseFlood;
	}

	@Override
	public PersonInfo responsePersonInfo(String firstName, String lastName) {

		PersonInfo responsePersonInfo = new PersonInfo();

		List<Person> listAllPersons = personModel.getAllPersons();

		for (Person personInList : listAllPersons) {

			if (personInList.getIdPerson().equals(firstName + lastName)) {

				responsePersonInfo.setFirstName(firstName);
				responsePersonInfo.setLastName(lastName);
				responsePersonInfo.setAddress(personInList.getAddress());
				responsePersonInfo.setAge(getPersonAge(personInList));
				responsePersonInfo.setEmail(personInList.getEmail());

				responsePersonInfo.setMedications(medicalRecordModel
						.getMedicalRecordById(responsePersonInfo.getFirstName() + responsePersonInfo.getLastName())
						.getMedications());
				responsePersonInfo.setAllergies(medicalRecordModel
						.getMedicalRecordById(responsePersonInfo.getFirstName() + responsePersonInfo.getLastName())
						.getAllergies());

				List<PersonInfoSameName> responsePersonInfoListOtherPersonsSameName = new ArrayList<>();

				for (Person personInListWithSameName : listAllPersons) {

					if (personInListWithSameName.getLastName().equals(responsePersonInfo.getLastName())
							&& !personInListWithSameName.getFirstName().equals(responsePersonInfo.getFirstName())) {

						PersonInfoSameName responsePersonInfoOtherPersonSameName = new PersonInfoSameName();

						responsePersonInfoOtherPersonSameName.setFirstName(personInListWithSameName.getFirstName());
						responsePersonInfoOtherPersonSameName.setLastName(personInListWithSameName.getLastName());
						responsePersonInfoOtherPersonSameName.setAddress(personInListWithSameName.getAddress());
						responsePersonInfoOtherPersonSameName.setAge(getPersonAge(personInListWithSameName));
						responsePersonInfoOtherPersonSameName.setEmail(personInListWithSameName.getEmail());

						responsePersonInfoOtherPersonSameName.setMedications(medicalRecordModel.getMedicalRecordById(
								personInListWithSameName.getFirstName() + personInListWithSameName.getLastName())
								.getMedications());
						responsePersonInfoOtherPersonSameName.setAllergies(medicalRecordModel.getMedicalRecordById(
								personInListWithSameName.getFirstName() + personInListWithSameName.getLastName())
								.getAllergies());

						responsePersonInfoListOtherPersonsSameName.add(responsePersonInfoOtherPersonSameName);
					}
				}
				responsePersonInfo.setOtherPersonsWithSameName(responsePersonInfoListOtherPersonsSameName);
			}
		}

		return responsePersonInfo;
	}

	private long getPersonAge(Person person) {

		String personBirthdateString = medicalRecordModel.getMedicalRecordById(person.getIdPerson()).getBirthdate();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate personBirthdateDate = LocalDate.parse(personBirthdateString, formatter);

		return ChronoUnit.YEARS.between(personBirthdateDate, LocalDate.now());
	}

}
