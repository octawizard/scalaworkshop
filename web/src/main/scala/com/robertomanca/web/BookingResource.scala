package com.robertomanca.web

import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import com.robertomanca.model.booking.Booking
import com.robertomanca.model.data.BookingsTrait
import com.robertomanca.model.user.{AppUser, CorporateUser, User}
import com.robertomanca.repository.BookingRepository
import com.robertomanca.service.BookingService
import com.robertomanca.service.exception.BookingNotFoundException

import scala.collection.mutable.ListBuffer

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 30/05/2017.
  */
object BookingResource extends BookingsTrait {

  val bookings = new ListBuffer[Booking] += booking1 += booking2 += booking3
  val bookingService = new BookingService(new BookingRepository(bookings))
  implicit val mat = WebServer.materializer

  implicit val bookingDeserializer = Unmarshaller.strict[HttpRequest, Booking] {
    httpRequest => JsonUtil.fromJson[Booking](Unmarshal(httpRequest).to[String].value.get.get)
  }

  val bookingNotFoundHandler = ExceptionHandler {
    case e: BookingNotFoundException =>
      complete(HttpResponse(StatusCodes.NotFound, entity = e.message))
  }

  val getBookingRoute = pathPrefix("booking") {
    concat(
      path(LongNumber) {
        bookingId =>
          handleExceptions(bookingNotFoundHandler) {
            concat(
              get {
                complete(HttpEntity(ContentTypes.`application/json`, JsonUtil.toJson(bookingService.getBookingById(bookingId))))
              },
              delete {
                complete(HttpEntity(ContentTypes.`application/json`, JsonUtil.toJson(bookingService.deleteBooking(bookingId))))
              },
              put {
                entity(as[Booking]) {
                  booking => complete(HttpEntity(ContentTypes.`application/json`, JsonUtil.toJson(bookingService.updateBooking(bookingId, booking))))
                }
              })
          }
      },
      parameter("userId".as[Long] ?) {
        userId =>
          concat(
            get {
              val uid = userId.getOrElse(-1L)
              complete(HttpEntity(ContentTypes.`application/json`, JsonUtil.toJson(bookingService.getBookingsByUser(uid))))
            },
            post {
              entity(as[Booking]) { booking =>
                complete(HttpEntity(ContentTypes.`application/json`, JsonUtil.toJson(bookingService.createBooking(booking))))
              }
            })
      }
    )
  }

  def createRoute = getBookingRoute
}
