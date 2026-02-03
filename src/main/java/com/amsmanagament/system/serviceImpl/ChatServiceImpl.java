package com.amsmanagament.system.serviceImpl;

import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.Buyer;
import com.amsmanagament.system.model.Chat;
import com.amsmanagament.system.model.Seller;
import com.amsmanagament.system.repo.ByerRepo;
import com.amsmanagament.system.repo.ChatRepo;
import com.amsmanagament.system.repo.SellerRepo;
import com.amsmanagament.system.services.ChatServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatServices {

    @Autowired
    private ChatRepo chatRepo;

    @Autowired
    private SellerRepo sellerRepo;

    @Autowired
    private ByerRepo byerRepo;

    @Override
    public Chat createChat(Long buyerId, Long sellerId) {

        Buyer buyer = byerRepo.findById(buyerId)
                .orElseThrow(() -> new ResourceNotFoundException("Buyer not found with this id " + buyerId));

        Seller seller = sellerRepo.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with id " + sellerId));

        // Check if chat already exists between buyer and seller
        Optional<Chat> existingChat = chatRepo.findByBuyerAndSeller(buyer, seller);
        if (existingChat.isPresent()) {
            return existingChat.get(); // return existing chat
        }

        Chat chat = new Chat();
        chat.setBuyer(buyer);
        chat.setSeller(seller);

        return chatRepo.save(chat);
    }

    @Override
    public void deleteChat(Long chatId) {
        Chat chat = chatRepo.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat not found with id " + chatId));
        chatRepo.delete(chat);
    }

    @Override
    public List<Chat> getAllChats() {
        return chatRepo.findAll();
    }

    @Override
    public Optional<Chat> getChatById(Long chatId) {
        return chatRepo.findById(chatId);
    }

    @Override
    public Optional<Chat> getChatByBuyerAndSeller(Long buyerId, Long sellerId) {
        Buyer buyer = byerRepo.findById(buyerId)
                .orElseThrow(() -> new ResourceNotFoundException("Buyer not found with id " + buyerId));

        Seller seller = sellerRepo.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with id " + sellerId));

        return chatRepo.findByBuyerAndSeller(buyer, seller);
    }
}
