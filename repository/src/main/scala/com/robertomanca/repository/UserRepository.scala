package com.robertomanca.repository

import com.robertomanca.model.user.User
import com.robertomanca.repository.contract.UserRepositoryTrait

import scala.collection.mutable.ListBuffer

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 07/04/2017.
  */
class UserRepository(var users: ListBuffer[User]) extends UserRepositoryTrait {

  override def delete(userId: Long) = ???
}
