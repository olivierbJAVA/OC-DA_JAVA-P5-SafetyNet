package com.safetynet.config;

import java.util.ArrayList;
import java.util.List;

import com.safetynet.entities.endpoints.FirestationMapping;
import com.safetynet.entities.endpoints.Person;

public class InputDataConfig {

	public List<Person> inputDataPerson() {

		List<Person> inputListAllPersons = new ArrayList<Person>();

		Person person1 = new Person("RogerBoyd", "Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		Person person2 = new Person("PeterDuncan", "Peter", "Duncan", "644 Gershwin Cir", "Culver", "97451",
				"841-874-6512", "jaboyd@email.com");
		Person person3 = new Person("ShawnaStelzer", "Shawna", "Stelzer", "947 E. Rose Dr", "Culver", "97451",
				"841-874-7784", "ssanw@email.com");
		Person person4 = new Person("JacobBoyd", "Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513",
				"drk@email.com");
		Person person5 = new Person("TenleyBoyd", "Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"tenz@email.com");
		Person person6 = new Person("JonanathanMarrack", "Jonanathan", "Marrack", "29 15th St", "Culver", "97451",
				"841-874-6513", "drk@email.com");
		Person person7 = new Person("KendrikStelzer", "Kendrik", "Stelzer", "947 E. Rose Dr", "Culver", "97451",
				"841-874-7784", "bstel@email.com");
		Person person8 = new Person("AllisonBoyd", "Allison", "Boyd", "112 Steppes Pl", "Culver", "97451",
				"841-874-9888", "aly@imail.com");
		Person person9 = new Person("WarrenZemicks", "Warren", "Zemicks", "892 Downing Ct", "Culver", "97451",
				"841-874-7512", "ward@email.com");
		Person person10 = new Person("ReginoldWalker", "Reginold", "Walker", "908 73rd St", "Culver", "97451",
				"841-874-8547", "reg@email.com");
		Person person11 = new Person("TessaCarman", "Tessa", "Carman", "834 Binoc Ave", "Culver", "97451",
				"841-874-6512", "tenz@email.com");
		Person person12 = new Person("LilyCooper", "Lily", "Cooper", "489 Manchester St", "Culver", "97451",
				"841-874-9845", "lily@email.com");
		Person person13 = new Person("EricCadigan", "Eric", "Cadigan", "951 LoneTree Rd", "Culver", "97451",
				"841-874-7458", "gramps@email.com");
		Person person14 = new Person("JohnBoyd", "John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		Person person15 = new Person("JamiePeters", "Jamie", "Peters", "908 73rd St", "Culver", "97451", "841-874-7462",
				"jpeter@email.com");
		Person person16 = new Person("BrianStelzer", "Brian", "Stelzer", "947 E. Rose Dr", "Culver", "97451",
				"841-874-7784", "bstel@email.com");
		Person person17 = new Person("CliveFerguson", "Clive", "Ferguson", "748 Townings Dr", "Culver", "97451",
				"841-874-6741", "clivfd@ymail.com");
		Person person18 = new Person("FosterShepard", "Foster", "Shepard", "748 Townings Dr", "Culver", "97451",
				"841-874-6544", "jaboyd@email.com");
		Person person19 = new Person("RonPeters", "Ron", "Peters", "112 Steppes Pl", "Culver", "97451", "841-874-8888",
				"jpeter@email.com");
		Person person20 = new Person("TonyCooper", "Tony", "Cooper", "112 Steppes Pl", "Culver", "97451",
				"841-874-6874", "tcoop@ymail.com");
		Person person21 = new Person("SophiaZemicks", "Sophia", "Zemicks", "892 Downing Ct", "Culver", "97451",
				"841-874-7878", "soph@email.com");
		Person person22 = new Person("FeliciaBoyd", "Felicia", "Boyd", "1509 Culver St", "Culver", "97451",
				"841-874-6544", "jaboyd@email.com");
		Person person23 = new Person("ZachZemicks", "Zach", "Zemicks", "892 Downing Ct", "Culver", "97451",
				"841-874-7512", "zarc@email.com");

		inputListAllPersons.add(person1);
		inputListAllPersons.add(person2);
		inputListAllPersons.add(person3);
		inputListAllPersons.add(person4);
		inputListAllPersons.add(person5);
		inputListAllPersons.add(person6);
		inputListAllPersons.add(person7);
		inputListAllPersons.add(person8);
		inputListAllPersons.add(person9);
		inputListAllPersons.add(person10);
		inputListAllPersons.add(person11);
		inputListAllPersons.add(person12);
		inputListAllPersons.add(person13);
		inputListAllPersons.add(person14);
		inputListAllPersons.add(person15);
		inputListAllPersons.add(person16);
		inputListAllPersons.add(person17);
		inputListAllPersons.add(person18);
		inputListAllPersons.add(person19);
		inputListAllPersons.add(person20);
		inputListAllPersons.add(person21);
		inputListAllPersons.add(person22);
		inputListAllPersons.add(person23);

		return inputListAllPersons;
	}

	public List<FirestationMapping> inputDataFirestatioMappings() {

		List<FirestationMapping> inputListAllFirestationMappings = new ArrayList<FirestationMapping>();

		FirestationMapping FirestationMapping1 = new FirestationMapping("748 Townings Dr", "3");
		FirestationMapping FirestationMapping2 = new FirestationMapping("951 LoneTree Rd", "2");
		FirestationMapping FirestationMapping3 = new FirestationMapping("489 Manchester St", "4");
		FirestationMapping FirestationMapping4 = new FirestationMapping("908 73rd St", "1");
		FirestationMapping FirestationMapping5 = new FirestationMapping("947 E. Rose Dr", "1");
		FirestationMapping FirestationMapping6 = new FirestationMapping("644 Gershwin Cir", "1");
		FirestationMapping FirestationMapping7 = new FirestationMapping("1509 Culver St", "3");
		FirestationMapping FirestationMapping8 = new FirestationMapping("892 Downing Ct", "2");
		FirestationMapping FirestationMapping9 = new FirestationMapping("834 Binoc Ave", "3");
		FirestationMapping FirestationMapping10 = new FirestationMapping("112 Steppes Pl", "4");
		FirestationMapping FirestationMapping11 = new FirestationMapping("29 15th St", "2");

		inputListAllFirestationMappings.add(FirestationMapping1);
		inputListAllFirestationMappings.add(FirestationMapping2);
		inputListAllFirestationMappings.add(FirestationMapping3);
		inputListAllFirestationMappings.add(FirestationMapping4);
		inputListAllFirestationMappings.add(FirestationMapping5);
		inputListAllFirestationMappings.add(FirestationMapping6);
		inputListAllFirestationMappings.add(FirestationMapping7);
		inputListAllFirestationMappings.add(FirestationMapping8);
		inputListAllFirestationMappings.add(FirestationMapping9);
		inputListAllFirestationMappings.add(FirestationMapping10);
		inputListAllFirestationMappings.add(FirestationMapping11);

		return inputListAllFirestationMappings;
	}
}
