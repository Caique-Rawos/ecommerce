package com.example.ecommerce.produto.dto;

import lombok.Data;

import java.math.BigDecimal;

public record ProdutoEntityDto(
        Long id,
        String codigoBarras,
        BigDecimal precoVenda,
        String descricao,
        String tipoEmbalagem
){}
