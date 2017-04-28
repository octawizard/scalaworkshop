package com.robertomanca.web

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives.{complete, extractUri, handleExceptions, path, pathPrefix}
import akka.http.scaladsl.server.ExceptionHandler
import akka.http.scaladsl.server.PathMatchers.IntNumber
import com.robertomanca.model.data.UsersTrait
import com.robertomanca.model.user.User
import com.robertomanca.repository.UserRepository
import com.robertomanca.service.UserService
import com.robertomanca.service.exception.UserNotFoundException

import scala.collection.mutable.ListBuffer

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 21/04/2017.
  */
object UserResource extends UsersTrait {

  val users = new ListBuffer[User] += aUser1 += aUser2 += cUser2
  val userService = new UserService(new UserRepository(users))

  val userNotFoundHandler = ExceptionHandler {
    case e: UserNotFoundException =>
      extractUri {
        uri => complete(HttpResponse(StatusCodes.NotFound, entity = e.message))
      }
  }

  val userRoute =
    pathPrefix("user") {
      path(IntNumber) {
        userId =>
          handleExceptions(userNotFoundHandler) {
            complete(HttpEntity(ContentTypes.`application/json`, JsonUtil.toJson(userService.getUser(userId))))
          }
      }
    }

  def createRoute = userRoute
}
