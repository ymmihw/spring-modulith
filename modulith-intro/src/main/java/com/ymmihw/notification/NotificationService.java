package com.ymmihw.notification;

import com.ymmihw.notification.internal.Notification;
import com.ymmihw.notification.internal.NotificationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {


    public void createNotification(NotificationDTO notificationDTO) {
        Notification notification = toEntity(notificationDTO);
        log.info("Received notification by module dependency for product {} in date {} by {}.", notification.getProductName(), notification.getDate(), notification.getFormat());
    }

    @Async
    @ApplicationModuleListener
    public void notificationEvent(NotificationDTO event) {
        Notification notification = toEntity(event);
        log.info("Received notification by event for product {} in date {} by {}.", notification.getProductName(), notification.getDate(), notification.getFormat());
    }

    private Notification toEntity(NotificationDTO notificationDTO) {
        Notification notification = new Notification();
        notification.setDate(notificationDTO.getDate());
        if (notificationDTO.getFormat().equals("SMS")) {
            notification.setFormat(NotificationType.SMS);
        }
        if (notificationDTO.getFormat().equals("EMAIL")) {
            notification.setFormat(NotificationType.EMAIL);
        }
        notification.setProductName(notificationDTO.getProductName());
        return notification;
    }
}