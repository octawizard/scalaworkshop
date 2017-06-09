package com.robertomanca.service

import com.robertomanca.model.booking.Booking
import com.robertomanca.repository.contract.BookingRepositoryTrait
import com.robertomanca.service.contract.BookingServiceTrait
import com.robertomanca.service.contract.exception.BookingNotFoundException

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 31/03/2017.
  */
class BookingService(bookingRepository: BookingRepositoryTrait) extends BookingServiceTrait {

  val booking_not_found = "booking id = %d not found"

  def getBookingsByUser(userId: Long): List[Booking] = bookingRepository.getByUser(userId) // notice no need of return or brackets

  def getBookingById(bookingId: Long): Booking = bookingRepository.get(bookingId)
    .getOrElse(throw new BookingNotFoundException(booking_not_found format bookingId))

  def createBooking(booking: Booking): Booking = bookingRepository.create(booking)

  def deleteBooking(bookingId: Long): Booking = bookingRepository.delete(bookingId)
    .getOrElse(throw new BookingNotFoundException(booking_not_found format bookingId))

  def updateBooking(bookingId: Long, booking: Booking): Booking =
    bookingRepository.update(bookingId, booking)
      .getOrElse(throw new BookingNotFoundException(booking_not_found format bookingId))

}