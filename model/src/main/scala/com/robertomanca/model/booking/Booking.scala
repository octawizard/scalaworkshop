package com.robertomanca.model.booking

import com.robertomanca.model.flight.Flight
import com.robertomanca.model.user.User

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 31/03/2017.
  */
class Booking(val id: Long, var user: User, var flights: List[Flight]) {

  override def toString = "Booking{id=" + id + "; User=" + user + "; Flight=" + flights +"}"
}
