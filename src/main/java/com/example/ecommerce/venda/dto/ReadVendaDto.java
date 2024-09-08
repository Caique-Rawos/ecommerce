package com.example.ecommerce.venda.dto;

import com.example.ecommerce.venda.vendaitem.dto.ReadVendaItemDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReadVendaDto(
        UUID id,
        UUID clienteId,
        BigDecimal valorTotal,
        LocalDateTime dataVenda,
        List<ReadVendaItemDto> itens
) {}