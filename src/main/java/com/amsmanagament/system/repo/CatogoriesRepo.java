package com.amsmanagament.system.repo;

import com.amsmanagament.system.model.Category;
import com.amsmanagament.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CatogoriesRepo extends JpaRepository<Category,Long> {

    Optional<Category> findById(Long id);




       @Query
         (value = "SELECT c FROM Category c WHERE c.categoryName = ?1")
         Optional<Category> findByCategoryName(String categoryName);
}
