package com.amsmanagament.system.repo;

import com.amsmanagament.system.model.Seller;
import com.amsmanagament.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepo  extends JpaRepository<Seller,Long> {

    Seller getById(Long id);

    Seller getByUser(User user);
    List<Seller> findByFarmernameContainingIgnoreCase(String farmername);


}
