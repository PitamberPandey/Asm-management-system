package com.amsmanagament.system.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private  Long RecivedId;
    private LocalDateTime createdAt;
    private Boolean read;
    private String action;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
