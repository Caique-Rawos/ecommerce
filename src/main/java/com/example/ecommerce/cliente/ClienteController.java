package com.example.ecommerce.cliente;

import com.example.ecommerce.cliente.dto.ClienteEntityDto;
import com.example.ecommerce.cliente.dto.ReadClienteDto;
import com.example.ecommerce.produto.dto.ReadProdutoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping()
    public ReadClienteDto createCliente(
            @RequestBody ClienteEntityDto dto,
            JwtAuthenticationToken token
    ) {
        return clienteService.create(dto, token);
    }

    @PatchMapping("/{id}")
    public ReadClienteDto updateCliente(
            @PathVariable("id") UUID id,
            @RequestBody ClienteEntityDto dto,
            JwtAuthenticationToken token
    ) {
        return clienteService.update(id, dto, token);
    }
}
