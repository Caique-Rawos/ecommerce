package com.example.ecommerce.venda;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VendaRepository extends JpaRepository<VendaEntity, UUID> {
}
