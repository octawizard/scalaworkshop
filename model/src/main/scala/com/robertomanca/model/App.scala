package com.robertomanca.model

import java.util.{Currency, Date}

import com.robertomanca.model.user.{AppUser, CorporateUser, User}
import com.robertomanca.model.location.Location
import com.robertomanca.model.flight._

/**
  * @author ${user.name}
  */
object App {

  def foo(x: Array[String]) = x.foldRight("")((a, b) => a + b)

  def sum(n: Int) = Array(n-1, n, n + 1)

  def foo1(x: List[Int]): List[Int] = x.flatMap(n => sum(n))

  def foo2(x: List[Int]): List[Int] = x.map(n => n + 1)

  def printPersonalizedWelcomeMessage(user: User) = {
    user match {
      case AppUser(_, _, name, surname, _, _) => println("Welcome " + name + " " + surname + "; " + user.printUser)
      case CorporateUser(_, _, name, surname, _, companyId, _) => println("Welcome " + name + " " + surname + ", companyId= " + companyId + "; " + user.printUser)
    }
  }

  def main(args: Array[String]) {
    //val args = Array("primo", "secondo")
    println("Hello World!")
//    println("concat arguments = " + foo(args))

    val barcelona = new Location("barcelona", "spain")
    val madrid = new Location("madrid", "spain")
    val rome = new Location("rome", "italy")

    val user = CorporateUser(1, "email@edreamsodigeo.com", "Alfredo", "Bianchi", "password", 1, List(barcelona, rome))
    println(user.name + " " + user.surname)
    println("General to string defined in the trait: " + user.printUser)
    print("And then personalized toString depending on the case class: ")
    printPersonalizedWelcomeMessage(user)

    val appUser = AppUser(2, "test@odigeo.com", "Mario", "Verdi", "test123", List(rome, madrid, barcelona))
    println(appUser.name + " " + appUser.surname)
    println("General to string defined in the trait:" + appUser.printUser)
    print("And then personalized toString depending on the case class: ")
    printPersonalizedWelcomeMessage(appUser)

    println

    println("Example of map:")
    println(foo2(List(1, 2, 3)))

    println("Example of flatmap, using defined function and lambda:")
    println(foo1(List(1, 2, 3)))  // in defined function
    println(List(1, 2, 3).flatMap(n => Array(n-1, n, n+1))) //in lambda

    println

    println("Fav locations of all users filtered by country \"spain\"")
    val users = List(user, appUser)
    println(users.flatMap(_.favouriteLocations).filter(_.country.equals("spain")).distinct) // notice I didn't define getter, didn't define equals and nor functions

    println

    println("Fav locations of all users filtered by country \"italy\"")
    println(users.flatMap(_.favouriteLocations).filter(_.country.equals("italy")).distinct) // notice I didn't define getter, didn't define equals and nor functions

    println

    println("Use lift to get an optional of an element in the users list and if it exists, print his Italian fav locations...")
    var optUser = users.lift(0)
    println(optUser.map(_.favouriteLocations).getOrElse(List.empty).filter(_.country.equals("italy")).distinct)

    println

    println("... otherwise print an empty list if the user doesn't exist")
    optUser = users.lift(5)
    println(optUser.map(_.favouriteLocations).getOrElse(List.empty).filter(_.country.equals("italy")).distinct)  //note no null check done to do all of thisç

    val flight = new Flight(1, new FlightSegment("alitalia", "alitalia.jpg", barcelona, rome, new Date(), new Date()),
      new FlightSegment("vueling", "alitalia.jpg", rome, barcelona, new Date(), new Date()), 123.23, Currency.getInstance("EUR"));
    println(flight)
  }

}
