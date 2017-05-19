package com.robertomanca.web

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpRequest}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import com.robertomanca.model.data.FlightsTrait
import com.robertomanca.model.flight.{Flight, FlightRequest}
import com.robertomanca.repository.FlightRepository
import com.robertomanca.service.FlightService

import scala.collection.mutable.ListBuffer

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 19/05/2017.
  */
object FlightResource extends FlightsTrait {

  val flights = new ListBuffer[Flight] += oneWayFlight += rtFlight
  val flightService = new FlightService(new FlightRepository(flights))
  implicit val mat = WebServer.materializer

  val flightRequestDeserializer = Unmarshaller.strict[HttpRequest, FlightRequest] {
    flightRequestString => JsonUtil.fromJson[FlightRequest](Unmarshal(flightRequestString).to[String].value.get.get)
  }

  val flightRoute =
    pathPrefix("flight") {
      post {
        entity(as(flightRequestDeserializer)) { flightRequest: FlightRequest =>
          Option.apply(flightRequest.arrival)
          .map(arr => complete(HttpEntity(ContentTypes.`application/json`,
            JsonUtil.toJson(flightService.search(flightRequest.origin, flightRequest.destination, flightRequest.departure, arr)))))
          .getOrElse(complete(HttpEntity(ContentTypes.`application/json`,
            JsonUtil.toJson(flightService.search(flightRequest.origin, flightRequest.destination, flightRequest.departure)))))
        }
      }
    }

  def createRoute = flightRoute
}
