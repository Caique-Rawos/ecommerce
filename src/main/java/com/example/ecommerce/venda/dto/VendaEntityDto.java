package com.example.ecommerce.venda.dto;

import com.example.ecommerce.venda.vendaitem.dto.VendaItemEntityDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record VendaEntityDto(
        UUID id,
        LocalDateTime dataVenda,
        List<VendaItemEntityDto> itens
) {}
