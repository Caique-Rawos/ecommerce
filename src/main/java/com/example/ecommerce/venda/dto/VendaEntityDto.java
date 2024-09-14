package com.example.ecommerce.venda.dto;

import com.example.ecommerce.venda.venda_item.dto.VendaItemEntityDto;
import com.example.ecommerce.venda.venda_parcela.dto.VendaParcelaEntityDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record VendaEntityDto(
        UUID id,
        LocalDateTime dataVenda,
        List<VendaItemEntityDto> itens,
        List<VendaParcelaEntityDto> parcelas
) {
}
