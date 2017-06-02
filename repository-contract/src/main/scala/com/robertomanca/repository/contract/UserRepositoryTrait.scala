package com.robertomanca.repository.contract

import com.robertomanca.model.location.Location
import com.robertomanca.model.user.{AppUser, CorporateUser, User}

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 02/06/2017.
  */
trait UserRepositoryTrait {

  def getAppUsers(): List[AppUser]

  def getCorporateUsers(companyId: Long): List[CorporateUser]

  def getCorporateUsers(): List[CorporateUser]

  def getFavouriteLocationsByCountryAndUser(country: String, userId: Long): List[Location]

  def getFavouriteLocationsByCountry(country: String): List[Location]

  def getFavouriteLocations(): List[Location]

  def update(userId: Long, newUser: User): Option[User]

  def delete(userId: Long): Option[User]

  def get(userId: Long): Option[User]

  def create(user: User): User
}
