package com.robertomanca.model.location

/**
  * Created by roberto on 27/03/2017.
  */
class Location(val city: String, val country: String) {

  override def toString: String = "Location:{city=" + city + "; country=" + country + "}";

}
