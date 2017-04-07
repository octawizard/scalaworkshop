package com.robertomanca.repository

import com.robertomanca.model.location.Location
import com.robertomanca.model.user.{AppUser, CorporateUser, User}

import scala.collection.mutable.ListBuffer
import scala.annotation.tailrec

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 07/04/2017.
  */
class UserRepository(var users: ListBuffer[User]) {

  val random = scala.util.Random

  def getAppUsers(): List[User] = users filter byAppUsers toList

  def getCorporateUsers(companyId: Long): List[CorporateUser] =
    users filterNot byAppUsers map(_.asInstanceOf[CorporateUser]) filter (_.companyId == companyId) toList // notice infix notation

  def getCorporateUsers(): List[User] = users filterNot byAppUsers toList

  def getFavouriteLocationsByCountryAndUser(country: String, userId: Long): List[Location] =
    get(userId).map(_.favouriteLocations.filter(_.country.equals(country))).getOrElse(List.empty)

  def getFavouriteLocationsByCountry(country: String): List[Location] =
    users.flatMap(_.favouriteLocations).distinct.filter(_.country.equals(country)).toList // not infix notation

  def getFavouriteLocations(): List[Location] = users.flatMap(_.favouriteLocations).distinct.toList

  def update(oldUser: User, newUser: User): Option[User] = ???  //TODO implement

  def delete(userId: Long) = users.drop(users.indexWhere(_.id == userId)) //TODO TEST if the original list is modified or not

  def get(userId: Long): Option[User] = users.find(_.id == userId)

  def create(user: User): User = {
    user.id = generateId
    users append user
    user  // no need to specify return statement
  }

  /**
    * Returns if the provided user is an instance of case class {@link AppUser}
    * @param user
    * @return true if the user is instance of AppUser
    */
  private def byAppUsers(user: User) = user.isInstanceOf[AppUser]

  /**
    * Generate a random long id that is unique in the current users list.
    * It use tail recursion
    * @return a user id
    */
  private def generateId: Long = {  // not efficient
    @tailrec
    val id = random.nextLong
    if (!(users.map(_.id) contains id)) return id

    generateId
  }

}
