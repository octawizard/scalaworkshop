package com.robertomanca.service

import com.robertomanca.model.data.UsersTrait
import com.robertomanca.repository.UserRepository
import com.robertomanca.service.exception.UserNotFoundException
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by roberto.manca@edreamsodigeo.com on 16/04/2017.
  */
class UserServiceTest extends FlatSpec with Matchers with MockFactory with UsersTrait {

  val userRepository = stub[UserRepository]
  val userService = new UserService(userRepository)

  "A UserService" should "return a user if exists" in {

    userRepository.get _ when aUser1.id returns Option.apply(aUser1)

    userService.getUser(aUser1.id) should be(aUser1)
    (userRepository.get _).verify(aUser1.id) // make sure that this method has been called only once
  }

  it should "throw an UserNotFoundException when is asked to get an user that doesn't exist" in {

    userRepository.get _ when * returns Option.empty

    a [UserNotFoundException] should be thrownBy {
      userService.getUser(99)
    }
    (userRepository.get _).verify(99)
  }
}
