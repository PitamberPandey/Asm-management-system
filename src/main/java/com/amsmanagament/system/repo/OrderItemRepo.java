package com.amsmanagament.system.repo;

import com.amsmanagament.system.model.Order;
import com.amsmanagament.system.model.OrderItem;
import com.amsmanagament.system.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {

    Optional<OrderItem> findById(Long id);
    List<OrderItem> findByProduct(Product product);
    List<OrderItem> findByOrder(Order Order);
    @Query("""
SELECT COALESCE(SUM(oi.price * oi.quantity),0)
FROM OrderItem oi
WHERE oi.order.id = :orderId
""")
    Long calculateTotalAmountByOrderId(@Param("orderId") Long orderId);

    @Query("""
        SELECT COUNT(DISTINCT oi.order.id)
        FROM OrderItem oi
        WHERE oi.product.farmer.id = :farmerId
    """)
    int countOrdersByFarmerId(@Param("farmerId") Long farmerId);

    // 2. Total Sold Quantity
    @Query("""
        SELECT COALESCE(SUM(oi.quantity), 0)
        FROM OrderItem oi
        WHERE oi.product.farmer.id = :farmerId
    """)
    int sumQuantityByFarmerId(@Param("farmerId") Long farmerId);

    // 3. Total Revenue (IMPORTANT 🔥)
    @Query("""
        SELECT COALESCE(SUM(oi.quantity * oi.price), 0)
        FROM OrderItem oi
        WHERE oi.product.farmer.id = :farmerId
    """)
    Double calculateRevenueByFarmerId(@Param("farmerId") Long farmerId);
}
