package com.robertomanca.repository

import com.robertomanca.model.notification.Platform.Platform
import com.robertomanca.model.notification.{Email, Notification, Push, SMS}
import com.robertomanca.repository.contract.NotificationRepositoryTrait

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 09/05/2017.
  */
class NotificationRepository extends NotificationRepositoryTrait{

  def get(params: Any): Notification =
    params match {
      case (sourceNumber: String, message: String) => SMS(sourceNumber, message)
      case (sourceEmail: String, title: String, body: String) => Email(sourceEmail, title,body)
      case (userId: Long, platform: Platform, link: String) => Push(userId, platform, link)
      case _ => throw new IllegalArgumentException("Notification type not available with the provided parameters")
    }
}
