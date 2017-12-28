package com.robertomanca.repository

import com.robertomanca.model.notification.Platform.Platform
import com.robertomanca.model.notification.{Email, Notification, Push, SMS}
import com.robertomanca.repository.contract.NotificationRepositoryTrait

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 09/05/2017.
  */
class NotificationRepository extends NotificationRepositoryTrait{

  override def get(params: Any) = ???
}
