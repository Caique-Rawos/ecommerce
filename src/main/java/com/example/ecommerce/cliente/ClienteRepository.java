package com.example.ecommerce.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {
}
