package com.robertomanca.repository.contract

import com.robertomanca.model.location.Location
import com.robertomanca.model.user.{AppUser, CorporateUser, User}

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 02/06/2017.
  */
trait UserRepositoryTrait {

  def get(userId: Long): Option[User]

  def get(p: User => Boolean): List[User]
}
