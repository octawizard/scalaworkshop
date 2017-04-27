package com.robertomanca.web

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.stream.ActorMaterializer
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.robertomanca.model.location.Location
import com.robertomanca.model.user.AppUser

import scala.collection.mutable.ListBuffer
import scala.io.StdIn

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 21/04/2017.
  */
object WebServer {

  def main(args: Array[String]) {

    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val user = AppUser(1, "mario@gmail.com", "Mario", "Rossi", "test123", new ListBuffer[Location])

    val route =
      path("hello") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
        }
      }

    val route2 =
      path("user") {
        get {
          complete(HttpEntity(ContentTypes.`application/json`, JsonUtil.toJson(user)))
        }
      }

//    val routes = route ~ route2
    val bindingFuture = Http().bindAndHandle(route2, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}

object JsonUtil {
  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper registerModule(DefaultScalaModule) configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  def toJson(value: Any): String = mapper.writeValueAsString(value)

  def fromJson[T](json: String)(implicit m : Manifest[T]): T = mapper.readValue[T](json)
}