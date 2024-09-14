package com.example.ecommerce.security.user.dto;

import com.example.ecommerce.security.user.permission.PermissionEntity;
import com.example.ecommerce.security.user.validation.password.ValidPassword;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

public record UserEntityDto(
        UUID id,
        @NotBlank(message = "MENSAGEM.USER.EMAIL-VAZIO")
        String email,
        @ValidPassword
        String senha,
        Set<PermissionEntity> permissons
) {
}
