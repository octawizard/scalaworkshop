package com.robertomanca.repository.contract

import com.robertomanca.model.notification.Notification

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 02/06/2017.
  */
trait NotificationRepositoryTrait {

  def get(params: Any): Notification
}
