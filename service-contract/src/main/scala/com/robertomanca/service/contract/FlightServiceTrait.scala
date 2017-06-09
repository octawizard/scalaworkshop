package com.robertomanca.service.contract

import java.util.Date

import com.robertomanca.model.flight.Flight
import com.robertomanca.model.location.Location

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 09/06/2017.
  */
trait FlightServiceTrait {

  def search(origin: Location, destination: Location, departure: Date): List[Flight]

  def search(origin: Location, destination: Location, departure: Date, arrival: Date): List[Flight]

}
