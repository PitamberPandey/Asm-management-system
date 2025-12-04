package com.amsmanagament.system.Response;

import com.amsmanagament.system.model.Buyer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // keeps default constructor
@AllArgsConstructor // adds constructor with all fields
public class ApiCreateBuyer {
    private String message;
    private boolean status;
    private Buyer entity;

    // Optional: custom constructor (if you don't want to use @AllArgsConstructor)
    // public ApiCreateBuyer(String message, boolean status, Buyer entity) {
    //     this.message = message;
    //     this.status = status;
    //     this.entity = entity;
    // }
}

