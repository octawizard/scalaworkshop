package com.robertomanca.model.booking

import com.robertomanca.model.flight.Flight
import com.robertomanca.model.user.User

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 31/03/2017.
  */
class Booking(var id: Long, var user: User, var flights: List[Flight]) {

  override def toString = "Booking{id=" + id + "; User=" + user + "; Flight=" + flights +"}"

  def canEqual(other: Any): Boolean = other.isInstanceOf[Booking]

  override def equals(other: Any): Boolean = other match {
    case that: Booking =>
      (that canEqual this) &&
        id == that.id &&
        user == that.user &&
        flights == that.flights
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(id, user, flights)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
