package com.robertomanca.repository

import com.robertomanca.model.booking.Booking
import com.robertomanca.model.user.User

import scala.collection.mutable.ListBuffer

/**
  * Created by roberto on 01/04/2017.
  */
class BookingRepository(val bookings: ListBuffer[Booking]) {

  def update(bookingId: Long, newBooking: Booking) =
    get(bookingId).flatMap(b => {
      b.flights = newBooking.flights
      b.user = newBooking.user
      Option.apply(b)
    }).orElse(Option.empty)

  def changeOwner(bookingId: Long, user: User): Option[Booking] =
    get(bookingId).flatMap(b => {
      b.user = user
      Option.apply(b)
    }).orElse(Option.empty)

  def delete(bookingId: Long) = bookings -= get(bookingId).orNull

  def create(booking: Booking) = bookings.append(booking)

  def get(bookingId: Long): Option[Booking] = bookings.find(_.id == bookingId)

  def getByUser(userId: Long): List[Booking] = bookings.filter(_.user.id == userId) toList
}
