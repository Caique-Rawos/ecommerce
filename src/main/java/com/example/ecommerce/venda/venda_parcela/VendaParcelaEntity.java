package com.example.ecommerce.venda.venda_parcela;

import com.example.ecommerce.pagamento.PagamentoEntity;
import com.example.ecommerce.venda.VendaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "venda_parcela")
public class VendaParcelaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "venda_id")
    private VendaEntity venda;
    @ManyToOne
    @JoinColumn(name = "pagamento_id")
    private PagamentoEntity pagamento;

    private BigDecimal valorParcela;

    private LocalDateTime dataVencimento;

    private Boolean statusPagamento = false;

    @Override
    public String toString() {
        return String.format(
                "VendaParcelaEntity{id=%d, venda=%s, pagamento=%s, valorParcela=%s, dataVencimento=%s, statusPagamento=%s}",
                id,
                venda != null ? venda.getId() : "null",
                pagamento != null ? pagamento.getId() : "null",
                valorParcela,
                dataVencimento,
                statusPagamento
        );
    }
}
