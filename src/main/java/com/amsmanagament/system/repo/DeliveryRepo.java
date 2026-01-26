package com.amsmanagament.system.repo;

import com.amsmanagament.system.model.Delivery;
import com.amsmanagament.system.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepo  extends JpaRepository<Delivery,Long> {

    Delivery findbyId(Long id);
    Delivery findByOrder(Order Order);
}
