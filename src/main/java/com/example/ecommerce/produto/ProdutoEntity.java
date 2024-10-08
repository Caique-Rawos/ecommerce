package com.example.ecommerce.produto;

import com.example.ecommerce.produto.categoria.CategoriaEntity;
import com.example.ecommerce.produto.dto.ProdutoEntityDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produto")
public class ProdutoEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private BigDecimal precoVenda;

    @Column(unique = true)
    private String codigoBarras;

    private String tipoEmbalagem;

    private BigDecimal estoque;

    private String descricao;

    private String descricaoDetalhada;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaEntity categoria;

    public ProdutoEntity(ProdutoEntityDto dto) {
        this.codigoBarras = dto.codigoBarras();
        this.descricao = dto.descricao();
        this.descricaoDetalhada = dto.descricaoDetalhada();
        this.precoVenda = dto.precoVenda();
        this.estoque = dto.estoque();
        this.tipoEmbalagem = dto.tipoEmbalagem();
    }
}
