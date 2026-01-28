package com.amsmanagament.system.repo;

import com.amsmanagament.system.model.Chat;
import com.amsmanagament.system.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepo extends JpaRepository<Chat,Long> {

    Optional<Chat> findById(Long chatId);
    List<Chat> findByMessage(Message message);


}
