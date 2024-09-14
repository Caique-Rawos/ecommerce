package com.example.ecommerce.pagamento.dto;

public record ReadPagamentoDto(
        Long id,
        String descricao,
        Integer quantidadeParcelas
) {
}
