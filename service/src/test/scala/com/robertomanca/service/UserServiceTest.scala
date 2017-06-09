package com.robertomanca.service

import com.robertomanca.model.data.{LocationsTrait, UsersTrait}
import com.robertomanca.model.location.Location
import com.robertomanca.model.user.{AppUser, CorporateUser, User}
import com.robertomanca.repository.contract.UserRepositoryTrait
import com.robertomanca.service.contract.exception.UserNotFoundException
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by roberto.manca@edreamsodigeo.com on 16/04/2017.
  */
class UserServiceTest extends FlatSpec with Matchers with MockFactory with UsersTrait with LocationsTrait {

  val userRepository = stub[UserRepositoryTrait]
  val userService = new UserService(userRepository)

  "A UserService" should "return a user if exists" in {

    userRepository.get _ when aUser1.id returns Option.apply(aUser1)

    userService.getUser(aUser1.id) should be(aUser1)
    (userRepository.get _).verify(aUser1.id) // make sure that this method has been called only once
  }

  it should "throw an UserNotFoundException when is asked to get an user that doesn't exist" in {

    userRepository.get _ when * returns Option.empty

    a[UserNotFoundException] should be thrownBy {
      userService.getUser(99)
    }
    (userRepository.get _).verify(99)
  }

  it should "return a list of app users" in {

    userRepository.getAppUsers _ when() returns List[AppUser](aUser1, aUser2)

    userService.getAppUsers should be(List[User](aUser1, aUser2))
  }

  it should "return a list of corporate users" in {

    (userRepository.getCorporateUsers: () => List[CorporateUser]) when() returns List[CorporateUser](cUser2)

    userService.getCorporateUsers should be(List[User](cUser2))
  }

  it should "return a list of corporate users filtered by company" in {

    (userRepository.getCorporateUsers: (Long) => List[CorporateUser]) when * returns List[CorporateUser](cUser2)

    val list = userService.getCorporateUsersByCompany(1234)

    list.length should be(1)
    list should contain(cUser2)
    (userRepository.getCorporateUsers: (Long) => List[CorporateUser]).verify(1234) // make sure that this method has been called only once
  }

  it should "return a list of favourite locations of all users" in {

    userRepository.getFavouriteLocations _ when() returns List[Location](rome, barcelona, madrid)

    val list = userService favouriteLocations

    list.length should be(3)
    list should contain(rome)
    list should contain(barcelona)
    list should contain(madrid)
  }

  it should "return a list of favourite locations of all users filtered by country" in {

    userRepository.getFavouriteLocationsByCountry _ when ("spain") returns List[Location](barcelona, madrid)

    val list = userService favouriteLocationsByCountry "spain"

    list.length should be(2)
    list should contain(barcelona)
    list should contain(madrid)
    (userRepository.getFavouriteLocationsByCountry _) verify ("spain") // make sure that this method has been called only once
  }

  it should "return a list of favourite locations of one user filtered by country" in {

    (userRepository.getFavouriteLocationsByCountryAndUser(_: String, _: Long)) when("spain", aUser1.id) returns List[Location](barcelona)

    val list = userService favouriteLocationsByCountryAndUser(1, "spain")

    list.length should be(1)
    list should contain(barcelona)
    (userRepository.getFavouriteLocationsByCountryAndUser(_: String, _: Long)) verify("spain", aUser1.id) // make sure that this method has been called only once
  }

  it should "delete an user if exists" in {

    userRepository.delete _ when 1 returns Option.apply(aUser1)

    userService deleteUser 1 should be(aUser1)
    (userRepository.delete _) verify(1)
  }

  it should "throw an UserNotFoundException when is asked to delete an user that doesn't exist" in {

    userRepository.delete _ when 99 returns Option.empty

    a[UserNotFoundException] should be thrownBy {
      userService.deleteUser(99)
    }
    (userRepository.delete _) verify(99)
  }

  it should "update an user if exists" in {

    (userRepository.update(_: Long, _: User)) when(1, aUser1) returns Option.apply(aUser1)

    userService updateUser (1, aUser1) should be(aUser1)
    (userRepository.update(_: Long, _: User)) verify(1, aUser1)
  }

  it should "throw an UserNotFoundException when is asked to update an user that doesn't exist" in {

    (userRepository.update(_: Long, _: User)) when(99, *) returns Option.empty

    a[UserNotFoundException] should be thrownBy {
      userService.updateUser(99, aUser1)
    }
    (userRepository.update(_: Long, _: User)) verify(99, aUser1)
  }

  it should "create a new user" in {

    userRepository.create _ when(aUser1) returns aUser1

    userService createUser (aUser1) should be(aUser1)
    (userRepository.create(_)) verify(aUser1)
  }
}
