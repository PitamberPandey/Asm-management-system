package com.amsmanagament.system.Response;

import com.amsmanagament.system.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseCategory {

    private String message;
    private Boolean status;
    private Category entity;
}
