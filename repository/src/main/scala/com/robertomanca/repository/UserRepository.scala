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

}
