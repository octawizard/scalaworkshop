package com.robertomanca.service

import java.util.Date

import com.robertomanca.model.flight.Flight
import com.robertomanca.model.location.Location
import com.robertomanca.repository.FlightRepository

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 31/03/2017.
  */
class FlightService(flightRepository: FlightRepository) extends FlightServiceTrait { //If a field doesn’t have a var or val modifier, Scala gets conservative, and doesn’t generate a getter or setter method for the field.

  def search(origin: Location, destination: Location, departure: Date): List[Flight] =
    return flightRepository.getOneWayFlights(origin, destination, departure).getOrElse(List.empty)

  def search(origin: Location, destination: Location, departure: Date, arrival: Date): List[Flight] =
    flightRepository.getRoundTripFlights(origin, destination, departure, arrival).getOrElse(List.empty)
}

trait FlightServiceTrait {

  def search(origin: Location, destination: Location, departure: Date): List[Flight]

  def search(origin: Location, destination: Location, departure: Date, arrival: Date): List[Flight]

}