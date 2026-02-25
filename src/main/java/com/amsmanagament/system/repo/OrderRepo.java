package com.amsmanagament.system.repo;

import com.amsmanagament.system.model.Buyer;
import com.amsmanagament.system.model.Order;
import com.amsmanagament.system.model.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo  extends JpaRepository<Order,Long> {

    Optional<Order> findById(Long id);

    List<Order> getOrderByUser(User user);





}
