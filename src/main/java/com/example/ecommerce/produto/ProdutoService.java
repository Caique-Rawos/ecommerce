package com.example.ecommerce.produto;

import com.example.ecommerce.produto.categoria.CategoriaEntity;
import com.example.ecommerce.produto.categoria.CategoriaRepository;
import com.example.ecommerce.produto.categoria.CategoriaService;
import com.example.ecommerce.produto.dto.ProdutoEntityDto;
import com.example.ecommerce.produto.dto.ReadProdutoDto;
import com.example.ecommerce.shared.PaginatedResponse;
import com.example.ecommerce.shared.exception.MessageException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;

    public PaginatedResponse<ReadProdutoDto> findPaginated(int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProdutoEntity> produtoPage = produtoRepository.findAll(pageable);

        Page<ReadProdutoDto> dtoPage = produtoPage.map(this::produtoToReadProdutoDto);

        return new PaginatedResponse<>(
                dtoPage.getTotalElements(),
                dtoPage.getTotalPages(),
                dtoPage.getSize(),
                dtoPage.getContent()
        );
    }

    @Transactional
    public ReadProdutoDto create(ProdutoEntityDto dto) {
        ProdutoEntity product = this.createUpdate(dto, null);
        return this.produtoToReadProdutoDto(produtoRepository.save(product));
    }

    public ProdutoEntity createUpdate(ProdutoEntityDto dto, Long id) {
        this.verifyCodigoBarraIsRegistered(dto.codigoBarras(), id);

        CategoriaEntity categoria = categoriaService.getOrCreateCategoria(dto.categoria());

        ProdutoEntity product = new ProdutoEntity(dto);
        product.setCategoria(categoria);

        return product;
    }

    @Transactional
    public ReadProdutoDto update(Long id, ProdutoEntityDto dto) {
        Long idExistentProduct = this.findById(id).getId();

        ProdutoEntity product = this.createUpdate(dto, id);
        product.setId(idExistentProduct);

        return this.produtoToReadProdutoDto(produtoRepository.save(product));
    }

    public ReadProdutoDto findReadProdutoDtoById(Long id){
        return this.produtoToReadProdutoDto(this.findById(id));
    }

    public ProdutoEntity findById(Long id) {
        return produtoRepository.findById(id).orElseThrow(() -> new MessageException("MENSAGEM.PRODUTO.NAO-ENCONTRADO"));
    }

    public ReadProdutoDto produtoToReadProdutoDto(ProdutoEntity produto){
        return  new ReadProdutoDto(
                produto.getDescricao(),
                produto.getCodigoBarras(),
                produto.getPrecoVenda()
        );
    }

    public void verifyCodigoBarraIsRegistered(String codigoBarra, Long id) {
        Optional<ProdutoEntity> produtoOptional;

        if (id != null) {
            produtoOptional = produtoRepository.findByCodigoBarrasAndIdNot(codigoBarra, id);
        } else {
            produtoOptional = produtoRepository.findByCodigoBarras(codigoBarra);
        }

        if (produtoOptional.isPresent()) {
            throw new MessageException("MENSAGEM.PRODUTO.CODIGO-BARRA-JA-CADASTRADO");
        }
    }

}
