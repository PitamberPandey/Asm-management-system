package com.amsmanagament.system.model;




import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationEvent {

    private User receiver;
    private User sender;
    private NotificationAction actionType;
    private String message;
    private Long referenceId;
}
