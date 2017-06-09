package com.robertomanca.service.contract.exception

/**
  * Created by roberto on 13/05/2017.
  */
class NotificationNotAvailableException(params: Any) extends RuntimeException {

  val message = "Notification not available for the provided parameters: " + params
}
