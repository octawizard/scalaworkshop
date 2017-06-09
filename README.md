# scalaworkshop
A little exercise to experiment Scala. The application scenario is the one typical of an
Online Travel Agency.
This project is a RESTful web service of this hypothetical OTA. The entities are users,
flights, bookings, locations, notifications.

## Technology stack
* Scala 2.12.1
* Akka Http 10.0.5
* Jackson 2.2.8
* Scala test 3.0.1
* Scala mock 3.5.0
* Maven 3.3.9

##To build
Just type the following in the root of the project:
```
mvn clean install
```

##To Run
Being in the root of the project, type:
```
cd web
mvn scala:run -DmainClass=com.robertomanca.web.WebServer
```
To stop, press CTRL+C.