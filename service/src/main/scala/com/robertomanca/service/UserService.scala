package com.robertomanca.service

import com.robertomanca.model.location.Location
import com.robertomanca.model.user.User
import com.robertomanca.repository.contract.UserRepositoryTrait
import com.robertomanca.service.contract.UserServiceTrait
import com.robertomanca.service.contract.exception.UserNotFoundException

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 31/03/2017.
  */
class UserService(userRepository: UserRepositoryTrait) extends UserServiceTrait {

  def createUser(user: User): User = userRepository.create(user)  // no need of return statement

  def getUser(userId: Long): User = userRepository.get(userId) getOrElse (throw new UserNotFoundException("user " + userId + " doesn't exist"))

  def deleteUser(userId: Long): User = userRepository.delete(userId).getOrElse(throw new UserNotFoundException("user " + userId + " doesn't exist"))

  def updateUser(userId: Long, user: User): User =
    userRepository.update(userId, user).getOrElse(throw new UserNotFoundException("user " + userId + " doesn't exist"))

  def favouriteLocations: List[Location] = userRepository.getFavouriteLocations

  def favouriteLocationsByCountry(country: String): List[Location] = userRepository.getFavouriteLocationsByCountry(country)

  def favouriteLocationsByCountryAndUser(userId: Long, country: String): List[Location] = userRepository.getFavouriteLocationsByCountryAndUser(country, userId)

  def getCorporateUsers: List[User] = userRepository.getCorporateUsers

  def getCorporateUsersByCompany(companyId: Long): List[User] = userRepository getCorporateUsers(companyId)

  def getAppUsers: List[User] = userRepository getAppUsers

}
