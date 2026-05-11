package com.amsmanagament.system.services;



import com.amsmanagament.system.model.Notification;
import com.amsmanagament.system.model.NotificationAction;
import com.amsmanagament.system.model.User;

import java.util.List;

public interface NotificationService {

    void createNotification(User receiver, User sender,
                            NotificationAction actionType,
                            String message,
                            Long referenceId);

    List<Notification> getUserNotifications(Long userId);

    List<Notification> getAllNotifications();

    void markAsRead(Long id);

    Notification deleteNotification(Long id);

    int getUnreadCount(Long userId);
}
