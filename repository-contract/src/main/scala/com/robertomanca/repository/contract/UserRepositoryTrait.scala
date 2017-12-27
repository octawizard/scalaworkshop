package com.robertomanca.repository.contract

import com.robertomanca.model.location.Location
import com.robertomanca.model.user.{AppUser, CorporateUser, User}

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 02/06/2017.
  */
trait UserRepositoryTrait {

  def getFavouriteLocations: List[Location]

  def getFavouriteLocationsByCountry(country: String): List[Location]

}
