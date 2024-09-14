package com.example.ecommerce.produto;

import com.example.ecommerce.produto.categoria.dto.CategoriaEntityDto;
import com.example.ecommerce.produto.dto.ProdutoEntityDto;
import com.example.ecommerce.produto.dto.ReadProdutoDto;
import com.example.ecommerce.shared.PaginatedResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProdutoControllerTest {
    @Mock
    private ProdutoService produtoService;
    @InjectMocks
    private ProdutoController produtoController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testGetProduto() throws Exception {

        ReadProdutoDto produtoDto = new ReadProdutoDto("teste", "123", new BigDecimal("12.20"));

        when(produtoService.findReadProdutoDtoById(any(Long.class))).thenReturn(produtoDto);

        mockMvc.perform(get("/v1/produto/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(produtoDto)));

        verify(produtoService, times(1)).findReadProdutoDtoById(any(Long.class));
    }

    @Test
    void testGetPaginatedProduto() throws Exception {

        List<ReadProdutoDto> produtos = Arrays.asList(
                new ReadProdutoDto("Produto1", "123", new BigDecimal("10.00")),
                new ReadProdutoDto("Produto2", "456", new BigDecimal("15.00"))
        );

        PaginatedResponse<ReadProdutoDto> paginatedResponse = new PaginatedResponse<>(
                2,
                1,
                2,
                produtos
        );

        when(produtoService.findPaginated(0, 2, "id", "asc"))
                .thenReturn(paginatedResponse);

        mockMvc.perform(get("/v1/produto")
                        .param("page", "0")
                        .param("size", "2")
                        .param("sortBy", "id")
                        .param("sortDir", "asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].descricao").value("Produto1"))
                .andExpect(jsonPath("$.content[1].descricao").value("Produto2"))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    void testCreateProduto() throws Exception {
        ProdutoEntityDto createProdutoDTO = new ProdutoEntityDto(
                null,
                "123",
                new BigDecimal("12.20"),
                new BigDecimal("1"),
                "teste",
                "teste",
                "caixa",
                new CategoriaEntityDto(1L, "teste"));

        ReadProdutoDto produtoDto = new ReadProdutoDto("teste", "123", new BigDecimal("12.20"));

        when(produtoService.create(any(ProdutoEntityDto.class))).thenReturn(produtoDto);

        mockMvc.perform(post("/v1/produto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createProdutoDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(produtoDto)));

        verify(produtoService, times(1)).create(any(ProdutoEntityDto.class));
    }

    @Test
    void testUpdateProduto() throws Exception {
        ProdutoEntityDto createProdutoDTO = new ProdutoEntityDto(
                1L,
                "123",
                new BigDecimal("12.20"),
                new BigDecimal("1"),
                "teste",
                "teste",
                "caixa",
                new CategoriaEntityDto(1L, "teste"));

        ReadProdutoDto produtoDto = new ReadProdutoDto("teste", "123", new BigDecimal("12.20"));

        when(produtoService.update(any(Long.class), any(ProdutoEntityDto.class))).thenReturn(produtoDto);

        mockMvc.perform(patch("/v1/produto/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createProdutoDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(produtoDto)));

        verify(produtoService, times(1)).update(any(Long.class), any(ProdutoEntityDto.class));
    }

}
