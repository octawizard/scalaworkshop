package com.robertomanca.model

import com.robertomanca.model.user.{AppUser, CorporateUser, User}

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
      case AppUser(_, _, name, surname, _) => println("Welcome " + name + " " + surname + "; " + user.printUser)
      case CorporateUser(_, _, name, surname, _, companyId) => println("Welcome " + name + " " + surname + ", companyId= " + companyId + "; " + user.printUser)
    }
  }

  def main(args: Array[String]) {
    val args = Array("primo", "secondo")
    println("Hello World!")
    println("concat arguments = " + foo(args))

    val user = CorporateUser(1, "email@edreamsodigeo.com", "Alfredo", "Bianchi", "password", 1);
    println(user.printUser)
    println(user.name + " " + user.surname)
    printPersonalizedWelcomeMessage(user)

    val appUser = AppUser(2, "test@odigeo.com", "Mario", "Verdi", "test123")
    println(user.printUser)
    println(appUser.name + " " + appUser.surname)
    printPersonalizedWelcomeMessage(appUser)

    println(foo1(List(1, 2, 3)))
    println(foo2(List(1, 2, 3)))
  }

}
