package com.example.ecommerce.cliente.endereco.dto;

import java.util.UUID;

public record EnderecoEntityDto(
        Long id,
        String rua,
        String numero,
        String bairro,
        String cep,
        String cidade,
        String estado,
        UUID clienteId

) {
}
