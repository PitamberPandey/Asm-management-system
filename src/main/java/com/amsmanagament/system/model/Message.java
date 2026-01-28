package com.amsmanagament.system.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Each message belongs to a chat
    @ManyToOne(optional = false)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    // Who sent this message (Buyer or Seller)
    @ManyToOne(optional = false)
    @JoinColumn(name = "sender_id")
    private User sender; // You can make User a parent class for Buyer & Seller, or handle dynamically

    private String message;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
