package com.robertomanca.service

import com.robertomanca.model.notification.Notification
import com.robertomanca.repository.contract.NotificationRepositoryTrait
import com.robertomanca.service.contract.NotificationServiceTrait
import com.robertomanca.service.contract.exception.NotificationNotAvailableException

/**
  * Created by roberto on 13/05/2017.
  */
class NotificationService(notificationRepository: NotificationRepositoryTrait) extends NotificationServiceTrait {

  def getNotification(params: Any): Notification =
    try {
      notificationRepository.get(params)
    } catch {
      case e: IllegalArgumentException => throw new NotificationNotAvailableException(params)
    }
}
