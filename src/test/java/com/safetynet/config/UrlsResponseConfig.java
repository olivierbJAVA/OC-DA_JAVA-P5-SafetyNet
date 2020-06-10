package com.safetynet.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.safetynet.entities.endpoints.Person;
import com.safetynet.entities.urls.ChildAlert;
import com.safetynet.entities.urls.ChildAlertChild;
import com.safetynet.entities.urls.Fire;
import com.safetynet.entities.urls.FirePerson;
import com.safetynet.entities.urls.Firestation;
import com.safetynet.entities.urls.FirestationPerson;
import com.safetynet.entities.urls.Flood;
import com.safetynet.entities.urls.FloodPerson;
import com.safetynet.entities.urls.FloodStation;
import com.safetynet.entities.urls.PersonInfo;
import com.safetynet.entities.urls.PersonInfoSameName;

/**
 * Class used to get expected responses to URLS requests.
 */
public class UrlsResponseConfig {

	public Firestation getUrlFirestationResponse() {

		Firestation firestationUrlResponse = new Firestation();

		FirestationPerson firestationPerson1 = new FirestationPerson("Jonanathan", "Marrack", "29 15th St",
				"841-874-6513");
		FirestationPerson firestationPerson2 = new FirestationPerson("Warren", "Zemicks", "892 Downing Ct",
				"841-874-7512");
		FirestationPerson firestationPerson3 = new FirestationPerson("Eric", "Cadigan", "951 LoneTree Rd",
				"841-874-7458");
		FirestationPerson firestationPerson4 = new FirestationPerson("Sophia", "Zemicks", "892 Downing Ct",
				"841-874-7878");
		FirestationPerson firestationPerson5 = new FirestationPerson("Zach", "Zemicks", "892 Downing Ct",
				"841-874-7512");

		List<FirestationPerson> firestationPersons = new ArrayList<>();
		firestationPersons.add(firestationPerson1);
		firestationPersons.add(firestationPerson2);
		firestationPersons.add(firestationPerson3);
		firestationPersons.add(firestationPerson4);
		firestationPersons.add(firestationPerson5);

		firestationUrlResponse.setFirestationPersons(firestationPersons);
		firestationUrlResponse.setNbAdults(4);
		firestationUrlResponse.setNbChilds(1);

		return firestationUrlResponse;
	}

	public ChildAlert getUrlChildAlertResponse() {

		ChildAlert childAlertUrlResponse = new ChildAlert();

		List<ChildAlertChild> childAlertChilds = new ArrayList<>();
		ChildAlertChild childAlertChild1 = new ChildAlertChild("Roger", "Boyd", 2);
		ChildAlertChild childAlertChild2 = new ChildAlertChild("Tenley", "Boyd", 8);
		childAlertChilds.add(childAlertChild1);
		childAlertChilds.add(childAlertChild2);

		List<Person> childAlertOtherHouseholdMembers = new ArrayList<>();
		Person childAlertOtherHouseholdMember1 = new Person("JacobBoyd", "Jacob", "Boyd", "1509 Culver St", "Culver",
				"97451", "841-874-6513", "drk@email.com");
		Person childAlertOtherHouseholdMember2 = new Person("JohnBoyd", "John", "Boyd", "1509 Culver St", "Culver",
				"97451", "841-874-6512", "jaboyd@email.com");
		Person childAlertOtherHouseholdMember3 = new Person("FeliciaBoyd", "Felicia", "Boyd", "1509 Culver St",
				"Culver", "97451", "841-874-6544", "jaboyd@email.com");
		childAlertOtherHouseholdMembers.add(childAlertOtherHouseholdMember1);
		childAlertOtherHouseholdMembers.add(childAlertOtherHouseholdMember2);
		childAlertOtherHouseholdMembers.add(childAlertOtherHouseholdMember3);

		childAlertUrlResponse.setChildAlertChilds(childAlertChilds);
		childAlertUrlResponse.setChildAlertHouseholdMembers(childAlertOtherHouseholdMembers);

		return childAlertUrlResponse;
	}

