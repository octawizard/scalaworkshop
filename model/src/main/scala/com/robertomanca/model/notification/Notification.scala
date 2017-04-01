package com.robertomanca.model.notification

import com.robertomanca.model.notification.Platform.Platform

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 24/03/2017.
  */
abstract class Notification
case class Email(sourceEmail: String, title: String, body: String) extends Notification
case class SMS(sourceNumber: String, message: String) extends Notification
case class Push(userId: Long, platform: Platform, link: String) extends Notification

object Platform extends Enumeration {
  type Platform = Value
  val iOS, Android = Value
}
