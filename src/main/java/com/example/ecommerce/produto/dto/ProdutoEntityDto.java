package com.example.ecommerce.produto.dto;

import com.example.ecommerce.produto.categoria.dto.CategoriaEntityDto;

import java.math.BigDecimal;

public record ProdutoEntityDto(
        Long id,
        String codigoBarras,
        BigDecimal precoVenda,
        BigDecimal estoque,
        String descricao,
        String descricaoDetalhada,
        String tipoEmbalagem,
        CategoriaEntityDto categoria
){}
