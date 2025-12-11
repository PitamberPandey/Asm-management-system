package com.amsmanagament.system.Response;


import com.amsmanagament.system.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseProduct {
    private String message;
    private Boolean status;
    private Product entity;
}
