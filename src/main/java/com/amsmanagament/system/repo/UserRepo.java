package com.amsmanagament.system.repo;


import com.amsmanagament.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface  UserRepo extends JpaRepository<User,Long> {

    public User findByPhoneNumber(String username);

    public Optional<User> findById(Long id);

    @Query("SELECT u FROM User u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> searchByFullName(@Param("name") String name);



}
