package com.amsmanagament.system.model;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventPublisher {

    @Autowired
    private ApplicationEventPublisher publisher;

    public void publish(NotificationEvent event) {
        publisher.publishEvent(event);
    }
}