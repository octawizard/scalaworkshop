package com.robertomanca.model.data

import java.util.{Currency, Date}

import com.robertomanca.model.flight.{Flight, FlightSegment}

/**
  * Created by roberto on 12/04/2017.
  */
trait FlightsTrait extends LocationsTrait {

  val rtFlight = new Flight(1, new FlightSegment("alitalia", "alitalia.jpg", barcelona, rome, new Date(), new Date()),
    new FlightSegment("vueling", "alitalia.jpg", rome, barcelona, new Date(), new Date()), 123.23, Currency.getInstance("EUR"))

  val oneWayFlight = new Flight(2, new FlightSegment("iberia", "iberia.jpg", barcelona, madrid, new Date(), new Date()),
    null, 80.14, Currency.getInstance("EUR"))

  val oneWayFlightFromRome = new Flight(3, new FlightSegment("iberia", "iberia.jpg", rome, barcelona, new Date(), new Date()),
    null, 80.14, Currency.getInstance("EUR"))
}
