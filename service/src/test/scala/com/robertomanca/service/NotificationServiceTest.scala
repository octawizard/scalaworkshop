package com.robertomanca.service

import com.robertomanca.model.notification.Email
import com.robertomanca.repository.NotificationRepository
import com.robertomanca.service.exception.NotificationNotAvailableException
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by roberto.manca@edreamsodigeo.com on 13/05/2017.
  */
class NotificationServiceTest extends FlatSpec with Matchers with MockFactory {

  val notificationRepository = stub[NotificationRepository]
  val notificationService = new NotificationService(notificationRepository)

  "A NotificationService" should "return a notification when the provided parameters are correct" in {

    val body = "Your booking: PNR AB1234, from Rome to Barcelona."
    val sourceEmail = "test@gmail.com"
    val title = "This is your booking!"

    notificationRepository.get _ when * returns Email(sourceEmail, title, body)

    val email = notificationRepository.get(sourceEmail, title, body)

    notificationService.getNotification(sourceEmail, title, body) shouldBe(Email(sourceEmail, title, body))
  }

  it should "throw a NotificationNotAvailableException when is asked to retrieve a notification with wrong parameters" in {

    notificationRepository.get _ when * throws new IllegalArgumentException

    a[NotificationNotAvailableException] should be thrownBy {
      notificationService.getNotification(1, 2, 3)
    }
  }
}
