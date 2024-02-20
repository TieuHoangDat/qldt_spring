package com.ptit.qldt.mappers;

import com.ptit.qldt.dtos.NotificationDto;
import com.ptit.qldt.models.Notification;

public class NotificationMapper {
    public static NotificationDto mapToNotificationDto(Notification notification) {
        NotificationDto notificationDto = NotificationDto.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .createdAt(notification.getCreatedAt())
                .build();
        return notificationDto;
    }

    public static Notification mapToNotification(NotificationDto notificationDto) {
        Notification notification = Notification.builder()
                .id(notificationDto.getId())
                .title(notificationDto.getTitle())
                .message(notificationDto.getMessage())
                .createdAt(notificationDto.getCreatedAt())
                .build();
        return notification;
    }
}
