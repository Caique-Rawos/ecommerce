package com.example.ecommerce.cliente;

import com.example.ecommerce.cliente.dto.ClienteEntityDto;
import com.example.ecommerce.cliente.dto.ReadClienteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClienteControllerTest {
    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetById() {
        // Arrange
        UUID clienteId = UUID.randomUUID();
        ReadClienteDto expectedCliente = new ReadClienteDto(
                "Nome do Cliente",
                "21331313131",
                "19912332131",
                "19912332131",
                null,
                null
        );

        when(clienteService.getById(clienteId)).thenReturn(expectedCliente);

        ReadClienteDto actualCliente = clienteController.getById(clienteId);

        assertEquals(expectedCliente, actualCliente);
        verify(clienteService, times(1)).getById(clienteId);
    }

    @Test
    public void testCreateUpdateCliente() {
        ClienteEntityDto clienteDto = new ClienteEntityDto(
                "Nome do Cliente",
                "21331313131",
                "19912332131",
                "19912332131",
                null,
                null
        );
        ReadClienteDto expectedCliente = new ReadClienteDto(
                "Nome do Cliente",
                "21331313131",
                "19912332131",
                "19912332131",
                null,
                null
        );
        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);

        when(clienteService.createUpdate(any(ClienteEntityDto.class), eq(token))).thenReturn(expectedCliente);

        ReadClienteDto actualCliente = clienteController.createUpdateCliente(clienteDto, token);

        assertEquals(expectedCliente, actualCliente);
        verify(clienteService, times(1)).createUpdate(clienteDto, token);
    }
}
