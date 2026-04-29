package com.amsmanagament.system.repo;

import com.amsmanagament.system.model.Inventory;
import com.amsmanagament.system.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InventoryRepo extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findById(Long id);

    // ✅ Correct: query by product ID
   Inventory findByProduct_Id(Long productId);

    // Optional: inventory by seller



    @Query("SELECT i FROM Inventory i WHERE LOWER(i.product.productName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Inventory> searchByProductName(String keyword);

    @Query("SELECT COALESCE(SUM(i.quantity), 0) FROM Inventory i WHERE i.farmer.id = :farmerId")
    Integer getTotalInventoryByFarmer(@Param("farmerId") Long farmerId);

    @Query("SELECT i FROM Inventory i WHERE i.product.farmer.id = :farmerId")
    List<Inventory> getInventoriesByFarmer(@Param("farmerId") Long farmerId);
}
