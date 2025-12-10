package com.amsmanagament.system.repo;



import com.amsmanagament.system.model.Farmer;
import com.amsmanagament.system.model.Product;
import com.amsmanagament.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    // Optional: Find product by exact name
    Optional<Product> findByProductName(String name);

    // Search products by name (partial match, case-insensitive)
    List<Product> findByProductNameContainingIgnoreCase(String keyword);

    // Search by name or description (partial match)
    List<Product> findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String nameKeyword, String descKeyword);

    // Optional: Find products by category
    List<Product> findByCategoryId(Long categoryId);

    // Optional: Find products created by a specific user
    List<Product> findByFarmer(Farmer farmer);
}
