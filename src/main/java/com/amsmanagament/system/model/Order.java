package com.amsmanagament.system.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    private Farmer farmer;

    private LocalDateTime orderDate;
    private LocalDateTime orderupdatedate;

    private String status;
    private Long totalPrice;// PENDING, COMPLETED, CANCELLED

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;
     private String verify="UNVERIFIED";// UNVERIFIED, VERIFIED, REJECTED

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private Payment_Status paymentStatus;

    // One Order → Many Payment attempts
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Payment> payments;


}

