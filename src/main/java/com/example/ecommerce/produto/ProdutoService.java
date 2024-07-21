package com.example.ecommerce.produto;

import com.example.ecommerce.produto.dto.ProdutoEntityDto;
import com.example.ecommerce.produto.dto.ReadProdutoDto;
import com.example.ecommerce.shared.PaginatedResponse;
import com.example.ecommerce.shared.exception.MessageException;
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

    public ReadProdutoDto create(ProdutoEntityDto dto) {
        ProdutoEntity product = new ProdutoEntity(dto);
        this.verifyCodigoBarraIsRegistered(dto.codigoBarras(), null);

        try {
            Long id = produtoRepository.save(product).getId();
            return this.findReadProdutoDtoById(id);
        }catch (Exception ex){
            throw new MessageException("MENSAGEM.PRODUTO.ERRO-CADASTRAR-PRODUTO");
        }
    }

    public ReadProdutoDto update(Long id, ProdutoEntityDto dto) {
        ProdutoEntity existentProduct = this.findById(id);

        this.verifyCodigoBarraIsRegistered(dto.codigoBarras(), id);

        ProdutoEntity product = new ProdutoEntity(dto);
        product.setId(existentProduct.getId());

        produtoRepository.save(product);

        return this.findReadProdutoDtoById(existentProduct.getId());
    }

    public ReadProdutoDto findReadProdutoDtoById(Long id){
        return this.produtoToReadProdutoDto(this.findById(id));
    }

    public ProdutoEntity findById(Long id) {
        return produtoRepository.findById(id).orElseThrow(() -> new MessageException("MENSAGEM.PRODUTO.PRODUTO-NAO-ENCONTRADO"));
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
