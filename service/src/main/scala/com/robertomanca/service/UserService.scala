package com.robertomanca.service

import com.robertomanca.model.location.Location
import com.robertomanca.model.user.{AppUser, CorporateUser, User}
import com.robertomanca.repository.UserRepository
import com.robertomanca.service.exception.UserNotFoundException

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 31/03/2017.
  */
class UserService(userRepository: UserRepository) {

  def createUser(user: User): User = userRepository.create(user)  // no need of return statement

  def getUser(userId: Long): User = userRepository.get(userId) getOrElse (throw new UserNotFoundException("user " + userId + " doesn't exist"))

  def deleteUser(userId: Long): User = userRepository.delete(userId).getOrElse(throw new UserNotFoundException("user " + userId + " doesn't exist"))

  def updateUser(userId: Long, user: User): User =
    userRepository.update(getUser(userId), user).getOrElse(throw new UserNotFoundException("user " + userId + " doesn't exist"))

  def favouriteLocations: List[Location] = userRepository.getFavouriteLocations

  def favouriteLocationsByCountry(country: String): List[Location] = userRepository.getFavouriteLocationsByCountry(country)

  def favouriteLocationsByCountryAndUser(userId: Long, country: String): List[Location] = userRepository.getFavouriteLocationsByCountryAndUser(country, userId)

  def getCorporateUsers: List[CorporateUser] = userRepository.getCorporateUsers.map(_.asInstanceOf[CorporateUser])

  def getCorporateUsersByCompany(companyId: Long): List[CorporateUser] = userRepository getCorporateUsers(companyId)

  def getAppUsers: List[User] = userRepository getAppUsers

}

trait UserServiceTrait {

  def createUser(user: User): User

  def getUser(userId: Long): User

  def deleteUser(userId: Long)

  def updateUser(userId: Long, user: User): User

  def favouriteLocations: List[Location]

  def favouriteLocationsByCountry(country: String): List[Location]

  def favouriteLocationsByCountryAndUser(userId: Long, country: String): List[Location]

  def getCorporateUsers: List[User]

  def getCorporateUsersByCompany(companyId: Long): List[User]

  def getAppUsers: List[User]
}
