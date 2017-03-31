package com.robertomanca.service

import java.util.Date

import com.robertomanca.model.flight.Flight
import com.robertomanca.model.location.Location

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 31/03/2017.
  */
class FlightService {

  def search(origin: Location, destination: Location, departure: Date): List[Flight] = return null //TODO

  def search(origin: Location, destination: Location, departure: Date, arrival: Date): List[Flight] = return null //TODO

}
