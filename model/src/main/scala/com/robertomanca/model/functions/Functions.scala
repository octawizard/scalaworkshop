package com.robertomanca.model.functions

import com.robertomanca.model.booking.{Booking, BookingSummary}
import com.robertomanca.model.data.LocationsTrait
import com.robertomanca.model.flight.{Flight, FlightSummary}
import com.robertomanca.model.location.Location
import com.robertomanca.model.user.User

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 27/12/2017.
  */
class Functions extends LocationsTrait {

  val anonFlightSummaryFunc = (flight: Flight) => new FlightSummary(flight.inbound.origin, flight.inbound.destination, flight.outbound.origin, flight.outbound.destination)

  val flightSummaryFunc = new Function1[Flight, FlightSummary] {
    override def apply(f: Flight) = new FlightSummary(f.inbound.origin, f.inbound.destination, f.outbound.origin, f.outbound.destination)
  }

  val anonBookingSummaryFunc = (flight: Flight, user: User) =>
    new BookingSummary(
      new FlightSummary(flight.inbound.origin, flight.inbound.destination, flight.outbound.origin, flight.outbound.destination),
      user.name, user.surname
    )

  val bookingSummaryFunc = new Function2[Flight, User, BookingSummary] {
    override def apply(f: Flight, u: User) = new BookingSummary(
      new FlightSummary(f.inbound.origin, f.inbound.destination, f.outbound.origin, f.outbound.destination),
      u.name, u.surname
    )
  }

}
