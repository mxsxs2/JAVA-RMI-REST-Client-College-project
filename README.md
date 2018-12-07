# JAVA-RMI-REST-Client-College-project
JAVA Remote Method Invocation + JAVA REST server with XML/JSON + SPRING Thymeleaf Client Project for college.

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
The datamodel was created as an [XML Schema](https://www.w3.org/standards/xml/schema). I designed the Schema and modifed as I developed the application. I wanted to avoid the creation of the classes and annotate them manually.
### The model entities
* Address

    Stores the address of the customers.
* Person

    Stores the ID, name and the address of the customer.
* Car

    Stores details of a car i.e. ID, model, make and colour.
* Cars

    Wrapper for a list of cars (Needed for data transfer from REST-Server to WEB-client).
* BookingTimeFrame

    Stores the booking start and end time in long format.
* Booking

    Stores the booking ID, customer information, car information, bookingtimeframe and the reservation time in long format.
* BookingMessage

    Wrapper for a booking and a message. (Needed for data transfer from REST-Server to WEB-client)
### Model generator
The model is generated with Maven `maven-jaxb2-plugin` plugin. This allows the generation of the model at maven compile time. The generated files resides at `ie.gmit.sw.model` package in Maven's `target/generated-sources/xjc` folder.
#### Bindings
I incorporated a bindig file to set the binding process `simple` (Which adds `XMLRootElement`) and adds `Serializable` interface to every class. This allows the smooth conversion for from XML to Object and vica-versa.
#### Annotations
* `jaxb2-basics-annotate` has been used to generate `javax.validation` annotations for Spring Boot's validation. 
* `jaxb2-basics` has been used to generate `toString` methods and setter methods for `List` types (setter is not generated with plain xjc)


## 1. Database
The underlying database is MongoDB. The structure is simple:
* `carbooking` as DB
    * `bookings` collection
    * `cars` collection
Each booking contains a copy of a car. If I would only have  the `_id` stored that would be against MonogDB's principles. If I would store only at each booking, then I would lose the reference to the cars and its information when a booking is deleted.

Denormalisation is completely normal with document stores.

## 2. RMI-Server
This project is the database connector.
### Database
MongoDB is used here a simple class represends the connection and configuration of the database. This class is used for CRUD operations by injecting into the RMI service implementation.
The database connection is fully configruable with command line arguments.(See at RMI-Server->Run).
#### Mongo/Bson Codec
Bson's default codec is used for data conversion. Is is very useful as i do not have to convert the XML to Objects and vica-versa. This is important as it allows me to change the model without changing the implementation. (Obviosly the database has to be emptied then)
### RMI Service implementation
This class is the implementation of `BookingService` interface. The class handles every booking and car related operations by colling the appropriate MongoDb methods.
### RMI Server
The server is a simple RMI registry. This is fully configruable with command line arguments.(See at RMI-Server->Run).
### ServiceSetup
Contains the main class for the application
### Run
The project is packaged with Maven. First it has to be packaged by `mvn install`, which generates two files into Maven's `target` folder:
* RMI-Server-1.0.SNAPSHOT.jar
* RMI-Server-1.0.SNAPSHOT-jar-with-dependencies.jar

As their name shows one is with dependencies and one is without.
They are both runnable jar files, however _RMI-Server-1.0.SNAPSHOT-jar-with-dependencies.jar_ is easier to run:
 
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

## 3. REST-Server
This project connects to the RMI-Server and it is a HTTP server
### RMI Connection
An `RMIClient` singleton class is implemented from the same `BookingService` interface as the RMI-Server.There are three helper methods:
* `connect` Returns the RMI resource from an RMI URL
* `invokeNull`,`invokeOne` and `invokeThree` are calling the appropriate interface methods on the `connect()`'s return resource. Heavily relies on reflection.
### REST
The server is implementeted with JAX-RS/Jersey with three services
* `BookingService` which deals with the booking related request
* `CarService` which deals with the car related requests
* `ProtectedService` which allows the car addition and can return a list of bookings
#### Response codes
* 200 - All good a resource will be returned: `Booking`,`Car`,`Cars`,`BookingMessage`
* 204 - The request was interpreted correctly bot no resource found
* 400 - One(or more) of the required field(s) is missing
* 404 - When an endpoint is not found
* 409 - The resource already exists
* 500 - Server error (during my tests it did not occour but it is possible)
#### Endpoints
I used PUT over POST as it is idempotent, therefore I can just return 200
* GET - `booking/{id}` returns a booking
* PUT - `booking/create` saves a new booking
* PUT - `booking/change` changes (it actually replaces) a booking (since it replaces, I choose to use PUT rather than POST)
* DELETE - `booking/{id}` deletes a booking
* GET - `car/{id}` returns a car
* PUT - `car/{id}/{from}/{to}/{bookingid:(/bookingid/[^/]+?)?}` returns the availabily of a car for a given date range. If the booking ID is present, the database search excludes that ID, this is used for booking change
* GET - `car/list` returns the `Cars` wrapper class with the list of cars in it
* PUT - `admin/car` saves a new car
* GET - `admin/booking/list` returns a list of bookings
### Deploy
The project is can be run on tomcat with maven: ```mvn tomcat:run```.
#### Server configuration
Connection to the RMI server can be modified at web.xml. I tried to make the connection modifiable from command line. However, it seemed to be a great effort to capture the command line arguments as they have to travel through 3 layers: `Tomcat->Jersey->RMI setup`.

Tomcat port can be specified as follows:
```mvn -Dmaven.tomcat.port=8081 tomcat:run```

## 4. WEB-Client
The webclient is a webserver serving the GUI and connection to the REST end points. It is a Spring Boot application with Maven and Thymeleaf
### GUI
The GUI is created with Thymeleaf. It uses normal HTML with additional expressions, making the development much easier.
#### Templates
There is 3 main templates:
* Index - the landing page
* View - search box for finding bookings by ID and renders the found model. It also has the option for changing or deleting a model
* New - renders a form for creating or modifind a booking. It has a dropdown with all the cars.
#### Fragments
Every fragment for the layout is stored in `templates/fragments/header`. These fragments are re-used on every page. It renders the menu, header links and JavaScripts and the server not available error message.
#### Webjars
For bootstrap `org.webjars` is used. It is a better approach for a small project than using CDN and it is a lot better aproach than having the resources in the static folder.
#### Data formatters
I had to use formatters for the car dropdown and for the date and time:
* The conversion for car dropdown is necessary as a complete class cannot be used as field value. I used the car's ID as value and I converted it on submission to an object.
* The time formatter was necessary as I stored the datetime as `long` type in the database.
#### Form validation
Form validation is done by Spring Boot's built-in mechanishm. The bean models has been apropriately annotated in the XML schema.  
### DAO
Both Car and Booking models has their own DAOs. They utilises the `RestService` service class which provides the connection to the rest server.
### Controllers
This is the back end for the GUI templates. They utilises the DAO classes to get the models/messages into the apropriate templates.
### Run
The project is packaged with Maven. First it has to be packaged by `mvn install`.
To run it:
```mvn spring-boot:run```
#### Server configuration
The configuration is stored at `application.properties` file. The port and the rest url can be modified here. They can also be modified in command line:
```mvn spring-boot:run -Dspring-boot.run.arguments=--resturl=http://127.0.0.1:8081/REST-Server,--port=81```
Where
* `--port` is the server port for spring boot
* `--resturl` is the url for the rest server

## 4. Python-Client
A simple command line client written in python to interact with the admin side of rest server. In only has to functions:
* Get booking list
* Add a new car.
This application should be used to add a new car to the database. It is just a demo of the JSON functionality of the REST server not a full client.
### Run
The easiest way to set up the python environment and get all required libraries is to install [Anaconda](https://www.anaconda.com/download/)
To run the application:
```python client.py```

Optionally the rest server's url can be provided the following way:
```python client.py --resturl http://localhost:8080/REST-Server/```

## What could be added
* REST protected features has no GUI buil into Web-Client
* REST protected features could have some sort of login and session management
* Each individual project could be added to Docker containers and spin up on AWS or some other cloud server
* WEB-Client cloud be AJAX rather than reload on form submission
* The generated `ie.gmit.sw.model` package could be an individual project, in which case the generated artefact should be added as a dependency in each project.

## Extras
* All three projects are full Maven projects, nicely organised pom files with dependencies
* The model is properly generated from schemas with extra annotations and extre methods
* The web clients structure is thoughfully designed and excuted. (Additionally with an excellent GUI)
* The servers and the connections between them is fully configurable