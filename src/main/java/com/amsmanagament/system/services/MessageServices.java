package com.amsmanagament.system.services;

import com.amsmanagament.system.model.Message;

public interface MessageServices {

    Message createMessage(Message message,Long recivedid,Long senderId);

}
