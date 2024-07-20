package com.example.ecommerce.security.permission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PermissonRepository extends JpaRepository<PermissionEntity, Long> {
}
