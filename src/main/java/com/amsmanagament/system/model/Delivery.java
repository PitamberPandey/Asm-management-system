package com.amsmanagament.system.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String deliveryAddress;
    private String deliveryStatus; // PENDING, DISPATCHED, DELIVERED
    private LocalDateTime createdAt;
    private LocalDateTime deliveryTime;

}