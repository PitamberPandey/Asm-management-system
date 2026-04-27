package com.amsmanagament.system.repo;



import com.amsmanagament.system.model.Category;
import com.amsmanagament.system.model.Farmer;
import com.amsmanagament.system.model.Product;
import com.amsmanagament.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    List<Product> findByCategory(Category category);


    List<Product> findByFarmer_Id(Long farmerId);

    @Query("SELECT i FROM Product i WHERE LOWER(i.address) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchByAddress(@Param("keyword") String keyword);

}
