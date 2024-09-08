package com.example.ecommerce.venda.vendaitem.dto;

import java.math.BigDecimal;

public record ReadVendaItemDto(
        Long id,
        Long produtoId,
        int quantidade,
        BigDecimal precoUnitario
) {
}
