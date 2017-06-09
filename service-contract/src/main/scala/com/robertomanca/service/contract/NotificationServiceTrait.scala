package com.robertomanca.service.contract

import com.robertomanca.model.notification.Notification

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 09/06/2017.
  */
trait NotificationServiceTrait {

  def getNotification(params: Any): Notification

}
