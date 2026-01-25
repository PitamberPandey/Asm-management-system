package com.amsmanagament.system.repo;

import com.amsmanagament.system.model.Order;
import com.amsmanagament.system.model.OrderItem;
import com.amsmanagament.system.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {

    Optional<OrderItem> findById(Long id);
    List<OrderItem> findByProduct(Product product);
    List<OrderItem> findByOrder(Order Order);
}
