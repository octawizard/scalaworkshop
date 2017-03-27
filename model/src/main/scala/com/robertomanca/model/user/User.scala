package com.robertomanca.model.user

import com.robertomanca.model.PrintableUser
import com.robertomanca.model.location.Location

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 24/03/2017.
  */
abstract class User extends PrintableUser
case class AppUser(var id: Long, var email: String, var name: String, var surname: String, var password: String, var favouriteLocations: List[Location]) extends User {
  override def toString =
    "User(id=" + id + ", email=" + email + ", name=" + name + ", surname=" + surname +")";
}
case class CorporateUser(var id: Long, var email: String, var name: String, var surname: String, var password: String, var companyId: Long, var favouriteLocations: List[Location]) extends User {
  override def toString =
    "User(id=" + id + ", email=" + email + ", name=" + name + ", surname=" + surname +", companyId=" + companyId + ")";
}