package com.robertomanca.repository

import com.robertomanca.model.notification.{Email, Platform, Push, SMS}
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 10/05/2017.
  */
class NotificationRepositoryTest extends FlatSpec with Matchers {

  val notificationRepository = new NotificationRepository

  "A NotificationRepository" should "return an Email notification" in {

    val body = "Your booking: PNR AB1234, from Rome to Barcelona."
    val sourceEmail = "test@gmail.com"
    val title = "This is your booking!"

    val email = notificationRepository.get(sourceEmail, title, body)

    email shouldBe a [Email]
    email.asInstanceOf[Email].body should be(body)
    email.asInstanceOf[Email].title should be(title)
    email.asInstanceOf[Email].sourceEmail should be(sourceEmail)
  }

 it should "return an SMS notification" in {

    val sourceNumber = "1234566789"
    val message = "Your booking: PNR AB1234, from Rome to Barcelona."

    val sms = notificationRepository.get(sourceNumber, message)

    sms shouldBe a [SMS]
    sms.asInstanceOf[SMS].sourceNumber should be(sourceNumber)
    sms.asInstanceOf[SMS].message should be(message)
  }

  it should "return an Push notification" in {

    val userId = 1L
    val link = "http://www.yourbooking.com/QWERTY"

    val push = notificationRepository.get(userId, Platform.Android, link)

    push shouldBe a [Push]
    push.asInstanceOf[Push].userId should be(userId)
    push.asInstanceOf[Push].link should be(link)
    push.asInstanceOf[Push].platform should be(Platform.Android)
  }
}
