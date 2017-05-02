package com.robertomanca.web

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import akka.http.scaladsl.server.PathMatchers.IntNumber
import com.robertomanca.model.data.UsersTrait
import com.robertomanca.model.user.{AppUser, CorporateUser, User}
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

  val userRoute = concat(
    pathPrefix("users") {
      path("locations") {
        get {
          complete(HttpEntity(ContentTypes.`application/json`, JsonUtil.toJson(userService.favouriteLocations)))
        }
      }
    },
    pathPrefix("user") {
      concat(
        path(IntNumber) {
          userId =>
            handleExceptions(userNotFoundHandler) {
              concat(
                get {
                  complete(HttpEntity(ContentTypes.`application/json`, JsonUtil.toJson(userService.getUser(userId))))
                },
                delete {
                  complete(HttpEntity(ContentTypes.`application/json`, JsonUtil.toJson(userService.deleteUser(userId))))
                },
                put {
                  entity(as[String]) { userString: String =>
                    if (userString.contains("companyId")) { // ugly
                      complete(HttpEntity(ContentTypes.`application/json`,
                        JsonUtil.toJson(userService.updateUser(userId, JsonUtil.fromJson[CorporateUser](userString)))))
                    } else {
                      complete(HttpEntity(ContentTypes.`application/json`,
                        JsonUtil.toJson(userService.updateUser(userId, JsonUtil.fromJson[AppUser](userString)))))
                    }
                  }
                })
            }
        },
        post {
          entity(as[String]) { userString: String =>
            if (userString.contains("companyId")) { // ugly
              complete(HttpEntity(ContentTypes.`application/json`,
                JsonUtil.toJson(userService.createUser(JsonUtil.fromJson[CorporateUser](userString)))))
            } else {
              complete(HttpEntity(ContentTypes.`application/json`,
                JsonUtil.toJson(userService.createUser(JsonUtil.fromJson[AppUser](userString)))))
            }
          }
        })
    })

  def createRoute = userRoute

  def asConcreteType(u: User): Any = u match {
    case AppUser(_, _, _, _, _, _) => u.asInstanceOf[AppUser]
    case CorporateUser(_, _, _, _, _, _, _) => u.asInstanceOf[CorporateUser]
  }
}
