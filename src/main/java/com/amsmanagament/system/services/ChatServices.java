package com.amsmanagament.system.services;

import com.amsmanagament.system.model.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatServices {

    // Create a chat between buyer and seller if not exists
    Chat createChat(Long buyerId, Long sellerId);

    // Delete chat by ID
    void deleteChat(Long chatId);

    // Get all chats
    List<Chat> getAllChats();

    // Get chat by ID
    Optional<Chat> getChatById(Long chatId);

    // Get chat between specific buyer & seller
    Optional<Chat> getChatByBuyerAndSeller(Long buyerId, Long sellerId);
}
