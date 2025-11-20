package com.amsmanagament.system.repo;


import com.amsmanagament.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  UserRepo extends JpaRepository<User,Long> {

    public User findByPhoneNumber(String username);




}
