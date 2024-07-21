package com.example.ecommerce.produto;

import com.example.ecommerce.produto.dto.ProdutoEntityDto;
import com.example.ecommerce.produto.dto.ReadProdutoDto;
import com.example.ecommerce.security.config.AdminUserConfig;
import com.example.ecommerce.shared.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/produto")
@RequiredArgsConstructor
public class ProdutoController {

    private static final Logger logger = LoggerFactory.getLogger(AdminUserConfig.class);

    private final ProdutoService produtoService;

    @GetMapping()
    public PaginatedResponse<ReadProdutoDto> getProdutosPaginated(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir
    ) {

        logger.warn("Received request with page={}, size={}, sortBy={}, sortDir={}", page, size, sortBy, sortDir);
        return produtoService.findPaginated(page, size, sortBy, sortDir);
    }

    @GetMapping(value = "/{id}")
    public ReadProdutoDto getById(@PathVariable("id") Long id) {
        return produtoService.findReadProdutoDtoById(id);
    }

    @PostMapping()
    public ReadProdutoDto createProduto(@RequestBody ProdutoEntityDto dto) {
        return produtoService.create(dto);
    }

    @PatchMapping("/{id}")
    public ReadProdutoDto updateProduto(@PathVariable("id") Long id, @RequestBody ProdutoEntityDto dto) {
        logger.warn("Received request with id={}", id);
        return produtoService.update(id, dto);
    }
}
