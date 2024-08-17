package com.example.ecommerce.produto.categoria;

import com.example.ecommerce.produto.ProdutoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produto_categoria")
public class CategoriaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String descricao;

    @OneToMany(mappedBy = "categoria")
    private Set<ProdutoEntity> produtos;
}
