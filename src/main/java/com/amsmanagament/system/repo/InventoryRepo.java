package com.amsmanagament.system.repo;

import com.amsmanagament.system.model.Inventory;
import com.amsmanagament.system.model.Product;
import com.amsmanagament.system.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepo extends JpaRepository<Inventory,Long> {

    Inventory findByProduct(Product product);
    Optional<Inventory> findById(Long id);
    Inventory findBySeller(Seller seller);
}
