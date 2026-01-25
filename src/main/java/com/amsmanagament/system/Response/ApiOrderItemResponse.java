package com.amsmanagament.system.Response;

import com.amsmanagament.system.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiOrderItemResponse {

    private String message;
    private boolean status;
    private OrderItem entity;
}
