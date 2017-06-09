package com.robertomanca.web

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import akka.http.scaladsl.server.PathMatchers.IntNumber
import com.robertomanca.model.data.UsersTrait
import com.robertomanca.model.user.{AppUser, CorporateUser, User}
import com.robertomanca.repository.UserRepository
import com.robertomanca.service.UserService
import com.robertomanca.service.contract.exception.UserNotFoundException

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
    }

  val userLocationsRoute =
    pathPrefix("locations") {
      concat(
        path(Segment) {
          country =>
            parameter("userId".as[Long] ?) { // implicit GET
              optUserId =>
                get {
                  optUserId.map(
                    userId => complete(HttpEntity(ContentTypes.`application/json`, JsonUtil.toJson(userService.favouriteLocationsByCountryAndUser(userId, country)))))
                    .getOrElse(complete(HttpEntity(ContentTypes.`application/json`, JsonUtil.toJson(userService.favouriteLocationsByCountry(country)))))
                }
            }
        },
        pathEndOrSingleSlash {
          get {
            complete(HttpEntity(ContentTypes.`application/json`, JsonUtil.toJson(userService.favouriteLocations)))
          }
        }
      )
    }

  var usersFilteredRoute =
    pathPrefix("users") {
      concat(
        path("app") {
          complete(HttpEntity(ContentTypes.`application/json`, JsonUtil.toJson(userService.getAppUsers)))
        },
        path("corporate") {
          parameter("companyId".as[Long] ?) { // validation handled by akka
            optCompanyId =>
              optCompanyId.map(
                companyId => complete(HttpEntity(ContentTypes.`application/json`, JsonUtil.toJson(userService.getCorporateUsersByCompany(companyId)))))
                .getOrElse(complete(HttpEntity(ContentTypes.`application/json`, JsonUtil.toJson(userService.getCorporateUsers))))
          }
        }
      )
    }

  def createRoute = concat(userRoute, userLocationsRoute, usersFilteredRoute)
}
