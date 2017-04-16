package com.robertomanca.repository

import com.robertomanca.model.user.{AppUser, CorporateUser, User}
import com.robertomanca.repository.data.UsersTrait
import org.scalatest._

import scala.collection.mutable.ListBuffer

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 07/04/2017.
  */

class UserRepositoryTest extends FlatSpec with Matchers with BeforeAndAfter with UserRepositoryWithUsers {

  before {
    usersList.+=(aUser1).+=(cUser2)
  }

  after {
    usersList.clear
  }

  "A UserRepository" should "return an optional user" in {

    userRepository.get(1) shouldBe Some(aUser1)
    userRepository.get(1).get should be(aUser1)
    userRepository.get(2).get should be(cUser2)
    userRepository.get(3) should be(None)
  }

  it should "delete properly a user" in {

    userRepository.delete(1) shouldBe Some(aUser1)
    userRepository.users.size should be(1)
  }

  it should "not delete properly a user if it doesn't exist" in {

    userRepository.delete(154) shouldBe None
    userRepository.users.size should be(2)
  }

  it should "return a list of AppUser" in {

    userRepository.getAppUsers.size should be(1)
    userRepository.getAppUsers.lift(0).get shouldBe a [AppUser]
  }

  it should "return a list of CorporateUser" in {

    userRepository.getCorporateUsers.size should be(1)
    userRepository.getCorporateUsers.lift(0).get shouldBe a [CorporateUser]
  }

  it should "return a list of CorporateUser given a company id" in {

    userRepository.getCorporateUsers(1234).size should be(1)
    userRepository.getCorporateUsers(1234).lift(0).get shouldBe a [CorporateUser]

    userRepository.getCorporateUsers(9999).size should be(0)
  }

  it should "return a list of favourite locations of all users" in {

    val favouriteLocations = userRepository.getFavouriteLocations

    favouriteLocations.size should be(3)
    favouriteLocations should contain (barcelona)
    favouriteLocations should contain (madrid)
    favouriteLocations should contain (rome)
  }

  it should "return a list of favourite locations of all users filtered by country" in {

    val favouriteLocations = userRepository.getFavouriteLocationsByCountry(barcelona.country)

    favouriteLocations.size should be(2)
    favouriteLocations should contain (barcelona)
    favouriteLocations should contain (madrid)
  }

  it should "return a list of favourite locations of a user filtered by country" in {

    var favouriteLocations = userRepository.getFavouriteLocationsByCountryAndUser(barcelona.country, 1)

    favouriteLocations.size should be(1)
    favouriteLocations should contain (barcelona)

    favouriteLocations = userRepository.getFavouriteLocationsByCountryAndUser(rome.country, 1)

    favouriteLocations.size should be(1)
    favouriteLocations should contain (rome)
  }

  it should "return an updated user" in {

    val dummy = CorporateUser(99, "victor@gmail.com", "Victor", "Valdes", "password123", 1, List(barcelona, madrid).to[ListBuffer])
    val newDummy = CorporateUser(99, "piero@gmail.com", "Piero", "Valdes", "password123", 1, List(barcelona, madrid).to[ListBuffer])
    usersList.append(dummy)

    val updatedUser = userRepository.update(dummy, newDummy)

    updatedUser.isDefined should be(true)
    updatedUser.get should equal(newDummy)
  }

  it should "return an empty option if the user to be updated doesn't exist" in {

    val dummy = CorporateUser(99, "victor@gmail.com", "Victor", "Valdes", "password123", 1, List(barcelona, madrid).to[ListBuffer])
    val newDummy = CorporateUser(99, "piero@gmail.com", "Piero", "Valdes", "password123", 1, List(barcelona, madrid).to[ListBuffer])

    val updatedUser = userRepository.update(dummy, newDummy)

    updatedUser shouldBe None
  }

  it should "add a new user to the users list" in {

    val dummy = CorporateUser(99, "victor@gmail.com", "Victor", "Valdes", "password123", 1, List(barcelona, madrid).to[ListBuffer])

    val addedUser = userRepository.create(dummy)

    addedUser should equal(dummy)
    userRepository.users.size should be(3)
    userRepository.users should contain(addedUser)
  }
}

trait UserRepositoryWithUsers extends UsersTrait {

  val usersList = new ListBuffer[User]

  val userRepository = new UserRepository(usersList)
}
