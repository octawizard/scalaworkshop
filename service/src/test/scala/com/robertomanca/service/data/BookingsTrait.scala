package com.robertomanca.service.data

import com.robertomanca.model.booking.Booking

/**
  * Created by roberto on 12/04/2017.
  */
trait BookingsTrait extends UsersTrait with FlightsTrait {

  val booking1 = new Booking(1, aUser1, List(oneWayFlight))

  val booking2 = new Booking(2, cUser2, List(oneWayFlight, rtFlight))
  val booking3 = new Booking(3, cUser2, List(oneWayFlight))

}
