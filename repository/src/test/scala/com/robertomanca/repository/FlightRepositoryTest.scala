package com.robertomanca.repository

import java.util.Date

import com.robertomanca.model.data.FlightsTrait
import com.robertomanca.model.flight.Flight
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

import scala.collection.mutable.ListBuffer

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 10/04/2017.
  */
class FlightRepositoryTest extends FlatSpec with Matchers with BeforeAndAfter with FlightRepositoryWithFlights {

  before {
    flightList += rtFlight += oneWayFlight
  }

  after {
    flightList clear
  }

  "A FlightRepository" should "return a list of one way flights given origin, destination and departure" in {

    val optionalFlights = flightRepository.getOneWayFlights(barcelona, madrid, new Date())

    optionalFlights.size should be(1)
    optionalFlights should contain (oneWayFlight)
  }

  it should "return an empty result if there are no one way flights given origin, destination and departure" in {

    val optionalFlights = flightRepository.getOneWayFlights(rome, madrid, new Date())

    optionalFlights should be(List.empty)
  }

  it should "return a list of round trip flights given origin, destination, departure and arrival" in {

    val optionalFlights = flightRepository.getRoundTripFlights(barcelona, rome, new Date(), new Date())

    optionalFlights.size should be(1)
    optionalFlights should contain (rtFlight)
  }

  it should "return an empty result if there are no round trip flights given origin, destination, departure and arrival" in {

    val optionalFlights = flightRepository.getRoundTripFlights(rome, madrid, new Date(), new Date())

    optionalFlights should be(List.empty)
  }

}

trait FlightRepositoryWithFlights extends FlightsTrait {

  val flightList = new ListBuffer[Flight]

  val flightRepository = new FlightRepository(flightList)
}