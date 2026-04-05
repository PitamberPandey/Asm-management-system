package com.amsmanagament.system.serviceImpl;

import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.Notification;
import com.amsmanagament.system.model.NotificationAction;
import com.amsmanagament.system.model.User;
import com.amsmanagament.system.repo.NotificationmRepo;
import com.amsmanagament.system.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationmRepo notificationRepo;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void notify(User user, NotificationAction actionType, String message, Long referenceId) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setActionType(actionType);
        notification.setMessage(message);
        notification.setReceivedId(referenceId);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        Notification savedNotification = notificationRepo.save(notification);

        // 2️⃣ Push notification via WebSocket to the specific user
        messagingTemplate.convertAndSend(
                "/user/" + user.getId() + "/queue/notifications", // per-user queue
                savedNotification
        );
    }



    @Override
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public List<Notification> find() {

        return notificationRepo.findAll();
    }

    @Override
    public Notification deleteNotification(Long id) {
        Notification notification = notificationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        notificationRepo.delete(notification);
        return notification;
    }

    @Override
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepo.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        notification.setRead(true);
        notificationRepo.save(notification);
    }
}
