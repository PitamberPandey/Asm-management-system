package com.amsmanagament.system.repo;

import com.amsmanagament.system.model.Buyer;
import com.amsmanagament.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ByerRepo extends JpaRepository<Buyer,Long> {

     Optional<Buyer> findById(Long id) ;
    Optional<Buyer> findByFullName(String buyerName) throws  Exception;

    boolean existsByUser(User user);
//
//
//
    @Query("SELECT f FROM Buyer f WHERE LOWER(f.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Buyer> findByUsername(@Param("name") String name);
    Optional<Buyer> findByUser(User user);



}
