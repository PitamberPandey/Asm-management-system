package com.amsmanagament.system.repo;

import com.amsmanagament.system.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface paymentRepo  extends JpaRepository<Payment,Long> {

    Optional<Payment> findByToken(String token);

}
