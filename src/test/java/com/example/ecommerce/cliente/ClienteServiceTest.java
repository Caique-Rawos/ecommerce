package com.example.ecommerce.cliente;

import com.example.ecommerce.cliente.dto.ClienteEntityDto;
import com.example.ecommerce.cliente.dto.ReadClienteDto;
import com.example.ecommerce.cliente.endereco.EnderecoEntity;
import com.example.ecommerce.cliente.endereco.dto.EnderecoEntityDto;
import com.example.ecommerce.cliente.endereco.dto.ReadEnderecoDto;
import com.example.ecommerce.security.user.UserEntity;
import com.example.ecommerce.security.user.UserService;
import com.example.ecommerce.shared.exception.MessageException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {
    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUpdate() {
        List<EnderecoEntityDto> enderecos = Arrays.asList(
                new EnderecoEntityDto(
                        null,
                        "Rua A",
                        "123",
                        "Bairro A",
                        "00000-000",
                        "Cidade A",
                        "Estado A",
                        null
                )
        );

        ClienteEntityDto clienteDto = new ClienteEntityDto(
                "Nome do Cliente",
                "123.456.789-00",
                "123456789",
                "987654321",
                null,
                enderecos
        );

        UUID userId = UUID.randomUUID();
        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        when(token.getName()).thenReturn(userId.toString());

        UserEntity usuario = new UserEntity();
        when(userService.getById(userId)).thenReturn(usuario);

        ClienteEntity cliente = new ClienteEntity(clienteDto);
        cliente.setUser(usuario);

        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(cliente);
        when(clienteRepository.findByUser(usuario)).thenReturn(Optional.empty());

        ReadClienteDto result = clienteService.createUpdate(clienteDto, token);

        assertEquals(cliente.getNome(), result.nome());
        verify(clienteRepository, times(1)).save(any(ClienteEntity.class));
    }

    @Test
    public void testGetById() {
        UUID clienteId = UUID.randomUUID();
        ClienteEntity cliente = new ClienteEntity();
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        ReadClienteDto result = clienteService.getById(clienteId);

        assertEquals(cliente.getNome(), result.nome());
        verify(clienteRepository, times(1)).findById(clienteId);
    }

    @Test
    public void testFindByIdNotFound() {
        UUID clienteId = UUID.randomUUID();
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        assertThrows(MessageException.class, () -> clienteService.findById(clienteId));
    }

    @Test
    public void testEnderecoToReadEnderecoDto() {
        EnderecoEntity endereco = new EnderecoEntity();
        endereco.setRua("Rua A");
        endereco.setNumero("123");
        endereco.setBairro("Bairro A");
        endereco.setCep("00000-000");
        endereco.setCidade("Cidade A");
        endereco.setEstado("Estado A");

        ReadEnderecoDto enderecoDto = clienteService.enderecoToReadEnderecoDto(endereco);

        assertEquals(endereco.getRua(), enderecoDto.rua());
        assertEquals(endereco.getNumero(), enderecoDto.numero());
        assertEquals(endereco.getBairro(), enderecoDto.bairro());
        assertEquals(endereco.getCep(), enderecoDto.cep());
        assertEquals(endereco.getCidade(), enderecoDto.cidade());
        assertEquals(endereco.getEstado(), enderecoDto.estado());
    }
}
