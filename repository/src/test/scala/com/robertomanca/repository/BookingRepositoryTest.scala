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
    booking1.user = aUser1
    booking1.flights = List(oneWayFlight)
    bookingList.clear
  }

  "A BookingRepository" should "return an optional booking" in {

    bookingRepository.get(1) shouldBe Some(booking1)
    bookingRepository.get(1).get should be(booking1)

    bookingRepository.get(99) should be(None)
  }

  it should "return get the user bookings" in {

    bookingRepository.getByUser(1) shouldBe List(booking1)

    bookingRepository.getByUser(2) shouldBe List(booking2, booking3)
    bookingRepository.getByUser(99) shouldBe List.empty
  }

  it should "add a new booking for a user" in {

    val booking = new Booking(4, aUser2, List(oneWayFlight))
    bookingRepository.create(booking)

    bookingRepository.getByUser(aUser2.id).size should be(1)
    bookingRepository.getByUser(aUser2.id) should contain(booking)
    bookingRepository.get(booking.id) shouldBe Some(booking)
  }

  it should "remove a booking if exists" in {

    bookingRepository.getByUser(booking2.user.id) should contain(booking2)
    bookingList.size should be(3)

    bookingRepository.delete(booking2.id)

    bookingRepository.getByUser(booking2.user.id) should not contain (booking2)
    bookingList.size should be(2)
  }

  it should "not remove a booking if doesn't exist" in {

    bookingList.size should be(3)

    bookingRepository.delete(99) // doesn't exist
    bookingList.size should be(3)
  }

  it should "change the owner of a booking if it exists" in {

    booking1.user should be(aUser1)

    val optBooking = bookingRepository.changeOwner(booking1.id, aUser2)

    optBooking should not be (None)
    optBooking.get.user should be (aUser2)
    bookingRepository.getByUser(aUser2.id) should contain (booking1)
  }

  it should "not change the owner of a booking if it doesn't exist" in {

    val optBooking = bookingRepository.changeOwner(99, aUser2)  // no effect

    optBooking should be (None)
  }

  it should "update the booking if it exists" in {

    booking1.user should be(aUser1)
    booking1.flights should contain (oneWayFlight)

    val newBooking = new Booking(99, cUser2, List(rtFlight)) // id will be ignored

    val optBooking = bookingRepository.update(booking1.id, newBooking)

    optBooking should not be (None)
    optBooking.get.user should be (cUser2)
    optBooking.get.flights should not contain (oneWayFlight)
    optBooking.get.flights should contain (rtFlight)
    bookingRepository.getByUser(cUser2.id) should contain (optBooking.get)
  }
}

trait BookingRepositoryWithBookings extends BookingsTrait {

  val bookingList = new ListBuffer[Booking]

  val bookingRepository = new BookingRepository(bookingList)
}