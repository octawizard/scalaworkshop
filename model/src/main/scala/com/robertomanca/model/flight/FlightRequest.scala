package com.robertomanca.model.flight

import java.util.Date

import com.robertomanca.model.location.Location

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 19/05/2017.
  */
class FlightRequest(var origin: Location, var destination: Location, var departure: Date, var arrival: Date) {
}
