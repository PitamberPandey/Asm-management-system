package com.amsmanagament.system.Response;

import com.amsmanagament.system.model.Chat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiChatReponse {
    private String message;
    private  Boolean status;
    private Chat entity;
}
