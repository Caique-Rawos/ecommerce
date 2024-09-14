package com.example.ecommerce.pagamento;

import com.example.ecommerce.pagamento.dto.PagamentoEntityDto;
import com.example.ecommerce.pagamento.dto.ReadPagamentoDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PagamentoService {
    private final PagamentoRepository pagamentoRepository;
    @Transactional
    public ReadPagamentoDto create(PagamentoEntityDto dto) {
        PagamentoEntity pagamento = this.createUpdate(dto);
        return this.PagamentoToReadPagamentoDto(pagamentoRepository.save(pagamento));
    }

    public PagamentoEntity createUpdate(PagamentoEntityDto dto) {
        PagamentoEntity pagamento = new PagamentoEntity(dto.id(), dto.descricao(), dto.quantidadeParcelas(),null );
        return pagamento;
    }

    public ReadPagamentoDto PagamentoToReadPagamentoDto(PagamentoEntity pagamento){
        return  new ReadPagamentoDto(
                pagamento.getId(),
                pagamento.getDescricao(),
                pagamento.getQuantidadeParcelas()
        );
    }
}