	public Set<String> getUrlPhoneAlertResponse() {

		Set<String> phoneAlertUrlResponse = new HashSet<>(
				Arrays.asList("841-874-7784", "841-874-7462", "841-874-6512", "841-874-8547"));

		return phoneAlertUrlResponse;
	}

	public Fire getUrlFireResponse() {

		Fire fireUrlResponse = new Fire();

		List<FirePerson> firePersons = new ArrayList<>();
		FirePerson firePerson1 = new FirePerson("Jonanathan", "Marrack", "841-874-6513", 31, new String[] {},
				new String[] {});
		firePersons.add(firePerson1);

		fireUrlResponse.setIdStation("2");
		fireUrlResponse.setFirePersons(firePersons);

		return fireUrlResponse;
	}

	public Flood getUrlFloodResponse() {

		Flood floodUrlReponse = new Flood();
		Map<String, FloodStation> mapFloodStations = new HashMap<>();

		FloodStation floodStation = new FloodStation();
		Map<String, List<FloodPerson>> mapFloodPersons = new HashMap<>();

		String address1 = "489 Manchester St";
		FloodPerson floodPerson1Address1 = new FloodPerson("Lily", "Cooper", "841-874-9845", 26, new String[] {},
				new String[] {});
		List<FloodPerson> floodPersonsAddress1 = new ArrayList<>();
		floodPersonsAddress1.add(floodPerson1Address1);

		String address2 = "112 Steppes Pl";
		FloodPerson floodPerson1Address2 = new FloodPerson("Allison", "Boyd", "841-874-9888", 55,
				new String[] { "aznol:200mg" }, new String[] { "nillacilan" });
		FloodPerson floodPerson2Address2 = new FloodPerson("Ron", "Peters", "841-874-8888", 55, new String[] {},
				new String[] {});
		FloodPerson floodPerson3Address2 = new FloodPerson("Tony", "Cooper", "841-874-6874", 26,
				new String[] { "hydrapermazol:300mg", "dodoxadin:30mg" }, new String[] { "shellfish" });
		List<FloodPerson> floodPersonsAddress2 = new ArrayList<>();
		floodPersonsAddress2.add(floodPerson1Address2);
		floodPersonsAddress2.add(floodPerson2Address2);
		floodPersonsAddress2.add(floodPerson3Address2);

		mapFloodPersons.put(address1, floodPersonsAddress1);
		mapFloodPersons.put(address2, floodPersonsAddress2);

		floodStation.setMapFloodPersons(mapFloodPersons);

		String station1 = "4";
		mapFloodStations.put(station1, floodStation);

		floodUrlReponse.setMapFloodStations(mapFloodStations);

		return floodUrlReponse;
	}

	public PersonInfo getUrlPersonInfoResponse() {

		List<PersonInfoSameName> otherPersonsWithSameName = new ArrayList<PersonInfoSameName>();

		PersonInfoSameName otherPersonWithSameName = new PersonInfoSameName("Tony", "Cooper", "112 Steppes Pl", 26,
				"tcoop@ymail.com", new String[] { "hydrapermazol:300mg", "dodoxadin:30mg" },
				new String[] { "shellfish" });

		otherPersonsWithSameName.add(otherPersonWithSameName);

		PersonInfo personInfoUrlResponse = new PersonInfo("Lily", "Cooper", "489 Manchester St", 26, "lily@email.com",
				new String[] {}, new String[] {}, otherPersonsWithSameName);

		return personInfoUrlResponse;
	}

	public Set<String> getUrlCommunityEmailResponse() {

		Set<String> communityEmailUrlResponse = new HashSet<>(Arrays.asList("jaboyd@email.com", "ssanw@email.com",
				"drk@email.com", "tenz@email.com", "bstel@email.com", "aly@imail.com", "ward@email.com",
				"reg@email.com", "lily@email.com", "gramps@email.com", "jpeter@email.com", "clivfd@ymail.com",
				"tcoop@ymail.com", "soph@email.com", "zarc@email.com"));

		return communityEmailUrlResponse;
	}

}
