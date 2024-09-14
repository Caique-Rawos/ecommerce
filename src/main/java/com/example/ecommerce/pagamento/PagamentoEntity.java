package com.example.ecommerce.pagamento;

import com.example.ecommerce.venda.venda_parcela.VendaParcelaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pagamento")
public class PagamentoEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private Integer quantidadeParcelas;

    @OneToMany(mappedBy = "pagamento")
    private List<VendaParcelaEntity> parcelas;
}
