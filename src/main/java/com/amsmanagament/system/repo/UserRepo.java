package com.amsmanagament.system.repo;


import com.amsmanagament.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  UserRepo extends JpaRepository<User,Long> {

    public User findByPhoneNumber(String username);

    public Optional<User> findById(Long id);




}
