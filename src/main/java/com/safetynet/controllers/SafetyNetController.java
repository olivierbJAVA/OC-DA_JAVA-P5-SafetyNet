package com.safetynet.controllers;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.safetynet.application.SafetyNetSpringConfig;
import com.safetynet.entities.Firestation;
import com.safetynet.entities.MedicalRecord;
import com.safetynet.entities.Person;
import com.safetynet.model.IFirestationModel;
import com.safetynet.model.IMedicalRecordModel;
import com.safetynet.model.IPersonModel;



public class SafetyNetController {

	private int userChoice;
	
	public SafetyNetController(int option) {
		userChoice = option;
	}

	public int getUserChoice() {
		return userChoice;
	}

	public void setUserChoice(int userChoice) {
		this.userChoice = userChoice;
	}

	public void getModelandPrintView() {
		
		//ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
				
		ApplicationContext context =new AnnotationConfigApplicationContext(SafetyNetSpringConfig.class);
		
		switch (userChoice) {
		case 1: {
			// Get & set Model - SETTERS
			System.out.println("\nController : get Person model");
			IPersonModel personModel = context.getBean(IPersonModel.class);
			
			// Print View - SETTERS
			System.out.println("\nController : print person");
			
			List<Person> personList = personModel.listPerson();
			
			// vue
		break;
		}
		case 2: {
			// Get & set Model - CONSTRUCTEUR
			System.out.println("\nController : get MedicalRecord model");
			
			IMedicalRecordModel medicalRecordModel = (IMedicalRecordModel)context.getBean(IMedicalRecordModel.class);
						
			// Print View - CONSTRUCTEUR
			System.out.println("\nController : print medicalRecord");
			
			List<MedicalRecord> medicalRecordList = medicalRecordModel.listMedicalRecords();
			
			// vue
		break;
		}
		case 3: {
			// Get & set Model - SETTERS
			System.out.println("\nController : get Firestation model");

			IFirestationModel firestationModel = (IFirestationModel)context.getBean(IFirestationModel.class);
						
			// Print View - METHOD PARAMETER
			System.out.println("\nController : print firestation");
			
			List<Firestation> firestationList = firestationModel.listFirestation();
			
			// vue
			break;
		}

		default:
			System.out.println("Unsupported option. Error");
		}
	}

}
