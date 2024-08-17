package com.example.ecommerce.cliente.endereco;

import com.example.ecommerce.cliente.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long> {
}
