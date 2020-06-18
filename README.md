# SafetyNet
Welcome to SafetyNet !

SafetyNet is a web application which goal is to produce information to help rescue teams in case of emergency.

It is a SpringBoot web application based on the MVC pattern and a REST API.

This application uses Java and SpringBoot to run. Maven is used to run the tests, build and package the application.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

You need to install the following software :

- Java 1.8
- Maven 3.6.2
- SpringBoot 2.3.0

### Installing

A step by step explanation that tell you how to get a development environment running:

1.Install Java:
<https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html>

2.Install Maven:
<https://maven.apache.org/install.html>

3.Install SpringBoot :
<https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started-installing-spring-boot>

### Application running

Then you can import and run the application from your favorite IDE.

### Features
The following Endpoints are implemented :

- Persons Endpoint : <http://localhost:8080/persons>

- Firestations Endpoint : <http://localhost:8080/firestations>

- Medical Records Endpoint : <http://localhost:8080/medicalRecords>

For each Endpoint, the GET, POST, PUT and DELETE requests are working.

The following URLS are implemented :

- Firestation : <http://localhost:8080/firestation?stationNumber=station_number>

- Child Alert : <http://localhost:8080/childAlert?address=address>

- Phone Alert : <http://localhost:8080/phoneAlert?firestation=firestation_number>

- Fire : <http://localhost:8080/fire?address=address>

- Flood : <http://localhost:8080/flood/stations?stations=a_list_of_station_numbers>

- Person Info : <http://localhost:8080/personInfo?firstName=firstName&lastName=lastName>

- Community Email : <http://localhost:8080/communityEmail?city=city>

For each URLS, the GET request allows to get the information.

### Standalone executable JAR file

You can produce a standalone executable JAR file, by running the below command :

`mvn clean install`

### Data Initialization file

The application uses a JSON format file to initialize the data. 

If you run the application using a JAR executable file, the input data file has to be named *'data.json'* and has to be put in the same directory as the JAR file. 

You can change this by modifying the property *'filePathInputData'* in the SpringBoot configuration file : *'application.properties'*.

### Logging

The tool Log4J2 is used for logging. Logs are sent to the console and to a file.

You can configure the logging to your own needs by using the configuration file : *'log4j2-spring.xml'*.

### Tests

Unit tests are included, you can run them using JUnit runner (`Run as JUnit test`) or using Maven (`Run as Maven test`).

There is a dedicated input data file for the tests : *'data-test.json'*.

Surefire reports are generated when application is built with Maven. To get a Surefire concatenated report, please use the following Maven command : 
`mvn surefire-report:report`

### Actuators

SpringBoot Actuators to monitor and get information about the application are available at the following addresses :

- Health : <http://localhost:8080/actuator/health>

- Info : <http://localhost:8080/actuator/info>

- Metrics : <http://localhost:8080/actuator/metrics>

- HTTPTrace : <http://localhost:8080/actuator/httptrace>
