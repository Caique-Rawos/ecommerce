package com.example.ecommerce.cliente;

import com.example.ecommerce.cliente.dto.ClienteEntityDto;
import com.example.ecommerce.cliente.dto.ReadClienteDto;
import com.example.ecommerce.cliente.endereco.EnderecoEntity;
import com.example.ecommerce.cliente.endereco.EnderecoService;
import com.example.ecommerce.cliente.endereco.dto.ReadEnderecoDto;
import com.example.ecommerce.security.user.UserEntity;
import com.example.ecommerce.security.user.UserRepository;
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
    private final EnderecoService enderecoService;

    @Transactional
    public ReadClienteDto create(ClienteEntityDto dto, JwtAuthenticationToken token) {
        ClienteEntity cliente = this.createUpdate(dto, token);
        return this.clienteToReadClienteDto(clienteRepository.save(cliente));
    }

    public ClienteEntity createUpdate(ClienteEntityDto dto, JwtAuthenticationToken token) {
        ClienteEntity cliente = new ClienteEntity(dto);
        System.out.println(token.getName());
        System.out.println(UUID.fromString(token.getName()));
        cliente.setUser(userService.getById(UUID.fromString(token.getName())));

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
        return cliente;
    }

    @Transactional
    public ReadClienteDto update(UUID id, ClienteEntityDto dto, JwtAuthenticationToken token) {
        UUID idExistentCliente = this.findById(id).getId();

        ClienteEntity cliente = this.createUpdate(dto, token);
        cliente.setId(idExistentCliente);

        return this.clienteToReadClienteDto(clienteRepository.save(cliente));
    }

    public ClienteEntity findById(UUID id) {
        return clienteRepository.findById(id).orElseThrow(() -> new MessageException("MENSAGEM.CLIENTE.NAO-ENCONTRADO"));
    }

    public ReadClienteDto clienteToReadClienteDto(ClienteEntity cliente){
        List<ReadEnderecoDto> enderecoDtos = cliente.getEnderecos().stream()
                .map(enderecoService::enderecoToReadEnderecoDto)
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
}
