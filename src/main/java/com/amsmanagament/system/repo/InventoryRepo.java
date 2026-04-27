package com.amsmanagament.system.repo;

import com.amsmanagament.system.model.Inventory;
import com.amsmanagament.system.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventoryRepo extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findById(Long id);

    // ✅ Correct: query by product ID
   Inventory findByProduct_Id(Long productId);

    // Optional: inventory by seller



    @Query("SELECT i FROM Inventory i WHERE LOWER(i.product.productName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Inventory> searchByProductName(String keyword);

}
