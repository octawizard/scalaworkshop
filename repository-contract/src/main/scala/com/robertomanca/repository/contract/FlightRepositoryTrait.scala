package com.robertomanca.repository.contract

import java.util.Date

import com.robertomanca.model.flight.Flight
import com.robertomanca.model.location.Location

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 02/06/2017.
  */
trait FlightRepositoryTrait {

  def getRoundTripFlights(origin: Location, destination: Location, departure: Date, arrival: Date): List[Flight]

  def getOneWayFlights(origin: Location, destination: Location, departure: Date): List[Flight]
}
