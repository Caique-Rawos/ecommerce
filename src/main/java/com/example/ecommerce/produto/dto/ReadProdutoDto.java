package com.example.ecommerce.produto.dto;

import java.math.BigDecimal;

public record ReadProdutoDto(
        String descricao,
        String codigoBarras,
        BigDecimal precoVenda
){};
