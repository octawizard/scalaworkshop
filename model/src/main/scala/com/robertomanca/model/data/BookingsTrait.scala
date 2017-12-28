package com.robertomanca.model.data

import com.robertomanca.model.booking.Booking

/**
  * Created by roberto on 12/04/2017.
  */
trait BookingsTrait extends UsersTrait with FlightsTrait {

  val booking1 = new Booking(1, aUser1, List(oneWayFlight))

  val booking2 = new Booking(2, cUser2, List(oneWayFlight, rtFlight))
  val booking3 = new Booking(3, cUser2, List(oneWayFlight))

  val booking4 = new Booking(4, cUser2, List(oneWayFlightFromRome))

}
