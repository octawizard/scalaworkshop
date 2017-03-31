package com.robertomanca.model.location

/**
  * Created by roberto on 27/03/2017.
  */
class Location(val city: String, val country: String) {

  override def toString: String = "Location:{city=" + city + "; country=" + country + "}";


  def canEqual(other: Any): Boolean = other.isInstanceOf[Location]

  override def equals(other: Any): Boolean = other match {
    case that: Location =>
      (that canEqual this) &&
        city == that.city &&
        country == that.country
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(city, country)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
