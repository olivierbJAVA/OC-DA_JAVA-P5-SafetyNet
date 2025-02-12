package com.safetynet.service.urls;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.safetynet.entities.urls.FloodStation;
import com.safetynet.entities.urls.FloodPerson;
import com.safetynet.entities.urls.PersonInfo;
import com.safetynet.entities.urls.PersonInfoSameName;
import com.safetynet.service.endpoints.IFirestationMappingService;
import com.safetynet.service.endpoints.IMedicalRecordService;
import com.safetynet.service.endpoints.IPersonService;

/**
 * Class in charge of managing the services for the responses to the URLS
 * requests.
 */
@Service
public class ResponseUrlsServiceImpl implements IResponseUrlsService {

	@Autowired
	private IPersonService personService;

	@Autowired
	private IFirestationMappingService firestationMappingService;

	@Autowired
	private IMedicalRecordService medicalRecordService;

	/**
	 * Return the response to the Firestation URL request.
	 * 
	 * @param idStation The requested id
	 * 
	 * @return The Firestation response corresponding to the id requested
	 */
	@Override
	public Firestation responseFirestation(String idStation) {

		Firestation responseFirestation = new Firestation();

		List<FirestationPerson> responseFirestationListPersons = new ArrayList<>();

		List<Person> listAllPersons = personService.getAllPersons();

		int nbAdults = 0;
		int nbChilds = 0;

		for (Person personInList : listAllPersons) {

			if ((firestationMappingService.getFirestationMappingByAdress(personInList.getAddress()).getStation())
					.equals(idStation)) {

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

	/**
	 * Return the response to the ChildAlert URL request.
	 * 
	 * @param address The requested address
	 * 
	 * @return The ChildAlert response corresponding to the address requested
	 */
	@Override
	public ChildAlert responseChildAlert(String address) {

		ChildAlert responseChildAlert = new ChildAlert();

		List<ChildAlertChild> responseChildAlertListChilds = new ArrayList<>();

		List<Person> responseChildAlertListHouseholdMembers = new ArrayList<>();

		List<Person> listAllPersons = personService.getAllPersons();

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

	/**
	 * Return the response to the PhoneAlert URL request.
	 * 
	 * @param idStation The requested id
	 * 
	 * @return The PhoneAlert response corresponding to the id requested
	 */
	@Override
	public Set<String> responsePhoneAlert(String idStation) {

		Set<String> responsePhoneAlert = new HashSet<>();

		List<Person> listAllPersons = personService.getAllPersons();

		for (Person personInList : listAllPersons) {

			if ((firestationMappingService.getFirestationMappingByAdress(personInList.getAddress()).getStation())
					.equals(idStation)) {

				responsePhoneAlert.add(personInList.getPhone());

			}
		}
		return responsePhoneAlert;
	}

	/**
	 * Return the response to the Fire URL request.
	 * 
	 * @param address The requested address
	 * 
	 * @return The Fire response corresponding to the address requested
	 */
	@Override
	public Fire responseFire(String address) {

		Fire responseFire = new Fire();

		List<FirePerson> responseFirePerson = new ArrayList<>();

		List<Person> listAllPersons = personService.getAllPersons();

		String idStation = firestationMappingService.getFirestationMappingByAdress(address).getStation();

		responseFire.setIdStation(idStation);

		for (Person personInList : listAllPersons) {

			if (personInList.getAddress().equals(address)) {

				FirePerson firePerson = new FirePerson();

				firePerson.setFirstName(personInList.getFirstName());
				firePerson.setLastName(personInList.getLastName());
				firePerson.setPhone(personInList.getPhone());
				firePerson.setAge(getPersonAge(personInList));

				firePerson.setMedications(medicalRecordService
						.getMedicalRecordById(firePerson.getFirstName() + firePerson.getLastName()).getMedications());
				firePerson.setAllergies(medicalRecordService
						.getMedicalRecordById(firePerson.getFirstName() + firePerson.getLastName()).getAllergies());

				responseFirePerson.add(firePerson);
			}
		}
		responseFire.setFirePersons(responseFirePerson);

		return responseFire;
	}

	/**
	 * Return the response to the Flood URL request.
	 * 
	 * @param idsStation An array containing the requested ids
	 * 
	 * @return The Flood response corresponding to the ids requested
	 */
	@Override
	public Flood responseFlood(String[] idsStation) {

		Flood responseFlood = new Flood();

		Map<String, FloodStation> responseMapFloodStations = new HashMap<>();

		List<Person> listAllPersons = personService.getAllPersons();

		Set<String> allAddress = personService.getAllAddress();

		for (int i = 0; i < idsStation.length; i++) {

			Map<String, List<FloodPerson>> responseMapFloodPersons = new HashMap<>();

			FloodStation responseFloodStation = new FloodStation();

			for (String address : allAddress) {

				List<FloodPerson> responseListFloodPersons = new ArrayList<>();

				if ((firestationMappingService.getFirestationMappingByAdress(address).getStation())
						.equals(idsStation[i])) {

					for (Person personInList : listAllPersons) {

						if (personInList.getAddress().equals(address)) {

							FloodPerson responseFloodPerson = new FloodPerson();

							responseFloodPerson.setFirstName(personInList.getFirstName());
							responseFloodPerson.setLastName(personInList.getLastName());
							responseFloodPerson.setPhone(personInList.getPhone());
							responseFloodPerson.setAge(getPersonAge(personInList));

							responseFloodPerson.setMedications(medicalRecordService
									.getMedicalRecordById(
											responseFloodPerson.getFirstName() + responseFloodPerson.getLastName())
									.getMedications());
							responseFloodPerson.setAllergies(medicalRecordService
									.getMedicalRecordById(
											responseFloodPerson.getFirstName() + responseFloodPerson.getLastName())
									.getAllergies());

							responseListFloodPersons.add(responseFloodPerson);
						}
					}
					responseMapFloodPersons.put(address, responseListFloodPersons);
				}
			}
			responseFloodStation.setMapFloodPersons(responseMapFloodPersons);

			responseMapFloodStations.put(idsStation[i], responseFloodStation);

		}
		responseFlood.setMapFloodStations(responseMapFloodStations);

		return responseFlood;
	}

	/**
	 * Return the response to the PersonInfo URL request.
	 * 
	 * @param firstName The firstName of the person
	 * 
	 * @param lastName  The lastName of the person
	 * 
	 * @return The PersonInfo response corresponding to the firstName and lastName
	 *         requested
	 */
	@Override
	public PersonInfo responsePersonInfo(String firstName, String lastName) {

		PersonInfo responsePersonInfo = new PersonInfo();

		List<Person> listAllPersons = personService.getAllPersons();

		for (Person personInList : listAllPersons) {

			if (personInList.getIdPerson().equals(firstName + lastName)) {

				responsePersonInfo.setFirstName(firstName);
				responsePersonInfo.setLastName(lastName);
				responsePersonInfo.setAddress(personInList.getAddress());
				responsePersonInfo.setAge(getPersonAge(personInList));
				responsePersonInfo.setEmail(personInList.getEmail());

				responsePersonInfo.setMedications(medicalRecordService
						.getMedicalRecordById(responsePersonInfo.getFirstName() + responsePersonInfo.getLastName())
						.getMedications());
				responsePersonInfo.setAllergies(medicalRecordService
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

						responsePersonInfoOtherPersonSameName.setMedications(medicalRecordService.getMedicalRecordById(
								personInListWithSameName.getFirstName() + personInListWithSameName.getLastName())
								.getMedications());
						responsePersonInfoOtherPersonSameName.setAllergies(medicalRecordService.getMedicalRecordById(
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

	/**
	 * Return the response to the CommunityEmail URL request.
	 * 
	 * @param city The requested city
	 * 
	 * @return The CommunityEmail response corresponding to the city requested
	 */
	@Override
	public Set<String> responseCommunityEmail(String city) {

		List<Person> listPersons = personService.getAllPersons();

		Set<String> responseCommunityEmail = listPersons.stream().filter(person -> person.getCity().equals(city))
				.map(Person::getEmail).distinct().collect(Collectors.toSet());

		return responseCommunityEmail;
	}

	private long getPersonAge(Person person) {

		String personBirthdateString = medicalRecordService.getMedicalRecordById(person.getIdPerson()).getBirthdate();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate personBirthdateDate = LocalDate.parse(personBirthdateString, formatter);

		return ChronoUnit.YEARS.between(personBirthdateDate, LocalDate.now());
	}

}
