package com.amsmanagament.system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference// allow null user
    private User user;

    private String farmerName;
    private String farmerAddress;
    private String wardNo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String document;
    @Enumerated(EnumType.STRING)
    private Farmer_Status status;

    @OneToMany(mappedBy = "farmer", cascade = CascadeType.ALL)
    private List<Product> products;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status==null){
            status=Farmer_Status.STATUS_PENDING;
        }
    }


    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


}
