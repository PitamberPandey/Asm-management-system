package com.amsmanagament.system.repo;

import com.amsmanagament.system.model.Delivery;
import com.amsmanagament.system.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DeliveryRepo  extends JpaRepository<Delivery,Long> {

    Optional<Delivery> findById(Long id);
    Delivery findByOrder_Id(Long orderId);
    Delivery findByOrder(Order Order);
    @Query("""
SELECT COUNT(d)
FROM Delivery d
WHERE d.order.id IN (
    SELECT oi.order.id
    FROM OrderItem oi
    WHERE oi.product.farmer.id = :farmerId
)
""")
    int countTotalDeliveriesByFarmer(@Param("farmerId") Long farmerId);

    @Query("""
SELECT COUNT(d)
FROM Delivery d
WHERE d.deliveryStatus = 'DELIVERED'
AND d.order.id IN (
    SELECT oi.order.id
    FROM OrderItem oi
    WHERE oi.product.farmer.id = :farmerId
)
""")
    int countDeliveredByFarmer(@Param("farmerId") Long farmerId);

    @Query("""
SELECT COUNT(d)
FROM Delivery d
WHERE d.deliveryStatus != 'DELIVERED'
AND d.order.id IN (
    SELECT oi.order.id
    FROM OrderItem oi
    WHERE oi.product.farmer.id = :farmerId
)
""")
    int countPendingByFarmer(@Param("farmerId") Long farmerId);
}
