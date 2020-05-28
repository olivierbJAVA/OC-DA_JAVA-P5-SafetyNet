# SafetyNet
Welcome to SafetyNet !

SafetyNet is a web application used for safety management.
It is a SpringBoot web application based on the MVC pattern and a REST API.

This application uses Java and SpringBoot to run. Maven is used to build and package the application.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them.

- Java 1.8
- Maven 3.6.2
- SpringBoot 2.3.0

### Installing

A step by step explanation that tell you how to get a development environment running:

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html

3.Install SpringBoot

### Application running

Then you can run the application from your favorite IDE.

### Features
The following endpoints are implemented :

- Persons endpoint : *http://localhost:8080/persons*

- Firestation endpoint : *http://localhost:8080/firestations*

- MedicalRecords endpoint : *http://localhost:8080/medicalRecords*

For each endpoint the GET, POST, PUT and DELETE methods are working.

The following URL is implemented :

- http://localhost:8080/communityEmail?city=city

### Logging

The tool Log4J2 is used for logging. Logs are sent to the console and to a file.

### TO DO
- URLS implementation
- Tests implementation
- Put in place Actuators
- Build with Maven
- API documentation
