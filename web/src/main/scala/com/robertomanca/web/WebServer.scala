package com.robertomanca.web

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, concat, get, path}
import akka.stream.ActorMaterializer
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind._
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.robertomanca.model.user.{AppUser, CorporateUser, User}

import scala.io.StdIn

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 21/04/2017.
  */
object WebServer {

  /* the following implicit values will be automatically provided when needed as arguments */
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  def main(args: Array[String]) {
    val route =
      path("hello") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
        }
      }

    val allRoutes = concat(route, UserResource.createRoute, NotificationResource.createRoute, FlightResource.createRoute, BookingResource.createRoute)

    val bindingFuture = Http().bindAndHandle(allRoutes, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine // let it run until user presses return
    bindingFuture
      .flatMap(_ unbind) // trigger unbinding from the port
      .onComplete(_ => system terminate) // and shutdown when done
  }
}

/**
  * Thanks to https://coderwall.com/p/o--apg/easy-json-un-marshalling-in-scala-with-jackson
  * for the inspiration.
  */
object JsonUtil {

  val customModule = new SimpleModule()
  customModule addDeserializer(classOf[User], new UserDeserializer)

  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper registerModules(DefaultScalaModule, customModule) configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  mapper configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
  mapper configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true)

  def toJson(value: Any): String = mapper.writeValueAsString(value)

  def fromJson[T](json: String)(implicit m: Manifest[T]): T = mapper.readValue[T](json)
}

class UserDeserializer extends JsonDeserializer[User] {

  override def deserialize(jp: JsonParser, ctxt: DeserializationContext): User = {
    val content = jp.getText
    //TODO implement this
    if (content.contains("companyId")) { // ugly
      JsonUtil.fromJson[CorporateUser](content)
    } else {
      JsonUtil.fromJson[AppUser](content)
    }
  }
}