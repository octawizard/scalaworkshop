package com.robertomanca.repository.contract

import com.robertomanca.model.booking.Booking
import com.robertomanca.model.user.User

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 02/06/2017.
  */
trait BookingRepositoryTrait {

  def update(bookingId: Long, newBooking: Booking): Option[Booking]

  def changeOwner(bookingId: Long, user: User): Option[Booking]

  def delete(bookingId: Long): Option[Booking]

  def create(booking: Booking): Booking

  def get(bookingId: Long): Option[Booking]

  def getByUser(userId: Long): List[Booking]
}
