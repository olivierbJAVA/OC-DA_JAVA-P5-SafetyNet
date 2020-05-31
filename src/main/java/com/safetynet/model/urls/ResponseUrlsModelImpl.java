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
import com.safetynet.model.endpoints.IFirestationMappingModel;
import com.safetynet.model.endpoints.IMedicalRecordModel;
import com.safetynet.model.endpoints.IPersonModel;

@Service
public class ResponseUrlsModelImpl implements IResponseUrlsModel {

	@Autowired
	private IPersonModel personModel;

	@Autowired
	private IFirestationMappingModel firestationMappingModel;

	@Autowired
	private IMedicalRecordModel medicalRecordModel;

	@Override
	public Firestation responseFirestation(int stationNumber) {

		Firestation responseFirestation = new Firestation();

		List<FirestationPerson> responseFirestationListPersons = new ArrayList<>();

		List<Person> listAllPersons = personModel.getAllPersons();

		int nbAdults = 0;
		int nbChilds = 0;

		for (Person personInList : listAllPersons) {

			if ((firestationMappingModel.getFirestationMappingByAdress(personInList.getAddress())
					.getStation()) == stationNumber) {

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
			responseFirestation.setFirestationPersons(responseFirestationListPersons);
			responseFirestation.setNbAdults(nbAdults);
			responseFirestation.setNbChilds(nbChilds);
		}

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
				responseChildAlert.setChildAlertChilds(responseChildAlertListChilds);
				responseChildAlert.setChildAlertHouseholdMembers(responseChildAlertListHouseholdMembers);
			}
		}

		if (responseChildAlertListChilds.isEmpty()) {
			return null;
		}

		return responseChildAlert;
	}

	@Override
	public Fire responseFire(String address) {

		Fire responseFire = new Fire();

		List<FirePerson> responseFirePerson = new ArrayList<>();

		List<Person> listAllPersons = personModel.getAllPersons();

		int nbStation = firestationMappingModel.getFirestationMappingByAdress(address).getStation();

		responseFire.setNbStation(nbStation);

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
	public Flood responseFlood(int stationNumber) {

		Flood responseFlood = new Flood();

		Map<String, List<FloodPerson>> responseMapFloodPersons = new HashMap<>();

		List<Person> listAllPersons = personModel.getAllPersons();

		Set<String> listAllAddress = personModel.getAllAddress();

		for (String address : listAllAddress) {

			List<FloodPerson> responseListFloodPersons = new ArrayList<>();

			if (firestationMappingModel.getFirestationMappingByAdress(address).getStation() == stationNumber) {

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

	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	@Override
	public PersonInfo responsePersonInfo(String firstName, String lastName) {

		PersonInfo responsePersonInfo = new PersonInfo();

		List<Person> listAllPersons = personModel.getAllPersons();

		for (Person personInList : listAllPersons) {

			if (personInList.getIdPerson().equals(firstName + lastName)) {

				responsePersonInfo.setFirstName(firstName);
				responsePersonInfo.setLastName(lastName);
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

	@Override
	public Set<String> responsePhoneAlert(int firestation) {

		Set<String> responsePhoneAlert = new HashSet<>();

		List<Person> listAllPersons = personModel.getAllPersons();

		for (Person personInList : listAllPersons) {

			if ((firestationMappingModel.getFirestationMappingByAdress(personInList.getAddress())
					.getStation()) == firestation) {

				responsePhoneAlert.add(personInList.getPhone());

			}
		}
		return responsePhoneAlert;
	}

	private long getPersonAge(Person person) {

		String personBirthdateString = medicalRecordModel.getMedicalRecordById(person.getIdPerson()).getBirthdate();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate personBirthdateDate = LocalDate.parse(personBirthdateString, formatter);

		return ChronoUnit.YEARS.between(personBirthdateDate, LocalDate.now());
	}

}
