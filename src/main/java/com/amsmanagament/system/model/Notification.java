package com.amsmanagament.system.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // reference id (postId, commentId, etc.)
    private Long referenceId;

    private String message;

    @Column(name = "is_read")
    private Boolean read = false;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private NotificationAction actionType;

    // who triggered action
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    // who receives notification
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}