package com.amsmanagament.system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification") // ✅ lowercase
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // who receives the notification (optional, but fine)
    private Long receivedId;

    private LocalDateTime createdAt;

    private String message;


    @Column(name = "is_read") // ✅ avoid reserved keyword
    private Boolean read = false;

    private String action;

    @Enumerated(EnumType.STRING)
    private NotificationAction actionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
}
