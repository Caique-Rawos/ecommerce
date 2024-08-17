package com.example.ecommerce.cliente.endereco.dto;

public record ReadEnderecoDto(
        String rua,
        String numero,
        String bairro,
        String cep,
        String cidade,
        String estado
) {
}
