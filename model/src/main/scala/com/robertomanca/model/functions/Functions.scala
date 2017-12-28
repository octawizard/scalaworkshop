package com.robertomanca.model.functions

import com.robertomanca.model.booking.{Booking, BookingSummary}
import com.robertomanca.model.data.{BookingsTrait, LocationsTrait}
import com.robertomanca.model.flight.{Flight, FlightSummary}
import com.robertomanca.model.location.Location
import com.robertomanca.model.user.User

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 27/12/2017.
  */
object Functions extends LocationsTrait with App with BookingsTrait {

  val flightSummaryFunc: Function1[Flight, FlightSummary] = ???

  val bookingSummaryFunc: Function2[Flight, User, BookingSummary] = ???

  /* curried params method */
  def bookedFlightsFromTo(outboundOrigin: Location)(outboundDestination: Location)(bookings: List[Booking]) = ???

  val bookedFlightsFromRome = ???

}
