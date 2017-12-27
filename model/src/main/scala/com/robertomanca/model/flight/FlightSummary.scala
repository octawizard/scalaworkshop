package com.robertomanca.model.flight

import com.robertomanca.model.location.Location

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 27/12/2017.
  */
class FlightSummary(val inboundOrigin: Location, val inboundDestination: Location,
                    val outboundOrigin: Location = null, val outboundDestination: Location = null) {

}
