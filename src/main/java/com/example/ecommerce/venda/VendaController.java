package com.example.ecommerce.venda;

import com.example.ecommerce.venda.dto.ReadVendaDto;
import com.example.ecommerce.venda.dto.VendaEntityDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/venda")
@RequiredArgsConstructor
@Tag(name = "Vendas")
public class VendaController {

    private final VendaService vendaService;

    @PostMapping()
    public ReadVendaDto createUpdateVenda(
            @RequestBody VendaEntityDto dto,
            JwtAuthenticationToken token
    ) {
        return vendaService.createUpdate(dto, token);
    }
}
