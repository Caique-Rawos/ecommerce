package com.example.ecommerce.venda.venda_parcela.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReadVendaParcelaDto(
        Long id,
        Long idPagamento,
        LocalDateTime dataVencimento,
        BigDecimal valorParcela,
        Boolean statusPagamento
) {
}
