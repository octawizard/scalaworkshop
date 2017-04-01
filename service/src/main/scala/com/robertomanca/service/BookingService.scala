package com.robertomanca.service

import com.robertomanca.model.booking.Booking
import com.robertomanca.model.user.User
import com.robertomanca.repository.BookingRepository
import com.robertomanca.service.exception.BookingNotFoundException

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 31/03/2017.
  */
class BookingService(bookingRepository: BookingRepository) extends BookingServiceTrait {

  def getBookingsByUser(userId: Long): List[Booking] = bookingRepository.getByUser(userId).getOrElse(List.empty) // notice no need of return or brackets

  def getBookingById(bookingId: Long): Booking = bookingRepository.get(bookingId).getOrElse(throw new BookingNotFoundException(""))

  def createBooking(booking: Booking) = bookingRepository.create(booking)

  def deleteBooking(bookingId: Long) = bookingRepository.delete(bookingId)

  def changeBookingOwner(bookingId: Long, user: User): Booking =
    bookingRepository.changeOwner(bookingRepository.get(bookingId), user).getOrElse(throw new BookingNotFoundException(""))

  def updateBooking(bookingId: Long, booking: Booking): Booking =
    bookingRepository.update(bookingRepository.get(bookingId), booking).getOrElse(throw new BookingNotFoundException(""))

}

trait BookingServiceTrait {

  def getBookingsByUser(userId: Long): List[Booking]

  def getBookingById(bookingId: Long): Booking

  def createBooking(booking: Booking)

  def deleteBooking(bookingId: Long)

  def changeBookingOwner(bookingId: Long, user: User): Booking

  def updateBooking(bookingId: Long, booking: Booking): Booking

}