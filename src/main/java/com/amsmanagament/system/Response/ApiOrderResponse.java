package com.amsmanagament.system.Response;

import com.amsmanagament.system.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiOrderResponse {

    private String message;
    private boolean status;
    private Order entity;

}
