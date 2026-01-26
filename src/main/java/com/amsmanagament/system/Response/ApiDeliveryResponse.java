package com.amsmanagament.system.Response;

import com.amsmanagament.system.model.Delivery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiDeliveryResponse {
    private  String message;
    private boolean status;
        private Delivery entity;
}
