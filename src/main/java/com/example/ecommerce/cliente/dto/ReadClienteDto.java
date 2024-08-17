package com.example.ecommerce.cliente.dto;

import com.example.ecommerce.cliente.endereco.dto.EnderecoEntityDto;
import com.example.ecommerce.cliente.endereco.dto.ReadEnderecoDto;

import java.time.LocalDate;
import java.util.List;

public record ReadClienteDto(
        String nome,
        String cpfCnpj,
        String celular,
        String telefoneFixo,
        LocalDate dataNascimento,
        List<ReadEnderecoDto> enderecos
) {
}
