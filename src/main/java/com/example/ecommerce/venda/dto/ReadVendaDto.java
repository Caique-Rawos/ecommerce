package com.example.ecommerce.venda.dto;

import com.example.ecommerce.venda.venda_item.dto.ReadVendaItemDto;
import com.example.ecommerce.venda.venda_parcela.dto.ReadVendaParcelaDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReadVendaDto(
        UUID id,
        UUID clienteId,
        BigDecimal valorTotal,
        LocalDateTime dataVenda,
        List<ReadVendaItemDto> itens,
        List<ReadVendaParcelaDto> parcelas
) {
}