# JAVA-RMI-REST-Client-College-project
JAVA Remote Method Invocation + JAVA REST server with XML/JSON + SPRING Tymeleaft Client Project for college.

Ideally this project should have been split up to multiple repositories, but as it is a college project I left them in one place for convinience.
## Overview (From odiginal reruirements document)
_You are required to use the JAX-RS/Jersey, Java RMI and JAXB frameworks to develop a simple Car Hire
Booking System. A Web Client page should provide users with the ability to Create/Modify/Update/Delete
bookings for a specific vehicle for a given set of dates. The Web Client will interact with a RESTful JAX-RS
Web Service for bookings which is deployed on Apache Tomcat Server. The RESTful Web Service will act as
a RMI client to a RMI Database Server which will handle persistence._

## Minimum Requirements (From original requirements document)
* _Simple Web Client (Java JSP/Servlet or .NET equivalent if preferred)_
    1. _A Web Client will act as a GUI for the entire Car Hire Booking System. This GUI will allow a booking
to be Created, Read, Updated or Deleted. The Web Client will communicate with the RESTful Web
Service below, using XML for marshalling and unmarshalling of data._
* _RESTful Web Service (JAX-RS/Jersey)_
    1. _Design a RESTful Web Service using JAX-RS/Jersey which will act as the gateway for all clients
which wish to use the Car Hire Booking System. Clients will be able to access CRUD functionality for
car hire bookings using the GET, POST, PUT and DELETE methods. This class will be responsible for
marshalling/unmarshalling data to/from XML for all Web Client requests/responses. This class will
also act as a client for the RMI Database Server._
* _Data Modelling_
    1. _An appropriate data model will be required for all classes/entities which are part of a car hire booking
(e.g. Customer, Vehicle, Booking). This data model will be in the form of an XML Schema Definition.
The xjc (XML to Java Converter) utility may then be used to generate the appropriate Java classes from
the schema._
* _RMI Database Server_
    1. _A remote interface called DatabaseService should expose remote methods which provide CRUD
(Create, Read, Update and Delete) functionality for each of the entities which have you modelled. This
interface definition must be available to the RMI Database Server and the RMI Client. Objects sent
or received via RMI will be required to implement the java.io.Serializable interface._
    2. _An implementation of the DatabaseService interface called DatabaseServiceImpl will handle
persistence and CRUD functionality using a database of your choice (e.g. JDBC)._
    3. _ServiceSetup will contain a main method which instantiates DatabaseServiceImpl and binds the
Remote Object into the RMI registry using the name “databaseservice”._

# Implementation

## Datamodel
The datamodel was created as an [XML Schema](https://www.w3.org/standards/xml/schema). Designed the Schema and modifed as I developed the application. I wanted to avoid the creation of the classes and annotate them manually.
### The model entities
* Address

    Stores the address of the costumer
* Person

    Stores the id, name and the address of the customer
* Car

    Stores details of a car e.g id, model, make, color
* Cars

    Wrapper for a list of the cars (Needed for data transfer from REST-Server to WEB-client)
* BookingTimeFrame

    Stores the booking start and end times in long format.
* Booking

    Stores the booking id, person, car, bookingtimeframe and the reservation time in long format
* BookingMessage

    Wrapper for a booking and a message. (Needed for data transfer from REST-Server to WEB-client)
### Model generator
The model is generated with Maven `maven-jaxb2-plugin` plugin. This allows the generation of the model at maven compile time. The generated files resides at `ie.gmit.sw.model` package in Maven's `target/generated-sources/xjc` folder.
#### Bindings
I incorporated a bindig file to set the binding process `simple` (Which adds `XMLRootElement`) and add the `serializable` to every class. This allows the smoth conversion for RMI and JAXB and MongoDB Driver.
#### Annotations
* `jaxb2-basics-annotate` has been used to generate `javax.validation` annotations for Spring Boots validation. 
* `jaxb2-basics` has been used to generate `toString` methods and setter methods for `List` types (setter is not generated with plain xjc)

## 1. RMI-Server
This project is the database connector.
### Database
MongoDB is used here a simple class Represends the connection and configuration of the database. This class is used for CRUD operations by injecting into the RMI service implementation.
The database connection is fully configruable with command line arguments.(See at RMI-Server->Run).
#### Mongo/Bson Codec
Bson's default codec is used for data conversion. Is is very useful as i do not have to bother with conversion of XML to Objects and vica-versa. This is important, I can change the model without changing the implementation. (Obviosly the database has to be emptied then)
### RMI Service implementation
This class is the implementation of `BookingService` interface. The class handles every booking and car related operations by colling the appropriate MongoDb methods.
### RMI Server
The server is a simple RMI registry. This is fully configruable with command line arguments.(See at RMI-Server->Run).
### ServiceSetup
Contains the main class for the application
### Run
The project is packaged with Maven. Frirst it has to be packaged by `mvn install`. Which generates two files into Maven's `target` folder:
* RMI-Server-1.0.SNAPSHOT.jar
* RMI-Server-1.0.SNAPSHOT-jar-with-dependencies.jar

As their name shows one is with dependencies and one is without.
They are both runnable jar files, however _ RMI-Server-1.0.SNAPSHOT-jar-with-dependencies.jar_ is easier to run: 
``` java -jar RMI-Server-1.0.SNAPSHOT-jar-with-dependencies.jar```
#### Server configuration
The server can be fully configured from command line with the following argumens:
* `-port` is the port for RMI registry
* `-name` is the name of the RMI service
* `-dbport` is the port for MongoDB
* `-dbhost` is the host for MongoDB
* `-dbname` is the database name for MongoDB
Each one of them can be used individaully or with others. They has to be used by the following way:
``` java -jar RMI-Server-1.0.SNAPSHOT-jar-with-dependencies.jar -port 1200```
