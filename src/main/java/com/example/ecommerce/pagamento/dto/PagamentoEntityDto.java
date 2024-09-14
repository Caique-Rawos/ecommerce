package com.example.ecommerce.pagamento.dto;

public record PagamentoEntityDto(
    Long id,
    String descricao,
    Integer quantidadeParcelas
) {
}
