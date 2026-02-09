package com.amsmanagament.system.repo;

import com.amsmanagament.system.model.Farmer;
import com.amsmanagament.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmerRepo extends JpaRepository<Farmer,Long> {

    Optional<Farmer> findById(Long id);


    boolean existsByUser(User user);

    Optional<Farmer> findByUser(User user);


    @Query("SELECT f FROM Farmer f WHERE LOWER(f.farmerName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Farmer> findByUsername(@Param("name") String name);


}
