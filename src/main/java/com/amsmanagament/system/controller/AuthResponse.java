package com.amsmanagament.system.controller;

import com.amsmanagament.system.model.User_Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String message;
    private String jwtToken;
    private User_Role role;
}
