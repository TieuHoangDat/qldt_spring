package com.ptit.qldt.services;

import com.ptit.qldt.dtos.NotificationDto;

import java.util.List;

public interface NotificationService {
    List<NotificationDto> findAllNotification();

    void save(String title, String mes);

    void deleteNotificationById(int notificationId);

    NotificationDto findById(int notificationId);

    void updateNotification(NotificationDto notificationDto);
}
