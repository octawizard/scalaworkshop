package com.robertomanca.repository

import com.robertomanca.model.booking.Booking
import com.robertomanca.repository.data.BookingsTrait
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

import scala.collection.mutable.ListBuffer

/**
  * Created by roberto on 12/04/2017.
  */
class BookingRepositoryTest extends FlatSpec with Matchers with BeforeAndAfter with BookingRepositoryWithBookings {

  before {
    bookingList += booking1 += booking2 += booking3
  }

  after {
    bookingList.clear
  }

  "A BookingRepository" should "return an optional booking" in {

    bookingRepository.get(1) shouldBe Some(booking1)
    bookingRepository.get(1).get should be (booking1)

    bookingRepository.get(99) should be(None)
  }
}

trait BookingRepositoryWithBookings extends BookingsTrait {

  val bookingList = new ListBuffer[Booking]

  val bookingRepository = new BookingRepository(bookingList)
}