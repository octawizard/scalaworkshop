package com.robertomanca.web

import akka.http.scaladsl.marshalling.Marshaller
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import akka.http.scaladsl.unmarshalling.Unmarshaller
import com.robertomanca.model.notification.{Notification, Platform}
import com.robertomanca.model.notification.Platform.Platform
import com.robertomanca.repository.NotificationRepository
import com.robertomanca.service.NotificationService
import com.robertomanca.service.exception.NotificationNotAvailableException

/**
  * Created by roberto on 15/05/2017.
  */
object NotificationResource {

  val notificationService = new NotificationService(new NotificationRepository)

  val notificationNotAvailableHandler = ExceptionHandler {
    case e: NotificationNotAvailableException =>
      extractUri {
        uri => complete(HttpResponse(StatusCodes.BadRequest, entity = e.message))
      }
    case e: IllegalArgumentException =>
      extractUri {
        uri => complete(HttpResponse(StatusCodes.BadRequest, entity = e.getMessage))
      }
  }

  val platformDeserializer = Unmarshaller.strict[String, Platform] {
    platformString => {
      platformString.toLowerCase match {
        case "ios" => Platform.iOS
        case "and" => Platform.Android
        case _ => throw new IllegalArgumentException(platformString + " is not a valid platform (ios/and)")
      }
    }
  }

  implicit val notificationSerializer = Marshaller.opaque[Notification, String] {
    notification => JsonUtil.toJson(notification)
  }

  implicit val notificationSerializer2 = Marshaller.opaque[Notification, String] {
    notification => JsonUtil.toJson(notification)
  }

  val notificationRoute = pathPrefix("notification") {
    handleExceptions(notificationNotAvailableHandler) {
      concat(
        path("sms") {
          parameters('sourceNumber, 'message) { (sourceNumber, message) =>
            get {
              complete(HttpEntity(ContentTypes.`application/json`, JsonUtil.toJson(notificationService.getNotification(sourceNumber, message))))
//              complete(Marshal(notificationSerializer.apply(notificationService.getNotification(sourceNumber, message))).to[String])
            }
          }
        },
        path("email") {
          parameters('sourceEmail, 'title, 'body) { (sourceEmail, title, body) =>
            get {
              complete(HttpEntity(ContentTypes.`application/json`, JsonUtil.toJson(notificationService.getNotification(sourceEmail, title, body))))
            }
          }
        },
        path("push") {
          parameters('link, 'userId.as[Long], 'platform.as(platformDeserializer)) { (link: String, userId: Long, platform: Platform) =>
            get {
              complete(HttpEntity(ContentTypes.`application/json`, JsonUtil.toJson(notificationService.getNotification(userId, platform, link))))
            }
          }
        }
      )
    }
  }

  def createRoute = notificationRoute
}
