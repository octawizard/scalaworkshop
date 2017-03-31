package com.robertomanca.model.flight

import java.util.{Currency, Date}

import com.robertomanca.model.location.Location

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 24/03/2017.
  */
class Flight(val id: Long, var outbound: FlightSegment, var inbound: FlightSegment, val price: Double, val currency: Currency) {

  override def toString = "Flight{id=" + id + "; outbound=" + outbound + "; inbound=" + inbound + "; price=" + price + "; currency=" + currency + "}"
}

class FlightSegment(var airline: String, var airlineImage: String, var origin: Location, var destination: Location, var departure: Date, var arrival: Date) {

  override def toString = "FlightSegment{airline=" + airline +" ; airlineImage=" + airlineImage +
    "; origin=" + origin + "; destination=" + destination + "; departure=" + departure + "; arrival=" + arrival + "}"
}
