package com.robertomanca.model

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 24/03/2017.
  */
trait PrintableUser {

  var id: Long
  var email: String

  def printUser = "User(id=" + id + ", email=" + email + ")";

}
