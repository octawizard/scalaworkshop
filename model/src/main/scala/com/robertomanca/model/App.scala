package com.robertomanca.model

import com.robertomanca.model.user.{AppUser, CorporateUser, User}
import com.robertomanca.model.location.Location

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
    val args = Array("primo", "secondo")
    println("Hello World!")
    println("concat arguments = " + foo(args))

    val barcelona = new Location("barcelona", "spain")
    val madrid = new Location("madrid", "spain")
    val rome = new Location("rome", "italy")

    val user = CorporateUser(1, "email@edreamsodigeo.com", "Alfredo", "Bianchi", "password", 1, List(barcelona, rome))
    println(user.printUser)
    println(user.name + " " + user.surname)
    printPersonalizedWelcomeMessage(user)

    val appUser = AppUser(2, "test@odigeo.com", "Mario", "Verdi", "test123", List(rome, madrid, barcelona))
    println(user.printUser)
    println(appUser.name + " " + appUser.surname)
    printPersonalizedWelcomeMessage(appUser)

    println(foo1(List(1, 2, 3)))
    println(foo2(List(1, 2, 3)))

    println("Fav locations of all users filtered by country \"spain\"")
    val users = List(user, appUser)
    println(users.flatMap(_.favouriteLocations).filter(_.country.equals("spain")).distinct) // notice I didn't define getter, didn't define equals and nor functions

    println("Fav locations of all users filtered by country \"italy\"")
    println(users.flatMap(_.favouriteLocations).filter(_.country.equals("italy")).distinct) // notice I didn't define getter, didn't define equals and nor functions
  }

}
