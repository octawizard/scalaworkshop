package com.robertomanca.model.user

import com.robertomanca.model.PrintableUser
import com.robertomanca.model.location.Location

import scala.collection.mutable.ListBuffer

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 24/03/2017.
  */
abstract class User extends PrintableUser {
  def updateUser(newUser: User): User
}

case class AppUser(var id: Long, var email: String, var name: String, var surname: String, var password: String, var favouriteLocations: ListBuffer[Location]) extends User {

  override def toString =
    "User(id=" + id + ", email=" + email + ", name=" + name + ", surname=" + surname + ")"

  override def updateUser(newUser: User): User = {
    email = newUser.email
    name = newUser.name
    surname = newUser.surname
    password = newUser.password
    favouriteLocations = newUser.favouriteLocations

    this
  }
}

case class CorporateUser(var id: Long, var email: String, var name: String, var surname: String, var password: String, var companyId: Long, var favouriteLocations: ListBuffer[Location]) extends User {

  override def toString =
    "User(id=" + id + ", email=" + email + ", name=" + name + ", surname=" + surname + ", companyId=" + companyId + ")"

  override def updateUser(newUser: User): User = {
    val newCorporateUser = newUser.asInstanceOf[CorporateUser]

    email = newCorporateUser.email
    name = newCorporateUser.name
    surname = newCorporateUser.surname
    password = newCorporateUser.password
    favouriteLocations = newCorporateUser.favouriteLocations
    companyId = newCorporateUser.companyId

    this
  }
}