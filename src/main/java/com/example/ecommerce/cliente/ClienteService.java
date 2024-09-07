package com.example.ecommerce.cliente;

import com.example.ecommerce.cliente.dto.ClienteEntityDto;
import com.example.ecommerce.cliente.dto.ReadClienteDto;
import com.example.ecommerce.cliente.endereco.EnderecoEntity;
import com.example.ecommerce.cliente.endereco.dto.ReadEnderecoDto;
import com.example.ecommerce.security.user.UserEntity;
import com.example.ecommerce.security.user.UserService;
import com.example.ecommerce.shared.exception.MessageException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UserService userService;

//    public List<ReadClienteDto> findAll() {
//        return this.clienteRepository
//                .findAll()
//                .stream()
//                .map(this::clienteToReadClienteDto)
//                .collect(Collectors.toList());
//    }

    @Transactional
    public ReadClienteDto createUpdate(ClienteEntityDto dto, JwtAuthenticationToken token) {
        ClienteEntity cliente = new ClienteEntity(dto);

        UserEntity usuario = userService.getById(UUID.fromString(token.getName()));

        cliente.setId(this.findByUsuario(usuario));
        cliente.setUser(usuario);

        List<EnderecoEntity> enderecos = dto.enderecos().stream()
                .map(enderecoDto -> {
                    EnderecoEntity endereco = new EnderecoEntity();
                    endereco.setRua(enderecoDto.rua());
                    endereco.setNumero(enderecoDto.numero());
                    endereco.setBairro(enderecoDto.bairro());
                    endereco.setCep(enderecoDto.cep());
                    endereco.setCidade(enderecoDto.cidade());
                    endereco.setEstado(enderecoDto.estado());
                    endereco.setCliente(cliente);
                    return endereco;
                })
                .collect(Collectors.toList());

        cliente.setEnderecos(enderecos);
        return this.clienteToReadClienteDto(clienteRepository.save(cliente));
    }



    public ReadClienteDto getById(UUID id) {
        return this.clienteToReadClienteDto(this.findById(id));
    }

    public ClienteEntity findById(UUID id) {
        return clienteRepository.findById(id).orElseThrow(() -> new MessageException("MENSAGEM.CLIENTE.NAO-ENCONTRADO"));
    }

    public UUID findByUsuario(UserEntity usuario) {
        return clienteRepository.findByUser(usuario).map(ClienteEntity::getId).orElse(null);
    }

    public ReadClienteDto clienteToReadClienteDto(ClienteEntity cliente){
        List<ReadEnderecoDto> enderecoDtos = cliente.getEnderecos().stream()
                .map(this::enderecoToReadEnderecoDto)
                .collect(Collectors.toList());

        return  new ReadClienteDto(
                cliente.getNome(),
                cliente.getCpfCnpj(),
                cliente.getCelular(),
                cliente.getTelefoneFixo(),
                cliente.getDataNascimento(),
                enderecoDtos
        );
    }

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
