package com.amsmanagament.system.controller;

import com.amsmanagament.system.Response.ApiChatReponse;
import com.amsmanagament.system.model.Chat;
import com.amsmanagament.system.model.Message;
import com.amsmanagament.system.services.ChatServices;
import com.amsmanagament.system.services.MessageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/farmerbuyer")
public class BuyerAndSellerControllers {

    @Autowired
    private ChatServices chatServices;

    @Autowired
    private MessageServices messageServices;


    @PostMapping("/create/{buyerId}/chat/{sellerId}")
    public ResponseEntity<ApiChatReponse> createChat(@PathVariable Long buyerId,
                                                     @PathVariable Long sellerId) {
        try {
            // Create or get chat
            Chat chat = chatServices.createChat(buyerId, sellerId);

            // Build API response
            ApiChatReponse apiChatReponse = new ApiChatReponse(
                    "Chat created successfully",
                    true,
                    chat
            );

            return ResponseEntity.ok(apiChatReponse);

        } catch (Exception e) {
            // Handle errors
            ApiChatReponse apiChatReponse = new ApiChatReponse(
                    "Failed to create chat: " + e.getMessage(),
                    false,
                    null
            );
            return ResponseEntity.status(500).body(apiChatReponse);
        }
    }

    @PostMapping("/create/{chatId}")
    public ResponseEntity<ApiChatReponse> deleteChat(@PathVariable Long chatId) {
        try {
            // Create or get chat
             chatServices.deleteChat(chatId);

            // Build API response
            ApiChatReponse apiChatReponse = new ApiChatReponse(
                    "DeleteChat created successfully",
                    true,
                    null
            );

            return ResponseEntity.ok(apiChatReponse);

        } catch (Exception e) {
            // Handle errors
            ApiChatReponse apiChatReponse = new ApiChatReponse(
                    "Failed to delete chat: " + e.getMessage(),
                    false,
                    null
            );
            return ResponseEntity.status(500).body(apiChatReponse);
        }
    }

    @GetMapping("/chats")
    public  List<Chat> findALLChat(){
        List<Chat> chats=chatServices.getAllChats();
        return chats;
    }

@GetMapping("chat/{chatId}")
public Chat findById(@PathVariable("chatId") Long chatId) throws  Exception{
        Optional<Chat> chat=chatServices.getChatById(chatId);
        return chat.orElse(null);
}

@GetMapping("chat/{buyId}/{sellerId}")
public Optional<Chat> findChatsBetween(@PathVariable("buyerid")Long buyerId, @PathVariable("sellerId") Long sellerId){
        Optional<Chat> chat=chatServices.getChatByBuyerAndSeller(buyerId,sellerId);
        return chat;
}


    @PostMapping("/{chatId}/message")
    public Message sendMessage(@PathVariable Long chatId,
                               @RequestBody Message message) {
        Message messages=messageServices.sendMessage(message,chatId);



        return messages;
    }


    @GetMapping("/{chatId}/messages")
    public List<Message> getMessages(@PathVariable Long chatId) {
        return messageServices.getMessagesByChat(chatId);
    }

    @PutMapping("message/update/{messageId}")
    public Message updateMessage(@RequestBody Message message,@PathVariable("messageId")Long messageId){
        Message message1=messageServices.updatemessage(message,messageId);
        return ResponseEntity.ok(message1).getBody();
    }


    @PostMapping("/message/{messageId}/read")
    public Message markAsRead(@PathVariable Long messageId) {
        return messageServices.markAsRead(messageId);
    }


    @DeleteMapping("/message/{messageId}")
    public String deleteMessage(@PathVariable Long messageId) {
        messageServices.deleteMessage(messageId);
        return "Message deleted successfully";
    }
}

