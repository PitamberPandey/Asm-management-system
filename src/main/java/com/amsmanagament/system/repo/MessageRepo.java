package com.amsmanagament.system.repo;

import com.amsmanagament.system.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepo  extends JpaRepository<Message,Long> {

    Optional<Message> findById(Long id);
}
