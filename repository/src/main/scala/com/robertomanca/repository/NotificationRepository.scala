package com.robertomanca.repository

import com.robertomanca.model.notification.Platform.Platform
import com.robertomanca.model.notification.{Email, Notification, Push, SMS}

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 09/05/2017.
  */
class NotificationRepository {

//  def getNotificationContent(notification: Notification): String =
//    notification match {
//      case SMS(_, message) => message
//      case Email(_, title, body) => title + "\n" + body
//      case Push(_, _, message) => message
//      case _ => "Notification type not supported"
//    }

  def getNotification(params: Any): Notification =
    params match {
      case (sourceNumber: String, message: String) => SMS(sourceNumber, message)
      case (sourceEmail: String, title: String, body: String) => Email(sourceEmail, title,body)
      case (userId: Long, platform: Platform, link: String) => Push(userId, platform, link)
      case _ => throw new IllegalArgumentException("Notification type not available with the provided parameters")
    }
}
