package com.robertomanca.repository

import com.robertomanca.model.location.Location
import com.robertomanca.model.user.{AppUser, CorporateUser, User}
import com.robertomanca.repository.contract.UserRepositoryTrait

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 07/04/2017.
  */
class UserRepository(var users: ListBuffer[User]) extends UserRepositoryTrait {

  val random = scala.util.Random

  def getAppUsers(): List[AppUser] = users filter byAppUsers map (_.asInstanceOf[AppUser]) toList

  def getCorporateUsers(companyId: Long): List[CorporateUser] =
    users filterNot byAppUsers map(_.asInstanceOf[CorporateUser]) filter (_.companyId == companyId) toList // notice infix notation

  def getCorporateUsers(): List[CorporateUser] = users filterNot byAppUsers map (_.asInstanceOf[CorporateUser]) toList

  def getFavouriteLocationsByCountryAndUser(country: String, userId: Long): List[Location] =
    get(userId).map(_.favouriteLocations.filter(_.country.equals(country))).map(_.toList).getOrElse(List.empty)

  def getFavouriteLocationsByCountry(country: String): List[Location] =
    users.flatMap(_.favouriteLocations).distinct.filter(_.country.equals(country)).toList // not using infix notation

  def getFavouriteLocations(): List[Location] = users.flatMap(_.favouriteLocations).distinct.toList

  def update(userId: Long, newUser: User): Option[User] = get(userId).map(_.updateUser(newUser))

  def delete(userId: Long): Option[User] =
    Option.apply(users.indexWhere(_.id == userId))
      .filterNot(_ == -1)
      .map(users.remove(_))
      .orElse(Option.empty)

  def get(userId: Long): Option[User] = users.find(_.id == userId)

  def create(user: User): User = {
    user.id = generateId
    users append user
    user // no need to specify return statement
  }

  /**
    * Returns if the provided user is an instance of case class {@link AppUser}
    *
    * @param user
    * @return true if the user is instance of AppUser
    */
  private def byAppUsers(user: User) = user.isInstanceOf[AppUser]

  /**
    * Generate a random long id that is unique in the current users list.
    * It use tail recursion
    *
    * @return a user id
    */
  private def generateId: Long = { // not efficient
    @tailrec
    val id = random.nextLong
    if (!(users.map(_.id) contains id)) return id

    generateId
  }

}
