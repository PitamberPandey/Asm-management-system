package com.amsmanagament.system.serviceImpl;



import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.Notification;
import com.amsmanagament.system.model.NotificationAction;
import com.amsmanagament.system.model.User;
import com.amsmanagament.system.repo.NotificationRepo;
import com.amsmanagament.system.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void createNotification(User receiver, User sender,
                                   NotificationAction actionType,
                                   String message,
                                   Long referenceId) {

        Notification notification = new Notification();
        notification.setUser(receiver);
        notification.setSender(sender);
        notification.setActionType(actionType);
        notification.setMessage(message);
        notification.setReferenceId(referenceId);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        Notification saved = notificationRepo.save(notification);

        // 🔔 send to specific user
        messagingTemplate.convertAndSend(
                "/user/" + receiver.getId() + "/queue/notifications",
                saved
        );
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepo.findAll();
    }

    @Override
    public void markAsRead(Long id) {
        Notification n = notificationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        n.setRead(true);
        notificationRepo.save(n);
    }

    @Override
    public Notification deleteNotification(Long id) {
        Notification n = notificationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        notificationRepo.delete(n);
        return n;
    }

    @Override
    public int getUnreadCount(Long userId) {
        return notificationRepo.countByUserIdAndReadFalse(userId);
    }
}