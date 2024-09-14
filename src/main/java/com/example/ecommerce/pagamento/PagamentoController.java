package com.example.ecommerce.pagamento;

import com.example.ecommerce.pagamento.dto.PagamentoEntityDto;
import com.example.ecommerce.pagamento.dto.ReadPagamentoDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/pagamento")
@RequiredArgsConstructor
@Tag(name = "Pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @PostMapping()
    public ReadPagamentoDto createProduto(@RequestBody PagamentoEntityDto dto) {
        return pagamentoService.create(dto);
    }
}
