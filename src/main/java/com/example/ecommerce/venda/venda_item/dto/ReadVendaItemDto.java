package com.example.ecommerce.venda.venda_item.dto;

import java.math.BigDecimal;

public record ReadVendaItemDto(
        Long id,
        Long produtoId,
        int quantidade,
        BigDecimal precoUnitario
) {
}
