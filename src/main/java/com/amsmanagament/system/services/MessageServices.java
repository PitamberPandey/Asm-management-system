package com.amsmanagament.system.services;

import com.amsmanagament.system.model.Message;
import lombok.extern.java.Log;

import java.util.List;

public interface MessageServices {

    public Message sendMessage( Message messages,Long chatId);
    List<Message> getMessagesByChat(Long chatId);

    // 3. Optional: Mark a message as read
    Message markAsRead(Long messageId);

    // 4. Optional: Delete a message
    void deleteMessage(Long messageId);

}
