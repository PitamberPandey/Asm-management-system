package com.amsmanagament.system.controller;

import com.amsmanagament.system.Response.ApiChatReponse;
import com.amsmanagament.system.model.Chat;
import com.amsmanagament.system.model.Message;
import com.amsmanagament.system.services.ChatServices;
import com.amsmanagament.system.services.MessageServices;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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








    /**
     * 2️⃣ Send a message in a chat
     */
    @PostMapping("/{chatId}/message")
    public Message sendMessage(@PathVariable Long chatId,
                               @RequestParam String content) {
        return null;
    }

    /**
     * 3️⃣ Get all messages of a chat
     */
    @GetMapping("/{chatId}/messages")
    public List<Message> getMessages(@PathVariable Long chatId) {
        return messageServices.getMessagesByChat(chatId);
    }

    /**
     * 4️⃣ Optional: Mark a message as read
     */
    @PostMapping("/message/{messageId}/read")
    public Message markAsRead(@PathVariable Long messageId) {
        return messageServices.markAsRead(messageId);
    }

    /**
     * 5️⃣ Optional: Delete a message
     */
    @DeleteMapping("/message/{messageId}")
    public String deleteMessage(@PathVariable Long messageId) {
        messageServices.deleteMessage(messageId);
        return "Message deleted successfully";
    }
}

