package com.example.ecommerce.security.config;

import com.example.ecommerce.security.user.permission.PermissionEntity;

public class JwtConfig {
    public static final String PERMISSION_CLAIM = "scope";

    public static String getPermissionClaim() {
        return PERMISSION_CLAIM;
    }

    private static String generateScope(String permission) {
        return String.format("%s_%s", PERMISSION_CLAIM.toUpperCase(), permission);
    }

    public static String getAdminScope() {
        return generateScope(PermissionEntity.permissions.ADMIN.name());
    }

    public static String getBasicScope() {
        return generateScope(PermissionEntity.permissions.BASIC.name());
    }
}
