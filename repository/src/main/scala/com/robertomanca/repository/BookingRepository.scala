package com.robertomanca.repository

import com.robertomanca.model.booking.Booking
import com.robertomanca.model.user.User

/**
  * Created by roberto on 01/04/2017.
  */
class BookingRepository(val bookings: List[Booking]) {

  def update(maybeBooking: Option[Booking], newBooking: Booking) =
    maybeBooking.flatMap(b => {
      b.flights = newBooking.flights
      b.user = newBooking.user
      Option.apply(b)
    })

  def changeOwner(maybeBooking: Option[Booking], user: User): Option[Booking] =
    maybeBooking.flatMap(b => {
      b.user = user
      Option.apply(b)
    })

  def delete(bookingId: Long) = bookings.filterNot(_.id == bookingId)

  def create(booking: Booking) = booking :: bookings

  def get(bookingId: Long): Option[Booking] = bookings.find(_.id == bookingId)

  def getByUser(userId: Long): Option[List[Booking]] = Option.apply(bookings.filter(_.user.id == userId))
}
