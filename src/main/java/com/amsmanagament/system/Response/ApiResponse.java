package com.amsmanagament.system.Response;

import com.amsmanagament.system.model.Farmer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    public String message;
    public boolean status;
}
