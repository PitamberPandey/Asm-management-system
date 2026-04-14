package com.amsmanagament.system.model;




import com.amsmanagament.system.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventListener {

    @Autowired
    private NotificationService notificationService;

    @EventListener
    public void handleNotification(NotificationEvent event) {

        notificationService.createNotification(
                event.getReceiver(),
                event.getSender(),
                event.getActionType(),
                event.getMessage(),
                event.getReferenceId()
        );
    }
}
