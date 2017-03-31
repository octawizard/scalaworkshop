package com.robertomanca.service

import com.robertomanca.model.booking.Booking
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.User

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 31/03/2017.
  */
class BookingService {

  def getBookingsByUser(userId: Long): List[Booking] = return null  //TODO

  def getBookingById(bookingId: Long): Booking = return null  //TODO

  def createBooking(booking: Booking) {}  //TODO

  def deleteBooking(bookingId: Long) {}  //TODO

  def changeBookingOwner(booking: Booking, user: User): Booking = return null //TODO

  def updateBooking(bookingId: Long, booking: Booking): Booking = return null //TODO

}
