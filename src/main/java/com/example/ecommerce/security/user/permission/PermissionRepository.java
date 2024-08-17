package com.example.ecommerce.security.user.permission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    PermissionEntity findByDescricao(String descricao);
}
