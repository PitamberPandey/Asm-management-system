package com.amsmanagament.system.repo;

import com.amsmanagament.system.model.Inventory;
import com.amsmanagament.system.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepo extends JpaRepository<Inventory, Long> {

    // ✅ Correct: query by product ID
    Inventory findByProduct_Id(Long productId);

    // Optional: inventory by seller
    Inventory findBySeller(Seller seller);
}
