package com.amsmanagament.system.repo;

import com.amsmanagament.system.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepo extends JpaRepository<Chat,Long> {

    Optional<Chat> findById(Long chatId);



    Optional<Chat> findByBuyerAndFarmer(Buyer buyer, Farmer seller);
}
