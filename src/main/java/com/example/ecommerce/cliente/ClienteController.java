package com.example.ecommerce.cliente;

import com.example.ecommerce.cliente.dto.ClienteEntityDto;
import com.example.ecommerce.cliente.dto.ReadClienteDto;
import com.example.ecommerce.produto.dto.ReadProdutoDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/cliente")
@RequiredArgsConstructor
@Tag(name = "Clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/{id}")
    public ReadClienteDto getById(
            @PathVariable("id") UUID id
    ) {
        return clienteService.getById(id);
    }

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
