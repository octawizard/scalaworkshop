package com.robertomanca.repository.data

import com.robertomanca.model.location.Location

/**
  * Created by roberto on 12/04/2017.
  */
trait LocationsTrait {
  val barcelona = new Location("barcelona", "spain")
  val madrid = new Location("madrid", "spain")
  val rome = new Location("rome", "italy")
}
