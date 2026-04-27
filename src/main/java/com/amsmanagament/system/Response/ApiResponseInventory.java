package com.amsmanagament.system.Response;

import com.amsmanagament.system.model.Inventory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseInventory {
    private String message;
    private boolean status;
    private Inventory entity;

}
