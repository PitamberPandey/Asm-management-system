package com.amsmanagament.system.controller;

import com.amsmanagament.system.model.NotificationAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDto {
    private Long userId;
    private NotificationAction actionType;
    private String message;
    private Long referenceId;
}
