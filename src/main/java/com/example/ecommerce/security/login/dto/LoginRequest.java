package com.example.ecommerce.security.login.dto;

import com.example.ecommerce.security.user.dto.UserValidation;

public record LoginRequest(String email, String senha) {

    public UserValidation toUserValidation() {
        return new UserValidation(email, senha);
    }
}
