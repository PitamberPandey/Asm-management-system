package com.amsmanagament.system.Response;

import com.amsmanagament.system.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerResponse {

    private String message;
    private boolean satutus;
    private Seller seller;
}
