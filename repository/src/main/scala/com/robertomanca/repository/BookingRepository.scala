package com.robertomanca.repository

import com.robertomanca.model.booking.Booking
import com.robertomanca.model.user.User

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

/**
  * Created by roberto on 01/04/2017.
  */
class BookingRepository(val bookings: ListBuffer[Booking]) {

  val random = scala.util.Random

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

  def delete(bookingId: Long): Option[Booking] = {
    val optBooking = get(bookingId)
    bookings -= optBooking.orNull
    optBooking
  }

  def create(booking: Booking) = {
    booking.id = generateId
    bookings append booking
    booking
  }

  def get(bookingId: Long): Option[Booking] = bookings.find(_.id == bookingId)

  def getByUser(userId: Long): List[Booking] = bookings.filter(_.user.id == userId) toList

  /**
    * Generate a random long id that is unique in the current bookings list.
    * It use tail recursion
    *
    * @return a booking id
    */
  private def generateId: Long = { // not efficient
    @tailrec
    val id = random.nextLong
    if (!(bookings.map(_.id) contains id)) return id

    generateId
  }
}
