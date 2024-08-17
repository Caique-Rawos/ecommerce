package com.example.ecommerce.cliente.dto;

import com.example.ecommerce.cliente.endereco.dto.EnderecoEntityDto;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ClienteEntityDto(
        String nome,
        String cpfCnpj,
        String celular,
        String telefoneFixo,
        LocalDate dataNascimento,
        List<EnderecoEntityDto> enderecos
) {
}
