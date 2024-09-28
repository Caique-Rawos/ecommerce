package com.example.ecommerce.produto;

import com.example.ecommerce.produto.categoria.CategoriaEntity;
import com.example.ecommerce.produto.categoria.CategoriaService;
import com.example.ecommerce.produto.categoria.dto.CategoriaEntityDto;
import com.example.ecommerce.produto.dto.ProdutoEntityDto;
import com.example.ecommerce.produto.dto.ReadProdutoDto;
import com.example.ecommerce.shared.PaginatedResponse;
import com.example.ecommerce.shared.exception.MessageException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class ProdutoServiceTest {
    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private ProdutoService produtoService;

    private ProdutoEntity produtoEntity;
    private ProdutoEntityDto produtoEntityDto;
    private CategoriaEntity categoriaEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        produtoEntity = new ProdutoEntity();
        produtoEntity.setId(1L);
        produtoEntity.setCodigoBarras("123456789");

        produtoEntityDto = new ProdutoEntityDto(
                null,
                "123456789",
                BigDecimal.valueOf(99.99),
                BigDecimal.valueOf(10),
                "desc",
                "desc",
                "UN",
                new CategoriaEntityDto(null, "CategoriaTeste")
        );

        categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(1L);
        categoriaEntity.setDescricao("CategoriaTeste");
    }

    @Test
    public void testCreateWhenCodigoBarraIsNotRegisteredReturnsDto() {
        when(produtoRepository.findByCodigoBarras(produtoEntityDto.codigoBarras())).thenReturn(Optional.empty());
        when(categoriaService.getOrCreateCategoria(produtoEntityDto.categoria())).thenReturn(categoriaEntity);
        when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);

        ReadProdutoDto result = produtoService.create(produtoEntityDto);

        assertNotNull(result);
        assertEquals(produtoEntity.getCodigoBarras(), result.codigoBarras());
        verify(produtoRepository, times(1)).save(any(ProdutoEntity.class));
    }

    @Test
    public void testCreateWhenCodigoBarraIsAlreadyRegisteredThrowsException() {
        when(produtoRepository.findByCodigoBarras(produtoEntityDto.codigoBarras())).thenReturn(Optional.of(produtoEntity));

        MessageException thrown = assertThrows(MessageException.class, () -> {
            produtoService.create(produtoEntityDto);
        });

        assertEquals("MENSAGEM.PRODUTO.CODIGO-BARRA-JA-CADASTRADO", thrown.getMessage());
    }

    @Test
    public void testUpdateWhenProdutoExistsReturnsUpdatedDto() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoEntity));
        when(produtoRepository.findByCodigoBarrasAndIdNot(produtoEntityDto.codigoBarras(), 1L)).thenReturn(Optional.empty());
        when(categoriaService.getOrCreateCategoria(produtoEntityDto.categoria())).thenReturn(categoriaEntity);
        when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);

        ReadProdutoDto result = produtoService.update(1L, produtoEntityDto);

        assertNotNull(result);
        assertEquals(produtoEntity.getCodigoBarras(), result.codigoBarras());
        verify(produtoRepository, times(1)).save(any(ProdutoEntity.class));
    }

    @Test
    public void testUpdateWhenProdutoDoesNotExistThrowsException() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        MessageException thrown = assertThrows(MessageException.class, () -> {
            produtoService.update(1L, produtoEntityDto);
        });

        assertEquals("MENSAGEM.PRODUTO.NAO-ENCONTRADO", thrown.getMessage());
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
    public void testVerifyCodigoBarraIsRegisteredWhenCodigoBarraIsRegisteredWithDifferentIdThrowsException() {
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
    public void testVerifyCodigoBarraIsRegisteredWhenCodigoBarraIsNotRegisteredDoesNotThrowException() {
        String codigoBarra = "123456789";
        Long id = 1L;

        when(produtoRepository.findByCodigoBarrasAndIdNot(codigoBarra, id)).thenReturn(Optional.empty());

        produtoService.verifyCodigoBarraIsRegistered(codigoBarra, id);
    }

    @Test
    public void testVerifyCodigoBarraIsRegisteredWhenCodigoBarraIsNotRegisteredWithNullIdDoesNotThrowException() {
        String codigoBarra = "123456789";

        when(produtoRepository.findByCodigoBarras(codigoBarra)).thenReturn(Optional.empty());

        produtoService.verifyCodigoBarraIsRegistered(codigoBarra, null);
    }

    @Test
    public void testVerifyCodigoBarraIsRegisteredWhenCodigoBarraIsRegisteredWithNullIdThrowsException() {
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
    public void testFindReadProdutoDtoByIdWhenProdutoExistsReturnsDto() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoEntity));

        ReadProdutoDto result = produtoService.findReadProdutoDtoById(1L);

        assertNotNull(result);
        assertEquals(produtoEntity.getCodigoBarras(), result.codigoBarras());
    }

    @Test
    public void testFindReadProdutoDtoByIdWhenProdutoDoesNotExistThrowsException() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        MessageException thrown = assertThrows(MessageException.class, () -> {
            produtoService.findReadProdutoDtoById(1L);
        });

        assertEquals("MENSAGEM.PRODUTO.NAO-ENCONTRADO", thrown.getMessage());
    }
}
