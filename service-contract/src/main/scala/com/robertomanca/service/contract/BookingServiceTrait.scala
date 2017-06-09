package com.robertomanca.service.contract

import com.robertomanca.model.booking.Booking

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 09/06/2017.
  */
trait BookingServiceTrait {

  def getBookingsByUser(userId: Long): List[Booking]

  def getBookingById(bookingId: Long): Booking

  def createBooking(booking: Booking): Booking

  def deleteBooking(bookingId: Long): Booking

  def updateBooking(bookingId: Long, booking: Booking): Booking

}