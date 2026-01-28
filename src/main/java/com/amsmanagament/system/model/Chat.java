package com.amsmanagament.system.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Each chat belongs to a Buyer
    @ManyToOne(optional = false)
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    // Each chat belongs to a Seller
    @ManyToOne(optional = false)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    // List of messages in this chat
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt ASC") // messages sorted by time
    private List<Message> messages = new ArrayList<>();
}

