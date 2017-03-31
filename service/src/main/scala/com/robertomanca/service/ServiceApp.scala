package com.robertomanca.service

import java.util.{Currency, Date}

import com.robertomanca.model.flight._
import com.robertomanca.model.location.Location
import com.robertomanca.model.user.{AppUser, CorporateUser, User}
import com.robertomanca.repository.FlightRepository

/**
  * @author ${user.name}
  */
object ServiceApp {

  def main(args: Array[String]) {

    val barcelona = new Location("barcelona", "spain")
    val madrid = new Location("madrid", "spain")
    val rome = new Location("rome", "italy")

    val user = CorporateUser(1, "email@edreamsodigeo.com", "Alfredo", "Bianchi", "password", 1, List(barcelona, rome))
    val appUser = AppUser(2, "test@odigeo.com", "Mario", "Verdi", "test123", List(rome, madrid, barcelona))

    println

    println("Fav locations of all users filtered by country \"spain\"")
    val users = List(user, appUser)
    println(users.flatMap(_.favouriteLocations).filter(_.country.equals("spain")).distinct) // notice I didn't define getter, didn't define equals and nor functions

    println

    println("Fav locations of all users filtered by country \"italy\"")
    println(users.flatMap(_.favouriteLocations).filter(_.country.equals("italy")).distinct) // notice I didn't define getter, didn't define equals and nor functions

    println

    println("Use lift to get an optional of an element in the users list and if it exists, print his Italian fav locations...")
    var optUser = users.lift(0)
    println(optUser.map(_.favouriteLocations).getOrElse(List.empty).filter(_.country.equals("italy")).distinct)

    println

    println("... otherwise print an empty list if the user doesn't exist")
    optUser = users.lift(5)
    println(optUser.map(_.favouriteLocations).getOrElse(List.empty).filter(_.country.equals("italy")).distinct)  //note no null check done to do all of thisç


    val flight = new Flight(1, new FlightSegment("alitalia", "alitalia.jpg", barcelona, rome, new Date(), new Date()),
      new FlightSegment("vueling", "alitalia.jpg", rome, barcelona, new Date(), new Date()), 123.23, Currency.getInstance("EUR"))
    println(flight)
    val oneWayFlight = new Flight(2, new FlightSegment("iberia", "iberia.jpg", barcelona, madrid, new Date(), new Date()),
      null, 80.14, Currency.getInstance("EUR"))
    println(oneWayFlight)

    val flightRepository = new FlightRepository(List(flight, oneWayFlight))
    val flightService = new FlightService(flightRepository)

    println(rome.equals(new Location("rome", "italy")))
    println(flightService.search(barcelona, rome, new Date(), new Date()))  // return flight
    println(flightService.search(madrid, rome, new Date(), new Date())) // return empty
    println(flightService.search(barcelona, madrid, new Date()))  // return oneWayFlight
    println(flightService.search(rome, madrid, new Date())) // return empty
  }

}
