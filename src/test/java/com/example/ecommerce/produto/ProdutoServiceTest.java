package com.example.ecommerce.produto;

import com.example.ecommerce.produto.dto.ReadProdutoDto;
import com.example.ecommerce.shared.PaginatedResponse;
import com.example.ecommerce.shared.exception.MessageException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class ProdutoServiceTest {
    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;


    private ProdutoEntity produtoEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        produtoEntity = new ProdutoEntity();
        produtoEntity.setId(1L);
        produtoEntity.setCodigoBarras("123456789");
    }

    @Test
    public void testFindPaginated() {
        int page = 0;
        int size = 5;
        String sortBy = "nome";
        String sortDir = "asc";

        ProdutoEntity produto1 = new ProdutoEntity();
        ProdutoEntity produto2 = new ProdutoEntity();
        List<ProdutoEntity> produtos = Arrays.asList(produto1, produto2);
        Page<ProdutoEntity> produtoPage = new PageImpl<>(produtos, PageRequest.of(page, size, Sort.by(sortBy).ascending()), produtos.size());

        when(produtoRepository.findAll(any(Pageable.class))).thenReturn(produtoPage);

        PaginatedResponse<ReadProdutoDto> response = produtoService.findPaginated(page, size, sortBy, sortDir);

        assertNotNull(response);
        assertEquals(2, response.getTotalElements());
        assertEquals(1, response.getTotalPages());
        assertEquals(size, response.getSize());
        assertEquals(2, response.getContent().size());

        verify(produtoRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void testVerifyCodigoBarraIsRegistered_WhenCodigoBarraIsRegisteredWithDifferentId_ThrowsException() {
        String codigoBarra = "123456789";
        Long id = 1L;
        ProdutoEntity existingProduto = new ProdutoEntity();
        existingProduto.setCodigoBarras(codigoBarra);

        when(produtoRepository.findByCodigoBarrasAndIdNot(codigoBarra, id)).thenReturn(Optional.of(existingProduto));

        MessageException thrown = assertThrows(MessageException.class, () -> {
            produtoService.verifyCodigoBarraIsRegistered(codigoBarra, id);
        });

        assertEquals("MENSAGEM.PRODUTO.CODIGO-BARRA-JA-CADASTRADO", thrown.getMessage());
    }

    @Test
    public void testVerifyCodigoBarraIsRegistered_WhenCodigoBarraIsNotRegistered_DoesNotThrowException() {
        String codigoBarra = "123456789";
        Long id = 1L;

        when(produtoRepository.findByCodigoBarrasAndIdNot(codigoBarra, id)).thenReturn(Optional.empty());

        produtoService.verifyCodigoBarraIsRegistered(codigoBarra, id);
    }

    @Test
    public void testVerifyCodigoBarraIsRegistered_WhenCodigoBarraIsNotRegisteredWithNullId_DoesNotThrowException() {
        String codigoBarra = "123456789";

        when(produtoRepository.findByCodigoBarras(codigoBarra)).thenReturn(Optional.empty());

        // Execute method without exception
        produtoService.verifyCodigoBarraIsRegistered(codigoBarra, null);
    }

    @Test
    public void testVerifyCodigoBarraIsRegistered_WhenCodigoBarraIsRegisteredWithNullId_ThrowsException() {
        String codigoBarra = "123456789";
        ProdutoEntity existingProduto = new ProdutoEntity();
        existingProduto.setCodigoBarras(codigoBarra);

        when(produtoRepository.findByCodigoBarras(codigoBarra)).thenReturn(Optional.of(existingProduto));

        MessageException thrown = assertThrows(MessageException.class, () -> {
            produtoService.verifyCodigoBarraIsRegistered(codigoBarra, null);
        });

        assertEquals("MENSAGEM.PRODUTO.CODIGO-BARRA-JA-CADASTRADO", thrown.getMessage());
    }

    @Test
    public void testFindReadProdutoDtoById_WhenProdutoExists_ReturnsDto() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoEntity));

        ReadProdutoDto result = produtoService.findReadProdutoDtoById(1L);

        assertNotNull(result);
        assertEquals(produtoEntity.getCodigoBarras(), result.codigoBarras());
    }

    @Test
    public void testFindReadProdutoDtoById_WhenProdutoDoesNotExist_ThrowsException() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        MessageException thrown = assertThrows(MessageException.class, () -> {
            produtoService.findReadProdutoDtoById(1L);
        });

        assertEquals("MENSAGEM.PRODUTO.NAO-ENCONTRADO", thrown.getMessage());
    }
}
