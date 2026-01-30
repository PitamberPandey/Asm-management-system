package com.amsmanagament.system.services;

import com.amsmanagament.system.model.Message;
import lombok.extern.java.Log;

import java.util.List;

public interface MessageServices {

    public Message sendMessage( Message messages,Long chatId);
    public Message updatemessage(Message message,Long messageId);
    List<Message> getMessagesByChat(Long chatId);


    Message markAsRead(Long messageId);


    void deleteMessage(Long messageId);

}
