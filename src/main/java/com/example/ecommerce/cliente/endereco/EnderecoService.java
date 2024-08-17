package com.example.ecommerce.cliente.endereco;

import com.example.ecommerce.cliente.ClienteEntity;
import com.example.ecommerce.cliente.dto.ReadClienteDto;
import com.example.ecommerce.cliente.endereco.dto.ReadEnderecoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoService {
    public ReadEnderecoDto enderecoToReadEnderecoDto(EnderecoEntity endereco){
        return  new ReadEnderecoDto(
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCep(),
                endereco.getCidade(),
                endereco.getEstado()
        );
    }

}
