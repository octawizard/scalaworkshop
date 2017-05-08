package com.robertomanca.service

import java.util.Date

import com.robertomanca.model.data.{FlightsTrait, LocationsTrait, UsersTrait}
import com.robertomanca.repository.FlightRepository
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 05/05/2017.
  */
class FlightServiceTest extends FlatSpec with Matchers with MockFactory with FlightsTrait with LocationsTrait {

  val flightRepository = stub[FlightRepository]
  val flightService = new FlightService(flightRepository)

  "A FlightService" should "return a list of one way flights given origin, destination and departure" in {

    (flightRepository.getOneWayFlights _) when(*, *, *) returns(List(oneWayFlight))

    flightService.search(rome, barcelona, new Date()) should be(List(oneWayFlight))
  }

  it should "return a list of round trip flights given origin, destination and departure" in {

    (flightRepository.getRoundTripFlights _) when(*, *, *, *) returns(List(rtFlight))

    flightService.search(rome, barcelona, new Date(), new Date()) should be(List(rtFlight))
  }
}
