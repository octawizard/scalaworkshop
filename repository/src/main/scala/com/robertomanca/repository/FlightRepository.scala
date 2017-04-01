package com.robertomanca.repository

import java.util.{Calendar, Date}

import com.robertomanca.model.flight.Flight
import com.robertomanca.model.location.Location

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 31/03/2017.
  */
class FlightRepository(val flights: List[Flight]) {

  def getRoundTripFlights(origin: Location, destination: Location, departure: Date, arrival: Date): Option[List[Flight]] =
    Option.apply(flights.filter(filterRoundTrip(_, origin, destination, departure, arrival)))

  def getOneWayFlights(origin: Location, destination: Location, departure: Date): Option[List[Flight]] =
    Option.apply(flights.filter(filterOneWay(_, origin, destination, departure)))

  private def filterOneWay(flight: Flight, origin: Location, destination: Location, departure: Date): Boolean =
    flight.outbound.origin.equals(origin) && flight.outbound.destination.equals(destination) &&
      flight.inbound == null && compareDay(flight.outbound.departure, departure)

  def filterRoundTrip(flight: Flight, origin: Location, destination: Location, departure: Date, arrival: Date): Boolean =
    flight.outbound.origin.equals(origin) && flight.outbound.destination.equals(destination) && compareDay(flight.outbound.departure, departure) &&
      flight.inbound.origin.equals(destination) && flight.inbound.destination.equals(origin) && compareDay(flight.inbound.departure, arrival)

  private def compareDay(departure: Date, departure1: Date): Boolean = {
    val calendar = Calendar.getInstance()
    val calendar1 = Calendar.getInstance()
    calendar.setTime(departure)
    calendar1.setTime(departure1)
    return calendar.get(Calendar.DAY_OF_YEAR).equals(calendar1.get(Calendar.DAY_OF_YEAR))
  }
}
