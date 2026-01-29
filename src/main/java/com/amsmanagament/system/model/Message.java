package com.amsmanagament.system.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The chat this message belongs to
    @ManyToOne(optional = false)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    // Who sent this message
    @ManyToOne(optional = false)
    @JoinColumn(name = "sender_id")
    private User sender;

    // Message content
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // Optional: Read/unread status
    @Column(name = "is_read")
    private boolean read;

    // Timestamp when the message was created
    @CreationTimestamp
    private LocalDateTime createdAt;

}
