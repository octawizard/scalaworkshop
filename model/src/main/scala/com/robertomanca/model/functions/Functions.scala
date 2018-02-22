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

  def bookedFlightsFromTo(outboundOrigin: Location)(outboundDestination: Location)(bookings: List[Booking]) = // curried params method
    bookings.flatMap(booking => booking.flights)
      .filter(flight => flight.outbound.origin == outboundOrigin && flight.outbound.destination == outboundDestination)

  val bookedFlightsFromRome = bookedFlightsFromTo(rome) _ // partially applied function of the curried params method

  val allBookings = List(booking1, booking2, booking3, booking4)

  assert(bookedFlightsFromRome(barcelona)(allBookings) == bookedFlightsFromRome.apply(barcelona).apply(allBookings))
  println(bookedFlightsFromRome(barcelona)(allBookings))
  println(bookedFlightsFromRome.apply(barcelona).apply(allBookings))


  println(new User("mike", "patton", None).loadUser("mike"))
}

class User(val name: String, val surname: String, val child: Option[User]) {

  def copy(name: String = this.name, surname: String = this.surname, child: Option[User]) = new User(name, surname, child)


  def loadUser(userName: String): Option[User] = Option(this).filter(user => user.name == userName).orElse(None)
  val getChild = (user: User) => user.child
//  val grandChild = loadUser("mike").flatMap(u => Option(getChild)).flatMap(u => Option(getChild)).getOrElse(None)
//  val grandChild = loadUser("mike").flatMap(u => Option(u.child)).flatMap(u => Option(u.child))
  val grandChild = loadUser("mike").flatMap(_.child).flatMap(_.child)


  override def toString = s"User($name, $surname, $child)"
}


trait Similarity {
  def isSimilar(x: Any): Boolean
  def isNotSimilar(x: Any): Boolean = !isSimilar(x)
}
