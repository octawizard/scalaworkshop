package com.robertomanca.service

import com.robertomanca.model.data.BookingsTrait
import com.robertomanca.model.user.User
import com.robertomanca.repository.BookingRepository
import com.robertomanca.service.exception.BookingNotFoundException
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 08/05/2017.
  */
class BookingServiceTest extends FlatSpec with Matchers with MockFactory with BookingsTrait {

  val bookingRepository = stub[BookingRepository]
  val bookingService = new BookingService(bookingRepository)

  "A BookingService" should "return the list of bookings of an user" in {

    (bookingRepository.getByUser _) when(aUser1.id) returns(List(booking1))

    bookingService.getBookingsByUser(aUser1.id) should be(List(booking1))
  }

  it should "return a booking if exists" in {

    (bookingRepository.get _) when(booking1.id) returns(Option.apply(booking1))

    bookingService.getBookingById(booking1.id) should be(booking1)
  }

  it should "throw an exception when is asked to return a booking that doesn't exist" in {

    bookingRepository.get _ when * returns Option.empty

    a[BookingNotFoundException] should be thrownBy {
      bookingService.getBookingById(99)
    }
    (bookingRepository.get _).verify(99)
  }

  it should "create a new booking" in {

    bookingService createBooking booking1
    (bookingRepository.create(_)) verify(booking1)
  }

  it should "delete a booking" in {

    bookingService deleteBooking booking1.id
    (bookingRepository.delete(_)) verify(booking1.id)
  }

  it should "change the owner of a booking if exists" in {

    (bookingRepository.changeOwner _) when(booking1.id, aUser2) returns(Option.apply(booking1))

    bookingService.changeBookingOwner(booking1.id, aUser2) should be(booking1)
    (bookingRepository.changeOwner(_: Long, _: User)).verify(booking1.id, aUser2)
  }

  it should "throw an exception when is asked to change the owner of a booking if it doesn't exist" in {

    (bookingRepository.changeOwner _) when(99, aUser2) returns(Option.empty)

    a[BookingNotFoundException] should be thrownBy {
      bookingService.changeBookingOwner(99, aUser2)
    }
    (bookingRepository.changeOwner(_: Long, _: User)).verify(99, aUser2)
  }

}
