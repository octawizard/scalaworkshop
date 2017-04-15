package com.robertomanca.repository.data

import com.robertomanca.model.user.{AppUser, CorporateUser}

import scala.collection.mutable.ListBuffer

/**
  * Created by roberto on 12/04/2017.
  */
trait UsersTrait extends LocationsTrait {

  val aUser1 = AppUser(1, "mario@gmail.com", "Mario", "Rossi", "test123", List(barcelona, rome).to[ListBuffer])
  val aUser2 = AppUser(3, "alfredo@gmail.com", "Alfredo", "Bovi", "psw91", List(madrid, rome).to[ListBuffer])
  val cUser2 = CorporateUser(2, "victor@gmail.com", "Victor", "Valdes", "password123", 1234, List(barcelona, madrid).to[ListBuffer])
}
