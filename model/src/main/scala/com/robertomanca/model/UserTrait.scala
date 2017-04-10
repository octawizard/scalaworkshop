package com.robertomanca.model

import com.robertomanca.model.location.Location

import scala.collection.mutable.ListBuffer

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 24/03/2017.
  */
trait PrintableUser {

  var id: Long
  var email: String
  var favouriteLocations: ListBuffer[Location]
  var name: String
  var surname: String
  var password: String

  def printUser = "User(id=" + id + ", email=" + email + ")"

}
