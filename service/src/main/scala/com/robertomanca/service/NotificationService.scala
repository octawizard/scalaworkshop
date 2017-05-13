package com.robertomanca.service

import com.robertomanca.model.notification.Notification
import com.robertomanca.repository.NotificationRepository
import com.robertomanca.service.exception.NotificationNotAvailableException

/**
  * Created by roberto on 13/05/2017.
  */
class NotificationService(notificationRepository: NotificationRepository) {

  def getNotification(params: Any): Notification =
    try {
      notificationRepository.get(params)
    } catch {
      case e: IllegalArgumentException => throw new NotificationNotAvailableException(params)
    }
}
