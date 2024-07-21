package com.example.ecommerce.security.login.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
