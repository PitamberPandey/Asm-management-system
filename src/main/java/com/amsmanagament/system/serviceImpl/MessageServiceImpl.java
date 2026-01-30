package com.amsmanagament.system.serviceImpl;

import com.amsmanagament.system.exception.ResourceNotFoundException;
import com.amsmanagament.system.model.Chat;
import com.amsmanagament.system.model.Message;
import com.amsmanagament.system.model.User;
import com.amsmanagament.system.repo.ChatRepo;
import com.amsmanagament.system.repo.MessageRepo;
import com.amsmanagament.system.repo.UserRepo;
import com.amsmanagament.system.services.MessageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageServices {

    @Autowired
    private ChatRepo chatRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MessageRepo messageRepo;

    @Override
    public Message sendMessage( Message messages,Long chatId) {

        // Get logged-in user
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User sender = userRepo.findByPhoneNumber(phoneNumber);

        // Find chat
        Chat chat = chatRepo.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat not found"));

        // Create message
        Message message = new Message();
        message.setChat(chat);
        message.setSender(sender);
        message.setRead(false);
        message.setContent(messages.getContent());
        message.setCreatedAt(LocalDateTime.now());


        return messageRepo.save(message);
    }

    @Override
    public Message updatemessage(Message message,Long messageId) {
        Message messages=messageRepo.findById(messageId).orElseThrow(()->new ResourceNotFoundException("Message not found this id"));
        messages.setContent(message.getContent());
        messages.setCreatedAt(LocalDateTime.now());

        return messageRepo.save(messages);
    }

    @Override
    public List<Message> getMessagesByChat(Long chatId) {
        Chat chat = chatRepo.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat not found"));

        return chat.getMessages();
    }

    @Override
    public Message markAsRead(Long messageId) {
        Message message = messageRepo.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));

        message.setRead(true);
        return messageRepo.save(message);
    }

    @Override
    public void deleteMessage(Long messageId) {
        Message message = messageRepo.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
        messageRepo.delete(message);
    }
}
