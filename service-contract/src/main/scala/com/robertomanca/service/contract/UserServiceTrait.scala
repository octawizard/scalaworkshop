package com.robertomanca.service.contract

import com.robertomanca.model.location.Location
import com.robertomanca.model.user.User

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 09/06/2017.
  */
trait UserServiceTrait {

  def createUser(user: User): User

  def getUser(userId: Long): User

  def deleteUser(userId: Long): User

  def updateUser(userId: Long, user: User): User

  def favouriteLocations: List[Location]

  def favouriteLocationsByCountry(country: String): List[Location]

  def favouriteLocationsByCountryAndUser(userId: Long, country: String): List[Location]

  def getCorporateUsers: List[User]

  def getCorporateUsersByCompany(companyId: Long): List[User]

  def getAppUsers: List[User]
}
