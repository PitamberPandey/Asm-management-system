package com.amsmanagament.system.repo;

import com.amsmanagament.system.model.Delivery;
import com.amsmanagament.system.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepo  extends JpaRepository<Delivery,Long> {

    Optional<Delivery> findById(Long id);
    Delivery findByOrder(Order Order);
}
